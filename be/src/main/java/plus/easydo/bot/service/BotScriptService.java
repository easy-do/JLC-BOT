package plus.easydo.bot.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.entity.BotEventScript;
import plus.easydo.bot.qo.DaBotEventScriptQo;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 机器人相关
 * @date 2024/2/21
 */

public interface BotScriptService {
    Page<BotEventScript> pageBotScript(DaBotEventScriptQo daBotEventScriptQo);

    Boolean addBotScript(BotEventScript botEventScript);

    boolean updateBotScript(BotEventScript botEventScript);

    boolean removeBotScript(List<String> ids);

    BotEventScript infoBotScript(Long id);

    void initBotEventScriptCache();

    List<BotEventScript> botScriptList();
}
