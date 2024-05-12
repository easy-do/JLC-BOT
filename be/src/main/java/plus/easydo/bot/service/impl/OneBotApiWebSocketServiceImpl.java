package plus.easydo.bot.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.service.OneBotApiService;
import plus.easydo.bot.util.OneBotWebSocketUtils;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-31
 * @Description oneBot协议scf-http请求api实现
 */
@Slf4j
@Service("websocket_one_bot_api")
public class OneBotApiWebSocketServiceImpl implements OneBotApiService {

    private static String sendSocketAwait(String botNumber, String action, JSONObject params) {
        JSONObject message = JSONUtil.createObj();
        String messageId = UUID.fastUUID().toString(true);
        message.set(OneBotConstants.ECHO, messageId);
        message.set(OneBotConstants.ACTION, action);
        if (Objects.nonNull(params)) {
            message.set(OneBotConstants.PARAMS, params);
        }
        return OneBotWebSocketUtils.sendMessageAwaitRes(botNumber, messageId, message.toStringPretty());
    }

    private static void sendSocket(String botNumber, String action, JSONObject params) {
        JSONObject message = JSONUtil.createObj();
        String messageId = UUID.fastUUID().toString(true);
        message.set(OneBotConstants.ECHO, messageId);
        message.set(OneBotConstants.ACTION, action);
        if (Objects.nonNull(params)) {
            message.set(OneBotConstants.PARAMS, params);
        }
        OneBotWebSocketUtils.sendMessage(botNumber, message.toStringPretty());
    }


    @Override
    public String getLoginInfo(String botNumber) {
        return sendSocketAwait(botNumber, OneBotConstants.GET_LOGIN_INFO, null);
    }

    @Override
    public String getGroupList(String botNumber) {
        JSONObject message = JSONUtil.createObj();
        String messageId = UUID.fastUUID().toString(true);
        message.set(OneBotConstants.ECHO, messageId);
        message.set(OneBotConstants.ACTION, OneBotConstants.GET_GROUP_LIST);
        return OneBotWebSocketUtils.sendMessageAwaitRes(botNumber, messageId, message.toStringPretty());
    }

    @Override
    public void sendMessage(String botNumber, String message) {
        if (JSONUtil.isTypeJSON(message)) {
            try {
                JSONObject jsonMessage = JSONUtil.parseObj(message);
                sendSocket(botNumber, OneBotConstants.SEND_MSG, jsonMessage);
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
        body.set(OneBotConstants.USER_ID, userId);
        body.set(OneBotConstants.MESSAGE, Objects.nonNull(jsonMessage)?jsonMessage:message);
        body.set(OneBotConstants.AUTO_ESCAPE, false);
        sendSocket(botNumber, OneBotConstants.SEND_PRIVATE_MSG, body);
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
        body.set(OneBotConstants.MESSAGE, Objects.nonNull(jsonMessage)?jsonMessage:message);
        body.set(OneBotConstants.AUTO_ESCAPE, false);
        sendSocket(botNumber, OneBotConstants.SEND_GROUP_MSG, body);
    }

    @Override
    public void sendGroupFile(String botNumber, String groupId, String filePath) {
        sendGroupMessage(botNumber, groupId, "[CQ=file,url={" + filePath + "}]");
    }

    @Override
    public void deleteMsg(String botNumber, String messageId) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.MESSAGE_ID, messageId);
        sendSocket(botNumber, OneBotConstants.DELETE_MSG, body);
    }

