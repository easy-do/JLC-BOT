package plus.easydo.bot.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.dto.EnableBotScriptDto;
import plus.easydo.bot.entity.DaBotConf;
import plus.easydo.bot.entity.DaBotInfo;
import plus.easydo.bot.entity.DaBotMessage;
import plus.easydo.bot.entity.DaBotNotice;
import plus.easydo.bot.entity.DaBotRequest;
import plus.easydo.bot.qo.DaBotMessageQo;
import plus.easydo.bot.qo.DaBotNoticeQo;
import plus.easydo.bot.qo.DaBotQo;
import plus.easydo.bot.qo.DaBotRequestQo;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 机器人相关
 * @date 2024/2/21
 */

public interface BotService {
    Page<DaBotInfo> pageBot(DaBotQo daBotQo);

    Boolean addBot(DaBotInfo daBotInfo);

    boolean updateBot(DaBotInfo daBotInfo);

    boolean removeBot(List<String> ids);

    void initBotCache();

    DaBotInfo infoBot(Long id);

    void initBotConfCache();

    Page<DaBotMessage> pageBotMessage(DaBotMessageQo daBotMessageQo);

    Page<DaBotRequest> pageBotRequest(DaBotRequestQo daBotRequestQo);

    Page<DaBotNotice> pageBotNotice(DaBotNoticeQo daBotNoticeQo);

    List<DaBotConf> getBotConf(String botNumber);

    boolean updateBotConf(DaBotConf daBotConf);

    boolean addBotConf(DaBotConf daBotConf);

    boolean removeBotConf(Long id);
    boolean removeBotConf(String botNumber,String key);

    DaBotConf getByBotNumberAndKey(String botNumber, String key);

    List<Long> getEnableBotScript(Long id);

    boolean enableBotScript(EnableBotScriptDto enableBotScriptDto);

    boolean removeBotConfLike(String botNumber, String key);
}
