package plus.easydo.bot.lowcode.node;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeIfComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.websocket.model.OneBotMessageParse;

import java.util.Objects;

/**
 * 消息判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id="messageTypeIfNode",name="消息类型判断")
public class MessageTypeIfNode extends NodeIfComponent {

    @Override
    public boolean processIf() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("判断消息节点: param:{},conf:{}", paramJson, confJson);
        if (Objects.nonNull(paramJson) && Objects.nonNull(confJson)) {
            String type = confJson.getStr("type");
            if (Objects.nonNull(type)) {
                JSONObject message = paramJson.getJSONObject(OneBotConstants.MESSAGE);
                if(Objects.nonNull(message)){
                    OneBotMessageParse messageParse = message.toBean(OneBotMessageParse.class);
                    return Objects.nonNull(messageParse) && CharSequenceUtil.equals(messageParse.getType(), type);
                }else {
                    log.warn("判断消息节点未完整执行,原因:消息字段不存在");
                    throw new BaseException("判断类型未设置");
                }
            } else {
                log.warn("判断消息节点未完整执行,原因:判断类型未设置");
                throw new BaseException("判断类型未设置");
            }
        } else {
            log.warn("判断消息节点未完整执行,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            throw new BaseException("参数或节点配置为空");
        }
    }


    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(),context.getParam());
    }
}
