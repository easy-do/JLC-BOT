package plus.easydo.bot.websocket;


import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.enums.onebot.OneBotIntergrationPostTypeEnum;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.util.OneBotWebSocketUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description oneBot反向websocket通信实现
 * @date 2023/12/2
 */
@Slf4j
public class OneBotWebSocketServerHandler implements WebSocketHandler {


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("oneBot反向websocket建立连接:" + session.getId());
        //鉴权
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> selfId = handshakeHeaders.get(OneBotConstants.HEADER_SELF_ID);
        if (Objects.nonNull(selfId) && !selfId.isEmpty()) {
            BotInfo botInfo = OneBotUtils.getBotInfo(selfId.get(0));
            if (Objects.nonNull(botInfo)) {
                //判断是否指定了反向websocket
                String postType = OneBotIntergrationPostTypeEnum.WEBSOCKET_REVERSE.getType();
                if (!CharSequenceUtil.equals(botInfo.getPostType(), postType)) {
                    log.warn("bot[{}]未配置[{}]上报,断开会话.", botInfo.getBotNumber(),postType);
                    OneBotWebSocketUtils.closeSession(session);
                    return;
                }
                String botSecret = botInfo.getBotSecret();
                if (CharSequenceUtil.isNotBlank(botSecret)) {
                    List<String> authorization = handshakeHeaders.get(OneBotConstants.HEADER_AUTHORIZATION);
                    if (Objects.nonNull(authorization) && !authorization.isEmpty()) {
                        if (CharSequenceUtil.equals(OneBotConstants.HEADER_AUTHORIZATION_VALUE_PRE + botSecret, authorization.get(0))) {
                            log.info("oneBot反向websocket鉴权成功,保持会话连接.");
                            OneBotWebSocketUtils.saveSession(session, botInfo);
                        } else {
                            log.warn("oneBot反向websocket鉴权失败,断开会话.");
                            OneBotWebSocketUtils.closeSession(session);
                        }
                    } else {
                        log.warn("oneBot反向websocket未传递token,断开会话.");
                        OneBotWebSocketUtils.closeSession(session);
                    }
                } else {
                    log.warn("oneBot反向websocket未设置密钥,跳过鉴权,保持会话连接.");
                    OneBotWebSocketUtils.saveSession(session, botInfo);
                }
            } else {
                log.warn("oneBot反向websocket匹配失败,断开会话.");
                OneBotWebSocketUtils.closeSession(session);
            }
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        log.debug("接收到oneBot反向websocket消息:" + message.getPayload());
        OneBotWebSocketUtils.handlerPostMessage(message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("oneBot反向websocket通信异常,:{},{}", session.getId(), ExceptionUtil.getMessage(exception));
        OneBotWebSocketUtils.removeSession(session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.warn("oneBot反向websocket断开连接:{},断开编码：{}", session.getId(), closeStatus.getCode());
        OneBotWebSocketUtils.removeSession(session);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
