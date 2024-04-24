package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.LowCodeNodeConf;
import plus.easydo.bot.mapper.LowCodeNodeConfMapper;
import plus.easydo.bot.qo.NodeConfQo;

import java.util.List;

import static plus.easydo.bot.entity.table.LowCodeNodeConfTableDef.LOW_CODE_NODE_CONF;

/**
 * 节点配置信息 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class LowCodeNodeConfManager extends ServiceImpl<LowCodeNodeConfMapper, LowCodeNodeConf> {

    public Page<LowCodeNodeConf> pageNodeConf(NodeConfQo nodeConfQo) {
        QueryWrapper queryWrapper = query().select(LOW_CODE_NODE_CONF.ID, LOW_CODE_NODE_CONF.CONF_NAME, LOW_CODE_NODE_CONF.EVENT_TYPE, LOW_CODE_NODE_CONF.CREATE_TIME, LOW_CODE_NODE_CONF.UPDATE_TIME);
        queryWrapper.where(LOW_CODE_NODE_CONF.CONF_NAME.eq(nodeConfQo.getConfName()));
        queryWrapper.where(LOW_CODE_NODE_CONF.EVENT_TYPE.eq(nodeConfQo.getEventType()));
        return page(new Page<>(nodeConfQo.getCurrent(), nodeConfQo.getPageSize()), queryWrapper);
    }

    public List<LowCodeNodeConf> listNodeConf() {
        QueryWrapper queryWrapper = query().select(LOW_CODE_NODE_CONF.ID, LOW_CODE_NODE_CONF.CONF_NAME);
        return list(queryWrapper);
    }
}
