package plus.easydo.bot.hook;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.service.OneBotApiService;

/**
 * @author laoyu
 * @version 1.0
 * @description 发送群消息
 * @date 2024/4/4
 */
@Component("send_group_msg")
@RequiredArgsConstructor
public class SendGroupMessageHook implements OneBotWebHook{

    private OneBotApiService oneBotApiService;

    @Override
    public Object hook(String botNumber, JSONObject params) {
        oneBotApiService.sendGroupMessage(botNumber,params.getStr(OneBotConstants.GROUP_ID),params.getStr(OneBotConstants.MESSAGE));
        return "success";
    }
}
