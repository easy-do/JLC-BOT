package plus.easydo.bot.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yomahub.liteflow.context.ContextBean;
import org.springframework.stereotype.Service;
import oshi.util.FileUtil;
import plus.easydo.bot.lowcode.context.ContextBeanDesc;
import plus.easydo.bot.lowcode.context.ContextBeanMethodDesc;
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
@ContextBean("botApi")
@ContextBeanDesc("机器人API")
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
    @ContextBeanMethodDesc("获取登录信息")
    public String getLoginInfo(String botNumber) {
        String res = JSONUtil.toJsonStr(OneBotUtils.getBotInfo(botNumber));
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", res));
        return res;
    }

    @Override
    @ContextBeanMethodDesc("获取群组列表")
    public String getGroupList(String botNumber) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "获取群组列表"));
        return "[]";
    }

    @Override
    @ContextBeanMethodDesc("发送群消息")
    public void sendGroupMessage(String botNumber, String groupId, String message) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "群消息:" + message));

    }

    @Override
    @ContextBeanMethodDesc("发送群文件")
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
    @ContextBeanMethodDesc("撤回消息")
    public void deleteMsg(String botNumber, String messageId) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "撤回消息[" + messageId + "]"));
    }

    @Override
    @ContextBeanMethodDesc("禁言群组指定成员 duration:时间(分钟) 0代表解除禁言")
    public void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "禁言群组[" + groupId + "]的用户[" + userId + "]" + duration + "毫秒"));
    }

    @Override
    @ContextBeanMethodDesc("设置群组全体禁言 duration:时间(分钟) 0代表解除禁言")
    public void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", enable ? "开启群组全体禁言" : "关闭群组全体禁言"));
    }

    @Override
    @ContextBeanMethodDesc("群组踢人 rejectAddRequest:是否加入黑名单")
    public void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        String rejectAddRequestText = rejectAddRequest ? "并拉黑" : "不拉黑";
        SandboxWebsocketHandler.sendMessage(buildSandboxMessage("text", "将[" + groupId + "]用户[" + userId + "]移出群组" + rejectAddRequestText));

    }
}
