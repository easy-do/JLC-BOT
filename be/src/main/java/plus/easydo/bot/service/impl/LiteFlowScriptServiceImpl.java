package plus.easydo.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.manager.LiteFlowScriptManager;
import plus.easydo.bot.service.LiteFlowScriptService;

/**
 * @author yuzhanfeng
 * @Date 2024-04-11
 * @Description LiteFlowScript
 */
@Service
@RequiredArgsConstructor
public class LiteFlowScriptServiceImpl implements LiteFlowScriptService {

    private final LiteFlowScriptManager liteFlowScriptManager;
    @Override
    public boolean updateLiteFlowScript(LiteFlowScript liteFlowScript) {
        return liteFlowScriptManager.updateScriptData(liteFlowScript);
    }
}
