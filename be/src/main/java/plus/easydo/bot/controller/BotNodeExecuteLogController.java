package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.entity.BotNodeExecuteLog;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.BotNodeExecuteLogService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.util.List;

/**
 * 节点执行日志 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nodeExecuteLog")
public class BotNodeExecuteLogController {

    private final BotNodeExecuteLogService botNodeExecuteLogService;

    /**
     * @param pageQo pageQo
     * @return plus.easydo.bot.vo.R<java.util.List < plus.easydo.bot.entity.BotNodeConfExecuteLog>>
     * @author laoyu
     * @date 2024-04-01
     */
    @SaCheckLogin
    @PostMapping("/page")
    @Operation(summary = "分页查询")
    public R<List<BotNodeExecuteLog>> pageNodeExecuteLog(@RequestBody PageQo pageQo) {
        return DataResult.ok(botNodeExecuteLogService.pageNodeConfExecuteLog(pageQo));
    }

    /**
     * 清空日志
     *
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-04-01
     */
    @SaCheckLogin
    @GetMapping("/clean")
    @Operation(summary = "清空")
    public R<Boolean> cleanNodeExecuteLog() {
        return DataResult.ok(botNodeExecuteLogService.cleanNodeExecuteLog());
    }
}
