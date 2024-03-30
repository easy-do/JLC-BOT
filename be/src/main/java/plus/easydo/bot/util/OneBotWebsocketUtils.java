package plus.easydo.bot.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.websocket.OneBotWebSocketHandler;

import java.util.Objects;


/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description bot工具
 */
public class OneBotWebsocketUtils {


    private OneBotWebsocketUtils() {
    }

    private static String sendSocketAwait(String botNumber, String action, JSONObject params){
        BotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("没有找到机器人[" + botNumber + "]信息");
        }
        JSONObject message = JSONUtil.createObj();
        String messageId = UUID.fastUUID().toString(true);
        message.set("echo", messageId);
        message.set("action", action);
        if(Objects.nonNull(params)){
            message.set("params", params);
        }
        return OneBotWebSocketHandler.sendMessageAwaitRes(botNumber,messageId,message.toStringPretty());
    }

    private static void sendSocket(String botNumber, String action, JSONObject params){
        BotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("没有找到机器人[" + botNumber + "]信息");
        }
        JSONObject message = JSONUtil.createObj();
        String messageId = UUID.fastUUID().toString(true);
        message.set("echo", messageId);
        message.set("action", action);
        if(Objects.nonNull(params)){
            message.set("params", params);
        }
        OneBotWebSocketHandler.sendMessage(botNumber,message.toStringPretty());
    }


    /**
     * 获取登录信息
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static String getLoginInfo(String botNumber) {
        return sendSocketAwait(botNumber, OneBotConstants.GET_LOGIN_INFO,null);
    }

    /**
     * 获取群列表
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static String getGroupList(String botNumber) {
        JSONObject message = JSONUtil.createObj();
        String messageId = UUID.fastUUID().toString(true);
        message.set("echo", messageId);
        message.set("action", OneBotConstants.GET_GROUP_LIST);
       return OneBotWebSocketHandler.sendMessageAwaitRes(botNumber,messageId,message.toStringPretty());
    }

    /**
     * 发送群消息
     *
     * @param groupId    groupId
     * @param message    message
     * @param autoEscape 是否纯文本
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static void sendGroupMessage(String botNumber, String groupId, String message, boolean autoEscape) {
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("message", message);
        body.set("auto_escape", autoEscape);
        sendSocket(botNumber, OneBotConstants.SEND_GROUP_MSG, body);
    }

    /**
     * 撤回消息
     *
     * @param messageId messageId
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static void deleteMsg(String botNumber, String messageId) {
        JSONObject body = JSONUtil.createObj();
        body.set("message_id", messageId);
        sendSocket(botNumber, OneBotConstants.DELETE_MSG, body);
    }

    /**
     * 设置单个禁言
     *
     * @param groupId  groupId
     * @param userId   userId
     * @param duration duration
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/21
     */
    public static void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        //group_id	int64	-	群号
        //user_id	int64	-	要禁言的 QQ 号
        //duration	uint32	30 * 60	禁言时长, 单位秒, 0 表示取消禁言
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("user_id", userId);
        body.set("duration", duration * 60);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_BAN, body);
    }

    /**
     * 设置全体禁言
     *
     * @param groupId groupId
     * @param enable  enable
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/21
     */
    public static void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        //group_id	int64	-	群号
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("enable", enable);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_WHOLE_BAN, body);
    }

    /**
     * 群组踢人
     *
     * @param groupId          群号
     * @param userId           要踢的 QQ 号
     * @param rejectAddRequest 拒绝此人的加群请求
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/21
     */
    public static void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("user_id", userId);
        body.set("reject_add_request", rejectAddRequest);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_KICK, body);
    }
}
