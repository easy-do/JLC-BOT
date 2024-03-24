package plus.easydo.bot.service;

import plus.easydo.bot.vo.NodePAVo;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 性能分析服务
 * @date 2024/3/23
 */

public interface PerformanceAnalysisService {
    Map<String, List<NodePAVo>> nodeExecutePa();

}
