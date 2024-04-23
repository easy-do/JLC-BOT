package plus.easydo.bot.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import oshi.util.FileUtil;
import plus.easydo.bot.sandbox.SandboxMessage;
import plus.easydo.bot.sandbox.SandboxWebsocketHandler;
import plus.easydo.bot.service.OneBotApiService;
import plus.easydo.bot.util.OneBotUtils;

/**
 * @author yuzhanfeng
 * @Date 2024-03-31
 * @Description oneBot协议沙盒请求api实现
 */
@Service("sandbox_one_bot_api")
public class OneBotApiSandboxServiceImpl implements OneBotApiService {

    private SandboxMessage buildSandboxMessage(String type, String message) {
        return SandboxMessage.builder()
                .messageId(UUID.fastUUID().toString(true))
                .isSelf(false)
                .type(type)
                .message(message)
                .time(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.NORM_DATETIME_PATTERN))
                .build();
    }

    @Override
    public String getLoginInfo(String botNumber) {
        String res = JSONUtil.toJsonStr(OneBotUtils.getBotInfo(botNumber));
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", res));
        return res;
    }

    @Override
    public String getGroupList(String botNumber) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "获取群组列表"));
        return "[]";
    }

    @Override
    public void sendMessage(String botNumber, String message) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "消息:" + message));
    }

    @Override
    public void sendPrivateMessage(String botNumber, String userId, String message) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "私聊消息:" + message));
    }

    @Override
    public void sendGroupMessage(String botNumber, String groupId, String message) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "群消息:" + message));
    }

    @Override
    public void sendGroupFile(String botNumber, String groupId, String filePath) {
        String type = "file";
        if (StrUtil.endWith(filePath, ".jpg")) {
            type = "image";
            if (!StrUtil.startWith(filePath, "http")) {
                byte[] bytes = FileUtil.readAllBytes(filePath);
                filePath = ("data:image/png;base64," + Base64.encode(bytes));
            }
        }
        if (StrUtil.endWith(filePath, ".mp4")) {
            type = "video";
            byte[] bytes = FileUtil.readAllBytes(filePath);
            filePath = ("data:video/mp4;base64," + Base64.encode(bytes));
        }
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage(type, filePath));
    }

    @Override
    public void deleteMsg(String botNumber, String messageId) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "撤回消息[" + messageId + "]"));
    }

    @Override
    public void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "禁言群组[" + groupId + "]的用户[" + userId + "]" + duration + "毫秒"));
    }

    @Override
    public void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", enable ? "开启群组全体禁言" : "关闭群组全体禁言"));
    }

    @Override
    public void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        String rejectAddRequestText = rejectAddRequest ? "并拉黑" : "不拉黑";
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "将[" + groupId + "]用户[" + userId + "]移出群组" + rejectAddRequestText));

    }

    @Override
    public void setGroupAddRequest(String botNumber, String flag, String type, boolean approve, String remark) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "处理群组请求"+"flag:"+flag+",type:"+type+",approve"+approve+",remark:"+remark));
    }

    @Override
    public void setGroupAdmin(String botNumber, String groupId, String userId, boolean enable) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "设置群管理员"+"groupId:"+groupId+",userId:"+userId+",enable"+enable));

    }

    @Override
    public void setGroupLeave(String botNumber, String groupId, boolean isDismiss) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "退出群组"+"groupId:"+groupId+",isDismiss:"+isDismiss));

    }

    @Override
    public void setGroupCard(String botNumber, String groupId, String userId, String card) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "设置群名片"+"groupId:"+groupId+",userId:"+userId+",card"+card));

    }

    @Override
    public void setGroupName(String botNumber, String groupId, String groupName) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "设置群名"+"groupId:"+groupId+",groupName:"+groupName));

    }

    @Override
    public void setFriendAddRequest(String botNumber, String flag, boolean approve, String remark) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "处理好友请求"+"flag:"+flag+",approve"+approve+",remark:"+remark));
    }
}
