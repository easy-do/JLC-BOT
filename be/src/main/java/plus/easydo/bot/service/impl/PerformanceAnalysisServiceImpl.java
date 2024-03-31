package plus.easydo.bot.service.impl;

import cn.hutool.core.map.MapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.manager.BotNodeConfExecuteLogManager;
import plus.easydo.bot.manager.BotNodeExecuteLogManager;
import plus.easydo.bot.service.PerformanceAnalysisService;
import plus.easydo.bot.vo.NodePAVo;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 性能分析服务实现
 * @date 2024/3/23
 */
@Service
@RequiredArgsConstructor
public class PerformanceAnalysisServiceImpl implements PerformanceAnalysisService {

    private final BotNodeExecuteLogManager nodeExecuteLogManager;

    private final BotNodeConfExecuteLogManager nodeConfExecuteLogManager;

    @Override
    public Map<String, List<NodePAVo>> nodeExecutePa() {
        Map<String, List<NodePAVo>> result = MapUtil.newConcurrentHashMap(2);
        List<NodePAVo> node = nodeExecuteLogManager.nodeExecutePa();
        result.put("node", node);
        List<NodePAVo> nodeTop = nodeExecuteLogManager.nodeExecuteTop();
        result.put("nodeTop", nodeTop);
        List<NodePAVo> nodeConf = nodeConfExecuteLogManager.nodeConfExecutePa();
        result.put("nodeConf", nodeConf);
        List<NodePAVo> nodeConfTop = nodeConfExecuteLogManager.nodeConfExecuteTop();
        result.put("nodeConfTop", nodeConfTop);

        return result;
    }
}
