package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.service.SandboxService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

/**
 * @author yuzhanfeng
 * @Date 2024-03-30
 * @Description 沙箱
 */
@Slf4j
@RestController
@RequestMapping("/api/sandbox")
@RequiredArgsConstructor
public class SandBoxController {

    private final SandboxService sandBoxService;

    @SaCheckLogin
    @PostMapping("/sendMessage")
    public R<Boolean> sendMessage(JSONObject message) {
        return DataResult.ok(sandBoxService.sendMessage(message));
    }

}
