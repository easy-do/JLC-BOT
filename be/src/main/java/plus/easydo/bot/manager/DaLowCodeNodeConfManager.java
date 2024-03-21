package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.DaLowCodeNodeConf;
import plus.easydo.bot.mapper.DaLowCodeNodeConfMapper;
import plus.easydo.bot.qo.PageQo;

import java.util.List;

import static plus.easydo.bot.entity.table.DaLowCodeNodeConfTableDef.DA_LOW_CODE_NODE_CONF;

/**
 * 节点配置信息 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class DaLowCodeNodeConfManager extends ServiceImpl<DaLowCodeNodeConfMapper, DaLowCodeNodeConf> {

    public Page<DaLowCodeNodeConf> pageNodeConf(PageQo pageQo) {
        QueryWrapper queryWrapper = query().select(DA_LOW_CODE_NODE_CONF.ID, DA_LOW_CODE_NODE_CONF.CONF_NAME,DA_LOW_CODE_NODE_CONF.CREATE_TIME,DA_LOW_CODE_NODE_CONF.UPDATE_TIME);
        return page(new Page<>(pageQo.getCurrent(),pageQo.getPageSize()),queryWrapper);
    }

    public List<DaLowCodeNodeConf> listNodeConf() {
        QueryWrapper queryWrapper = query().select(DA_LOW_CODE_NODE_CONF.ID, DA_LOW_CODE_NODE_CONF.CONF_NAME);
        return list(queryWrapper);
    }
}
