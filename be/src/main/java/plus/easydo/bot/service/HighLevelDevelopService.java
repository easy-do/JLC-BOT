package plus.easydo.bot.service;


import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.dto.SetBotConfIdDto;
import plus.easydo.bot.entity.HighLevelDevelopConf;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.qo.HighLevelDevelopConfQo;

import java.util.List;

/**
 * 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface HighLevelDevelopService {

    Page<HighLevelDevelopConf> highLevelDevPage(HighLevelDevelopConfQo highLevelDevelopConfQo);

    List<HighLevelDevelopConf> highLevelDevList();

    boolean saveHighLevelDev(HighLevelDevelopConf highLevelDevelopConf);

    boolean removeHighLevelDev(Long id);

    boolean updateHighLevelDev(HighLevelDevelopConf highLevelDevelopConf);

    HighLevelDevelopConf getHighLevelDevInfo(Long id);

    Long importConf(HighLevelDevelopConf conf);

    CmpStepResult debug(DebugDto debugDto);

    void initCache();

    List<Long> getBotHighLevelDevelop(Long botId);

    boolean setBotHighLevelDevelop(SetBotConfIdDto setBotConfIdDto);
}
