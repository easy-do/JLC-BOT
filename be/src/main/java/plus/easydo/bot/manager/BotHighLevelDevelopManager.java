package plus.easydo.bot.manager;


import com.mybatisflex.core.query.QueryWrapper;
import plus.easydo.bot.mapper.BotHighLevelDevelopMapper;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotHighLevelDevelop;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import static plus.easydo.bot.entity.table.BotHighLevelDevelopTableDef.BOT_HIGH_LEVEL_DEVELOP;


/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotHighLevelDevelopManager extends ServiceImpl<BotHighLevelDevelopMapper, BotHighLevelDevelop>  {

    public List<BotHighLevelDevelop> lisByBotId(Long botId) {
        QueryWrapper query = query().where(BOT_HIGH_LEVEL_DEVELOP.BOT_ID.eq(botId));
        return list(query);
    }

    public boolean clearByBotId(Long botId) {
        QueryWrapper query = query().where(BOT_HIGH_LEVEL_DEVELOP.BOT_ID.eq(botId));
        return remove(query);
    }

    public boolean setBotHighLevelDevelop(Long botId, List<Long> confIdList) {
        List<BotHighLevelDevelop> botHighLevelDevelopList = new ArrayList<>();
        confIdList.forEach(confId -> {
            BotHighLevelDevelop botHighLevelDevelop = new BotHighLevelDevelop();
            botHighLevelDevelop.setConfId(confId);
            botHighLevelDevelop.setBotId(botId);
            botHighLevelDevelopList.add(botHighLevelDevelop);
        });
        return saveBatch(botHighLevelDevelopList);
    }
}
