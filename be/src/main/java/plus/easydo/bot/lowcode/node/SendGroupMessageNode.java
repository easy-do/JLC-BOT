package plus.easydo.bot.lowcode.node;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.lowcode.context.JLCLiteFlowContext;
import plus.easydo.bot.util.OneBotApiUtils;
import plus.easydo.bot.util.ParamReplaceUtils;

/**
 * 字段判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id = "sendGroupMessageNode", name = "发送群消息")
public class SendGroupMessageNode extends NodeComponent {

    @Override
    public void process() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("发送群消息节点: param:{},conf:{}", paramJson, confJson);
        try {
            Assert.notNull(paramJson, "参数配置为空");
            Assert.notNull(confJson, "节点配置为空");
            String message = confJson.getStr("message");
            Assert.notNull(message, "发送消息为空");
            String botNumber = paramJson.getStr(OneBotConstants.SELF_ID);
            String groupId = paramJson.getStr(OneBotConstants.GROUP_ID);
            message = ParamReplaceUtils.replaceParam(message, paramJson);
            OneBotApiUtils.sendGroupMessage(botNumber, groupId, message);
        } catch (Exception e) {
            log.warn("ollama模型调用节点未完整执行,原因:{}", e.getMessage());
            throw e;
        }
    }


    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(), context.getParam());
    }
}
