package plus.easydo.bot.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import plus.easydo.bot.entity.DaRole;
import plus.easydo.bot.qo.DaRoleQo;

import java.util.List;

/**
 * 角色信息表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaRoleService extends IService<DaRole> {

    List<DaRole> userRole();

    List<String> userRoleCodes(Long userId);
    List<String> userRoleCodes();

    void bindingUserRole(Long userId, String roleName);

    Page<DaRole> pageRole(DaRoleQo daRoleQo);

    void bindingDefaultRole(Long uid);
}
