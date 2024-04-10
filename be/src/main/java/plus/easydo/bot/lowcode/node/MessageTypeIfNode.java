package plus.easydo.bot.lowcode.node;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeIfComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.websocket.model.OneBotMessageParse;

import java.util.Objects;

/**
 * 消息判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id = "messageTypeIfNode", name = "消息类型判断")
public class MessageTypeIfNode extends NodeIfComponent {

    @Override
    public boolean processIf() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("判断消息节点: param:{},conf:{}", paramJson, confJson);
        try {
            Assert.notNull(paramJson, "参数配置为空");
            Assert.notNull(confJson, "节点配置为空");
            String type = confJson.getStr("type");
            Assert.notNull(type, "判断类型未设置");
            JSONObject message = paramJson.getJSONObject(OneBotConstants.MESSAGE_PARSE);
            Assert.notNull(type, OneBotConstants.MESSAGE_PARSE + "为空");
            OneBotMessageParse messageParse = message.toBean(OneBotMessageParse.class);
            return Objects.nonNull(messageParse) && CharSequenceUtil.equals(messageParse.getType(), type);
        } catch (Exception e) {
            log.warn("判断消息节点未完整执行,原因:{}", e.getMessage());
            throw e;
        }
    }


    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(), context.getParam());
    }
}
