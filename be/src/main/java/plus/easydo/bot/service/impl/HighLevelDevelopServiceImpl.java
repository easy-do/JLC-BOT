package plus.easydo.bot.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.dto.SetBotConfIdDto;
import plus.easydo.bot.entity.BotHighLevelDevelop;
import plus.easydo.bot.entity.HighLevelDevelopConf;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.lowcode.execute.HighLevelDevelopExecuteServer;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.manager.BotHighLevelDevelopManager;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.manager.HighLevelDevelopConfManager;
import plus.easydo.bot.manager.LiteFlowScriptManager;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.HighLevelDevelopService;
import plus.easydo.bot.util.LiteFlowUtils;
import plus.easydo.bot.util.MessageParseUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

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

    private final BotHighLevelDevelopManager botHighLevelDevelopManager;

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
            try {
                liteFlowScriptManager.createData(highLevelDevelopConf);
                initCache();
            }catch (Exception e){
                highLevelDevelopConfManager.removeById(highLevelDevelopConf.getId());
                throw new BaseException("创建脚本失败:"+e.getMessage());
            }
        }
        return res;
    }

    @Override
    public boolean removeHighLevelDev(Long id) {
        boolean res = highLevelDevelopConfManager.removeById(id);
        if(res){
            liteFlowScriptManager.removeByScriptId(LowCodeConstants.HIGH_LEVEL_DEVELOP+id);
            initCache();
        }
        return res;
    }

    @Override
    public boolean updateHighLevelDev(HighLevelDevelopConf highLevelDevelopConf) {
        HighLevelDevelopConf old = highLevelDevelopConfManager.getById(highLevelDevelopConf.getId());
        boolean res = highLevelDevelopConfManager.updateById(highLevelDevelopConf);
        if (res){
            LiteFlowScript liteFlowScript = liteFlowScriptManager.getByScriptId(LowCodeConstants.HIGH_LEVEL_DEVELOP + highLevelDevelopConf.getId());

            liteFlowScript.setScriptLanguage(highLevelDevelopConf.getScriptLanguage());
            liteFlowScript.setScriptName(highLevelDevelopConf.getConfName());
            try {
                liteFlowScriptManager.updateScriptData(liteFlowScript);
                initCache();
            }catch (Exception e){
                highLevelDevelopConfManager.updateById(old);
                throw new BaseException("更新脚本失败:"+e.getMessage());
            }
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
    public Long importConf(HighLevelDevelopConf conf) {
        boolean res = highLevelDevelopConfManager.save(conf);
        if(res){
            try {
                liteFlowScriptManager.createData(conf);
                initCache();
            }catch (Exception e){
                highLevelDevelopConfManager.removeById(conf.getId());
                throw new BaseException("创建脚本失败:"+e.getMessage());
            }
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
            cmpStepResult.setParam(paramsJson);
            if (cmpStep != null && !cmpStep.isSuccess()) {
                cmpStepResult.setMessage(ExceptionUtil.getMessage(cmpStep.getException()));
            }
            //构建可用上下文
            LiteFlowUtils.buildContextBeanList(response, cmpStepResult);
            return cmpStepResult;
        }else {
            return CmpStepResult.builder().success(false).message("执行响应为空").build();
        }
    }

    @PostConstruct
    @Override
    public void initCache() {
        //缓存节点配置缓存
        List<HighLevelDevelopConf> confList = highLevelDevelopConfManager.list();
        CacheManager.HIGH_LEVEL_DEV_CONF_CACHE.clear();
        confList.forEach(conf -> CacheManager.HIGH_LEVEL_DEV_CONF_CACHE.put(conf.getId(), conf));
        //缓存机器人与节点关联缓存
        List<BotHighLevelDevelop> list = botHighLevelDevelopManager.list();
        Map<Long, List<BotHighLevelDevelop>> groupMap = list.stream().collect(Collectors.groupingBy(BotHighLevelDevelop::getBotId));
        CacheManager.BOT_HIGH_LEVEL_DEV_CONF_CACHE.clear();
        groupMap.forEach((key, value) -> CacheManager.BOT_HIGH_LEVEL_DEV_CONF_CACHE.put(key, value.stream().map(BotHighLevelDevelop::getConfId).toList()));
    }

    @Override
    public List<Long> getBotHighLevelDevelop(Long botId) {
        return botHighLevelDevelopManager.lisByBotId(botId).stream().map(BotHighLevelDevelop::getConfId).toList();
    }

    @Override
    public boolean setBotHighLevelDevelop(SetBotConfIdDto setBotConfIdDto) {
        Long botId = setBotConfIdDto.getBotId();
        Assert.notNull(botId, "机器人id不能为空");
        List<Long> confIdList = setBotConfIdDto.getConfIdList();
        if (Objects.isNull(confIdList) || confIdList.isEmpty()) {
            return botHighLevelDevelopManager.clearByBotId(botId);
        }
        botHighLevelDevelopManager.clearByBotId(botId);
        boolean res = botHighLevelDevelopManager.setBotHighLevelDevelop(botId, confIdList);
        if (res) {
            initCache();
        }
        return res;
    }
}