    @Override
    public void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set(OneBotConstants.USER_ID, userId);
        body.set(OneBotConstants.DURATION, duration * 60);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_BAN, body);
    }

    @Override
    public void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set(OneBotConstants.ENABLE, enable);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_WHOLE_BAN, body);
    }

    @Override
    public void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set(OneBotConstants.USER_ID, userId);
        body.set(OneBotConstants.REJECT_ADD_REQUEST, rejectAddRequest);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_KICK, body);
    }

    @Override
    public void setGroupAddRequest(String botNumber, String flag, String type, boolean approve, String remark) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.FLAG, flag);
        body.set(OneBotConstants.TYPE, type);
        body.set(OneBotConstants.APPROVE, approve);
        body.set(OneBotConstants.REASON, remark);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_ADD_REQUEST, body);
    }

    @Override
    public void setGroupAdmin(String botNumber, String groupId, String userId, boolean enable) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set(OneBotConstants.USER_ID, userId);
        body.set(OneBotConstants.ENABLE, enable);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_ADMIN, body);
    }

    @Override
    public void setGroupLeave(String botNumber, String groupId, boolean isDismiss) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set(OneBotConstants.IS_DISMISS, isDismiss);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_LEAVE, body);
    }

    @Override
    public void setGroupCard(String botNumber, String groupId, String userId, String card) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set(OneBotConstants.USER_ID, userId);
        body.set(OneBotConstants.CARD, card);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_CARD, body);
    }

    @Override
    public void setGroupName(String botNumber, String groupId, String groupName) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set(OneBotConstants.GROUP_NAME, groupName);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_NAME, body);
    }

    @Override
    public void setFriendAddRequest(String botNumber, String flag, boolean approve, String remark) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.FLAG, flag);
        body.set(OneBotConstants.APPROVE, approve);
        body.set(OneBotConstants.REASON, remark);
        sendSocket(botNumber, OneBotConstants.SET_FRIEND_ADD_REQUEST, body);

    }

    @Override
    public String getMsg(String botNumber, String messageId) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.MESSAGE_ID, messageId);
        return sendSocketAwait(botNumber, OneBotConstants.GET_MSG, body);
    }

    @Override
    public String getForwardMsg(String botNumber, String messageId) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.MESSAGE_ID, messageId);
        return sendSocketAwait(botNumber, OneBotConstants.GET_FORWARD_MSG, body);
    }

    @Override
    public String getGroupMemberList(String botNumber, String groupId) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        return sendSocketAwait(botNumber, OneBotConstants.GET_GROUP_MEMBER_LIST, body);
    }

    @Override
    public String getGroupMemberInfo(String botNumber, String groupId, String userId, boolean noCache) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set(OneBotConstants.USER_ID, userId);
        body.set(OneBotConstants.NO_CACHE, noCache);
        return sendSocketAwait(botNumber, OneBotConstants.GET_GROUP_MEMBER_INFO, body);
    }

    @Override
    public String getGroupMsgHistory(String botNumber, String groupId, Integer messageSeq) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set(OneBotConstants.MESSAGE_SEQ, messageSeq);
        return sendSocketAwait(botNumber, OneBotConstants.GET_GROUP_MSG_HISTORY, body);
    }

    @Override
    public void sendForwardMsg(String botNumber, String data) {
        JSONObject body = JSONUtil.parseObj(data);
        sendSocket(botNumber, OneBotConstants.SEND_FORWARD_MSG, body);
    }

    @Override
    public void sendGroupForwardMsg(String botNumber, String groupId, String messages) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.GROUP_ID, groupId);
        body.set(OneBotConstants.MESSAGE, JSONUtil.parseObj(messages));
        sendSocket(botNumber, OneBotConstants.SEND_GROUP_FORWARD_MSG, body);
    }

    @Override
    public void sendPrivateForwardMsg(String botNumber, String userId, String messages) {
        JSONObject body = JSONUtil.createObj();
        body.set(OneBotConstants.USER_ID, userId);
        body.set(OneBotConstants.MESSAGE, JSONUtil.parseObj(messages));
        sendSocket(botNumber, OneBotConstants.SEND_PRIVATE_FORWARD_MSG, body);
    }

    @Override
    public String customApi(String botNumber, String action, String data) {
        JSONObject body = JSONUtil.parseObj(data);
        return sendSocketAwait(botNumber, body.getStr(OneBotConstants.ACTION), body);
    }
}
