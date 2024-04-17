package plus.easydo.bot.manager;


import com.mybatisflex.core.query.QueryWrapper;
import plus.easydo.bot.mapper.BotSimpleCmdDevelopMapper;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotSimpleCmdDevelop;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import static plus.easydo.bot.entity.table.BotSimpleCmdDevelopTableDef.BOT_SIMPLE_CMD_DEVELOP;

/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotSimpleCmdDevelopManager extends ServiceImpl<BotSimpleCmdDevelopMapper, BotSimpleCmdDevelop> {



    public List<BotSimpleCmdDevelop> listByBotId(Long botId) {
        QueryWrapper query = query().where(BOT_SIMPLE_CMD_DEVELOP.BOT_ID.eq(botId));
        return list(query);
    }


    public boolean clearByBotId(Long botId) {
        QueryWrapper query = query().where(BOT_SIMPLE_CMD_DEVELOP.BOT_ID.eq(botId));
        return remove(query);
    }

    public boolean setBotSimpleCmdDevelop(Long botId, List<Long> confIdList) {
        List<BotSimpleCmdDevelop> botSimpleCmdDevelops = new ArrayList<>();
        confIdList.forEach(confId -> {
            BotSimpleCmdDevelop botSimpleCmdDevelop = new BotSimpleCmdDevelop();
            botSimpleCmdDevelop.setConfId(confId);
            botSimpleCmdDevelop.setBotId(botId);
            botSimpleCmdDevelops.add(botSimpleCmdDevelop);
        });
        return saveBatch(botSimpleCmdDevelops);
    }
}
