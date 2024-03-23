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
import plus.easydo.bot.qo.DaBotMessageQo;
import plus.easydo.bot.qo.DaBotNoticeQo;
import plus.easydo.bot.qo.DaBotQo;
import plus.easydo.bot.qo.DaBotRequestQo;

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

    private final DaBotInfoManager daBotInfoManager;

    private final DaBotConfManager daBotConfManager;

    private final DaBotMessageManager daBotMessageManager;

    private final DaBotRequestManager daBotRequestManager;

    private final DaBotNoticeManager daBotNoticeManager;

    private final DaBotScriptBotManager daBotScriptBotManager;

    private final BotScriptService botScriptService;

    @Override
    public Page<BotInfo> pageBot(DaBotQo daBotQo) {
        return daBotInfoManager.page(new Page<>(daBotQo.getCurrent(), daBotQo.getPageSize()));
    }

    @Override
    public Boolean addBot(BotInfo botInfo) {
        botInfo.setBotSecret(UUID.fastUUID().toString(true));
        boolean res = daBotInfoManager.save(botInfo);
        if (res) {
            initBotCache();
        }
        return res;
    }

    @Override
    public boolean updateBot(BotInfo botInfo) {
        boolean res = daBotInfoManager.updateById(botInfo);
        if (res) {
            initBotCache();
        }
        return res;
    }

    @Override
    public boolean removeBot(List<String> ids) {
        boolean res = daBotInfoManager.removeByIds(ids);
        if (res) {
            initBotCache();
        }
        return res;
    }

    @PostConstruct
    @Override
    public void initBotCache() {
        Map<String, BotInfo> map = daBotInfoManager.list().stream().collect(Collectors.toMap(BotInfo::getBotNumber, (c) -> c));
        CacheManager.BOT_CACHE.putAll(map);
    }

    @PostConstruct
    @Override
    public void initBotConfCache() {
        List<BotConf> allConf = daBotConfManager.list();
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
    public Page<BotMessage> pageBotMessage(DaBotMessageQo daBotMessageQo) {
        return daBotMessageManager.page(new Page<>(daBotMessageQo.getCurrent(), daBotMessageQo.getPageSize()));
    }

    @Override
    public Page<BotRequest> pageBotRequest(DaBotRequestQo daBotRequestQo) {
        return daBotRequestManager.page(new Page<>(daBotRequestQo.getCurrent(), daBotRequestQo.getPageSize()));
    }

    @Override
    public Page<BotNotice> pageBotNotice(DaBotNoticeQo daBotNoticeQo) {
        return daBotNoticeManager.page(new Page<>(daBotNoticeQo.getCurrent(), daBotNoticeQo.getPageSize()));
    }

    @Override
    public List<BotConf> getBotConf(String botNumber) {
        return daBotConfManager.getByBotNumber(botNumber);
    }

    @Override
    public boolean updateBotConf(BotConf botConf) {
        boolean res = daBotConfManager.updateById(botConf);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public boolean addBotConf(BotConf botConf) {
        BotConf dbConf = daBotConfManager.getByBotNumberAndKey(botConf.getBotNumber(), botConf.getConfKey());
        if(Objects.nonNull(dbConf)){
            botConf.setId(dbConf.getId());
            botConf.setConfValue(botConf.getConfValue());
            return updateBotConf(botConf);
        }

        boolean res = daBotConfManager.save(botConf);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public boolean removeBotConf(Long id) {
        boolean res = daBotConfManager.removeById(id);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public boolean removeBotConf(String botNumber, String key) {
        boolean res = daBotConfManager.removeBotConf(botNumber, key);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public BotConf getByBotNumberAndKey(String botNumber, String key) {
        return daBotConfManager.getByBotNumberAndKey(botNumber,key);
    }

    @Override
    public List<Long> getEnableBotScript(Long id) {
        BotInfo botInfo = daBotInfoManager.getById(id);
        if(Objects.isNull(botInfo)){
            throw new BaseException("机器人不存在");
        }
        return daBotScriptBotManager.getBotScript(botInfo.getBotNumber()).stream().map(BotScriptBot::getScriptId).toList();
    }

    @Override
    public boolean enableBotScript(EnableBotScriptDto enableBotScriptDto) {
        BotInfo botInfo = daBotInfoManager.getById(enableBotScriptDto.getBotId());
        boolean res;
        if(Objects.isNull(botInfo)){
            throw new BaseException("机器人不存在");
        }
        if(Objects.isNull(enableBotScriptDto.getScriptIds()) || enableBotScriptDto.getScriptIds().isEmpty()){
            return daBotScriptBotManager.clearBotScript(botInfo.getBotNumber());
        }
        res = daBotScriptBotManager.saveBotScript(botInfo.getBotNumber(), enableBotScriptDto.getScriptIds());
        if(res){
            botScriptService.initBotEventScriptCache();
        }
        return res;
    }

    @Override
    public boolean removeBotConfLike(String botNumber, String key) {
        boolean res = daBotConfManager.removeBotConfLike(botNumber, key);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public BotInfo infoBot(Long id) {
        return daBotInfoManager.getById(id);
    }
}
