package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.service.LiteFlowScriptService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

/**
 * @author yuzhanfeng
 * @Date 2024-04-11
 * @Description liteflowScript
 */
@RestController
@Tag(name = "liteflowScript")
@RequiredArgsConstructor
@RequestMapping("/api/fls")
public class LiteFlowScriptController {

    private final LiteFlowScriptService liteFlowScriptService;

    @SaCheckLogin
    @Operation(summary = "更新脚本配置")
    @PostMapping("/update")
    public R<Boolean> updateLiteFlowScript(@RequestBody LiteFlowScript lowCodeSysNode) {
        return DataResult.ok(liteFlowScriptService.updateLiteFlowScript(lowCodeSysNode));
    }

}
