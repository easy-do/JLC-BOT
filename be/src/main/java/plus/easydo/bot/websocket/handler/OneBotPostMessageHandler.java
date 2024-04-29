package plus.easydo.bot.websocket.handler;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotMessage;
import plus.easydo.bot.enums.onebot.OneBotPostMessageTypeEnum;
import plus.easydo.bot.manager.BotMessageManager;
import plus.easydo.bot.util.MessageParseUtil;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.websocket.handler.message.OneBotGroupMessageHandler;
import plus.easydo.bot.websocket.handler.message.OneBotGroupPrivateMessageHandler;
import plus.easydo.bot.websocket.handler.message.OneBotPrivateMessageHandler;
import plus.easydo.bot.websocket.model.OneBotMessageParse;
import plus.easydo.bot.websocket.model.OneBotSender;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报消息处理
 * @date 2024/2/25
 */
@Slf4j
@Service("message")
@RequiredArgsConstructor
public class OneBotPostMessageHandler implements OneBotPostHandler {

    private final OneBotGroupMessageHandler groupMessageHandler;

    private final OneBotPrivateMessageHandler privateMessageHandler;

    private final OneBotGroupPrivateMessageHandler groupPrivateMessageHandler;

    private final BotMessageManager botMessageManager;

    @Override
    public void handlerPost(JSONObject postData) {
        long time = OneBotUtils.getPostTime(postData);
        String messageType = postData.getStr(OneBotConstants.MESSAGE_TYPE);
        String message = postData.getStr(OneBotConstants.MESSAGE);
        String selfId = postData.getStr(OneBotConstants.SELF_ID);
        Object messageId = postData.get(OneBotConstants.MESSAGE_ID);
        log.debug("接收到消息,类型:{},内容:{}", OneBotPostMessageTypeEnum.getDescByType(messageType), message);
        Object groupId = postData.get(OneBotConstants.GROUP_ID);

        //处理发送人
        String senderId;
        try {
            JSONObject senderJson = postData.getJSONObject(OneBotConstants.SENDER);
            OneBotSender sender = JSONUtil.toBean(senderJson, OneBotSender.class);
            senderId = sender.getUserId();
        } catch (Exception e) {
            senderId = postData.getStr(OneBotConstants.SENDER);
        }

        OneBotMessageParse oneBotMessageParse = MessageParseUtil.parseMessage(message);
        if (oneBotMessageParse.getSegmentSize() != 0) {
            BotMessage botMessage = BotMessage.builder()
                    .sendUser(senderId)
                    .selfUser(selfId)
                    .selfTime(LocalDateTimeUtil.of(time))
                    .message(message)
                    .messageFormat(oneBotMessageParse.getType())
                    .messageId(String.valueOf(messageId))
                    .build();
            if (CharSequenceUtil.equals(messageType, OneBotPostMessageTypeEnum.PRIVATE.getType())) {
                //群内发起的私聊
                if (Objects.nonNull(groupId)) {
                    botMessage.setGroupId(String.valueOf(groupId));
                    groupPrivateMessageHandler.handlerMessage(String.valueOf(groupId), senderId, message);
                } else {
                    //直接私聊
                    privateMessageHandler.handlerMessage(senderId, message);
                }
            } else {
                botMessage.setGroupId(String.valueOf(groupId));
                groupMessageHandler.handlerMessage(selfId, String.valueOf(groupId), senderId, message);
            }
            CompletableFuture.runAsync(() -> botMessageManager.save(botMessage));
        }
    }
}
