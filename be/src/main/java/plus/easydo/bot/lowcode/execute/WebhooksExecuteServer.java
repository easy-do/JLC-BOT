package plus.easydo.bot.lowcode.execute;

import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.entity.WebhooksConf;
import plus.easydo.bot.manager.BotNodeExecuteLogManager;
import plus.easydo.bot.util.LiteFlowUtils;

import java.util.concurrent.CompletableFuture;

/**
 * @author yuzhanfeng
 * @Date 2024-04-25
 * @Description webhook脚本执行服务
 */
@Component
@RequiredArgsConstructor
public class WebhooksExecuteServer {

    private final FlowExecutor flowExecutor;

    private final BotNodeExecuteLogManager nodeExecuteLogManager;

    public LiteflowResponse execute(WebhooksConf webhooksConf, JSONObject paramsJson) {
        //匹配指令规则
        LiteFlowScript script = webhooksConf.getScript();
        //EL表达式
        String elData = "THEN(" + script.getScriptId() + ");";
        String chainName = LowCodeConstants.WEBHOOKS + "Chain" + webhooksConf.getId();
        LiteFlowChainELBuilder.createChain().setChainId(chainName).setEL(elData).build();

        //初始化上下文
        Object[] contexts = LiteFlowUtils.buildContext(paramsJson);

        //执行并返回结果
        LiteflowResponse res = flowExecutor.execute2Resp(chainName, "", contexts);
        CompletableFuture.runAsync(() -> nodeExecuteLogManager.saveExecuteLog(res, webhooksConf.getId(), webhooksConf.getConfName()));
        return res;
    }

}
