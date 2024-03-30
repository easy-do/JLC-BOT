package plus.easydo.bot.websocket.handler.message;

import org.springframework.stereotype.Component;
import plus.easydo.bot.websocket.model.OneBotSender;


/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description qq群发起的私聊消息处理
 */
@Component
public class OneBotGroupPrivateMessageHandler {


    public String handlerMessage(String groupId, String senderId, String message) {
        return "";
    }
}
