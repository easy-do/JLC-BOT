package plus.easydo.bot.service.impl;


import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.BotNodeConfExecuteLog;
import plus.easydo.bot.manager.BotNodeConfExecuteLogManager;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.BotNodeConfExecuteLogService;

/**
 * 节点配置执行日志 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class BotNodeConfExecuteLogServiceImpl implements BotNodeConfExecuteLogService {

    private final BotNodeConfExecuteLogManager botNodeConfExecuteLogManager;

    @Override
    public Page<BotNodeConfExecuteLog> pageNodeConfExecuteLog(PageQo pageQo) {
        return botNodeConfExecuteLogManager.pageNodeConfExecuteLog(pageQo);
    }

    @Override
    public boolean cleanNodeConfExecuteLog() {
        return botNodeConfExecuteLogManager.clean();
    }
}
