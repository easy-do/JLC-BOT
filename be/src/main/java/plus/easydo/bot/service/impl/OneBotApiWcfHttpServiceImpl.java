package plus.easydo.bot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yomahub.liteflow.context.ContextBean;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.OneBotWcfConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.lowcode.context.ContextBeanDesc;
import plus.easydo.bot.lowcode.context.ContextBeanMethodDesc;
import plus.easydo.bot.service.OneBotApiService;
import plus.easydo.bot.util.OneBotUtils;

/**
 * @author yuzhanfeng
 * @Date 2024-03-31
 * @Description oneBot协议websocket请求api实现
 */
@Service("wcf_http_one_bot_api")
@ContextBean("botApi")
@ContextBeanDesc("机器人API")
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
    @ContextBeanMethodDesc("获取登录信息")
    public String getLoginInfo(String botNumber) {
        String res = getRequest(botNumber, OneBotWcfConstants.USERINFO);
        return JSONUtil.parseObj(res).getJSONObject("data").toStringPretty();
    }

    @Override
    @ContextBeanMethodDesc("获取群组列表")
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
    @ContextBeanMethodDesc("发送群消息")
    public void sendGroupMessage(String botNumber, String groupId, String message) {
        JSONObject body = JSONUtil.createObj();
        body.set("aters", "");
        body.set("receiver", "receiver");
        body.set("msg", "message");
        postRequest(botNumber, "/text", body);
    }

    @Override
    @ContextBeanMethodDesc("发送群文件")
    public void sendGroupFile(String botNumber, String groupId, String filePath) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    @ContextBeanMethodDesc("撤回消息")
    public void deleteMsg(String botNumber, String messageId) {
        getRequest(botNumber, "revoke-msg?id=" + messageId);
    }

    @Override
    @ContextBeanMethodDesc("禁言群组指定成员 duration:时间(分钟) 0代表解除禁言")
    public void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    @ContextBeanMethodDesc("设置群组全体禁言 duration:时间(分钟) 0代表解除禁言")
    public void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        throw new BaseException("暂不支持的api");
    }

    @Override
    @ContextBeanMethodDesc("群组踢人 rejectAddRequest:是否加入黑名单")
    public void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        throw new BaseException("暂不支持的api");
    }
}
