package plus.easydo.bot.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.BotEventScript;
import plus.easydo.bot.qo.BotEventScriptQo;
import plus.easydo.bot.service.BotScriptService;
import plus.easydo.bot.entity.BotScriptBot;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.manager.BotEventScriptManager;
import plus.easydo.bot.manager.BotScriptBotManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author laoyu
 * @version 1.0
 * @description 机器人相关
 * @date 2024/2/21
 */
@Service
@RequiredArgsConstructor
public class BotScriptServiceImpl implements BotScriptService {

    private final BotEventScriptManager botEventScriptManager;

    private final BotScriptBotManager botScriptBotManager;

    @Override
    public Page<BotEventScript> pageBotScript(BotEventScriptQo botEventScriptQo) {
        return botEventScriptManager.pageBotScript(botEventScriptQo);
    }

    @Override
    public Boolean addBotScript(BotEventScript botEventScript) {
        return botEventScriptManager.save(botEventScript);
    }

    @Override
    public boolean updateBotScript(BotEventScript botEventScript) {
        boolean res = botEventScriptManager.updateById(botEventScript);
        if(res && CharSequenceUtil.isNotBlank(botEventScript.getScriptContent())){
            initBotEventScriptCache();
        }
        return res;
    }

    @Override
    public boolean removeBotScript(List<String> ids) {
        return botEventScriptManager.removeByIds(ids);
    }

    @Override
    public BotEventScript infoBotScript(Long id) {
        return botEventScriptManager.getById(id);
    }

    @PostConstruct
    @Override
    public void initBotEventScriptCache() {
        //缓存脚本
        List<BotEventScript> list = botEventScriptManager.list();
        //按事件类型分组
        Map<String, List<BotEventScript>> eventScriptGroup = list.stream().collect(Collectors.groupingBy(BotEventScript::getEventType));
        //事件与脚本id关联关系
        Map<String,List<Long>> eventScriptIdMap = new HashMap<>();
        eventScriptGroup.forEach((key,value)-> eventScriptIdMap.put(key,value.stream().map(BotEventScript::getId).toList()));
        CacheManager.EVENT_SCRIPT_ID_CACHE.putAll(eventScriptIdMap);
        //bot的所有脚本
        List<BotScriptBot> allBf = botScriptBotManager.list();
        //按机器人编号分组
        Map<String, List<BotScriptBot>> botScriptGroup = allBf.stream().collect(Collectors.groupingBy(BotScriptBot::getBotNumber));
        //机器人与脚本的对应关系
        Map<String,List<Long>> botScriptIdMap = new HashMap<>();
        botScriptGroup.forEach((key,value)-> botScriptIdMap.put(key,value.stream().map(BotScriptBot::getScriptId).toList()));
        CacheManager.BOT_SCRIPT_ID_CACHE.putAll(botScriptIdMap);
        //所有脚本缓存
        CacheManager.EVENT_SCRIPT_CACHE.putAll(list.stream().collect(Collectors.toMap(BotEventScript::getId, fun -> fun)));
    }

    @Override
    public List<BotEventScript> botScriptList() {
        return botEventScriptManager.listAll();
    }
}
