package plus.easydo.bot.util;

import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.wcf.Client;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2024-03-29
 * @Description wcf工具
 */
public class OneBotWcfClientUtils {

    private OneBotWcfClientUtils() {
    }

    public static final Map<String, Client> CLIENT_CACHE = new HashMap<>();

    public static void saveClient(Client client) {
        CLIENT_CACHE.put(client.getSelfWxid(), client);
    }

    public static Client getClient(String botNumber) {
        if (!CLIENT_CACHE.isEmpty()) {
            return CLIENT_CACHE.get(botNumber);
        }
        throw new BaseException("client缓存为空");
    }

}
