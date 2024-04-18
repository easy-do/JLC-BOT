package plus.easydo.bot.websocket.handler;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.HighLevelDevelopConf;
import plus.easydo.bot.lowcode.execute.HighLevelDevelopExecuteServer;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.util.MessageParseUtil;
import plus.easydo.bot.util.OneBotUtils;

import java.util.List;
import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报消息高级开发处理
 * @date 2024/4/12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OneBotHighLevelPostHandler {


    private final HighLevelDevelopExecuteServer highLevelDevelopExecuteServer;

    public void handler(String evenType, JSONObject postData) {
        //通过机器人编码找到机器人id
        String botNumber = postData.getStr(OneBotConstants.SELF_ID);
        BotInfo botInfo = OneBotUtils.getBotInfo(botNumber);
        if (Objects.nonNull(botInfo)) {
            List<Long> highLevelIdList = CacheManager.BOT_HIGH_LEVEL_DEV_CONF_CACHE.get(botInfo.getId());
            if (Objects.nonNull(highLevelIdList)) {
                log.debug("机器人节点处理器,为机器人[{}]找到{}个高级开发配置", botNumber, highLevelIdList.size());
                //开始执行流程
                highLevelIdList.forEach(highLevelId -> {
                    HighLevelDevelopConf highLevelDevelopConf = CacheManager.HIGH_LEVEL_DEV_CONF_CACHE.get(highLevelId);
                    if (Objects.nonNull(highLevelDevelopConf) && (CharSequenceUtil.equals(evenType, highLevelDevelopConf.getEventType())
                            || CharSequenceUtil.equals("all", highLevelDevelopConf.getEventType()))) {

                        //预处理参数、parseMessage
                        MessageParseUtil.parseMessage(postData);
                        postData.set(OneBotConstants.BOT_NUMBER, botNumber);
                        postData.set(OneBotConstants.BOT_CONF, CacheManager.BOT_CONF_CACHE.get(botNumber));

                        //执行处理
                        highLevelDevelopExecuteServer.execute(highLevelDevelopConf, postData);
                    }
                });
            }
        }
    }
}
