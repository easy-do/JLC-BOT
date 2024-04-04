package plus.easydo.bot.websocket;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.enums.onebot.OneBotIntergrationPostTypeEnum;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.util.OneBotWebSocketUtils;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description oneBot websocket 定时任务
 * @date 2024/4/4
 */
@Slf4j
@Component
public class OneBotWebSocketJob {

    /**
     * 每10秒扫描一次websocket客户端
     *
     * @author laoyu
     * @date 2024/4/4
     */
    @Scheduled(fixedDelay = 10000)
    public void createWebsocketClient() {
        log.debug("开始创建所有oneBot正向websocket......");
        List<BotInfo> botList = OneBotUtils.getBotInfoList();
        for (BotInfo botInfo : botList) {
            // 如果指定使用正向websocket
            if (CharSequenceUtil.equals(botInfo.getPostType(), OneBotIntergrationPostTypeEnum.WEBSOCKET.getType())) {
                //如果未连接则尝试创建连接
                if(Objects.isNull(OneBotWebSocketUtils.getSession(botInfo.getBotNumber()))){
                    WebSocketClient client = new StandardWebSocketClient();
                    WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
                    headers.set(OneBotConstants.HEADER_AUTHORIZATION, OneBotConstants.HEADER_AUTHORIZATION_VALUE_PRE + botInfo.getBotSecret());
                    try {
                        WebSocketSession session = client.execute(new OneBotWebSocketClientHandler(), headers, new URI(botInfo.getBotUrl())).get();
                        OneBotWebSocketUtils.saveSession(session, botInfo);
                    } catch (Exception e) {
                        log.warn("创建机器人[{}]的oneBot正向websocket失败：{}", botInfo.getBotNumber(), ExceptionUtil.getMessage(e));
                    }
                }
            }
        }
        log.debug("完成创建所有oneBot正向websocket......");
    }


}
