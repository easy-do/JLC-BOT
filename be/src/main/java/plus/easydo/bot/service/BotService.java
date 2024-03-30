package plus.easydo.bot.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.dto.EnableBotScriptDto;
import plus.easydo.bot.entity.BotConf;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.BotMessage;
import plus.easydo.bot.entity.BotNotice;
import plus.easydo.bot.entity.BotRequest;
import plus.easydo.bot.qo.BotMessageQo;
import plus.easydo.bot.qo.BotNoticeQo;
import plus.easydo.bot.qo.BotQo;
import plus.easydo.bot.qo.BotRequestQo;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 机器人相关
 * @date 2024/2/21
 */

public interface BotService {
    Page<BotInfo> pageBot(BotQo botQo);

    Boolean addBot(BotInfo botInfo);

    boolean updateBot(BotInfo botInfo);

    boolean removeBot(List<String> ids);

    void initBotCache();

    BotInfo infoBot(Long id);

    void initBotConfCache();

    Page<BotMessage> pageBotMessage(BotMessageQo botMessageQo);

    Page<BotRequest> pageBotRequest(BotRequestQo botRequestQo);

    Page<BotNotice> pageBotNotice(BotNoticeQo botNoticeQo);

    List<BotConf> getBotConf(String botNumber);

    boolean updateBotConf(BotConf botConf);

    boolean addBotConf(BotConf botConf);

    boolean removeBotConf(Long id);

    boolean removeBotConf(String botNumber, String key);

    BotConf getByBotNumberAndKey(String botNumber, String key);

    BotInfo getByBotNumber(String botNumber);

    List<Long> getEnableBotScript(Long id);

    boolean enableBotScript(EnableBotScriptDto enableBotScriptDto);

    boolean removeBotConfLike(String botNumber, String key);

    OneBotApiService getApiServer(String botNumber);
}
