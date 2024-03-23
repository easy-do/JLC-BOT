package plus.easydo.bot.manager;


import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotConf;
import plus.easydo.bot.mapper.DaBotConfMapper;

import java.util.List;

import static plus.easydo.bot.entity.table.BotConfTableDef.BOT_CONF;

/**
 * 机器人配置 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class DaBotConfManager extends ServiceImpl<DaBotConfMapper, BotConf> {

    public List<BotConf> getByBotNumber(String botNumber) {
        QueryWrapper queryWrapper = query().and(BOT_CONF.BOT_NUMBER.eq(botNumber));
        return list(queryWrapper);
    }

    public BotConf getByBotNumberAndKey(String botNumber, String key) {
        QueryWrapper queryWrapper = query().and(BOT_CONF.BOT_NUMBER.eq(botNumber)).and(BOT_CONF.CONF_KEY.eq(key));
        List<BotConf> res = list(queryWrapper);
        if(!res.isEmpty()){
            return res.get(0);
        }
        return null;
    }

    public boolean removeBotConf(String botNumber, String key) {
        QueryWrapper queryWrapper = query().and(BOT_CONF.BOT_NUMBER.eq(botNumber)).and(BOT_CONF.CONF_KEY.eq(key));
        return remove(queryWrapper);
    }

    public boolean removeBotConfLike(String botNumber, String key) {
        QueryWrapper queryWrapper = query().and(BOT_CONF.BOT_NUMBER.eq(botNumber)).and(BOT_CONF.CONF_KEY.like(key));
        return remove(queryWrapper);
    }


}
