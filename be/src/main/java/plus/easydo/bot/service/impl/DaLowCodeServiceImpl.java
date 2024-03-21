package plus.easydo.bot.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.entity.DaLowCodeBotNode;
import plus.easydo.bot.entity.DaLowCodeNodeConf;
import plus.easydo.bot.lowcode.exec.NodeExecuteServer;
import plus.easydo.bot.manager.DaLowCodeBotNodeManager;
import plus.easydo.bot.manager.DaLowCodeNodeConfManager;
import plus.easydo.bot.dto.BotNodeDto;
import plus.easydo.bot.dto.DebugBotNodeDto;
import plus.easydo.bot.lowcode.model.NodeExecuteResult;
import plus.easydo.bot.dto.SetBotNodeDto;
import plus.easydo.bot.service.DaLowCodeService;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.qo.PageQo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author laoyu
 * @version 1.0
 * @description 低代码服务实现
 * @date 2024/3/5
 */
@Service
@RequiredArgsConstructor
public class DaLowCodeServiceImpl implements DaLowCodeService {

    private final DaLowCodeNodeConfManager lowCodeNodeConfManager;

    private final NodeExecuteServer nodeExecuteServer;

    private final DaLowCodeBotNodeManager lowCodeBotNodeManager;

    @Override
    public Long saveNodeConf(BotNodeDto botNodeDto) {
        DaLowCodeNodeConf daLowCodeNodeConf = DaLowCodeNodeConf.builder()
                .confName(botNodeDto.getConfName())
                .eventType(botNodeDto.getEventType())
                .nodeData(JSONUtil.toJsonPrettyStr(botNodeDto.getNodes()))
                .confData(JSONUtil.toJsonPrettyStr(botNodeDto.getNodeConf()))
                .createTime(LocalDateTimeUtil.now())
                .build();
        boolean res = lowCodeNodeConfManager.save(daLowCodeNodeConf);
        if(res){
            initLowCodeNodeCache();
        }
        return daLowCodeNodeConf.getId();
    }

    @Override
    public boolean updateNodeConf(BotNodeDto botNodeDto) {
        DaLowCodeNodeConf daLowCodeNodeConf = DaLowCodeNodeConf.builder()
                .id(botNodeDto.getId())
                .eventType(botNodeDto.getEventType())
                .confName(botNodeDto.getConfName())
                .nodeData(JSONUtil.toJsonPrettyStr(botNodeDto.getNodes()))
                .confData(JSONUtil.toJsonPrettyStr(botNodeDto.getNodeConf()))
                .updateTime(LocalDateTimeUtil.now())
                .build();
        boolean res = lowCodeNodeConfManager.updateById(daLowCodeNodeConf);
        if(res){
            initLowCodeNodeCache();
        }
        return res;
    }

    @Override
    public BotNodeDto getNodeConf(Long id) {
        DaLowCodeNodeConf conf = lowCodeNodeConfManager.getById(id);
        if(Objects.isNull(conf)){
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
    public Page<DaLowCodeNodeConf> pageNodeConf(PageQo pageQo) {
        return lowCodeNodeConfManager.pageNodeConf(pageQo);
    }

    @Override
    public boolean removeNodeConf(Long id) {
        boolean res = lowCodeNodeConfManager.removeById(id);
        if(res){
            initLowCodeNodeCache();
        }
        return res;
    }

    @Override
    public List<NodeExecuteResult> debugNodeConf(DebugBotNodeDto debugBotNodeDto) {
        DaLowCodeNodeConf conf = lowCodeNodeConfManager.getById(debugBotNodeDto.getId());
        if(Objects.isNull(conf)){
            throw new BaseException("配置不存在");
        }
        return nodeExecuteServer.execute(conf,debugBotNodeDto.getParams());
    }

    @Override
    public boolean setBotNode(SetBotNodeDto setBotNodeDto) {
        Long botId = setBotNodeDto.getBotId();
        if(Objects.isNull(botId)){
            throw new BaseException("机器人id不能为空");
        }
        List<Long> confIdList = setBotNodeDto.getConfIdList();
        if(Objects.isNull(confIdList) || confIdList.isEmpty()){
          return lowCodeBotNodeManager.clearBotConf(botId);
        }
        return lowCodeBotNodeManager.saveBotConf(botId,confIdList);
    }

    @Override
    public List<DaLowCodeNodeConf> listNodeConf() {
        return lowCodeNodeConfManager.listNodeConf();
    }

    @Override
    public List<Long> getBotNode(Long botId) {
        List<DaLowCodeBotNode> list = lowCodeBotNodeManager.lowCodeBotNodeManager(botId);
        return list.stream().map(DaLowCodeBotNode::getConfId).toList();
    }

    @PostConstruct
    @Override
    public void initLowCodeNodeCache() {
        //缓存节点配置缓存
        List<DaLowCodeNodeConf> confList = lowCodeNodeConfManager.list();
        CacheManager.NODE_CONF_CACHE.clear();
        confList.forEach(conf-> CacheManager.NODE_CONF_CACHE.put(conf.getId(),conf));
        //缓存机器人与节点关联缓存
        List<DaLowCodeBotNode> list = lowCodeBotNodeManager.list();
        Map<Long, List<DaLowCodeBotNode>> groupMap = list.stream().collect(Collectors.groupingBy(DaLowCodeBotNode::getBotId));
        CacheManager.BOT_NODE_CONF_CACHE.clear();
        groupMap.forEach((key,value)-> CacheManager.BOT_NODE_CONF_CACHE.put(key,value.stream().map(DaLowCodeBotNode::getConfId).toList()));
    }


}
