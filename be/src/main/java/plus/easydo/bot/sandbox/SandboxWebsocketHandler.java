package plus.easydo.bot.sandbox;


import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.enums.onebot.OneBotPostMessageTypeEnum;
import plus.easydo.bot.enums.onebot.OneBotPostTypeEnum;
import plus.easydo.bot.websocket.OneBotService;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author laoyu
 * @version 1.0
 * @description 沙箱WebSocket服务
 * @date 2024/3/30
 */
@Slf4j
public class SandboxWebsocketHandler implements WebSocketHandler {

    private static final ConcurrentLinkedDeque<WebSocketSession> CONCURRENT_LINKED_DEQUE = new ConcurrentLinkedDeque<>();

    private static final Cache<String,SandboxMessage> MESSAGE_CACHE = CacheUtil.newFIFOCache(100);

    private static OneBotService oneBotService;

    public static OneBotService getOneBotService(){
        if(Objects.isNull(oneBotService)){
            oneBotService = SpringUtil.getBean(OneBotService.class);
        }
        return oneBotService;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        saveSession(session);
        log.info("沙箱客户端连接:" + session.getId());
        for (SandboxMessage sandboxMessage : MESSAGE_CACHE){
            sendMessageNotCache(session,sandboxMessage);
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        Object textMessage = message.getPayload();
        log.info("接收到沙箱客户端消息:" + textMessage);
        SandboxMessage sandboxMessage = SandboxMessage.builder()
                .messageId(UUID.fastUUID().toString(true))
                .isSelf(true)
                .type("text")
                .message(String.valueOf(textMessage))
                .time(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.NORM_DATETIME_PATTERN))
                .build();
        cacheMessage(sandboxMessage);
        sendMessageNotCache(session,sandboxMessage);
        JSONObject jsonObject = JSONUtil.parseObj(sandboxMessage);
        jsonObject.set(OneBotConstants.SELF_ID,"jlc-bot-sandbox");
        jsonObject.set(OneBotConstants.GROUP_ID,"jlc-bot-sandbox");
        jsonObject.set(OneBotConstants.MESSAGE,textMessage);
        jsonObject.set(OneBotConstants.RAW_MESSAGE,textMessage);
        jsonObject.set(OneBotConstants.POST_TYPE, OneBotPostTypeEnum.MESSAGE.getType());
        jsonObject.set(OneBotConstants.MESSAGE_TYPE, OneBotPostMessageTypeEnum.GROUP.getType());
        getOneBotService().handlerPost(jsonObject);
    }

    public static void sendMessageNotCache(WebSocketSession session, SandboxMessage message){
        String str = JSONUtil.toJsonStr(message);
        WebSocketMessage<?> textMessage = new TextMessage(str);
        try {
            session.sendMessage(textMessage);
        } catch (Exception exception) {
            log.error("沙箱:广播消息失败->{},{}", session.getId(), ExceptionUtil.getMessage(exception));
        }
    }
    public static void sendMessage(WebSocketSession session, SandboxMessage message){
        cacheMessage(message);
        sendMessageNotCache(session,message);
    }

    public static void sendMessage(SandboxMessage message){
        cacheMessage(message);
        String str = JSONUtil.toJsonStr(message);
        WebSocketMessage<?> textMessage = new TextMessage(str);
        CONCURRENT_LINKED_DEQUE.forEach(session->{
            try {
                session.sendMessage(textMessage);
            } catch (Exception exception) {
                log.error("沙箱:广播消息失败->{},{}", session.getId(), ExceptionUtil.getMessage(exception));
            }
        });
    }

    public static void cacheMessage(SandboxMessage message){
        MESSAGE_CACHE.put(message.getMessageId(), message);
    }

    private void saveSession(WebSocketSession session){
        CONCURRENT_LINKED_DEQUE.add(session);
    }

    private void removeSession(WebSocketSession session){
        CONCURRENT_LINKED_DEQUE.remove(session);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("沙箱socket通信异常,:{},{}", session.getId(), ExceptionUtil.getMessage(exception));
        removeSession(session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.warn("沙箱客户端断开连接:{},断开编码：{}" , session.getId(),closeStatus.getCode());
        removeSession(session);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
