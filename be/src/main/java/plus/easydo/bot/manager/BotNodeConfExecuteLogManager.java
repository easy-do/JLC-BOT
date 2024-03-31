package plus.easydo.bot.manager;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotNodeConfExecuteLog;
import plus.easydo.bot.mapper.BotNodeConfExecuteLogMapper;
import plus.easydo.bot.vo.NodePAVo;

import java.util.List;

/**
 * 节点配置执行日志 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotNodeConfExecuteLogManager extends ServiceImpl<BotNodeConfExecuteLogMapper, BotNodeConfExecuteLog> {

    public List<NodePAVo> nodeConfExecutePa() {
        return mapper.nodeConfExecutePa();
    }

    public List<NodePAVo> nodeConfExecuteTop() {
        return mapper.nodeConfExecuteTop();
    }
}
