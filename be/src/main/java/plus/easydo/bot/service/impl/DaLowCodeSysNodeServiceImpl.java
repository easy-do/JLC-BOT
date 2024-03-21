package plus.easydo.bot.service.impl;


import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.DaLowCodeSysNode;
import plus.easydo.bot.mapper.DaLowCodeSysNodeMapper;
import plus.easydo.bot.service.DaLowCodeSysNodeService;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统节点信息 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaLowCodeSysNodeServiceImpl extends ServiceImpl<DaLowCodeSysNodeMapper, DaLowCodeSysNode> implements DaLowCodeSysNodeService {

    @Override
    public Map<String, List<DaLowCodeSysNode>> listSysNode() {
        List<DaLowCodeSysNode> list = list();
        return list.stream().collect(Collectors.groupingBy(DaLowCodeSysNode::getGroupType));
    }
}
