package plus.easydo.bot.sandbox;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import plus.easydo.bot.constant.OneBotConstants;

import java.net.URI;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author laoyu
 * @version 1.0
 * @description 沙箱WebSocket服务
 * @date 2024/3/30
 */
@Slf4j
public class SandboxWebsocketHandler implements WebSocketHandler {

    private static final ConcurrentLinkedDeque<WebSocketSession> CONCURRENT_LINKED_DEQUE = new ConcurrentLinkedDeque<>();

    private static final Cache<String, SandboxMessage> MESSAGE_CACHE = CacheUtil.newFIFOCache(50);


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("沙箱客户端连接:{}", session.getId());
        URI uri = session.getUri();
        String query = uri.getQuery();
        if (Objects.nonNull(query)) {
            String token = CharSequenceUtil.subAfter(query, OneBotConstants.HEADER_AUTHORIZATION + "=", false);
            Object loginId = StpUtil.getLoginIdByToken(token);
            if (Objects.nonNull(loginId)) {
                log.info("沙箱客户端鉴权成功,保持连接:{}", session.getId());
                CONCURRENT_LINKED_DEQUE.forEach(value -> closeSession(value));
                CONCURRENT_LINKED_DEQUE.clear();
                saveSession(session);
                for (SandboxMessage sandboxMessage : MESSAGE_CACHE) {
                    sendMessageNotCache(session, sandboxMessage);
                }
            } else {
                closeSession(session);
                log.info("沙箱客户端鉴权失败,断开连接:{}", session.getId());
            }
        } else {
            closeSession(session);
            log.info("沙箱客户端未携带token,断开连接:{}", session.getId());
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        Object textMessage = message.getPayload();
        log.info("接收到沙箱客户端消息:" + textMessage);
    }

    public static void sendMessageNotCache(WebSocketSession session, SandboxMessage message) {
        String str = JSONUtil.toJsonStr(message);
        WebSocketMessage<?> textMessage = new TextMessage(str);
        try {
            session.sendMessage(textMessage);
        } catch (Exception exception) {
            log.error("沙箱:广播消息失败->{},{}", session.getId(), ExceptionUtil.getMessage(exception));
        }
    }

    public static void sendMessage(WebSocketSession session, SandboxMessage message) {
        cacheMessage(message);
        sendMessageNotCache(session, message);
    }

    public static void sendMessage(SandboxMessage message) {
        cacheMessage(message);
        String str = JSONUtil.toJsonStr(message);
        WebSocketMessage<?> textMessage = new TextMessage(str);
        CONCURRENT_LINKED_DEQUE.forEach(session -> {
            try {
                session.sendMessage(textMessage);
            } catch (Exception exception) {
                log.error("沙箱:广播消息失败->{},{}", session.getId(), ExceptionUtil.getMessage(exception));
            }
        });
    }

    private void closeSession(WebSocketSession session) {
        try {
            session.close();
        } catch (Exception e) {
        }
    }

    public static void cacheMessage(SandboxMessage message) {
        MESSAGE_CACHE.put(message.getMessageId(), message);
    }

    private void saveSession(WebSocketSession session) {
        CONCURRENT_LINKED_DEQUE.add(session);
    }

    private void removeSession(WebSocketSession session) {
        CONCURRENT_LINKED_DEQUE.remove(session);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("沙箱socket通信异常,:{},{}", session.getId(), ExceptionUtil.getMessage(exception));
        removeSession(session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.warn("沙箱客户端断开连接:{},断开编码：{}", session.getId(), closeStatus.getCode());
        removeSession(session);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
