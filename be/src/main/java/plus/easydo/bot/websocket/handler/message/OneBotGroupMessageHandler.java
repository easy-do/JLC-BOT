package plus.easydo.bot.websocket.handler.message;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description qq群消息处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OneBotGroupMessageHandler {


    private final OneBotGroupAtMessageHandler groupAtMessageHandler;

    public String handlerMessage(String botNumber, String groupId, String sender, String message){


        return "";
    }

}
