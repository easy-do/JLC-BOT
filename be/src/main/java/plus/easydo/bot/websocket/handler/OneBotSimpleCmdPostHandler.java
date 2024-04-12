package plus.easydo.bot.websocket.handler;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.enums.onebot.OneBotPostTypeEnum;
import plus.easydo.bot.lowcode.execute.SimpleCmdDevelopExecuteServer;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.util.MessageParseUtil;
import plus.easydo.bot.util.OneBotUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报消息简单指令处理
 * @date 2024/4/12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OneBotSimpleCmdPostHandler {


    private final SimpleCmdDevelopExecuteServer simpleCmdDevelopExecuteServer;

    public void handler(String evenType, JSONObject postData) {
        if (CharSequenceUtil.equals(evenType, OneBotPostTypeEnum.MESSAGE.getType())) {
            //通过机器人编码找到机器人id
            String botNumber = postData.getStr(OneBotConstants.SELF_ID);
            BotInfo botInfo = OneBotUtils.getBotInfo(botNumber);
            if (Objects.nonNull(botInfo)) {
                List<Long> highLevelIdList = CacheManager.BOT_SIMPLE_CMD_DEV_CONF_CACHE.get(botInfo.getId());
                if (Objects.nonNull(highLevelIdList)) {
                    log.debug("机器人节点处理器,为机器人[{}]找到{}个高级开发配置", botNumber, highLevelIdList.size());
                    //开始执行流程
                    highLevelIdList.forEach(highLevelId -> {
                        SimpleCmdDevelopConf simpleCmdDevelopConf = CacheManager.SIMPLE_CMD_DEV_CONF_CACHE.get(highLevelId);
                        //预处理参数、parseMessage
                        MessageParseUtil.parseMessage(postData);
                        postData.set("botNumber", botNumber);
                        postData.set("botConf", CacheManager.BOT_CONF_CACHE.get(botNumber));
                        //执行处理
                        simpleCmdDevelopExecuteServer.execute(simpleCmdDevelopConf, postData);
                    });
                }
            }
        }
    }
}
