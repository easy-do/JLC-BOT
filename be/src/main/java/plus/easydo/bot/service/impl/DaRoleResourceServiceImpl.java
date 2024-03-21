package plus.easydo.bot.service.impl;


import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.easydo.bot.service.IDaRoleResourceService;
import plus.easydo.bot.entity.DaRoleResource;
import plus.easydo.bot.mapper.DaRoleResourceMapper;

import java.util.List;

import static plus.easydo.bot.entity.table.DaRoleResourceTableDef.DA_ROLE_RESOURCE;

/**
 * 角色和资源关联表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaRoleResourceServiceImpl extends ServiceImpl<DaRoleResourceMapper, DaRoleResource> implements IDaRoleResourceService {

    @Override
    public void removeByRoleId(Long roleId) {
        QueryWrapper queryWrapper = query().and(DA_ROLE_RESOURCE.ROLE_ID.eq(roleId));
        remove(queryWrapper);
    }

    @Override
    public List<DaRoleResource> listByRoleIds(List<Long> roleIds) {
        QueryWrapper query = query().and(DA_ROLE_RESOURCE.ROLE_ID.in(roleIds));
        return list(query);
    }
}
