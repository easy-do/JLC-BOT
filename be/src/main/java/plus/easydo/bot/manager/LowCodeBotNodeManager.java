package plus.easydo.bot.manager;


import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.LowCodeBotNode;
import plus.easydo.bot.mapper.LowCodeBotNodeMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static plus.easydo.bot.entity.table.LowCodeBotNodeTableDef.LOW_CODE_BOT_NODE;

/**
 * 机器人与节点配置关联表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class LowCodeBotNodeManager extends ServiceImpl<LowCodeBotNodeMapper, LowCodeBotNode> {

    public boolean clearBotConf(Long botId) {
        QueryWrapper query = query().and(LOW_CODE_BOT_NODE.BOT_ID.eq(botId));
        return remove(query);
    }

    public boolean saveBotConf(Long botId, List<Long> confIdList) {
        List<LowCodeBotNode> list = new ArrayList<>();
        confIdList.forEach(confId->{
            LowCodeBotNode entity = LowCodeBotNode.builder()
                    .botId(botId)
                    .confId(confId)
                    .build();
            list.add(entity);
        });
        return saveBatch(list);
    }

    public List<LowCodeBotNode> lowCodeBotNodeManager(Long botId) {
        QueryWrapper query = query().and(LOW_CODE_BOT_NODE.BOT_ID.eq(botId));
        return list(query);
    }
}
