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
import plus.easydo.bot.entity.HighLevelDevelopConf;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.lowcode.execute.HighLevelDevelopExecuteServer;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.manager.HighLevelDevelopConfManager;
import plus.easydo.bot.manager.LiteFlowScriptManager;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.HighLevelDevelopService;
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
public class HighLevelDevelopServiceImpl implements HighLevelDevelopService {

    private final HighLevelDevelopConfManager highLevelDevelopConfManager;

    private final LiteFlowScriptManager liteFlowScriptManager;

    private final HighLevelDevelopExecuteServer highLevelDevelopExecuteServer;

    @Override
    public Page<HighLevelDevelopConf> highLevelDevPage(PageQo pageQo) {
        return highLevelDevelopConfManager.page(new Page<>(pageQo.getCurrent(),pageQo.getPageSize()));
    }

    @Override
    public List<HighLevelDevelopConf> highLevelDevList() {
        return highLevelDevelopConfManager.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public boolean updateHighLevelDev(HighLevelDevelopConf highLevelDevelopConf) {
        boolean res = highLevelDevelopConfManager.updateById(highLevelDevelopConf);
        if (res){
            LiteFlowScript liteFlowScript = liteFlowScriptManager.getByScriptId(LowCodeConstants.HIGH_LEVEL_DEVELOP + highLevelDevelopConf.getId());
            liteFlowScript.setScriptLanguage(highLevelDevelopConf.getScriptLanguage());
            liteFlowScript.setScriptName(highLevelDevelopConf.getConfName());
            liteFlowScriptManager.updateScriptData(liteFlowScript);
        }
        return res;
    }

    @Override
    public HighLevelDevelopConf getHighLevelDevInfo(Long id) {
        HighLevelDevelopConf res = highLevelDevelopConfManager.getById(id);
        if(Objects.nonNull(res)){
            LiteFlowScript script = liteFlowScriptManager.getByScriptId(LowCodeConstants.HIGH_LEVEL_DEVELOP + res.getId());
            Assert.notNull(script,"脚本不存在");
            res.setScript(script);
            res.setScriptLanguage(script.getScriptLanguage());
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long importConf(HighLevelDevelopConf conf) {
        boolean res = highLevelDevelopConfManager.save(conf);
        if(res){
            liteFlowScriptManager.createData(conf);
        }
        return conf.getId();
    }

    @Override
    public CmpStepResult debug(DebugDto debugDto) {
        HighLevelDevelopConf highLevelDevelopConf = highLevelDevelopConfManager.getById(debugDto.getId());
        Assert.notNull(highLevelDevelopConf, "配置不存在");
        JSONObject paramsJson = JSONUtil.parseObj(debugDto.getParams());
        MessageParseUtil.parseMessage(paramsJson);
        LiteflowResponse response = highLevelDevelopExecuteServer.execute(highLevelDevelopConf, paramsJson);
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
