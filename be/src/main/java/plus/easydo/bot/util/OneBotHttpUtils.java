package plus.easydo.bot.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.manager.CacheManager;

import java.util.Objects;


/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description bot工具
 */
public class OneBotHttpUtils {


    private OneBotHttpUtils() {
    }

    private static String getRequest(String botNumber, String path) {
        BotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("没有找到机器人[" + botNumber + "]信息");
        }
        return HttpRequest.get(bot.getBotUrl() + "/" + path)
                .header(OneBotConstants.HEADER_AUTHORIZATION, OneBotConstants.HEADER_AUTHORIZATION_VALUE_PRE + bot.getBotSecret())
                .execute().body();
    }

    private static String postRequest(String botNumber, String path, JSONObject body) {
        BotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("没有找到机器人[" + botNumber + "]信息");
        }
        return HttpRequest.post(bot.getBotUrl() + "/" + path)
                .header(OneBotConstants.HEADER_AUTHORIZATION, OneBotConstants.HEADER_AUTHORIZATION_VALUE_PRE + bot.getBotSecret())
                .body(body.toStringPretty())
                .execute().body();
    }

    public static String getLoginInfo(String botNumber) {
        return getRequest(botNumber,OneBotConstants.GET_LOGIN_INFO);
    }

    public static String getGroupList(String botNumber) {
        return getRequest(botNumber,OneBotConstants.GET_GROUP_LIST);
    }

    public static void sendGroupMessage(String botNumber, String groupId, String message, boolean autoEscape) {
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("message", message);
        body.set("auto_escape", autoEscape);
        postRequest(botNumber, OneBotConstants.SEND_GROUP_MSG, body);
    }

    public static void deleteMsg(String botNumber, String messageId) {
        JSONObject body = JSONUtil.createObj();
        body.set("message_id", messageId);
        postRequest(botNumber, OneBotConstants.DELETE_MSG, body);
    }

    public static void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("user_id", userId);
        body.set("duration", duration * 60);
        postRequest(botNumber, OneBotConstants.SET_GROUP_BAN, body);
    }

    public static void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("enable", enable);
        postRequest(botNumber, OneBotConstants.SET_GROUP_WHOLE_BAN, body);
    }

    public static void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("user_id", userId);
        body.set("reject_add_request", rejectAddRequest);
        postRequest(botNumber, OneBotConstants.SET_GROUP_KICK, body);
    }
}
