package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotRequest;
import plus.easydo.bot.mapper.BotRequestMapper;
import plus.easydo.bot.qo.BotRequestQo;

import static plus.easydo.bot.entity.table.BotRequestTableDef.BOT_REQUEST;

/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotRequestManager extends ServiceImpl<BotRequestMapper, BotRequest> {
    public boolean clean() {
        QueryWrapper query = query().where("1=1");
        return remove(query);
    }

    public Page<BotRequest> pageBotRequest(BotRequestQo botRequestQo) {
        QueryWrapper queryWrapper = query();
        queryWrapper.where(BOT_REQUEST.REQUEST_TYPE.eq(botRequestQo.getRequestType()));
        queryWrapper.where(BOT_REQUEST.GROUP_ID.eq(botRequestQo.getGroupId()));
        queryWrapper.where(BOT_REQUEST.SEND_USER.eq(botRequestQo.getSendUser()));
        queryWrapper.where(BOT_REQUEST.SELF_USER.eq(botRequestQo.getSelfUser()));
        queryWrapper.where(BOT_REQUEST.COMMENT.eq(botRequestQo.getComment()));
        queryWrapper.where(BOT_REQUEST.FLAG.eq(botRequestQo.getFlag()));
        queryWrapper.orderBy(BOT_REQUEST.SELF_TIME.desc());
        return page(new Page<>(botRequestQo.getCurrent(), botRequestQo.getPageSize()), queryWrapper);
    }
}
