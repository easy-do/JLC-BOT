package plus.easydo.bot.service;

import cn.hutool.json.JSONObject;

/**
 * @author yuzhanfeng
 * @Date 2024-03-30
 * @Description 沙箱服务
 */
public interface SandboxService {
    boolean sendMessage(JSONObject message);
}
