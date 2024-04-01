package plus.easydo.bot.service.impl;


import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.BotNodeExecuteLog;
import plus.easydo.bot.manager.BotNodeExecuteLogManager;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.BotNodeExecuteLogService;

/**
 * 节点执行日志 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class BotNodeExecuteLogServiceImpl implements BotNodeExecuteLogService {

    private final BotNodeExecuteLogManager botNodeExecuteLogManager;

    @Override
    public Page<BotNodeExecuteLog> pageNodeConfExecuteLog(PageQo pageQo) {
        return botNodeExecuteLogManager.pageNodeConfExecuteLog(pageQo);
    }

    @Override
    public boolean cleanNodeExecuteLog() {
        return botNodeExecuteLogManager.clean();
    }
}
