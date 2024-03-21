package plus.easydo.bot.config;

import cn.dev33.satoken.stp.StpInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return Collections.emptyList();
    }
}
