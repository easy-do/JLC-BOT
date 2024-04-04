package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.dto.LoginDto;
import plus.easydo.bot.service.LoginService;
import plus.easydo.bot.vo.CurrentUser;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

/**
 * @author laoyu
 * @version 1.0
 * @description 用户相关
 * @date 2023/10/11
 */
@RestController
@Tag(name = "用户")
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final LoginService loginService;


    @Operation(summary = "登录")
    @SaIgnore
    @PostMapping("/login")
    public R<String> login(@Validated @RequestBody LoginDto loginDto) {
        return DataResult.ok(loginService.login(loginDto));
    }


    @Operation(summary = "退出")
    @GetMapping("/logout")
    public R<String> logout() {
        loginService.logout();
        return DataResult.ok();
    }

    @Operation(summary = "获取当前用户信息")
    @SaCheckLogin
    @GetMapping("/currentUser")
    public R<CurrentUser> currentUser() {
        return DataResult.ok(loginService.currentUser());
    }
}

