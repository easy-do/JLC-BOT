package plus.easydo.bot.service.impl;

import cn.hutool.json.JSONObject;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.service.BotService;
import plus.easydo.bot.service.SandboxService;
import plus.easydo.bot.websocket.OneBotService;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-30
 * @Description 沙箱服务实现
 */
@Service
@RequiredArgsConstructor
public class SandboxServiceImpl implements SandboxService {

    private static final String SANDBOX_BOT_NUMBER = "jlc-bot-sandbox";
    private static final String SANDBOX = "sandbox";

    private final BotService botService;

    private final OneBotService oneBotService;

    @PostConstruct
    public void initSandBoxBot() {
        BotInfo bot = botService.getByBotNumber(SANDBOX_BOT_NUMBER);
        if (Objects.isNull(bot)) {
            BotInfo botInfo = BotInfo.builder()
                    .botNumber(SANDBOX_BOT_NUMBER)
                    .postType(SANDBOX)
                    .invokeType(SANDBOX)
                    .remark("沙箱机器人")
                    .platform(SANDBOX)
                    .build();
            botService.addBot(botInfo);
        }
    }


    @Override
    public boolean sendMessage(JSONObject message) {
        oneBotService.handlerPost(message);
//        SandboxMessage sandboxMessage = SandboxMessage.builder()
//                .isSelf(true)
//                .type("text")
//                .message(message.getStr(OneBotConstants.MESSAGE))
//                .date(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.NORM_DATETIME_PATTERN))
//                .build();
//        SandboxWebsocketHandler.sendMessage(sandboxMessage);
        return false;
    }
}
