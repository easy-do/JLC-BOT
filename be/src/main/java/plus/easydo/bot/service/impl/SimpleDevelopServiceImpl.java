package plus.easydo.bot.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.lowcode.execute.SimpleCmdDevelopExecuteServer;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.manager.LiteFlowScriptManager;
import plus.easydo.bot.manager.SimpleDevelopConfManager;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.SimpleDevelopService;
import plus.easydo.bot.util.MessageParseUtil;

import java.util.List;
import java.util.Objects;
import java.util.Queue;

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

    private final SimpleCmdDevelopExecuteServer simpleCmdDevelopExecuteServer;

    @Override
    public List<SimpleCmdDevelopConf> listSimpleDevelop() {
        return simpleDevelopConfManager.list();
    }

    @Override
    public Page<SimpleCmdDevelopConf> pageSimpleDevelop(PageQo pageQo) {
        return simpleDevelopConfManager.page(new Page<>(pageQo.getCurrent(), pageQo.getPageSize()));
    }

    @Override
    public SimpleCmdDevelopConf getSimpleDevelopInfo(Long id) {
        SimpleCmdDevelopConf res = simpleDevelopConfManager.getById(id);
        if (Objects.nonNull(res)) {
            LiteFlowScript script = liteFlowScriptManager.getByScriptId(LowCodeConstants.SIMPLE_CMD_DEVELOP + res.getId());
            Assert.notNull(script, "脚本不存在");
            res.setScript(script);
            res.setScriptLanguage(script.getScriptLanguage());
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSimpleDevelop(SimpleCmdDevelopConf simpleCmdDevelopConf) {
        boolean res = simpleDevelopConfManager.save(simpleCmdDevelopConf);
        if (res) {
            liteFlowScriptManager.createData(simpleCmdDevelopConf);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSimpleDevelop(SimpleCmdDevelopConf simpleCmdDevelopConf) {
        boolean res = simpleDevelopConfManager.updateById(simpleCmdDevelopConf);
        if (res) {
            LiteFlowScript liteFlowScript = liteFlowScriptManager.getByScriptId(LowCodeConstants.SIMPLE_CMD_DEVELOP + simpleCmdDevelopConf.getId());
            liteFlowScript.setScriptLanguage(simpleCmdDevelopConf.getScriptLanguage());
            liteFlowScript.setScriptName(simpleCmdDevelopConf.getConfName());
            liteFlowScriptManager.updateScriptData(liteFlowScript);
        }
        return res;
    }

    @Override
    public boolean removeSimpleDevelop(Long id) {
        boolean res = simpleDevelopConfManager.removeById(id);
        if (res) {
            liteFlowScriptManager.removeByScriptId(LowCodeConstants.SIMPLE_CMD_DEVELOP + id);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long importConf(SimpleCmdDevelopConf conf) {
        boolean res = simpleDevelopConfManager.save(conf);
        if (res) {
            liteFlowScriptManager.createData(conf);
        }
        return conf.getId();
    }

    @Override
    public CmpStepResult debug(DebugDto debugDto) {
        SimpleCmdDevelopConf simpleCmdDevelopConf = simpleDevelopConfManager.getById(debugDto.getId());
        Assert.notNull(simpleCmdDevelopConf, "配置不存在");
        JSONObject paramsJson = JSONUtil.parseObj(debugDto.getParams());
        MessageParseUtil.parseMessage(paramsJson);
        LiteflowResponse response = simpleCmdDevelopExecuteServer.execute(simpleCmdDevelopConf, paramsJson);
        if(Objects.nonNull(response)){
            Queue<CmpStep> cmpSteps = response.getExecuteStepQueue();
            CmpStep cmpStep = cmpSteps.poll();
            CmpStepResult cmpStepResult = BeanUtil.copyProperties(cmpStep, CmpStepResult.class);
            if (cmpStep != null && !cmpStep.isSuccess()) {
                cmpStepResult.setMessage(ExceptionUtil.getMessage(cmpStep.getException()));
            }
            return cmpStepResult;
        }else {
            return CmpStepResult.builder().success(false).message("执行响应为空").build();
        }
    }
}
