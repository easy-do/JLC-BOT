package plus.easydo.bot.service;


import cn.hutool.core.lang.tree.Tree;
import com.mybatisflex.core.service.IService;
import plus.easydo.bot.dto.AuthRoleResourceDto;
import plus.easydo.bot.entity.DaResource;

import java.util.List;

/**
 * 系统资源 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaResourceService extends IService<DaResource> {

    boolean authRoleResource(AuthRoleResourceDto authRoleResourceDto);

    List<Tree<Long>> roleResource(Long roleId);

    List<Long> roleResourceIds(Long roleId);

    List<Tree<Long>> resourceTree();

    List<String> userResource(Long userId);

    List<Tree<Long>> userResource();

    List<String> userResourceCodes();

}
