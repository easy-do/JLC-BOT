package plus.easydo.bot.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import plus.easydo.bot.util.OneBotWebSocketUtils;


/**
 * @author laoyu
 * @version 1.0
 * @description oneBot正向websocket通信实现
 * @date 2024/4/4
 */
@Slf4j
public class OneBotWebSocketClientHandler extends StandardWebSocketClient implements WebSocketHandler {

    private WebSocketSession session;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        log.info("oneBot正向websocket建立连接:{}", session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.debug("接收到oneBot正向websocket消息:{}", message.getPayload());
        OneBotWebSocketUtils.handlerPostMessage(message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("oneBot正向websocket通信异常,:{},{}", session.getId(), ExceptionUtil.getMessage(exception));
        OneBotWebSocketUtils.removeSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.error("oneBot正向websocket通信断开,:{},{}", session.getId(), closeStatus);
        OneBotWebSocketUtils.removeSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
