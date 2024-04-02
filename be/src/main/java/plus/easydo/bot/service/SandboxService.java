package plus.easydo.bot.service;


import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.sandbox.SandboxMessage;

import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-03-30
 * @Description 沙箱服务
 */
public interface SandboxService {
    List<CmpStepResult> sendMessage(SandboxMessage sandboxMessage);
}
