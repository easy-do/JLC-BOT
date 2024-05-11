package plus.easydo.bot.lowcode.context;

import com.yomahub.liteflow.context.ContextBean;
import plus.easydo.bot.util.OneBotApiUtils;

/**
 * @author yuzhanfeng
 * @Date 2024/4/16
 * @Description 机器人api上下文
 */
@ContextBean("botApi")
@ContextBeanDesc("机器人API")
public class BotApiContext {

    private String botNumber;


    public BotApiContext(String botNumber) {
        this.botNumber = botNumber;
    }


    @ContextBeanMethodDesc("获取登录信息")
    private String getLoginInfo() {
        return OneBotApiUtils.getLoginInfo(botNumber);
    }


    @ContextBeanMethodDesc("获取群组列表")
    public String getGroupList() {
        return OneBotApiUtils.getGroupList(botNumber);
    }

    @ContextBeanMethodDesc("发送消息")
    public void sendMessage(String message) {
        OneBotApiUtils.sendMessage(botNumber, message);
    }

    @ContextBeanMethodDesc("发送私聊消息")
    public void sendPrivateMessage(String userId, String message) {
        OneBotApiUtils.sendPrivateMessage(botNumber, userId, message);
    }

    @ContextBeanMethodDesc("发送群消息")
    public void sendGroupMessage(String groupId, String message) {
        OneBotApiUtils.sendGroupMessage(botNumber, groupId, message);
    }


    @ContextBeanMethodDesc("发送群文件")
    public void sendGroupFile(String groupId, String filePath) {
        OneBotApiUtils.sendGroupFile(botNumber, groupId, filePath);
    }


    @ContextBeanMethodDesc("撤回消息")
    public void deleteMsg(String messageId) {
        OneBotApiUtils.deleteMsg(botNumber, messageId);
    }


    @ContextBeanMethodDesc("禁言群组指定成员 duration:时间(分钟) 0代表解除禁言")
    public void setGroupBan(String groupId, String userId, Long duration) {
        OneBotApiUtils.setGroupBan(botNumber, groupId, userId, duration);
    }


    @ContextBeanMethodDesc("设置群组全体禁言 duration:时间(分钟) 0代表解除禁言")
    public void setGroupWholeBan(String groupId, boolean enable) {
        OneBotApiUtils.setGroupWholeBan(botNumber, groupId, enable);
    }


    @ContextBeanMethodDesc("群组踢人 rejectAddRequest:是否加入黑名单")
    public void setGroupKick(String groupId, String userId, boolean rejectAddRequest) {
        OneBotApiUtils.setGroupKick(botNumber, groupId, userId, rejectAddRequest);
    }

    @ContextBeanMethodDesc("退群 isDismiss 是否解散，如果登录号是群主，则仅在此项为 true 时能够解散 ")
    public void setGroupLeave(String groupId, boolean isDismiss) {
        OneBotApiUtils.setGroupLeave(botNumber, groupId, isDismiss);
    }

    @ContextBeanMethodDesc("处理加群请求 approve:是否同意")
    public void setGroupAddRequest(String flag, String type, boolean approve, String remark) {
        OneBotApiUtils.setGroupAddRequest(botNumber, flag, type, approve, remark);
    }

    @ContextBeanMethodDesc("设置管理员 enable:设置或取消")
    public void setGroupAdmin(String groupId, String userId, boolean enable) {
        OneBotApiUtils.setGroupAdmin(botNumber, groupId, userId, enable);
    }

    @ContextBeanMethodDesc("设置群名片 card:群名片内容，不填或空字符串表示删除群名片")
    public void setGroupCard(String groupId, String userId, String card) {
        OneBotApiUtils.setGroupCard(botNumber, groupId, userId, card);
    }

    @ContextBeanMethodDesc("设置群名称")
    public void setGroupName(String groupId, String groupName) {
        OneBotApiUtils.setGroupName(botNumber, groupId, groupName);
    }

    @ContextBeanMethodDesc("处理好友请求")
    public void setFriendAddRequest(String flag, boolean approve, String remark) {
        OneBotApiUtils.setFriendAddRequest(botNumber, flag, approve, remark);
    }

    @ContextBeanMethodDesc("获取消息详情")
    public String getMsg(String messageId) {
        return OneBotApiUtils.getMsg(botNumber, messageId);
    }

    @ContextBeanMethodDesc("获取合并转发消息")
    public String getForwardMsg(String messageId) {
        return OneBotApiUtils.getForwardMsg(botNumber, messageId);
    }

    @ContextBeanMethodDesc("获取群成员列表")
    public String getGroupMemberList(String groupId) {
        return OneBotApiUtils.getGroupMemberList(botNumber, groupId);
    }

    @ContextBeanMethodDesc("获取群成员信息 no_cache:是否不使用缓存")
    public String getGroupMemberInfo(String groupId, String userId, boolean noCache) {
        return OneBotApiUtils.getGroupMemberInfo(botNumber, groupId, userId , noCache);
    }

    @ContextBeanMethodDesc("获取群消息历史记录 message_seq: 起始消息序号, 可通过 get_msg 获得")
    public String getGroupMsgHistory(String groupId, Integer messageSeq ) {
        return OneBotApiUtils.getGroupMsgHistory(botNumber, groupId, messageSeq);
    }

    @ContextBeanMethodDesc("发送合并转发消息")
    public void sendForwardMsg(String data) {
        OneBotApiUtils.sendForwardMsg(botNumber, data);
    }

    @ContextBeanMethodDesc("发送合并转发消息 ( 群聊 )")
    public void sendGroupForwardMsg(String groupId, String messages) {
        OneBotApiUtils.sendGroupForwardMsg(botNumber, groupId, messages);
    }

    @ContextBeanMethodDesc("发送合并转发消息 ( 好友 )")
    public void sendPrivateForwardMsg(String userId, String messages) {
        OneBotApiUtils.sendPrivateForwardMsg(botNumber, userId, messages);
    }

}
