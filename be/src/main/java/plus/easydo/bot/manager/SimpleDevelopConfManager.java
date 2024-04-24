package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.mapper.SimpleDevelopConfMapper;
import plus.easydo.bot.qo.SimpleCmdDevelopConfQo;

import static plus.easydo.bot.entity.table.SimpleCmdDevelopConfTableDef.SIMPLE_CMD_DEVELOP_CONF;

/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class SimpleDevelopConfManager extends ServiceImpl<SimpleDevelopConfMapper, SimpleCmdDevelopConf> {

    public Page<SimpleCmdDevelopConf> pageSimpleDevelop(SimpleCmdDevelopConfQo simpleCmdDevelopConfQo) {
        QueryWrapper queryWrapper = query();
        queryWrapper.where(SIMPLE_CMD_DEVELOP_CONF.CONF_NAME.eq(simpleCmdDevelopConfQo.getConfName()));
        queryWrapper.where(SIMPLE_CMD_DEVELOP_CONF.CMD.eq(simpleCmdDevelopConfQo.getCmd()));
        queryWrapper.where(SIMPLE_CMD_DEVELOP_CONF.CMD_TYPE.eq(simpleCmdDevelopConfQo.getCmdType()));
        queryWrapper.where(SIMPLE_CMD_DEVELOP_CONF.REMARK.like(simpleCmdDevelopConfQo.getRemark()));
        return page(new Page<>(simpleCmdDevelopConfQo.getCurrent(), simpleCmdDevelopConfQo.getPageSize()), queryWrapper);
    }
}
