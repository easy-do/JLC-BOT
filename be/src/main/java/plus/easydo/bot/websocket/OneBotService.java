package plus.easydo.bot.websocket;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.websocket.handler.OneBotNodePostHandler;
import plus.easydo.bot.websocket.handler.OneBotPostHandler;
import plus.easydo.bot.websocket.handler.OneBotScriptPostHandler;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot服务
 * @date 2024/2/25
 */
@Component
@RequiredArgsConstructor
public class OneBotService {

    @Autowired
    private Map<String, OneBotPostHandler> postHandlerMap;

    private final OneBotScriptPostHandler oneBotScriptPostHandler;

    private final OneBotNodePostHandler oneBotNodePostHandler;

    public void handlerPost(JSONObject postData){
        String postType = postData.getStr(OneBotConstants.POST_TYPE);
        if(CharSequenceUtil.isNotBlank(postType)){
            OneBotPostHandler postDataHandler = postHandlerMap.get(postType);
            if(Objects.nonNull(postDataHandler)){
                CompletableFuture.runAsync(()->postDataHandler.handlerPost(postData));
            }
        }
        CompletableFuture.runAsync(()->oneBotScriptPostHandler.handler(postType,postData));
        CompletableFuture.runAsync(()->oneBotNodePostHandler.handler(postType,postData));
    }
}
