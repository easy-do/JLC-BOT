package plus.easydo.bot.service;


import plus.easydo.bot.entity.LowCodeSysNode;
import com.mybatisflex.core.service.IService;

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

}
