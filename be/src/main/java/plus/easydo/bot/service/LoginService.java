package plus.easydo.bot.service;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.bot.config.SystemConfig;
import plus.easydo.bot.dto.LoginDto;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.vo.CurrentUser;

import java.util.List;
import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 * @description 登录工具类
 * @date 2023/10/11
 */
@Component
@RequiredArgsConstructor
public class LoginService {


    private final IDaRoleService roleService;

    private final IDaResourceService resourceService;

    private final SystemConfig systemConfig;

    private final CaptchaService captchaService;


    /**
     * 登录
     *
     * @param loginDto loginDto
     * @return java.lang.String
     * @author laoyu
     * @date 2023/10/14
     */
    public String login(LoginDto loginDto) {
        if (!captchaService.verify(loginDto.getCaptchaKey(),loginDto.getVerificationCode())) {
            throw new BaseException("验证码错误。");
        }

//        if (Objects.isNull(accounts)) {
//            throw new BaseException("账号不存在或密码错误");
//        }
//        String md5Password = SecureUtil.md5().digestHex(loginDto.getPassword());
//        if (CharSequenceUtil.equals(md5Password, accounts.getPassword())) {
//
//            StpUtil.login(1, SaLoginConfig
//                    .setExtra("userInfo", JSONUtil.toJsonPrettyStr(accounts)));
//            return StpUtil.getTokenValue();
//        }
        throw new BaseException("账号不存在或密码错误");
    }

    /**
     * 退出
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2023/10/14
     */
    public void logout() {
        StpUtil.logout();
    }


    public CurrentUser currentUser() {
        Object userInfo = StpUtil.getExtra("userInfo");
        JSONObject userJson = JSONUtil.parseObj(userInfo);
        userJson.remove("password");
        userJson.set("mode",systemConfig.getMode());
        userJson.set("menu",resourceService.userResource());
        userJson.set("role",roleService.userRoleCodes());
        userJson.set("resource",resourceService.userResourceCodes());
        return JSONUtil.toBean(userJson, CurrentUser.class);
    }



}
