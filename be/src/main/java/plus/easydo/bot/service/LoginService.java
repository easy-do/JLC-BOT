package plus.easydo.bot.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.bot.config.SystemConfig;
import plus.easydo.bot.constant.SystemConstant;
import plus.easydo.bot.dto.LoginDto;
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.vo.CurrentUser;

import java.util.Collections;
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

    private final SystemConfig systemConfig;

    private final SystemConfService systemConfService;


    /**
     * 登录
     *
     * @param loginDto loginDto
     * @return java.lang.String
     * @author laoyu
     * @date 2023/10/14
     */
    public String login(LoginDto loginDto) {
        SystemConf userNameConf = systemConfService.getByConfKey(SystemConstant.USER_NAME);
        SystemConf passwordConf = systemConfService.getByConfKey(SystemConstant.PASSWORD);
        if(Objects.isNull(userNameConf) || Objects.isNull(passwordConf)){
            throw new BaseException("没有找到用户名和密码配置");
        }
        if (CharSequenceUtil.equals(loginDto.getUserName(),userNameConf.getConfData()) && CharSequenceUtil.equals(loginDto.getPassword(),passwordConf.getConfData())) {
            StpUtil.login(1);
            return StpUtil.getTokenValue();
        }
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
        StpUtil.checkLogin();
        CurrentUser currentUser = new CurrentUser();
        currentUser.setMode(systemConfig.getMode());
        currentUser.setUserName(systemConfService.getByConfKey(SystemConstant.USER_NAME).getConfData());
        currentUser.setMenu(Collections.emptyList());
        currentUser.setResource(Collections.emptyList());
        return currentUser;
    }



}
