package plus.easydo.bot.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.SystemConstant;
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.service.SystemConfService;

import java.util.Collections;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 自定义权限验证接口扩展
 * @date 2023/10/19
 */
@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final SystemConfService systemConfService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        SystemConf resourceConf = systemConfService.getByConfKey(SystemConstant.RESOURCE);
        JSONArray resArray = JSONUtil.parseArray(resourceConf.getConfData());
        return resArray.stream().map(String::valueOf).toList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return Collections.emptyList();
    }
}
