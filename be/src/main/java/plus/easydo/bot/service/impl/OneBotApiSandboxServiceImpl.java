package plus.easydo.bot.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import plus.easydo.bot.sandbox.SandboxMessage;
import plus.easydo.bot.sandbox.SandboxWebsocketHandler;
import plus.easydo.bot.service.OneBotApiService;
import plus.easydo.bot.util.OneBotUtils;

/**
 * @author yuzhanfeng
 * @Date 2024-03-31
 * @Description oneBot协议沙盒请求api实现
 */
@Service("sandboxOneBotApi")
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
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "沙箱群组为空"));
        return "[]";
    }

    @Override
    public void sendGroupMessage(String botNumber, String groupId, String message) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "群消息:" + message));

    }

    @Override
    public void sendGroupFile(String botNumber, String groupId, String filePath) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("file", filePath));
    }

    @Override
    public void deleteMsg(String botNumber, String messageId) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "撤回消息[" + messageId + "]"));
    }

    @Override
    public void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "禁言群组[" + groupId + "]用户[" + userId + "]" + duration + "毫秒"));
    }

    @Override
    public void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", enable ? "开启全体禁言" : "关闭全体禁言"));
    }

    @Override
    public void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        String rejectAddRequestText = rejectAddRequest ? "并拉黑" : "不拉黑";
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "移出群组[" + groupId + "]用户[" + userId + "]" + rejectAddRequestText));

    }
}
