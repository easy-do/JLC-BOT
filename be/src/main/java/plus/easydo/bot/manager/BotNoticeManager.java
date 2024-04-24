package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotNotice;
import plus.easydo.bot.mapper.BotNoticeMapper;
import plus.easydo.bot.qo.BotNoticeQo;

import static plus.easydo.bot.entity.table.BotNoticeTableDef.BOT_NOTICE;

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

    public Page<BotNotice> pageBotNotice(BotNoticeQo botNoticeQo) {
        QueryWrapper queryWrapper = query();
        queryWrapper.where(BOT_NOTICE.GROUP_ID.eq(botNoticeQo.getGroupId()));
        queryWrapper.where(BOT_NOTICE.MESSAGE_ID.eq(botNoticeQo.getMessageId()));
        queryWrapper.where(BOT_NOTICE.NOTICE_TYPE.eq(botNoticeQo.getNoticeType()));
        queryWrapper.where(BOT_NOTICE.SELF_USER.eq(botNoticeQo.getSelfUser()));
        queryWrapper.where(BOT_NOTICE.SUB_TYPE.eq(botNoticeQo.getSubType()));
        queryWrapper.where(BOT_NOTICE.OPERATOR_ID.eq(botNoticeQo.getOperatorId()));
        queryWrapper.where(BOT_NOTICE.USER_ID.eq(botNoticeQo.getUserId()));
        queryWrapper.orderBy(BOT_NOTICE.SELF_TIME.desc());
        return page(new Page<>(botNoticeQo.getCurrent(), botNoticeQo.getPageSize()), queryWrapper);
    }
}
