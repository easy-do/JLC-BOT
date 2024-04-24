package plus.easydo.bot.service;


import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.dto.SetBotConfIdDto;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.qo.SimpleCmdDevelopConfQo;

import java.util.List;

/**
 * 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface SimpleDevelopService {

    List<SimpleCmdDevelopConf> listSimpleDevelop();

    Page<SimpleCmdDevelopConf> pageSimpleDevelop(SimpleCmdDevelopConfQo simpleCmdDevelopConfQo);

    SimpleCmdDevelopConf getSimpleDevelopInfo(Long id);

    boolean saveSimpleDevelop(SimpleCmdDevelopConf simpleCmdDevelopConf);

    boolean updateSimpleDevelop(SimpleCmdDevelopConf simpleCmdDevelopConf);

    boolean removeSimpleDevelop(Long id);

    Long importConf(SimpleCmdDevelopConf conf);

    CmpStepResult debug(DebugDto debugDto);

    void initCache();

    List<Long> getSimpleCmdDevelop(Long botId);

    boolean setBotSimpleCmdDevelop(SetBotConfIdDto setBotConfIdDto);
}
