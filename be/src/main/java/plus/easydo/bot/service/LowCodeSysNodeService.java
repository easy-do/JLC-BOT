package plus.easydo.bot.service;


import com.mybatisflex.core.service.IService;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.entity.LowCodeSysNode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 系统节点信息 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface LowCodeSysNodeService extends IService<LowCodeSysNode> {

    Map<String, List<LowCodeSysNode>> listSysNode();

    boolean saveSysNode(LowCodeSysNode lowCodeSysNode);

    boolean updateSysNode(LowCodeSysNode lowCodeSysNode);

    boolean removeSysNode(Serializable id);

    boolean updateSysNodeFormData(LowCodeSysNode lowCodeSysNode);

    LiteFlowScript getSysNodeScriptData(Serializable id);

    Long importNode(LowCodeSysNode sysNode);

    LowCodeSysNode getSysNodeInfo(Serializable id);
}
