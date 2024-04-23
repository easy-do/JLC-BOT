package plus.easydo.bot.util;


import cn.hutool.extra.spring.SpringUtil;
import plus.easydo.bot.service.BotService;
import plus.easydo.bot.service.OneBotApiService;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-29
 * @Description OneBotApi工具
 */
public class OneBotApiUtils {

    private static BotService botService;


    private OneBotApiUtils() {
    }

    public static OneBotApiService getApiServer(String botNumber) {
        if (Objects.isNull(botService)) {
            botService = SpringUtil.getBean(BotService.class);
        }
        return botService.getApiServer(botNumber);
    }

    /**
     * 获取登录信息
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static String getLoginInfo(String botNumber) {
        return getApiServer(botNumber).getLoginInfo(botNumber);
    }

    /**
     * 获取群列表
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static String getGroupList(String botNumber) {
        return getApiServer(botNumber).getGroupList(botNumber);
    }

    /**
     * 发送消息
     *
     * @param message message
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static void sendMessage(String botNumber, String message) {
        getApiServer(botNumber).sendMessage(botNumber, message);
    }


    /**
     * 发送私聊消息
     *
     * @param userId  userId
     * @param message message
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static void sendPrivateMessage(String botNumber, String userId, String message) {
        getApiServer(botNumber).sendPrivateMessage(botNumber, userId, message);
    }

    /**
     * 发送群消息
     *
     * @param groupId groupId
     * @param message message
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static void sendGroupMessage(String botNumber, String groupId, String message) {
        getApiServer(botNumber).sendGroupMessage(botNumber, groupId, message);
    }

    public static void sendGroupFile(String botNumber, String groupId, String filePath) {
        getApiServer(botNumber).sendGroupFile(botNumber, groupId, filePath);
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
        getApiServer(botNumber).deleteMsg(botNumber, messageId);
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
        getApiServer(botNumber).setGroupBan(botNumber, groupId, userId, duration);
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
        getApiServer(botNumber).setGroupWholeBan(botNumber, groupId, enable);
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
        getApiServer(botNumber).setGroupKick(botNumber, groupId, userId, rejectAddRequest);
    }


    /**
     * 退群
     *
     * @param botNumber  botNumber
     * @param groupId    群号
     * @param is_dismiss 是否解散，如果登录号是群主，则仅在此项为 true 时能够解散
     * @return void
     * @author laoyu
     * @date 2024/4/23
     */
    public static void setGroupLeave(String botNumber, String groupId, boolean is_dismiss) {
        getApiServer(botNumber).setGroupLeave(botNumber, groupId, is_dismiss);
    }

    /**
     * 处理群组请求
     *
     * @param botNumber botNumber
     * @param flag      flag
     * @param type      type
     * @param approve   approve
     * @param remark    remark
     * @author laoyu
     * @date 2024/4/23
     */
    public static void setGroupAddRequest(String botNumber, String flag, String type, boolean approve, String remark) {
        getApiServer(botNumber).setGroupAddRequest(botNumber, flag, type, approve, remark);
    }

    /**
     * 设置管理员
     *
     * @param botNumber botNumber
     * @param groupId   groupId
     * @param userId    userId
     * @param enable    enable
     * @author laoyu
     * @date 2024/4/23
     */
    public static void setGroupAdmin(String botNumber, String groupId, String userId, boolean enable) {
        getApiServer(botNumber).setGroupAdmin(botNumber, groupId, userId, enable);
    }

    /**
     * 设置群名片
     *
     * @param botNumber botNumber
     * @param groupId   groupId
     * @param userId    userId
     * @param card      card
     * @return void
     * @author laoyu
     * @date 2024/4/23
     */
    public static void setGroupCard(String botNumber, String groupId, String userId, String card) {
        getApiServer(botNumber).setGroupCard(botNumber, groupId, userId, card);
    }

    /**
     * 设置群名称
     *
     * @param botNumber botNumber
     * @param groupId   groupId
     * @param groupName groupName
     * @author laoyu
     * @date 2024/4/23
     */
    public static void setGroupName(String botNumber, String groupId, String groupName) {
        getApiServer(botNumber).setGroupName(botNumber, groupId, groupName);
    }


    /**
     * 设置好友添加请求
     *
     * @param botNumber 机器人号码
     * @param flag      标识
     * @param approve   是否同意好友请求
     * @param remark    备注信息
     */
    public static void setFriendAddRequest(String botNumber, String flag, boolean approve, String remark) {
        getApiServer(botNumber).setFriendAddRequest(botNumber, flag, approve, remark);
    }
}
