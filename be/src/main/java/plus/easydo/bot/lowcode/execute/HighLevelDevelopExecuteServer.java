package plus.easydo.bot.lowcode.execute;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.entity.BotNodeExecuteLog;
import plus.easydo.bot.entity.HighLevelDevelopConf;
import plus.easydo.bot.lowcode.node.JLCLiteFlowContext;
import plus.easydo.bot.manager.BotNodeExecuteLogManager;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;

/**
 * @author yuzhanfeng
 * @Date 2024-04-10
 * @Description 高级开发执行服务
 */
@Component
@RequiredArgsConstructor
public class HighLevelDevelopExecuteServer {


    private final FlowExecutor flowExecutor;

    private final BotNodeExecuteLogManager nodeExecuteLogManager;

    public LiteflowResponse execute(HighLevelDevelopConf highLevelDevelopConf, JSONObject paramsJson) {
        //将EL表达式
        String elData = "THEN("+ LowCodeConstants.HIGH_LEVEL_DEVELOP+highLevelDevelopConf.getId() +");";
        String chainName = LowCodeConstants.HIGH_LEVEL_DEVELOP+"Chain" + highLevelDevelopConf.getId();
        LiteFlowChainELBuilder.createChain().setChainName(chainName).setEL(elData).build();
        //初始化上下文
        JLCLiteFlowContext context = new JLCLiteFlowContext();
        context.setParam(paramsJson);
        //执行并返回结果
        LiteflowResponse res = flowExecutor.execute2Resp(chainName, "", context);
        CompletableFuture.runAsync(() -> saveExecuteLog(highLevelDevelopConf, res));
        return res;
    }

    private void saveExecuteLog(HighLevelDevelopConf highLevelDevelopConf, LiteflowResponse res) {
        Queue<CmpStep> cmpSteps = res.getExecuteStepQueue();
        long timeNum = 0L;
        for (CmpStep cmpStep : cmpSteps) {
            com.yomahub.liteflow.flow.element.Node node = cmpStep.getRefNode();
            nodeExecuteLogManager.save(BotNodeExecuteLog.builder()
                    .confId(highLevelDevelopConf.getId())
                    .confName(highLevelDevelopConf.getConfName())
                    .nodeCode(node.getId())
                    .nodeName(node.getName())
                    .executeTime(timeNum)
                    .createTime(LocalDateTimeUtil.now())
                    .build());
            timeNum += cmpStep.getTimeSpent();
        }
    }
}
