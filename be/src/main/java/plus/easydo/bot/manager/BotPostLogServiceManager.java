package plus.easydo.bot.manager;


import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.BotPostLog;
import plus.easydo.bot.mapper.BotPostLogMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 接收消息日志 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotPostLogServiceManager extends ServiceImpl<BotPostLogMapper, BotPostLog>{

    public void saveLog(JSONObject messageJson) {
        BotInfo botInfo = CacheManager.BOT_CACHE.get(messageJson.get(OneBotConstants.SELF_ID));
        save(BotPostLog.builder()
                .postTime(LocalDateTimeUtil.now())
                .platform(botInfo.getPlatform())
                .message(messageJson.toJSONString(0)).build());
    }
}
