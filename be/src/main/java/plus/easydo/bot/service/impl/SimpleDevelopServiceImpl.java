package plus.easydo.bot.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;
import com.yomahub.liteflow.slot.Slot;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.dto.SetBotConfIdDto;
import plus.easydo.bot.entity.BotSimpleCmdDevelop;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.lowcode.execute.SimpleCmdDevelopExecuteServer;
import plus.easydo.bot.lowcode.model.CmpContextBean;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.manager.BotSimpleCmdDevelopManager;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.manager.LiteFlowScriptManager;
import plus.easydo.bot.manager.SimpleDevelopConfManager;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.SimpleDevelopService;
import plus.easydo.bot.util.MessageParseUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
public class SimpleDevelopServiceImpl implements SimpleDevelopService {

    private final SimpleDevelopConfManager simpleDevelopConfManager;

    private final LiteFlowScriptManager liteFlowScriptManager;

    private final SimpleCmdDevelopExecuteServer simpleCmdDevelopExecuteServer;

    private final BotSimpleCmdDevelopManager botSimpleCmdDevelopManager;

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
    public boolean saveSimpleDevelop(SimpleCmdDevelopConf simpleCmdDevelopConf) {
        boolean res = simpleDevelopConfManager.save(simpleCmdDevelopConf);
        if (res) {
            liteFlowScriptManager.createData(simpleCmdDevelopConf);
            initCache();
        }
        return res;
    }

    @Override
    public boolean updateSimpleDevelop(SimpleCmdDevelopConf simpleCmdDevelopConf) {
        boolean res = simpleDevelopConfManager.updateById(simpleCmdDevelopConf);
        if (res) {
            LiteFlowScript liteFlowScript = liteFlowScriptManager.getByScriptId(LowCodeConstants.SIMPLE_CMD_DEVELOP + simpleCmdDevelopConf.getId());
            liteFlowScript.setScriptLanguage(simpleCmdDevelopConf.getScriptLanguage());
            liteFlowScript.setScriptName(simpleCmdDevelopConf.getConfName());
            liteFlowScriptManager.updateScriptData(liteFlowScript);
            initCache();
        }
        return res;
    }

    @Override
    public boolean removeSimpleDevelop(Long id) {
        boolean res = simpleDevelopConfManager.removeById(id);
        if (res) {
            liteFlowScriptManager.removeByScriptId(LowCodeConstants.SIMPLE_CMD_DEVELOP + id);
            initCache();
        }
        return res;
    }

    @Override
    public Long importConf(SimpleCmdDevelopConf conf) {
        boolean res = simpleDevelopConfManager.save(conf);
        if (res) {
            liteFlowScriptManager.createData(conf);
            initCache();
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
            cmpStepResult.setParam(paramsJson);
            if (cmpStep != null && !cmpStep.isSuccess()) {
                cmpStepResult.setMessage(ExceptionUtil.getMessage(cmpStep.getException()));
            }
            Slot slot = response.getSlot();
            List<CmpContextBean> cmpContextBeanList = new ArrayList<>();
            List<Object> contextBeanList = slot.getContextBeanList();
            contextBeanList.forEach(o -> {
                Class<?> clazz = o.getClass();
                String name = clazz.getSimpleName();
                Method[] methods = clazz.getMethods();
                List<String> methodNameList = Arrays.stream(methods).map(Method::getName).toList();
                cmpContextBeanList.add(CmpContextBean.builder().name(StrUtil.lowerFirst(name)).methods(methodNameList).build());
            });
            cmpStepResult.setContextBeanList(cmpContextBeanList);
            return cmpStepResult;
        }else {
            return CmpStepResult.builder().success(false).message("执行响应为空").build();
        }
    }

    @PostConstruct
    @Override
    public void initCache() {
        //缓存节点配置缓存
        List<SimpleCmdDevelopConf> confList = simpleDevelopConfManager.list();
        CacheManager.SIMPLE_CMD_DEV_CONF_CACHE.clear();
        confList.forEach(conf -> CacheManager.SIMPLE_CMD_DEV_CONF_CACHE.put(conf.getId(), conf));
        //缓存机器人与节点关联缓存
        List<BotSimpleCmdDevelop> list = botSimpleCmdDevelopManager.list();
        Map<Long, List<BotSimpleCmdDevelop>> groupMap = list.stream().collect(Collectors.groupingBy(BotSimpleCmdDevelop::getBotId));
        CacheManager.BOT_SIMPLE_CMD_DEV_CONF_CACHE.clear();
        groupMap.forEach((key, value) -> CacheManager.BOT_SIMPLE_CMD_DEV_CONF_CACHE.put(key, value.stream().map(BotSimpleCmdDevelop::getConfId).toList()));
    }

    @Override
    public List<Long> getSimpleCmdDevelop(Long botId) {
        return botSimpleCmdDevelopManager.listByBotId(botId).stream().map(BotSimpleCmdDevelop::getConfId).toList();
    }

    @Override
    public boolean setBotSimpleCmdDevelop(SetBotConfIdDto setBotConfIdDto) {
        Long botId = setBotConfIdDto.getBotId();
        Assert.notNull(botId, "机器人id不能为空");
        List<Long> confIdList = setBotConfIdDto.getConfIdList();
        if (Objects.isNull(confIdList) || confIdList.isEmpty()) {
            return botSimpleCmdDevelopManager.clearByBotId(botId);
        }
        botSimpleCmdDevelopManager.clearByBotId(botId);
        boolean res = botSimpleCmdDevelopManager.setBotSimpleCmdDevelop(botId, confIdList);
        if (res) {
            initCache();
        }
        return res;
    }
}
