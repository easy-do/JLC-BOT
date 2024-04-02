package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.sandbox.SandboxMessage;
import plus.easydo.bot.service.SandboxService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-04-02
 * @Description 沙盒相关
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sandbox")
public class SandboxController {

    private final SandboxService sandboxService;

    @SaCheckLogin
    @Operation(summary = "机器人详情")
    @PostMapping("/sendMessage")
    public R<List<CmpStepResult>> sendSandboxMessage(@RequestBody SandboxMessage sandboxMessage) {
        return DataResult.ok(sandboxService.sendMessage(sandboxMessage));
    }

}
