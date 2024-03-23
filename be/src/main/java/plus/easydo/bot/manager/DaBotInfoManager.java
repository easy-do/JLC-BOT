package plus.easydo.bot.manager;


import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.mapper.DaBotInfoMapper;

import static plus.easydo.bot.entity.table.BotInfoTableDef.BOT_INFO;


/**
 * 机器人信息 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class DaBotInfoManager extends ServiceImpl<DaBotInfoMapper, BotInfo> {

    public boolean updateBybotNumber(BotInfo botInfo) {
        QueryWrapper queryWrapper = query().and(BOT_INFO.BOT_NUMBER.eq(botInfo.getBotNumber()));
        return update(botInfo,queryWrapper);
    }
}
