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


    @ContextBeanMethodDesc("发送群消息")
    public void sendGroupMessage(String groupId, String message) {
        OneBotApiUtils.sendGroupMessage(botNumber, groupId, message);
    }


    @ContextBeanMethodDesc("发送群文件")
    public void sendGroupFile( String groupId, String filePath) {
        OneBotApiUtils.sendGroupFile(botNumber, groupId, filePath);
    }


    @ContextBeanMethodDesc("撤回消息")
    public void deleteMsg( String messageId) {
        OneBotApiUtils.deleteMsg(botNumber, messageId);
    }


    @ContextBeanMethodDesc("禁言群组指定成员 duration:时间(分钟) 0代表解除禁言")
    public void setGroupBan( String groupId, String userId, Long duration) {
        OneBotApiUtils.setGroupBan(botNumber, groupId, userId, duration);
    }


    @ContextBeanMethodDesc("设置群组全体禁言 duration:时间(分钟) 0代表解除禁言")
    public void setGroupWholeBan( String groupId, boolean enable) {
        OneBotApiUtils.setGroupWholeBan(botNumber, groupId, enable);
    }


    @ContextBeanMethodDesc("群组踢人 rejectAddRequest:是否加入黑名单")
    public void setGroupKick( String groupId, String userId, boolean rejectAddRequest) {
        OneBotApiUtils.setGroupKick(botNumber, groupId, userId, rejectAddRequest);
    }

}
