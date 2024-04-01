package plus.easydo.bot.manager;


import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotNotice;
import plus.easydo.bot.mapper.BotNoticeMapper;

/**
 * 通知记录 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotNoticeManager extends ServiceImpl<BotNoticeMapper, BotNotice> {

    public boolean clean() {
        QueryWrapper query = query().where("1=1");
        return remove(query);
    }
}
