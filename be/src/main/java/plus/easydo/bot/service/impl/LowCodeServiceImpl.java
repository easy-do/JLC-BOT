package plus.easydo.bot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.element.Node;
import com.yomahub.liteflow.flow.entity.CmpStep;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.dto.BotNodeDto;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.dto.SetBotConfIdDto;
import plus.easydo.bot.entity.LowCodeBotNode;
import plus.easydo.bot.entity.LowCodeNodeConf;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.lowcode.node.JLCLiteFlowContext;
import plus.easydo.bot.lowcode.execute.LiteFlowNodeExecuteServer;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.manager.LowCodeBotNodeManager;
import plus.easydo.bot.manager.LowCodeNodeConfManager;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.LowCodeService;
import plus.easydo.bot.util.MessageParseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * @author laoyu
 * @version 1.0
 * @description 低代码服务实现
 * @date 2024/3/5
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LowCodeServiceImpl implements LowCodeService {

    private final LowCodeNodeConfManager lowCodeNodeConfManager;

    private final LiteFlowNodeExecuteServer liteFlowNodeExecuteServer;

    private final LowCodeBotNodeManager lowCodeBotNodeManager;

    @Override
    public Long saveNodeConf(BotNodeDto botNodeDto) {
        LowCodeNodeConf lowCodeNodeConf = LowCodeNodeConf.builder()
                .confName(botNodeDto.getConfName())
                .eventType(botNodeDto.getEventType())
                .nodeData(JSONUtil.toJsonPrettyStr(botNodeDto.getNodes()))
                .confData(JSONUtil.toJsonPrettyStr(botNodeDto.getNodeConf()))
                .createTime(LocalDateTimeUtil.now())
                .build();
        boolean res = lowCodeNodeConfManager.save(lowCodeNodeConf);
        if (res) {
            initLowCodeNodeCache();
        }
        return lowCodeNodeConf.getId();
    }

    @Override
    public boolean updateNodeConf(BotNodeDto botNodeDto) {
        LowCodeNodeConf lowCodeNodeConf = LowCodeNodeConf.builder()
                .id(botNodeDto.getId())
                .eventType(botNodeDto.getEventType())
                .confName(botNodeDto.getConfName())
                .nodeData(JSONUtil.toJsonPrettyStr(botNodeDto.getNodes()))
                .confData(JSONUtil.toJsonPrettyStr(botNodeDto.getNodeConf()))
                .updateTime(LocalDateTimeUtil.now())
                .build();
        boolean res = lowCodeNodeConfManager.updateById(lowCodeNodeConf);
        if (res) {
            initLowCodeNodeCache();
        }
        return res;
    }

    @Override
    public BotNodeDto getNodeConf(Long id) {
        LowCodeNodeConf conf = lowCodeNodeConfManager.getById(id);
        if (Objects.isNull(conf)) {
            throw new BaseException("配置不存在");
        }
        return BotNodeDto.builder()
                .id(conf.getId())
                .eventType(conf.getEventType())
                .confName(conf.getConfName())
                .nodes(JSONUtil.parseObj(conf.getNodeData()))
                .nodeConf(JSONUtil.parseObj(conf.getConfData()))
                .build();
    }

    @Override
    public Page<LowCodeNodeConf> pageNodeConf(PageQo pageQo) {
        return lowCodeNodeConfManager.pageNodeConf(pageQo);
    }

    @Override
    public boolean removeNodeConf(Long id) {
        boolean res = lowCodeNodeConfManager.removeById(id);
        if (res) {
            initLowCodeNodeCache();
        }
        return res;
    }

    @Override
    public List<CmpStepResult> debugNodeConf(DebugDto debugDto) {
        LowCodeNodeConf conf = lowCodeNodeConfManager.getById(debugDto.getId());
        if (Objects.isNull(conf)) {
            throw new BaseException("配置不存在");
        }

        //处理参数格式、parseMessage
        JSONObject paramsJson;
        Object params = debugDto.getParams();
        if(params instanceof JSONObject){
            paramsJson = (JSONObject) params;
        }else {
            paramsJson = JSONUtil.parseObj(params);
        }
        MessageParseUtil.parseMessage(paramsJson);

        LiteflowResponse res = liteFlowNodeExecuteServer.execute(conf, paramsJson);
        Queue<CmpStep> cmpSteps = res.getExecuteStepQueue();
        JLCLiteFlowContext context = res.getContextBean(JLCLiteFlowContext.class);
        Map<String, JSONObject> paramCache = context.getNodeParamCache();
        List<CmpStepResult> resultList = new ArrayList<>();
        for (CmpStep cmpStep : cmpSteps) {
            CmpStepResult cmpStepResult = BeanUtil.copyProperties(cmpStep, CmpStepResult.class);
            Node node = cmpStep.getRefNode();
            cmpStepResult.setParam(paramCache.get(node.getTag()));
            if (!cmpStep.isSuccess()) {
                cmpStepResult.setMessage(ExceptionUtil.getMessage(cmpStep.getException()));
            }
            resultList.add(cmpStepResult);
        }
        return resultList;
    }

    @Override
    public boolean setBotNode(SetBotConfIdDto setBotConfIdDto) {
        Long botId = setBotConfIdDto.getBotId();
        if (Objects.isNull(botId)) {
            throw new BaseException("机器人id不能为空");
        }
        List<Long> confIdList = setBotConfIdDto.getConfIdList();
        if (Objects.isNull(confIdList) || confIdList.isEmpty()) {
            return lowCodeBotNodeManager.clearBotConf(botId);
        }
        lowCodeBotNodeManager.clearBotConf(botId);
        boolean res = lowCodeBotNodeManager.saveBotConf(botId, confIdList);
        if (res) {
            initLowCodeNodeCache();
        }
        return res;
    }

    @Override
    public List<LowCodeNodeConf> listNodeConf() {
        return lowCodeNodeConfManager.listNodeConf();
    }

    @Override
    public List<Long> getBotNode(Long botId) {
        List<LowCodeBotNode> list = lowCodeBotNodeManager.lowCodeBotNodeManager(botId);
        return list.stream().map(LowCodeBotNode::getConfId).toList();
    }

    @Override
    public Long copyNodeConf(Long id) {
        BotNodeDto nodeConf = getNodeConf(id);
        nodeConf.setConfName("副本-" + nodeConf.getConfName());
        return saveNodeConf(nodeConf);
    }

    @PostConstruct
    @Override
    public void initLowCodeNodeCache() {
        //缓存节点配置缓存
        List<LowCodeNodeConf> confList = lowCodeNodeConfManager.list();
        CacheManager.NODE_CONF_CACHE.clear();
        confList.forEach(conf -> CacheManager.NODE_CONF_CACHE.put(conf.getId(), conf));
        //缓存机器人与节点关联缓存
        List<LowCodeBotNode> list = lowCodeBotNodeManager.list();
        Map<Long, List<LowCodeBotNode>> groupMap = list.stream().collect(Collectors.groupingBy(LowCodeBotNode::getBotId));
        CacheManager.BOT_NODE_CONF_CACHE.clear();
        groupMap.forEach((key, value) -> CacheManager.BOT_NODE_CONF_CACHE.put(key, value.stream().map(LowCodeBotNode::getConfId).toList()));
    }


}
