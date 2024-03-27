package plus.easydo.bot.lowcode.node;

import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.util.BotConfUtil;

import java.util.Objects;

/**
 * 字段判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id="botConfNode",name="机器人配置")
public class BotConfNode extends NodeComponent {

    @Override
    public void process() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("保存机器人配置节点: param:{},conf:{}",paramJson,confJson);
        if(Objects.nonNull(paramJson) && Objects.nonNull(confJson)){
            String botNumber = paramJson.getStr(OneBotConstants.SELF_ID);
            String confKey = confJson.getStr("confkKey");
            String confValue = confJson.getStr("confValue");
            if(Objects.nonNull(confKey) && Objects.nonNull(confValue)){
                BotConfUtil.saveBotConf(botNumber,confKey,confValue);
            }else {
                log.warn("保存机器人配置节点未完整执行,原因:配置信息不全,conf:{}",confJson);
                throw new BaseException("配置信息不全");
            }
        }else {
            log.warn("保存机器人配置节点未完整执行,原因:参数或节点配置为空,param:{},conf:{}",paramJson,confJson);
            throw new BaseException("参数或节点配置为空");
        }
    }

    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(),context.getParam());
    }

}
