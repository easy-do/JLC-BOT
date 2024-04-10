package plus.easydo.bot.lowcode;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotNodeExecuteLog;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.lowcode.node.JLCLiteFlowContext;
import plus.easydo.bot.manager.BotNodeExecuteLogManager;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;

/**
 * @author yuzhanfeng
 * @Date 2024-04-10
 * @Description 建议开发执行服务
 */
@Component
@RequiredArgsConstructor
public class SimpleCmdDevelopExecuteServer {

    private final FlowExecutor flowExecutor;

    private final BotNodeExecuteLogManager nodeExecuteLogManager;

    public LiteflowResponse execute(SimpleCmdDevelopConf simpleCmdDevelopConf, JSONObject paramsJson) {
        String message = paramsJson.getStr(OneBotConstants.MESSAGE);
        if(CharSequenceUtil.equals(simpleCmdDevelopConf.getCmd(),message)){
            //将EL表达式
            String elData = "THEN(" + LowCodeConstants.SIMPLE_CMD_DEVELOP + simpleCmdDevelopConf.getId() + ");";
            String chainName = LowCodeConstants.SIMPLE_CMD_DEVELOP + "Chain" + simpleCmdDevelopConf.getId();
            LiteFlowChainELBuilder.createChain().setChainName(chainName).setEL(elData).build();
            //初始化上下文
            JLCLiteFlowContext context = new JLCLiteFlowContext();
            context.setParam(paramsJson);
            //执行并返回结果
            LiteflowResponse res = flowExecutor.execute2Resp(chainName, "", context);
            CompletableFuture.runAsync(() -> saveExecuteLog(simpleCmdDevelopConf, res));
            return res;
        }
        return null;
    }

    private void saveExecuteLog(SimpleCmdDevelopConf simpleCmdDevelopConf, LiteflowResponse res) {
        Queue<CmpStep> cmpSteps = res.getExecuteStepQueue();
        long timeNum = 0L;
        for (CmpStep cmpStep : cmpSteps) {
            com.yomahub.liteflow.flow.element.Node node = cmpStep.getRefNode();
            nodeExecuteLogManager.save(BotNodeExecuteLog.builder()
                    .confId(simpleCmdDevelopConf.getId())
                    .confName(simpleCmdDevelopConf.getConfName())
                    .nodeCode(node.getId())
                    .nodeName(node.getName())
                    .executeTime(timeNum)
                    .createTime(LocalDateTimeUtil.now())
                    .build());
            timeNum += cmpStep.getTimeSpent();
        }
    }
}
