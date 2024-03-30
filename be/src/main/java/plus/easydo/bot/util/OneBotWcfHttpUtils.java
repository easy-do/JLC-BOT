package plus.easydo.bot.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import plus.easydo.bot.constant.OneBotWcfConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.manager.CacheManager;

import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-29
 * @Description wcf工具
 */
public class OneBotWcfHttpUtils {

    private OneBotWcfHttpUtils() {
    }

    private static String getRequest(String botNumber, String path) {
        BotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("没有找到机器人[" + botNumber + "]信息");
        }
        return HttpRequest.get(bot.getBotUrl() + "/" + path)
                .execute().body();
    }

    private static String postRequest(String botNumber, String path, JSONObject body) {
        BotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("没有找到机器人[" + botNumber + "]信息");
        }
        return HttpRequest.post(bot.getBotUrl() + "/" + path)
                .body(body.toStringPretty())
                .execute().body();
    }

    public static String getLoginInfo(String botNumber) {
        String res = getRequest(botNumber, OneBotWcfConstants.USERINFO);
        return JSONUtil.parseObj(res).getJSONObject("data").toStringPretty();
    }

    public static String getGroupList(String botNumber) {
        String res = getRequest(botNumber, OneBotWcfConstants.CONTACTS);
        JSONArray contacts = JSONUtil.parseObj(res).getJSONArray("data");
        JSONArray array = JSONUtil.createArray();
        for (Object obj: contacts){
            JSONObject js = JSONUtil.parseObj(obj);
            String wxid = js.getStr("wxid");
            if(StrUtil.endWith(wxid,"@chatroom")){
                array.add(obj);
            }
        }
        return array.toStringPretty();
    }

    public static void sendMessage(String botNumber, String receiver, String message) {
        JSONObject body = JSONUtil.createObj();
        body.set("aters","");
        body.set("receiver","receiver");
        body.set("msg","message");
        postRequest(botNumber,"/text",body);
    }

    public static void deleteMsg(String botNumber, String messageId) {
        getRequest(botNumber,"revoke-msg?id="+messageId);
    }
}
