package plus.easydo.bot.lowcode.node;

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

    private static final String TARGET_VALUE_NOT_CONFIG = "要判断的内容未配置";

    @Override
    public boolean processIf() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("判断机器人配置: param:{},conf:{}", paramJson, confJson);
        if (Objects.nonNull(paramJson) && Objects.nonNull(confJson)) {
            String botNumber = paramJson.getStr(OneBotConstants.SELF_ID);
            String confKey = confJson.getStr("confKey");
            String type = confJson.getStr("type");
            if (Objects.nonNull(confKey) && Objects.nonNull(type)) {
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
                        if (checkTargetValue(targetValue)) {
                            targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                            return Objects.nonNull(confValue) && CharSequenceUtil.equals(confValue, targetValue);
                        } else {
                            throwNotConfigTargetValue();
                        }
                    }
                    case "notEquals" -> {
                        if (checkTargetValue(targetValue)) {
                            targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                            return Objects.isNull(confValue) || !CharSequenceUtil.equals(confValue, targetValue);
                        } else {
                            throwNotConfigTargetValue();
                        }
                    }
                    case "lengthEquals" -> {
                        if (checkTargetValue(targetValue)) {
                            return Objects.nonNull(confValue) && confValue.length() == Integer.parseInt(targetValue);
                        } else {
                            throwNotConfigTargetValue();
                        }
                    }
                    case "toListContains" -> {
                        if (checkTargetValue(targetValue)) {
                            if (Objects.nonNull(confValue)) {
                                List<String> list = StrUtil.split(confValue, ",");
                                return list.contains(targetValue);
                            }
                            return false;
                        } else {
                            throwNotConfigTargetValue();
                        }
                    }
                    default -> throw new BaseException("没有匹配到类型[" + type + "]");
                }
            } else {
                log.warn("配置信息不全,conf:{}", confJson);
                throw new BaseException("配置信息不全");
            }
        } else {
            log.warn("判断机器人配置节点未完整执行,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            throw new BaseException("参数或节点配置为空");
        }
        return false;
    }


    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(), context.getParam());
    }

    private boolean checkTargetValue(String targetValue) {
        boolean res = CharSequenceUtil.isNotBlank(targetValue);
        if (!res) {
            log.warn("配置判断节点未完整执行,原因:要判断的内容未配置");
        }
        return res;
    }

    private void throwNotConfigTargetValue() {
        throw new BaseException(TARGET_VALUE_NOT_CONFIG);
    }

}
