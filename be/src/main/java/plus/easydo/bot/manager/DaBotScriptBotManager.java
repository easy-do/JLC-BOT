package plus.easydo.bot.manager;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.DaBotScriptBot;
import plus.easydo.bot.mapper.DaBotScriptBotMapper;

import java.util.ArrayList;
import java.util.List;

import static plus.easydo.bot.entity.table.DaBotScriptBotTableDef.DA_BOT_SCRIPT_BOT;

/**
 * 机器人与脚本关联表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class DaBotScriptBotManager extends ServiceImpl<DaBotScriptBotMapper, DaBotScriptBot> {

    public boolean clearBotScript(String botNumber) {
        return remove(query().and(DA_BOT_SCRIPT_BOT.BOT_NUMBER.eq(botNumber)));
    }

    public boolean saveBotScript(String botNumber, List<Long> ids) {
        List<DaBotScriptBot> list = new ArrayList<>();
        ids.forEach(scriptId->{
            DaBotScriptBot daBotScriptBot = DaBotScriptBot.builder().botNumber(botNumber).scriptId(scriptId).build();
            list.add(daBotScriptBot);
        });
        return saveBatch(list);
    }

    public List<DaBotScriptBot> getBotScript(String botNumber) {
        return list(query().and(DA_BOT_SCRIPT_BOT.BOT_NUMBER.eq(botNumber)));
    }
}
