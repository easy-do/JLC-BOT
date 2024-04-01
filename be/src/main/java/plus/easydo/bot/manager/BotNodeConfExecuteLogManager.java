package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotNodeConfExecuteLog;
import plus.easydo.bot.mapper.BotNodeConfExecuteLogMapper;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.vo.NodePAVo;

import java.util.List;

import static plus.easydo.bot.entity.table.BotNodeConfExecuteLogTableDef.BOT_NODE_CONF_EXECUTE_LOG;

/**
 * 节点配置执行日志 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotNodeConfExecuteLogManager extends ServiceImpl<BotNodeConfExecuteLogMapper, BotNodeConfExecuteLog> {

    public List<NodePAVo> nodeConfExecutePa() {
        return mapper.nodeConfExecutePa();
    }

    public List<NodePAVo> nodeConfExecuteTop() {
        return mapper.nodeConfExecuteTop();
    }

    public boolean clean() {
        QueryWrapper query = query().where("1=1");
        return remove(query);
    }

    public Page<BotNodeConfExecuteLog> pageNodeConfExecuteLog(PageQo pageQo) {
        QueryWrapper query = query().orderBy(BOT_NODE_CONF_EXECUTE_LOG.CREATE_TIME,false);
        return page(new Page<>(pageQo.getCurrent(), pageQo.getPageSize()),query);
    }

    public List<NodePAVo> nodeConfExecuteMax() {
        return mapper.nodeConfExecuteMax();
    }
}
