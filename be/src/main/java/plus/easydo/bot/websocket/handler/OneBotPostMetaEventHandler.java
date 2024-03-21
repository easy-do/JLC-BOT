package plus.easydo.bot.websocket.handler;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.manager.DaBotInfoManager;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.entity.DaBotInfo;

import java.util.concurrent.CompletableFuture;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报元事件处理
 * @date 2024/2/25
 */
@Slf4j
@Service("meta_event")
@RequiredArgsConstructor
public class OneBotPostMetaEventHandler implements OneBotPostHandler{

    private final DaBotInfoManager daBotInfoManager;
    @Override
    public void handlerPost(JSONObject postData) {
        long time = OneBotUtils.getPostTime(postData);
        String selfId = postData.getStr(OneBotConstants.SELF_ID);
        String metaEventType = postData.getStr(OneBotConstants.META_EVENT_TYPE);
        if(CharSequenceUtil.equals(metaEventType, OneBotConstants.HEARTBEAT)){
            JSONObject statusJson = postData.getJSONObject(OneBotConstants.STATUS);
            JSONObject extData = JSONUtil.createObj();
            extData.set(OneBotConstants.HEARTBEAT,statusJson);
            DaBotInfo daBotInfo = DaBotInfo.builder()
                    .botNumber(selfId)
                    .lastHeartbeatTime(LocalDateTimeUtil.of(time))
                    .extData(extData.toStringPretty())
                    .build();
            CompletableFuture.runAsync(()->daBotInfoManager.updateBybotNumber(daBotInfo));
        }
    }
}
