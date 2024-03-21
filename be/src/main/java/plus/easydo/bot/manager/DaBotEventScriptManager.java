package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.DaBotEventScript;
import plus.easydo.bot.mapper.DaBotEventScriptMapper;
import plus.easydo.bot.qo.DaBotEventScriptQo;

import java.util.List;

import static plus.easydo.bot.entity.table.DaBotEventScriptTableDef.DA_BOT_EVENT_SCRIPT;

/**
 * 机器人脚本 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class DaBotEventScriptManager extends ServiceImpl<DaBotEventScriptMapper, DaBotEventScript> {

    public List<DaBotEventScript> listAll() {
        return list(query().select(DA_BOT_EVENT_SCRIPT.ID,DA_BOT_EVENT_SCRIPT.EVENT_TYPE,DA_BOT_EVENT_SCRIPT.SCRIPT_NAME,DA_BOT_EVENT_SCRIPT.REMARK));
    }

    public Page<DaBotEventScript> pageBotScript(DaBotEventScriptQo daBotEventScriptQo) {
        QueryWrapper queryWrapper = query().select(DA_BOT_EVENT_SCRIPT.ID,DA_BOT_EVENT_SCRIPT.EVENT_TYPE,DA_BOT_EVENT_SCRIPT.SCRIPT_TYPE,DA_BOT_EVENT_SCRIPT.SCRIPT_NAME,DA_BOT_EVENT_SCRIPT.REMARK);
        return page(new Page<>(daBotEventScriptQo.getCurrent(),daBotEventScriptQo.getPageSize()),queryWrapper);
    }
}
