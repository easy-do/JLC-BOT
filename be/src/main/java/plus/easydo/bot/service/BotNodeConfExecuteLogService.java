package plus.easydo.bot.service;


import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.entity.BotNodeConfExecuteLog;
import plus.easydo.bot.qo.PageQo;


/**
 * 节点配置执行日志 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface BotNodeConfExecuteLogService {

    Page<BotNodeConfExecuteLog> pageNodeConfExecuteLog(PageQo pageQo);

    boolean cleanNodeConfExecuteLog();
}
