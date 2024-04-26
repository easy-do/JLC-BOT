package plus.easydo.bot.manager;


import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONObject;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.BotPostLog;
import plus.easydo.bot.mapper.BotPostLogMapper;
import plus.easydo.bot.qo.PostLogQo;
import plus.easydo.bot.util.OneBotUtils;

import static plus.easydo.bot.entity.table.BotPostLogTableDef.BOT_POST_LOG;
/**
 * 接收消息日志 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class BotPostLogManager extends ServiceImpl<BotPostLogMapper, BotPostLog> {

    public void saveLog(JSONObject messageJson) {
        BotInfo botInfo = OneBotUtils.getBotInfoByNumber(messageJson.getStr(OneBotConstants.SELF_ID));
        save(BotPostLog.builder()
                .postTime(LocalDateTimeUtil.now())
                .platform(botInfo.getPlatform())
                .message(messageJson.toJSONString(0)).build());
    }

    public boolean clean() {
        QueryWrapper query = query().where("1=1");
        return remove(query);
    }

    public Page<BotPostLog> pagePostLog(PostLogQo pageQo) {
        QueryWrapper queryWrapper = query();
        queryWrapper.where(BOT_POST_LOG.MESSAGE.like(pageQo.getMessage()));
        queryWrapper.where(BOT_POST_LOG.PLATFORM.eq(pageQo.getPlatform()));
        queryWrapper.orderBy(BOT_POST_LOG.POST_TIME.desc());
        return page(new Page<>(pageQo.getCurrent(), pageQo.getPageSize()), queryWrapper);
    }
}
