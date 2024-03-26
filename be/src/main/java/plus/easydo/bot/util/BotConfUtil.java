package plus.easydo.bot.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.spring.SpringUtil;
import plus.easydo.bot.entity.BotConf;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.service.BotService;

import java.util.Map;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-02-23
 * @Description 机器人配置工具类
 */
public class BotConfUtil {

    private static BotService botService;

    private BotConfUtil() {
    }

    public static BotService getBotService(){
        if (Objects.isNull(botService)) {
            botService = SpringUtil.getBean(BotService.class);
        }
        return botService;
    }

    public static Map<String, String> getAllBotConf(String botNumber){
        return CacheManager.BOT_CONF_CACHE.get(botNumber);
    }

    public static String getBotConf(String botNumber,String key){
        Map<String, String> conf = CacheManager.BOT_CONF_CACHE.get(botNumber);
        if(Objects.nonNull(conf)){
            String value = conf.get(key);
            return Objects.nonNull(value)?value:"";
        }
        return "";
    }

    public static boolean saveBotConf(String botNumber,String key, String value){
        BotConf botConf = BotConf.builder().botNumber(botNumber).confKey(key).confValue(value).build();
        return getBotService().addBotConf(botConf);
    }

    public static boolean saveBotConf(String botNumber,String key, String value,String remark){
        BotConf botConf = BotConf.builder().botNumber(botNumber).confKey(key).confValue(value).remark(remark).build();
        return getBotService().addBotConf(botConf);
    }

    public static boolean updateBotConf(String botNumber,String key, String value){
        BotConf botConf = getBotService().getByBotNumberAndKey(botNumber,key);
        if(Objects.isNull(botConf)){
            return saveBotConf(botNumber,key,value);
        }
        botConf = BotConf.builder().id(botConf.getId()).confValue(value).build();
        return getBotService().updateBotConf(botConf);
    }

    public static boolean removeBotConf(String botNumber,String key){
        if(CharSequenceUtil.isBlank(botNumber) || CharSequenceUtil.isBlank(key)){
            return false;
        }
        return getBotService().removeBotConf(botNumber,key);
    }
    public static boolean removeBotConfLike(String botNumber,String key){
        if(CharSequenceUtil.isBlank(botNumber) || CharSequenceUtil.isBlank(key)){
            return false;
        }
        return getBotService().removeBotConfLike(botNumber,key);
    }
}
