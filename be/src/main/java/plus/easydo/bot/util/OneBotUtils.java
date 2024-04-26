package plus.easydo.bot.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.manager.CacheManager;

import java.util.List;
import java.util.Objects;


/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description bot工具
 */
public class OneBotUtils {


    private OneBotUtils() {
    }

    public static List<BotInfo> getBotInfoList() {
        return CacheManager.BOT_CACHE.values().stream().toList();
    }

    public static BotInfo getBotInfoById(Long botId) {
        BotInfo botInfo = CacheManager.BOT_CACHE.get(botId);
        Assert.notNull(botInfo, "机器人不存在");
        return botInfo;
    }


    public static BotInfo getBotInfoByNumber(String botNumber) {
        Long botId = CacheManager.BOT_NUMBER_CACHE.get(botNumber);
        Assert.notNull(botId, "机器人不存在");
        return getBotInfoById(botId);
    }

    public static BotInfo getBotInfoBySecret(String secret) {
        Long botInfo = CacheManager.BOT_SECRET_CACHE.get(secret);
        Assert.notNull(botInfo, "机器人不存在");
        return getBotInfoById(botInfo);
    }

    public static void cacheBotInfo(List<BotInfo> botInfos) {
        botInfos.forEach(botInfo -> {
            Long botId = botInfo.getId();
            CacheManager.BOT_CACHE.put(botId, botInfo);
            CacheManager.BOT_SECRET_CACHE.put(botInfo.getBotSecret(), botId);
            CacheManager.BOT_NUMBER_CACHE.put(botInfo.getBotNumber(), botId);
        });
    }

    public static void cacheSystemConf(List<SystemConf> confList) {
        CacheManager.SYSTEM_CONF_LIST.clear();
        CacheManager.SYSTEM_CONF_LIST.addAll(confList);
        confList.forEach(conf -> CacheManager.SYSTEM_CONF_MAP.put(conf.getConfKey(), conf));
    }


    public static long getPostTime(JSONObject postData) {
        Long time = postData.getLong(OneBotConstants.TIME);
        String timeStr = String.valueOf(time);
        if (timeStr.length() == 10) {
            return time * 1000;
        }
        return time;
    }

    public static String getParamBotNumber(JSONObject paramJson) {
        String botNumber = paramJson.getStr(OneBotConstants.BOT_NUMBER);
        if (Objects.nonNull(botNumber)) {
            return botNumber;
        } else {
            return paramJson.getStr(OneBotConstants.SELF_ID);
        }
    }
}
