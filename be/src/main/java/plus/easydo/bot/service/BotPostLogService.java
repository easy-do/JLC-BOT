package plus.easydo.bot.service;


import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.entity.BotPostLog;
import plus.easydo.bot.qo.PostLogQo;


/**
 * 接收消息日志 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface BotPostLogService {

    Page<BotPostLog> pagePostLog(PostLogQo pageQo);

    boolean cleanPostLog();
}
