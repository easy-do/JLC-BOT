package plus.easydo.bot.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.dto.EnableBotScriptDto;
import plus.easydo.bot.entity.BotConf;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.BotMessage;
import plus.easydo.bot.entity.BotNotice;
import plus.easydo.bot.entity.BotRequest;
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
    Page<BotInfo> pageBot(DaBotQo daBotQo);

    Boolean addBot(BotInfo botInfo);

    boolean updateBot(BotInfo botInfo);

    boolean removeBot(List<String> ids);

    void initBotCache();

    BotInfo infoBot(Long id);

    void initBotConfCache();

    Page<BotMessage> pageBotMessage(DaBotMessageQo daBotMessageQo);

    Page<BotRequest> pageBotRequest(DaBotRequestQo daBotRequestQo);

    Page<BotNotice> pageBotNotice(DaBotNoticeQo daBotNoticeQo);

    List<BotConf> getBotConf(String botNumber);

    boolean updateBotConf(BotConf botConf);

    boolean addBotConf(BotConf botConf);

    boolean removeBotConf(Long id);
    boolean removeBotConf(String botNumber,String key);

    BotConf getByBotNumberAndKey(String botNumber, String key);

    List<Long> getEnableBotScript(Long id);

    boolean enableBotScript(EnableBotScriptDto enableBotScriptDto);

    boolean removeBotConfLike(String botNumber, String key);
}
