package plus.easydo.bot.service;


import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.entity.BotNodeExecuteLog;
import plus.easydo.bot.qo.PageQo;

/**
 * 节点执行日志 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface BotNodeExecuteLogService {

    Page<BotNodeExecuteLog> pageNodeConfExecuteLog(PageQo pageQo);

    boolean cleanNodeExecuteLog();
}
