package plus.easydo.bot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.OneBotWcfConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.service.OneBotApiService;
import plus.easydo.bot.util.OneBotUtils;

/**
 * @author yuzhanfeng
 * @Date 2024-03-31
 * @Description oneBot协议websocket请求api实现
 */
@Service("wcf_http_one_bot_api")
public class OneBotApiWcfHttpServiceImpl implements OneBotApiService {


    private static String getRequest(String botNumber, String path) {
        BotInfo bot = OneBotUtils.getBotInfo(botNumber);
        return HttpRequest.get(bot.getBotUrl() + "/" + path)
                .execute().body();
    }

    private static String postRequest(String botNumber, String path, JSONObject body) {
        BotInfo bot = OneBotUtils.getBotInfo(botNumber);
        return HttpRequest.post(bot.getBotUrl() + "/" + path)
                .body(body.toStringPretty())
                .execute().body();
    }


    @Override
    public String getLoginInfo(String botNumber) {
        String res = getRequest(botNumber, OneBotWcfConstants.USERINFO);
        return JSONUtil.parseObj(res).getJSONObject("data").toStringPretty();
    }

    @Override
    public String getGroupList(String botNumber) {
        String res = getRequest(botNumber, OneBotWcfConstants.CONTACTS);
        JSONArray contacts = JSONUtil.parseObj(res).getJSONArray("data");
        JSONArray array = JSONUtil.createArray();
        for (Object obj : contacts) {
            JSONObject js = JSONUtil.parseObj(obj);
            String wxid = js.getStr("wxid");
            if (StrUtil.endWith(wxid, "@chatroom")) {
                array.add(obj);
            }
        }
        return array.toStringPretty();
    }

    @Override
    public void sendMessage(String botNumber, String message) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void sendPrivateMessage(String botNumber, String userId, String message) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void sendGroupMessage(String botNumber, String groupId, String message) {
        JSONObject body = JSONUtil.createObj();
        body.set("aters", "");
        body.set("receiver", "receiver");
        body.set("msg", "message");
        postRequest(botNumber, "/text", body);
    }

    @Override
    public void sendGroupFile(String botNumber, String groupId, String filePath) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void deleteMsg(String botNumber, String messageId) {
        getRequest(botNumber, "revoke-msg?id=" + messageId);
    }

    @Override
    public void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupAddRequest(String botNumber, String flag, String type, boolean approve, String remark) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupAdmin(String botNumber, String groupId, String userId, boolean enable) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupLeave(String botNumber, String groupId, boolean isDismiss) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupCard(String botNumber, String groupId, String userId, String card) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setGroupName(String botNumber, String groupId, String groupName) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    public void setFriendAddRequest(String botNumber, String flag, boolean approve, String remark) {
        throw new BaseException("暂不支持的api");
    }
}
