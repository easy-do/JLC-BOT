package plus.easydo.bot.hook;

import cn.hutool.json.JSONObject;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBotWebHook抽象接口
 * @date 2024/4/4
 */

public interface OneBotWebHook {

    Object hook(String botNumber, JSONObject params);
}
