package plus.easydo.bot.lowcode.node;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeIfComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.util.BotConfUtil;
import plus.easydo.bot.util.ParamReplaceUtils;

import java.util.List;
import java.util.Objects;

/**
 * 字段判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id = "botConfIfNode", name = "判断机器人配置")
public class BotConfIfNode extends NodeIfComponent {

    private static final String TARGET_VALUE_NOT_CONFIG = "目标值为空";

    @Override
    public boolean processIf() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("判断机器人配置: param:{},conf:{}", paramJson, confJson);
        try {
            Assert.notNull(paramJson, "参数配置为空");
            Assert.notNull(confJson, "节点配置为空");
            String botNumber = paramJson.getStr(OneBotConstants.SELF_ID);
            String confKey = confJson.getStr("confKey");
            String type = confJson.getStr("type");
            Assert.notNull(confKey, "机器人配置的key为空");
            Assert.notNull(type, "判断类型为空");
            String confValue = BotConfUtil.getBotConfNull(botNumber, confKey);
            String targetValue = confJson.getStr("targetValue");

            // 开始根据类型执行不同逻辑
            switch (type) {
                case "isNull" -> {
                    return Objects.isNull(confValue);
                }
                case "isNotNull" -> {
                    return Objects.nonNull(confValue);
                }
                case "isBlank" -> {
                    return Objects.isNull(confValue) || CharSequenceUtil.isBlank(confValue);
                }
                case "isNotBlank" -> {
                    return Objects.nonNull(confValue) && CharSequenceUtil.isNotBlank(confValue);
                }
                case "contains" -> {
                    return Objects.nonNull(confValue) && CharSequenceUtil.contains(confValue, targetValue);
                }
                case "equals" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    return Objects.nonNull(confValue) && CharSequenceUtil.equals(confValue, targetValue);
                }
                case "notEquals" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    return Objects.isNull(confValue) || !CharSequenceUtil.equals(confValue, targetValue);
                }
                case "lengthEquals" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    return Objects.nonNull(confValue) && confValue.length() == Integer.parseInt(targetValue);
                }
                case "toListContains" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    if (Objects.nonNull(confValue)) {
                        List<String> list = StrUtil.split(confValue, ",");
                        return list.contains(targetValue);
                    }
                    return false;
                }
                default -> throw new BaseException("没有匹配到类型[" + type + "]");
            }
        } catch (Exception e) {
            log.warn("判断机器人配置节点未完整执行,原因:{}", e.getMessage());
            throw e;
        }
    }


    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(), context.getParam());
    }

}
