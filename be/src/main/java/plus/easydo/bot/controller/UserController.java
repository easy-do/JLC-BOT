package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.dto.LoginDto;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;
import plus.easydo.bot.service.IDaResourceService;
import plus.easydo.bot.service.LoginService;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 用户相关
 * @date 2023/10/11
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;


    private final IDaResourceService resourceService;

    @Operation(summary = "登录")
    @SaIgnore
    @PostMapping("/login")
    public R<String> login(@Validated @RequestBody LoginDto loginDto){
        return DataResult.ok(loginService.login(loginDto));
    }

    @Operation(summary = "退出")
    @GetMapping("/logout")
    public R<String> logout(){
        loginService.logout();
        return DataResult.ok();
    }

    @Operation(summary = "获取当前用户资源信息")
    @SaCheckLogin
    @GetMapping("/userResource")
    public R<List<Tree<Long>>> userResource(){
        return DataResult.ok(resourceService.userResource());
    }

}

