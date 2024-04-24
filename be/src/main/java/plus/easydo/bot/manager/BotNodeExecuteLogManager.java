package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotNodeExecuteLog;
import plus.easydo.bot.mapper.BotNodeExecuteLogMapper;
import plus.easydo.bot.qo.NodeExecuteLogQo;
import plus.easydo.bot.vo.NodePAVo;

import java.util.List;

import static plus.easydo.bot.entity.table.BotNodeExecuteLogTableDef.BOT_NODE_EXECUTE_LOG;

/**
 * 节点执行日志 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotNodeExecuteLogManager extends ServiceImpl<BotNodeExecuteLogMapper, BotNodeExecuteLog> {

    public List<NodePAVo> nodeExecutePa() {
        return mapper.nodeExecutePa();
    }

    public List<NodePAVo> nodeExecuteTop() {
        return mapper.nodeExecuteTop();
    }

    public boolean clean() {
        QueryWrapper query = query().where("1=1");
        return remove(query);
    }

    public Page<BotNodeExecuteLog> pageNodeConfExecuteLog(NodeExecuteLogQo nodeExecuteLogQo) {
        QueryWrapper queryWrapper = query();
        queryWrapper.where(BOT_NODE_EXECUTE_LOG.CONF_ID.eq(nodeExecuteLogQo.getConfId()));
        queryWrapper.where(BOT_NODE_EXECUTE_LOG.CONF_NAME.eq(nodeExecuteLogQo.getConfName()));
        queryWrapper.where(BOT_NODE_EXECUTE_LOG.NODE_CODE.eq(nodeExecuteLogQo.getNodeCode()));
        queryWrapper.where(BOT_NODE_EXECUTE_LOG.NODE_NAME.eq(nodeExecuteLogQo.getNodeName()));
        queryWrapper.where(BOT_NODE_EXECUTE_LOG.EXECUTE_TIME.eq(nodeExecuteLogQo.getExecuteTime()));
        queryWrapper.orderBy(BOT_NODE_EXECUTE_LOG.CREATE_TIME, false);
        return page(new Page<>(nodeExecuteLogQo.getCurrent(), nodeExecuteLogQo.getPageSize()), queryWrapper);
    }

    public List<NodePAVo> nodeExecuteMax() {
        return mapper.nodeExecuteMax();
    }
}
