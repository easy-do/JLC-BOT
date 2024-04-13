package plus.easydo.bot.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.enums.onebot.OneBotPostMessageTypeEnum;
import plus.easydo.bot.enums.onebot.OneBotPostTypeEnum;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.sandbox.SandboxMessage;
import plus.easydo.bot.sandbox.SandboxWebsocketHandler;
import plus.easydo.bot.service.BotService;
import plus.easydo.bot.service.HighLevelDevelopService;
import plus.easydo.bot.service.LowCodeService;
import plus.easydo.bot.service.SandboxService;
import plus.easydo.bot.service.SimpleDevelopService;
import plus.easydo.bot.websocket.OneBotService;

import java.util.List;
import java.util.Objects;


/**
 * @author yuzhanfeng
 * @Date 2024-03-30
 * @Description 沙箱服务实现
 */
@Service
@RequiredArgsConstructor
public class SandboxServiceImpl implements SandboxService {

    private static final String SANDBOX = "sandbox";

    private final BotService botService;

    private final OneBotService oneBotService;

    private final LowCodeService lowCodeService;

    private final SimpleDevelopService simpleDevelopService;

    private final HighLevelDevelopService highLevelDevelopService;


    @PostConstruct
    public void initSandBoxBot() {
        BotInfo bot = botService.getByBotNumber(LowCodeConstants.JLC_BOT_SANDBOX);
        if (Objects.isNull(bot)) {
            BotInfo botInfo = BotInfo.builder()
                    .botNumber(LowCodeConstants.JLC_BOT_SANDBOX)
                    .postType(SANDBOX)
                    .invokeType(SANDBOX)
                    .remark("沙箱机器人")
                    .platform(SANDBOX)
                    .build();
            botService.addBot(botInfo);
        }
    }

    @Override
    public List<CmpStepResult> sendMessage(SandboxMessage sandboxMessage) {
        sandboxMessage.setTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.NORM_DATETIME_PATTERN));
        //保存并广播消息
        sandboxMessage.setIsSelf(true);
        sandboxMessage.setMessageId(UUID.fastUUID().toString(true));
        SandboxWebsocketHandler.sendMessage(sandboxMessage);
        JSONObject postData = JSONUtil.parseObj(sandboxMessage);
        postData.set(OneBotConstants.SELF_ID, LowCodeConstants.JLC_BOT_SANDBOX);
        postData.set(OneBotConstants.GROUP_ID, LowCodeConstants.JLC_BOT_SANDBOX);
        postData.set(OneBotConstants.MESSAGE, sandboxMessage.getMessage());
        postData.set(OneBotConstants.RAW_MESSAGE, sandboxMessage.getMessage());
        postData.set(OneBotConstants.POST_TYPE, OneBotPostTypeEnum.MESSAGE.getType());
        postData.set(OneBotConstants.MESSAGE_TYPE, OneBotPostMessageTypeEnum.GROUP.getType());
        Long confId = sandboxMessage.getConfId();
        if (Objects.isNull(confId)) {
            oneBotService.handlerPost(postData);
            return ListUtil.empty();
        } else {
            postData.set(OneBotConstants.BOT_NUMBER, LowCodeConstants.JLC_BOT_SANDBOX);
            String confType = sandboxMessage.getConfType();
            return switch (confType) {
                case LowCodeConstants.LOWCODE_NODE ->
                        lowCodeService.debugNodeConf(DebugDto.builder().id(confId).params(postData).build());
                case LowCodeConstants.SIMPLE_CMD_DEVELOP ->{
                    CmpStepResult result = simpleDevelopService.debug(DebugDto.builder().id(confId).params(postData).build());
                    yield result.isSuccess() ? ListUtil.of(result) : ListUtil.empty();
                }
                case LowCodeConstants.HIGH_LEVEL_DEVELOP ->{
                    CmpStepResult result = highLevelDevelopService.debug(DebugDto.builder().id(confId).params(postData).build());
                    yield result.isSuccess() ? ListUtil.of(result) : ListUtil.empty();
                }
                default -> ListUtil.empty();
            };
        }
    }
}
