package plus.easydo.bot.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.enums.onebot.OneBotPostTypeEnum;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.manager.BotPostLogManager;
import plus.easydo.bot.websocket.OneBotService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author laoyu
 * @version 1.0
 * @description oneBotwebsocket工具类
 * @date 2024/4/4
 */

public class OneBotWebSocketUtils {


    private static final Map<String, WebSocketSession> SESSION_MAP = new HashMap<>();
    private static final Map<String, String> SESSION_BOT_MAP = new HashMap<>();
    private static final Map<String, String> BOT_SESSION_MAP = new HashMap<>();
    private static final Cache<String, String> MESSAGE_CACHE = CacheUtil.newTimedCache(5000);

    private static OneBotService oneBotService;

    private static BotPostLogManager botPostLogManager;

    private OneBotWebSocketUtils() {
    }

    private static OneBotService getOneBotService() {
        if (Objects.isNull(oneBotService)) {
            return SpringUtil.getBean(OneBotService.class);
        }
        return oneBotService;
    }

    private static BotPostLogManager getBotPostLogServiceManager() {
        if (Objects.isNull(oneBotService)) {
            return SpringUtil.getBean(BotPostLogManager.class);
        }
        return botPostLogManager;
    }

    public static void handlerPostMessage(WebSocketMessage<?> message) {
        JSONObject messageJson = JSONUtil.parseObj(message.getPayload());
        String postType = messageJson.getStr(OneBotConstants.POST_TYPE);
        if (Objects.nonNull(postType)) {
            if (!CharSequenceUtil.contains(postType, OneBotPostTypeEnum.META_EVENT.getType())) {
                CompletableFuture.runAsync(() -> getBotPostLogServiceManager().saveLog(messageJson));
            }
            getOneBotService().handlerPost(messageJson);
        }
    }

    public static WebSocketSession getSession(String botNumber) {
        String sessionId = BOT_SESSION_MAP.get(botNumber);
        if (Objects.nonNull(sessionId)) {
            WebSocketSession session = SESSION_MAP.get(sessionId);
            if (Objects.nonNull(session)) {
                return session;
            }
        }
        return null;
    }

    public static void saveSession(WebSocketSession session, BotInfo botInfo) {
        SESSION_MAP.put(session.getId(), session);
        SESSION_BOT_MAP.put(session.getId(), botInfo.getBotNumber());
        SESSION_BOT_MAP.put(session.getId(), botInfo.getBotNumber());
        BOT_SESSION_MAP.put(botInfo.getBotNumber(), session.getId());
    }

    public static void removeSession(WebSocketSession session) {
        SESSION_MAP.remove(session.getId());
        String botNumber = SESSION_BOT_MAP.get(session.getId());
        SESSION_BOT_MAP.remove(session.getId());
        if (CharSequenceUtil.isNotBlank(botNumber)) {
            BOT_SESSION_MAP.remove(botNumber);
        }
    }

    public static void sendMessage(String botNumber, String message) {
        WebSocketSession session = getSession(botNumber);
        if (Objects.nonNull(session)) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String sendMessageAwaitRes(String botNumber, String messageId, String message) {
        WebSocketSession session = getSession(botNumber);
        if (Objects.nonNull(session)) {
            try {
                session.sendMessage(new TextMessage(message));
                String res = MESSAGE_CACHE.get(messageId);
                int sleepTime = 0;
                while (Objects.isNull(res) && sleepTime < 5000) {
                    try {
                        Thread.sleep(500);
                        sleepTime = sleepTime + 500;
                    } catch (InterruptedException e) {
                        throw new BaseException(ExceptionUtil.getMessage(e));
                    }
                    res = MESSAGE_CACHE.get(messageId);
                }
                return res;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }

    public static void closeSession(WebSocketSession session) {
        try {
            session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getSessionParam(WebSocketSession session, String key){
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> selfId = handshakeHeaders.get(key);
        if (Objects.nonNull(selfId) && !selfId.isEmpty()) {
            return selfId.get(0);
        }
        return getQueryParam(session.getUri().getQuery(), key);
    }

    public static String getQueryParam(String queryParamUrl, String key) {
        Map<String, List<String>> queryParam = HttpUtil.decodeParams(queryParamUrl, Charset.defaultCharset());
        if(Objects.nonNull(queryParam)) {
            List<String> values = queryParam.get(key);
            if(Objects.nonNull(values) && !values.isEmpty()) {
                return values.get(0);
            }
        }
        return null;
    }

    public static void cacheResponseMessage(String messageId, String message) {
        MESSAGE_CACHE.put(messageId, message);
    }
}
