package plus.easydo.bot.manager;


import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.BotNodeConfExecuteLog;
import plus.easydo.bot.mapper.BotNodeConfExecuteLogMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import plus.easydo.bot.vo.NodePAVo;

import java.util.List;

/**
 * 节点配置执行日志 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class BotNodeConfExecuteLogManager extends ServiceImpl<BotNodeConfExecuteLogMapper, BotNodeConfExecuteLog> {

    public List<NodePAVo> nodeConfExecutePa() {
        return mapper.nodeConfExecutePa();
    }
}
