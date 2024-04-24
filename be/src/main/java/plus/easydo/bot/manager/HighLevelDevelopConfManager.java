package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.HighLevelDevelopConf;
import plus.easydo.bot.mapper.HighLevelDevelopConfMapper;
import plus.easydo.bot.qo.HighLevelDevelopConfQo;

import static plus.easydo.bot.entity.table.HighLevelDevelopConfTableDef.HIGH_LEVEL_DEVELOP_CONF;

/**
 * 数据操作实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class HighLevelDevelopConfManager extends ServiceImpl<HighLevelDevelopConfMapper, HighLevelDevelopConf> {

    public Page<HighLevelDevelopConf> highLevelDevPage(HighLevelDevelopConfQo highLevelDevelopConfQo) {
        QueryWrapper queryWrapper = query();
        queryWrapper.where(HIGH_LEVEL_DEVELOP_CONF.CONF_NAME.eq(highLevelDevelopConfQo.getConfName()));
        queryWrapper.where(HIGH_LEVEL_DEVELOP_CONF.EVENT_TYPE.like(highLevelDevelopConfQo.getEventType()));
        queryWrapper.where(HIGH_LEVEL_DEVELOP_CONF.REMARK.like(highLevelDevelopConfQo.getRemark()));
        return page(new Page<>(highLevelDevelopConfQo.getCurrent(), highLevelDevelopConfQo.getPageSize()), queryWrapper);
    }
}
