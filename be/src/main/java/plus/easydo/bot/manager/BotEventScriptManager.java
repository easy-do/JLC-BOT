package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotEventScript;
import plus.easydo.bot.mapper.BotEventScriptMapper;
import plus.easydo.bot.qo.BotEventScriptQo;

import java.util.List;

import static plus.easydo.bot.entity.table.BotEventScriptTableDef.BOT_EVENT_SCRIPT;

/**
 * 机器人脚本 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotEventScriptManager extends ServiceImpl<BotEventScriptMapper, BotEventScript> {

    public List<BotEventScript> listAll() {
        return list(query().select(BOT_EVENT_SCRIPT.ID, BOT_EVENT_SCRIPT.EVENT_TYPE, BOT_EVENT_SCRIPT.SCRIPT_NAME, BOT_EVENT_SCRIPT.REMARK));
    }

    public Page<BotEventScript> pageBotScript(BotEventScriptQo botEventScriptQo) {
        QueryWrapper queryWrapper = query().select(BOT_EVENT_SCRIPT.ID, BOT_EVENT_SCRIPT.EVENT_TYPE, BOT_EVENT_SCRIPT.SCRIPT_TYPE, BOT_EVENT_SCRIPT.SCRIPT_NAME, BOT_EVENT_SCRIPT.REMARK);
        return page(new Page<>(botEventScriptQo.getCurrent(), botEventScriptQo.getPageSize()), queryWrapper);
    }
}
