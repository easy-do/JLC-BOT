package plus.easydo.bot.util;

import cn.hutool.core.text.CharSequenceUtil;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.enums.onebot.OneBotProtocolsTypeEnum;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.manager.CacheManager;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-29
 * @Description OneBotApi工具
 */
public class OneBotApiUtils {

    private OneBotApiUtils() {
    }

    public static BotInfo getBotInfo(String botNumber){
        BotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if(Objects.isNull(bot)){
            throw new BaseException("机器人["+botNumber+"]不存在");
        }
        return bot;
    }

    /**
     * 获取登录信息
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static String getLoginInfo(String botNumber) {
        BotInfo bot = getBotInfo(botNumber);
        String invokeType = bot.getInvokeType();
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.HTTP.getType())){
            return OneBotHttpUtils.getLoginInfo(botNumber);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WEBSOCKET.getType())){
            return OneBotWebsocketUtils.getLoginInfo(botNumber);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WCF_HTTP.getType())){
            return OneBotWcfHttpUtils.getLoginInfo(botNumber);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WCF_CLIENT.getType())){
            return OneBotWcfClientUtils.getLoginInfo(botNumber);
        }
        return "";
    }

    /**
     * 获取群列表
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static String getGroupList(String botNumber) {
        BotInfo bot = getBotInfo(botNumber);
        String invokeType = bot.getInvokeType();
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.HTTP.getType())){
            return OneBotHttpUtils.getGroupList(botNumber);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WEBSOCKET.getType())){
            return OneBotWebsocketUtils.getGroupList(botNumber);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WCF_HTTP.getType())){
            return OneBotWcfHttpUtils.getGroupList(botNumber);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WCF_CLIENT.getType())){
            return OneBotWcfClientUtils.getGroupList(botNumber);
        }
        return "";
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
        BotInfo bot = getBotInfo(botNumber);
        String invokeType = bot.getInvokeType();
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.HTTP.getType())){
            OneBotHttpUtils.sendGroupMessage(botNumber,groupId,message,autoEscape);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WEBSOCKET.getType())){
            OneBotWebsocketUtils.sendGroupMessage(botNumber,groupId,message,autoEscape);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WCF_HTTP.getType())){
            OneBotWcfHttpUtils.sendMessage(botNumber,groupId, message);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WCF_CLIENT.getType())){
            OneBotWcfClientUtils.sendMessage(botNumber,groupId, message);
        }

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
        BotInfo bot = getBotInfo(botNumber);
        String invokeType = bot.getInvokeType();
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.HTTP.getType())){
            OneBotHttpUtils.deleteMsg(botNumber,messageId);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WEBSOCKET.getType())){
            OneBotWebsocketUtils.deleteMsg(botNumber,messageId);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WCF_HTTP.getType())){
            OneBotWcfHttpUtils.deleteMsg(botNumber,messageId);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WCF_CLIENT.getType())){
            OneBotWcfClientUtils.deleteMsg(botNumber,messageId);
        }
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
        BotInfo bot = getBotInfo(botNumber);
        String invokeType = bot.getInvokeType();
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.HTTP.getType())){
            OneBotHttpUtils.setGroupBan(botNumber,groupId,userId,duration);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WEBSOCKET.getType())){
            OneBotWebsocketUtils.setGroupBan(botNumber,groupId,userId,duration);
        }
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
        BotInfo bot = getBotInfo(botNumber);
        String invokeType = bot.getInvokeType();
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.HTTP.getType())){
            OneBotHttpUtils.setGroupWholeBan(botNumber,groupId,enable);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WEBSOCKET.getType())){
            OneBotWebsocketUtils.setGroupWholeBan(botNumber,groupId,enable);
        }
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
        BotInfo bot = getBotInfo(botNumber);
        String invokeType = bot.getInvokeType();
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.HTTP.getType())){
            OneBotHttpUtils.setGroupKick(botNumber,groupId,userId,rejectAddRequest);
        }
        if(CharSequenceUtil.equals(invokeType, OneBotProtocolsTypeEnum.WEBSOCKET.getType())){
            OneBotWebsocketUtils.setGroupKick(botNumber,groupId,userId,rejectAddRequest);
        }
    }
}
