package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.service.PerformanceAnalysisService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.NodePAVo;
import plus.easydo.bot.vo.R;

import java.util.List;
import java.util.Map;


/**
 * @author laoyu
 * @version 1.0
 * @description 性能分析
 * @date 2024/3/23
 */
@Slf4j
@RestController
@Tag(name = "性能分析")
@RequiredArgsConstructor
@RequestMapping("/api/pa")
public class PerformanceAnalysisController {


    private final PerformanceAnalysisService performanceAnalysisService;

    /**
     * 查询所有系统节点信息
     *
     * @return 所有数据
     */
    @SaCheckLogin
    @Operation(summary = "节点执行分析")
    @GetMapping("/nodeExecutePa")
    public R<Map<String, List<NodePAVo>>> nodeExecutePa() {
        return DataResult.ok(performanceAnalysisService.nodeExecutePa());
    }

}
