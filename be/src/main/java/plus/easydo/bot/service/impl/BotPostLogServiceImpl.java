package plus.easydo.bot.service.impl;


import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.BotPostLog;
import plus.easydo.bot.manager.BotPostLogManager;
import plus.easydo.bot.qo.PostLogQo;
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

    private final BotPostLogManager botPostLogManager;

    @Override
    public Page<BotPostLog> pagePostLog(PostLogQo pageQo) {
        return botPostLogManager.pagePostLog(pageQo);
    }

    @Override
    public boolean cleanPostLog() {
        return botPostLogManager.clean();
    }
}
