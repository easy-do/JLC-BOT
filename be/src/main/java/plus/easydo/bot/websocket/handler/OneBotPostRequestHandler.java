package plus.easydo.bot.websocket.handler;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.manager.DaBotRequestManager;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.enums.onebot.OneBotPostRequestTypeEnum;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.entity.BotRequest;
import plus.easydo.bot.websocket.handler.request.OneBotFriendRequestHandler;
import plus.easydo.bot.websocket.handler.request.OneBotGroupRequestHandler;

import java.util.concurrent.CompletableFuture;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报请求处理
 * @date 2024/2/25
 */
@Slf4j
@Service("request")
@RequiredArgsConstructor
public class OneBotPostRequestHandler implements OneBotPostHandler{

    private final OneBotFriendRequestHandler friendRequestHandler;

    private final OneBotGroupRequestHandler groupRequestHandler;

    private final DaBotRequestManager daBotRequestManager;

    @Override
    public void handlerPost(JSONObject postData) {
        long time = OneBotUtils.getPostTime(postData);
        String requestType = postData.getStr(OneBotConstants.REQUEST_TYPE);
        Object selfId = postData.get(OneBotConstants.SELF_ID);
        String comment = postData.getStr(OneBotConstants.COMMENT);
        String flag = postData.getStr(OneBotConstants.FLAG);
        String userId  = String.valueOf(postData.get(OneBotConstants.USER_ID));
        BotRequest botRequest = BotRequest.builder()
                .requestType(requestType)
                .sendUser(userId)
                .selfUser(String.valueOf(selfId))
                .selfTime(LocalDateTimeUtil.of(time))
                .comment(comment)
                .flag(flag)
                .build();
        log.debug("接收到请求消息,类型:{},验证信息:{}", OneBotPostRequestTypeEnum.getDescByType(requestType), comment);
        if(CharSequenceUtil.equals(requestType, OneBotPostRequestTypeEnum.GROUP.getType())){
            Object groupId = postData.get(OneBotConstants.GROUP_ID);
            botRequest.setGroupId(String.valueOf(groupId));
            groupRequestHandler.handlerRequest(userId, String.valueOf(groupId), comment, flag);
        }else {
            friendRequestHandler.handlerRequest(userId,comment,flag);
        }
        CompletableFuture.runAsync(()->daBotRequestManager.save(botRequest));
    }
}
