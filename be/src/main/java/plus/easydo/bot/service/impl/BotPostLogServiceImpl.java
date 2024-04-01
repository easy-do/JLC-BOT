package plus.easydo.bot.service.impl;


import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.BotPostLog;
import plus.easydo.bot.manager.BotPostLogServiceManager;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.BotPostLogService;

/**
 * 接收消息日志 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class BotPostLogServiceImpl implements BotPostLogService {

    private final BotPostLogServiceManager botPostLogServiceManager;

    @Override
    public Page<BotPostLog> pagePostLog(PageQo pageQo) {
        return botPostLogServiceManager.page(new Page<>(pageQo.getCurrent(), pageQo.getPageSize()));
    }

    @Override
    public boolean cleanPostLog() {
        return botPostLogServiceManager.clean();
    }
}
