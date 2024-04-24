package plus.easydo.bot.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.dto.BotNodeDto;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.dto.SetBotConfIdDto;
import plus.easydo.bot.entity.LowCodeNodeConf;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.qo.NodeConfQo;

import java.util.List;


/**
 * @author laoyu
 * @version 1.0
 * @description 低代码相关
 * @date 2024/3/5
 */

public interface LowCodeService {

    Long saveNodeConf(BotNodeDto botNodeDto);

    boolean updateNodeConf(BotNodeDto botNodeDto);

    BotNodeDto getNodeConf(Long id);

    Page<LowCodeNodeConf> pageNodeConf(NodeConfQo nodeConfQo);

    boolean removeNodeConf(Long id);

    List<CmpStepResult> debugNodeConf(DebugDto debugDto);

    void initLowCodeNodeCache();

    boolean setBotNode(SetBotConfIdDto setBotConfIdDto);

    List<LowCodeNodeConf> listNodeConf();

    List<Long> getBotNode(Long botId);

    Long copyNodeConf(Long id);
}
