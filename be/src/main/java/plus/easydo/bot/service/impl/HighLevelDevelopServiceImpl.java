package plus.easydo.bot.service.impl;


import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.entity.HighLevelDevelopConf;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.manager.HighLevelDevelopConfManager;
import plus.easydo.bot.manager.LiteFlowScriptManager;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.HighLevelDevelopService;

import java.util.List;
import java.util.Objects;

/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class HighLevelDevelopServiceImpl implements HighLevelDevelopService {

    private final HighLevelDevelopConfManager highLevelDevelopConfManager;

    private final LiteFlowScriptManager liteFlowScriptManager;

    @Override
    public Page<HighLevelDevelopConf> highLevelDevPage(PageQo pageQo) {
        return highLevelDevelopConfManager.page(new Page<>(pageQo.getCurrent(),pageQo.getPageSize()));
    }

    @Override
    public List<HighLevelDevelopConf> highLevelDevList() {
        return highLevelDevelopConfManager.list();
    }

    @Override
    public boolean saveHighLevelDev(HighLevelDevelopConf highLevelDevelopConf) {
        boolean res = highLevelDevelopConfManager.save(highLevelDevelopConf);
        if(res){
            liteFlowScriptManager.createData(highLevelDevelopConf);
        }
        return res;
    }

    @Override
    public boolean removeHighLevelDev(Long id) {
        boolean res = highLevelDevelopConfManager.removeById(id);
        if(res){
            liteFlowScriptManager.removeByScriptId(LowCodeConstants.HIGH_LEVEL_DEVELOP+id);
        }
        return res;
    }

    @Override
    public boolean updateHighLevelDev(HighLevelDevelopConf highLevelDevelopConf) {
        return highLevelDevelopConfManager.updateById(highLevelDevelopConf);
    }

    @Override
    public HighLevelDevelopConf getHighLevelDevInfo(Long id) {
        HighLevelDevelopConf res = highLevelDevelopConfManager.getById(id);
        if(Objects.nonNull(res)){
            LiteFlowScript script = liteFlowScriptManager.getByScriptId(LowCodeConstants.HIGH_LEVEL_DEVELOP + res.getId());
            res.setScript(script);
        }
        return res;
    }
}
