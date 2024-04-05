package plus.easydo.bot.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.manager.CacheManager;

import java.util.List;
import java.util.Map;
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
        return CacheManager.BOT_CACHE.entrySet().stream().map(Map.Entry::getValue).toList();
    }

    public static BotInfo getBotInfo(String botNumber) {
        BotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("机器人[" + botNumber + "]不存在");
        }
        return bot;
    }

    public static BotInfo getBotInfoBySecret(String secret) {
        BotInfo botInfo = CacheManager.SECRET_BOT_CACHE.get(secret);
        if (Objects.isNull(botInfo)) {
            throw new BaseException("机器人不存在");
        }
        return botInfo;
    }

    public static void saveSecretBotCache(Map<String, BotInfo> secretMap) {
        CacheManager.SECRET_BOT_CACHE.putAll(secretMap);
    }

    public static void saveBotNumberBotCache(Map<String, BotInfo> botMap) {
        CacheManager.BOT_CACHE.putAll(botMap);
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

    public static JSONArray buildMessageJson(String message) {
        JSONArray arr = JSONUtil.createArray();
        JSONObject obj = JSONUtil.createObj();
        obj.set("type", "text");
        JSONObject obj1 = JSONUtil.createObj();
        obj1.set("text", message);
        obj.set("data", obj1);
        arr.add(obj);
        return arr;
    }
}
