package plus.easydo.bot.lowcode.execute;

import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.entity.HighLevelDevelopConf;
import plus.easydo.bot.manager.BotNodeExecuteLogManager;
import plus.easydo.bot.util.LiteFlowUtils;

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
        LiteFlowChainELBuilder.createChain().setChainId(chainName).setEL(elData).build();

        //初始化上下文
        Object[] contexts = LiteFlowUtils.buildContext(paramsJson);

        //执行并返回结果
        LiteflowResponse res = flowExecutor.execute2Resp(chainName, "", contexts);
        CompletableFuture.runAsync(() -> nodeExecuteLogManager.saveExecuteLog(res, highLevelDevelopConf.getId(), highLevelDevelopConf.getConfName()));
        return res;
    }

}
