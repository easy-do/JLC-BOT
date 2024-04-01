package plus.easydo.bot.manager;


import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotMessage;
import plus.easydo.bot.mapper.BotMessageMapper;

/**
 * 消息记录 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotMessageManager extends ServiceImpl<BotMessageMapper, BotMessage> {

    public boolean clean() {
        QueryWrapper query = query().where("1=1");
        return remove(query);
    }
}
