package plus.easydo.bot.manager;


import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.DaLowCodeBotNode;
import plus.easydo.bot.mapper.DaLowCodeBotNodeMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static plus.easydo.bot.entity.table.DaLowCodeBotNodeTableDef.DA_LOW_CODE_BOT_NODE;

/**
 * 机器人与节点配置关联表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaLowCodeBotNodeManager extends ServiceImpl<DaLowCodeBotNodeMapper, DaLowCodeBotNode> {

    public boolean clearBotConf(Long botId) {
        QueryWrapper query = query().and(DA_LOW_CODE_BOT_NODE.BOT_ID.eq(botId));
        return remove(query);
    }

    public boolean saveBotConf(Long botId, List<Long> confIdList) {
        List<DaLowCodeBotNode> list = new ArrayList<>();
        confIdList.forEach(confId->{
            DaLowCodeBotNode entity = DaLowCodeBotNode.builder()
                    .botId(botId)
                    .confId(confId)
                    .build();
            list.add(entity);
        });
        return saveBatch(list);
    }

    public List<DaLowCodeBotNode> lowCodeBotNodeManager(Long botId) {
        QueryWrapper query = query().and(DA_LOW_CODE_BOT_NODE.BOT_ID.eq(botId));
        return list(query);
    }
}
