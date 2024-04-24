package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.entity.BotPostLog;
import plus.easydo.bot.qo.PostLogQo;
import plus.easydo.bot.service.BotPostLogService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.util.List;

/**
 * 接收消息日志 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "上报消息日志")
@RequestMapping("/api/botPostLog")
public class BotPostLogController {

    private final BotPostLogService botPostLogService;


    /**
     * 分页查询
     *
     * @param pageQo pageQo
     * @return plus.easydo.bot.vo.R<java.util.List < plus.easydo.bot.entity.BotPostLog>>
     * @author laoyu
     * @date 2024-04-01
     */
    @SaCheckLogin
    @PostMapping("/page")
    @Operation(summary = "分页查询")
    public R<List<BotPostLog>> pagePostLog(@RequestBody PostLogQo pageQo) {
        return DataResult.ok(botPostLogService.pagePostLog(pageQo));
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
    public R<Boolean> cleanPostLog() {
        return DataResult.ok(botPostLogService.cleanPostLog());
    }
}
