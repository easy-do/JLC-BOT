package plus.easydo.bot.service.impl;

import cn.hutool.core.lang.UUID;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.service.BotScriptService;
import plus.easydo.bot.service.BotService;
import plus.easydo.bot.dto.EnableBotScriptDto;
import plus.easydo.bot.entity.*;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.manager.*;
import plus.easydo.bot.qo.BotMessageQo;
import plus.easydo.bot.qo.BotNoticeQo;
import plus.easydo.bot.qo.BotQo;
import plus.easydo.bot.qo.BotRequestQo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author laoyu
 * @version 1.0
 * @description 机器人相关实现
 * @date 2024/2/21
 */
@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final BotInfoManager botInfoManager;

    private final BotConfManager botConfManager;

    private final BotMessageManager botMessageManager;

    private final BotRequestManager botRequestManager;

    private final BotNoticeManager botNoticeManager;

    private final BotScriptBotManager botScriptBotManager;

    private final BotScriptService botScriptService;

    @Override
    public Page<BotInfo> pageBot(BotQo botQo) {
        return botInfoManager.page(new Page<>(botQo.getCurrent(), botQo.getPageSize()));
    }

    @Override
    public Boolean addBot(BotInfo botInfo) {
        botInfo.setBotSecret(UUID.fastUUID().toString(true));
        boolean res = botInfoManager.save(botInfo);
        if (res) {
            initBotCache();
        }
        return res;
    }

    @Override
    public boolean updateBot(BotInfo botInfo) {
        boolean res = botInfoManager.updateById(botInfo);
        if (res) {
            initBotCache();
        }
        return res;
    }

    @Override
    public boolean removeBot(List<String> ids) {
        boolean res = botInfoManager.removeByIds(ids);
        if (res) {
            initBotCache();
        }
        return res;
    }

    @PostConstruct
    @Override
    public void initBotCache() {
        Map<String, BotInfo> map = botInfoManager.list().stream().collect(Collectors.toMap(BotInfo::getBotNumber, (c) -> c));
        CacheManager.BOT_CACHE.putAll(map);
        Map<String, BotInfo> secretMap = botInfoManager.list().stream().collect(Collectors.toMap(BotInfo::getBotSecret, (c) -> c));
        CacheManager.SECRET_BOT_CACHE.putAll(secretMap);
    }

    @PostConstruct
    @Override
    public void initBotConfCache() {
        List<BotConf> allConf = botConfManager.list();
        Map<String,Map<String,String>> cacheMap = new HashMap<>();
        Map<String, List<BotConf>> botNumberMap = allConf.stream().collect(Collectors.groupingBy(BotConf::getBotNumber));
        for (Map.Entry<String, List<BotConf>> entry :botNumberMap.entrySet()){
            String platformBotNumber = entry.getKey();
            List<BotConf> values = entry.getValue();
            Map<String, String> confMap = values.stream().collect(Collectors.toMap(BotConf::getConfKey, BotConf::getConfValue));
            cacheMap.put(platformBotNumber,confMap);
        }
       CacheManager.BOT_CONF_CACHE.putAll(cacheMap);
    }

    @Override
    public Page<BotMessage> pageBotMessage(BotMessageQo botMessageQo) {
        return botMessageManager.page(new Page<>(botMessageQo.getCurrent(), botMessageQo.getPageSize()));
    }

    @Override
    public Page<BotRequest> pageBotRequest(BotRequestQo botRequestQo) {
        return botRequestManager.page(new Page<>(botRequestQo.getCurrent(), botRequestQo.getPageSize()));
    }

    @Override
    public Page<BotNotice> pageBotNotice(BotNoticeQo botNoticeQo) {
        return botNoticeManager.page(new Page<>(botNoticeQo.getCurrent(), botNoticeQo.getPageSize()));
    }

    @Override
    public List<BotConf> getBotConf(String botNumber) {
        return botConfManager.getByBotNumber(botNumber);
    }

    @Override
    public boolean updateBotConf(BotConf botConf) {
        boolean res = botConfManager.updateById(botConf);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public boolean addBotConf(BotConf botConf) {
        BotConf dbConf = botConfManager.getByBotNumberAndKey(botConf.getBotNumber(), botConf.getConfKey());
        if(Objects.nonNull(dbConf)){
            botConf.setId(dbConf.getId());
            botConf.setConfValue(botConf.getConfValue());
            return updateBotConf(botConf);
        }

        boolean res = botConfManager.save(botConf);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public boolean removeBotConf(Long id) {
        boolean res = botConfManager.removeById(id);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public boolean removeBotConf(String botNumber, String key) {
        boolean res = botConfManager.removeBotConf(botNumber, key);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public BotConf getByBotNumberAndKey(String botNumber, String key) {
        return botConfManager.getByBotNumberAndKey(botNumber,key);
    }

    @Override
    public List<Long> getEnableBotScript(Long id) {
        BotInfo botInfo = botInfoManager.getById(id);
        if(Objects.isNull(botInfo)){
            throw new BaseException("机器人不存在");
        }
        return botScriptBotManager.getBotScript(botInfo.getBotNumber()).stream().map(BotScriptBot::getScriptId).toList();
    }

    @Override
    public boolean enableBotScript(EnableBotScriptDto enableBotScriptDto) {
        BotInfo botInfo = botInfoManager.getById(enableBotScriptDto.getBotId());
        boolean res;
        if(Objects.isNull(botInfo)){
            throw new BaseException("机器人不存在");
        }
        if(Objects.isNull(enableBotScriptDto.getScriptIds()) || enableBotScriptDto.getScriptIds().isEmpty()){
            return botScriptBotManager.clearBotScript(botInfo.getBotNumber());
        }
        botScriptBotManager.clearBotScript(botInfo.getBotNumber());
        res = botScriptBotManager.saveBotScript(botInfo.getBotNumber(), enableBotScriptDto.getScriptIds());
        if(res){
            botScriptService.initBotEventScriptCache();
        }
        return res;
    }

    @Override
    public boolean removeBotConfLike(String botNumber, String key) {
        boolean res = botConfManager.removeBotConfLike(botNumber, key);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public BotInfo infoBot(Long id) {
        return botInfoManager.getById(id);
    }
}
