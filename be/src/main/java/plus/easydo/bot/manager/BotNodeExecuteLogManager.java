package plus.easydo.bot.manager;


import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotNodeExecuteLog;
import plus.easydo.bot.mapper.BotNodeExecuteLogMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import plus.easydo.bot.vo.NodePAVo;

import java.util.List;

/**
 * 节点执行日志 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotNodeExecuteLogManager extends ServiceImpl<BotNodeExecuteLogMapper, BotNodeExecuteLog>{

    public List<NodePAVo> nodeExecutePa() {
        return mapper.nodeExecutePa();
    }

    public List<NodePAVo> nodeExecuteTop() {
        return mapper.nodeExecuteTop();
    }
}
