package plus.easydo.bot.service.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.entity.LowCodeSysNode;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.manager.LiteFlowScriptManager;
import plus.easydo.bot.mapper.LowCodeSysNodeMapper;
import plus.easydo.bot.qo.SysNodeQo;
import plus.easydo.bot.service.LowCodeSysNodeService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static plus.easydo.bot.entity.table.LowCodeSysNodeTableDef.LOW_CODE_SYS_NODE;

/**
 * 系统节点信息 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class LowCodeSysNodeServiceImpl extends ServiceImpl<LowCodeSysNodeMapper, LowCodeSysNode> implements LowCodeSysNodeService {

    private final LiteFlowScriptManager liteFlowScriptManager;

    @Override
    public Map<String, List<LowCodeSysNode>> listSysNode() {
        List<LowCodeSysNode> list = list();
        return list.stream().collect(Collectors.groupingBy(LowCodeSysNode::getGroupType));
    }

    private void checkNodeCode(String nodeCode) {
        QueryWrapper query = query().where(LOW_CODE_SYS_NODE.NODE_CODE.eq(nodeCode));
        if (exists(query)) {
            throw new BaseException("节点编号重复");
        }
    }

    private void checkNodeCode(String nodeCode, Long id) {
        if (Objects.nonNull(nodeCode) && Objects.nonNull(id)) {
            QueryWrapper query = query().where(LOW_CODE_SYS_NODE.NODE_CODE.eq(nodeCode).and(LOW_CODE_SYS_NODE.ID.ne(id)));
            if (exists(query)) {
                throw new BaseException("节点编号重复");
            }
        }
    }

    private void checkNodeName(String nodeName) {
        QueryWrapper query = query().where(LOW_CODE_SYS_NODE.NODE_NAME.eq(nodeName));
        if (exists(query)) {
            throw new BaseException("节点名称重复");
        }
    }

    private void checkNodeName(String nodeName, Long id) {
        if (Objects.nonNull(nodeName) && Objects.nonNull(id)) {
            QueryWrapper query = query().where(LOW_CODE_SYS_NODE.NODE_NAME.eq(nodeName).and(LOW_CODE_SYS_NODE.ID.ne(id)));
            if (exists(query)) {
                throw new BaseException("节点名称重复");
            }
        }
    }

    @Override
    public boolean saveSysNode(LowCodeSysNode lowCodeSysNode) {
        // TODO 校验节点编码是否合规
        checkNodeCode(lowCodeSysNode.getNodeCode());
        checkNodeName(lowCodeSysNode.getNodeName());
        boolean res = save(lowCodeSysNode);
        if (res && Boolean.FALSE.equals(lowCodeSysNode.getSystemNode())) {
            try {
                liteFlowScriptManager.createData(lowCodeSysNode);
            }catch (Exception e){
                removeById(lowCodeSysNode.getId());
                throw new BaseException("创建脚本失败:"+e.getMessage());
            }
        }
        return res;
    }

    @Override
    public boolean updateSysNode(LowCodeSysNode lowCodeSysNode) {
        // TODO 校验节点编码是否合规
        checkNodeCode(lowCodeSysNode.getNodeCode(), lowCodeSysNode.getId());
        checkNodeName(lowCodeSysNode.getNodeName(), lowCodeSysNode.getId());
        lowCodeSysNode.setFormData(null);
        return updateById(lowCodeSysNode);
    }

    @Override
    public boolean removeSysNode(Serializable id) {
        boolean res = removeById(id);
        if (res) {
            liteFlowScriptManager.removeById(id);
        }
        return res;
    }

    @Override
    public boolean updateSysNodeFormData(LowCodeSysNode lowCodeSysNode) {
        LowCodeSysNode entity = LowCodeSysNode.builder()
                .id(lowCodeSysNode.getId())
                .formData(lowCodeSysNode.getFormData())
                .build();
        return updateById(entity);
    }

    @Override
    public LiteFlowScript getSysNodeScriptData(Serializable id) {
        return liteFlowScriptManager.getById(id);
    }

    @Override
    public Long importNode(LowCodeSysNode sysNode) {
        saveSysNode(sysNode);
        if(Boolean.FALSE.equals(sysNode.getSystemNode())){
            try {
                liteFlowScriptManager.createData(sysNode);
            }catch (Exception e){
                removeById(sysNode.getId());
                return null;
            }
        }
        return sysNode.getId();
    }

    @Override
    public LowCodeSysNode getSysNodeInfo(Serializable id) {
        LowCodeSysNode node = getById(id);
        if(Objects.nonNull(node) && Boolean.FALSE.equals(node.getSystemNode())){
            node.setScript(liteFlowScriptManager.getById(id));
        }
        return node;
    }

    @Override
    public Page<LowCodeSysNode> pageSysNode(SysNodeQo sysNodeQo) {
        QueryWrapper queryWrapper = query();
        queryWrapper.where(LOW_CODE_SYS_NODE.NODE_NAME.eq(sysNodeQo.getNodeName()));
        queryWrapper.where(LOW_CODE_SYS_NODE.GROUP_TYPE.eq(sysNodeQo.getGroupType()));
        queryWrapper.where(LOW_CODE_SYS_NODE.NODE_CODE.eq(sysNodeQo.getNodeCode()));
        queryWrapper.where(LOW_CODE_SYS_NODE.SYSTEM_NODE.eq(sysNodeQo.getSystemNode()));
        queryWrapper.where(LOW_CODE_SYS_NODE.REMARK.like(sysNodeQo.getRemark()));
        return page(new Page<>(sysNodeQo.getCurrent(), sysNodeQo.getPageSize()), queryWrapper);
    }
}
