package plus.easydo.bot.service.impl;


import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.manager.LiteFlowScriptManager;
import plus.easydo.bot.manager.SimpleDevelopConfManager;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.SimpleDevelopService;

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
public class SimpleDevelopServiceImpl implements SimpleDevelopService {

    private final SimpleDevelopConfManager simpleDevelopConfManager;

    private final LiteFlowScriptManager liteFlowScriptManager;

    @Override
    public List<SimpleCmdDevelopConf> listSimpleDevelop() {
        return simpleDevelopConfManager.list();
    }

    @Override
    public Page<SimpleCmdDevelopConf> pageSimpleDevelop(PageQo pageQo) {
        return simpleDevelopConfManager.page(new Page<>(pageQo.getCurrent(),pageQo.getPageSize()));
    }

    @Override
    public SimpleCmdDevelopConf getSimpleDevelopInfo(Long id) {
        SimpleCmdDevelopConf res = simpleDevelopConfManager.getById(id);
        if(Objects.nonNull(res)){
            LiteFlowScript script = liteFlowScriptManager.getByScriptId(LowCodeConstants.HIGH_LEVEL_DEVELOP + res.getId());
            res.setScript(script);
        }
        return res;
    }

    @Override
    public boolean saveSimpleDevelop(SimpleCmdDevelopConf simpleCmdDevelopConf) {
        boolean res = simpleDevelopConfManager.save(simpleCmdDevelopConf);
        if (res){
            liteFlowScriptManager.createData(simpleCmdDevelopConf);
        }
        return res;
    }

    @Override
    public boolean updateSimpleDevelop(SimpleCmdDevelopConf simpleCmdDevelopConf) {
        return simpleDevelopConfManager.updateById(simpleCmdDevelopConf);
    }

    @Override
    public boolean removeSimpleDevelop(Long id) {
        boolean res = simpleDevelopConfManager.removeById(id);
        if (res){
            liteFlowScriptManager.removeByScriptId(LowCodeConstants.SIMPLE_CMD_DEVELOP +id);
        }
        return res;
    }
}
