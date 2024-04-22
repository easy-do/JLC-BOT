package plus.easydo.bot.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.service.OneBotApiService;
import plus.easydo.bot.util.OneBotUtils;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-31
 * @Description oneBot协议wcf-client请求api实现
 */
@Slf4j
@Service("http_one_bot_api")
public class OneBotApiHttpServiceImpl implements OneBotApiService {

    private static String getRequest(String botNumber, String path) {
        BotInfo bot = OneBotUtils.getBotInfo(botNumber);
        return HttpRequest.get(bot.getBotUrl() + "/" + path)
                .header(OneBotConstants.HEADER_AUTHORIZATION, OneBotConstants.HEADER_AUTHORIZATION_VALUE_PRE + bot.getBotSecret())
                .execute().body();
    }

    private static String postRequest(String botNumber, String path, JSONObject body) {
        BotInfo bot = OneBotUtils.getBotInfo(botNumber);
        return HttpRequest.post(bot.getBotUrl() + "/" + path)
                .header(OneBotConstants.HEADER_AUTHORIZATION, OneBotConstants.HEADER_AUTHORIZATION_VALUE_PRE + bot.getBotSecret())
                .body(body.toStringPretty())
                .execute().body();
    }

    @Override
    public String getLoginInfo(String botNumber) {
        return getRequest(botNumber, OneBotConstants.GET_LOGIN_INFO);
    }

    @Override
    public String getGroupList(String botNumber) {
        return getRequest(botNumber, OneBotConstants.GET_GROUP_LIST);
    }

    @Override
    public void sendMessage(String botNumber, String message) {
        if (JSONUtil.isTypeJSON(message)) {
            try {
                JSONObject jsonMessage = JSONUtil.parseObj(message);
                postRequest(botNumber, OneBotConstants.SEND_MSG, jsonMessage);
            }catch (Exception e) {
                //do nothing
                log.error("发送消息失败", e);
            }
        }
    }

    @Override
    public void sendPrivateMessage(String botNumber, String userId, String message) {
        JSON jsonMessage = null;
        if (JSONUtil.isTypeJSON(message)) {
            try {
                jsonMessage = JSONUtil.parse(message);
            }catch (Exception e) {
                //do nothing
            }
        }
        JSONObject body = JSONUtil.createObj();
        body.set("user_id", userId);
        body.set("message", Objects.nonNull(jsonMessage)?jsonMessage:message);
        body.set("auto_escape", false);
        postRequest(botNumber, OneBotConstants.SEND_PRIVATE_MSG, body);
    }

    @Override
    public void sendGroupMessage(String botNumber, String groupId, String message) {
        JSON jsonMessage = null;
        if (JSONUtil.isTypeJSON(message)) {
            try {
                jsonMessage = JSONUtil.parse(message);
            }catch (Exception e) {
                //do nothing
            }
        }
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set("message", Objects.nonNull(jsonMessage)?jsonMessage:message);
        body.set("auto_escape", false);
        postRequest(botNumber, OneBotConstants.SEND_GROUP_MSG, body);
    }

    @Override
    public void sendGroupFile(String botNumber, String groupId, String filePath) {
        sendGroupMessage(botNumber, groupId, "[CQ=file,url={" + filePath + "}]");
    }

    @Override
    public void deleteMsg(String botNumber, String messageId) {
        JSONObject body = JSONUtil.createObj();
        body.set("message_id", messageId);
        postRequest(botNumber, OneBotConstants.DELETE_MSG, body);
    }

    @Override
    public void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set("user_id", userId);
        body.set("duration", duration * 60);
        postRequest(botNumber, OneBotConstants.SET_GROUP_BAN, body);
    }

    @Override
    public void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set("enable", enable);
        postRequest(botNumber, OneBotConstants.SET_GROUP_WHOLE_BAN, body);
    }

    @Override
    public void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set("user_id", userId);
        body.set("reject_add_request", rejectAddRequest);
        postRequest(botNumber, OneBotConstants.SET_GROUP_KICK, body);
    }
}
