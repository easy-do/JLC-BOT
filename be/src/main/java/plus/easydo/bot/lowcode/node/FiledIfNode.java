package plus.easydo.bot.lowcode.node;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeIfComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.lowcode.context.JLCLiteFlowContext;
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
@LiteflowComponent(id = "filedIfNode", name = "字段判断")
public class FiledIfNode extends NodeIfComponent {

    private static final String TARGET_VALUE_NOT_CONFIG = "目标值为空";

    @Override
    public boolean processIf() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("判断节点: param:{},conf:{}", paramJson, confJson);
        try {
            Assert.notNull(paramJson, "参数配置为空");
            Assert.notNull(confJson, "节点配置为空");
            String type = confJson.getStr("type");
            Assert.notNull(type, "判断类型未设置");
            String targetFiled = confJson.getStr("targetFiled");
            Assert.notNull(targetFiled, "要判断字段未定义");
            Object value = paramJson.getByPath(targetFiled);
            Assert.notNull(value, "要判断字段内容为空");
            String targetValue = confJson.getStr("targetValue");
            // 开始根据类型执行不同逻辑
            switch (type) {
                case "contains" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    return Objects.nonNull(value) && CharSequenceUtil.contains((String) value, targetValue);
                }
                case "startWith" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    return Objects.nonNull(value) && CharSequenceUtil.startWith((String) value, targetValue);
                }
                case "endWith" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    return Objects.nonNull(value) && CharSequenceUtil.endWith((String) value, targetValue);
                }
                case "equals" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    return Objects.nonNull(value) && CharSequenceUtil.equals(String.valueOf(value), targetValue);
                }
                case "notEquals" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    return Objects.isNull(value) || !CharSequenceUtil.equals(String.valueOf(value), targetValue);
                }
                case "lengthEquals" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    return Objects.nonNull(value) && String.valueOf(value).length() == Integer.parseInt(targetValue);
                }
                case "toListContains" -> {
                    Assert.notNull(targetValue, TARGET_VALUE_NOT_CONFIG);
                    if (Objects.nonNull(value)) {
                        List<String> list = StrUtil.split(String.valueOf(value), ",");
                        return list.contains(targetValue);
                    }
                    return false;
                }
                case "isNull" -> {
                    return Objects.isNull(value);
                }
                case "isNotNull" -> {
                    return Objects.nonNull(value);
                }
                case "isBlank" -> {
                    return Objects.isNull(value) || CharSequenceUtil.isBlank(String.valueOf(value));
                }
                case "isNotBlank" -> {
                    return Objects.nonNull(value) && CharSequenceUtil.isNotBlank(String.valueOf(value));
                }
                default -> throw new BaseException("没有匹配到类型[" + type + "]");
            }
        } catch (Exception e) {
            log.warn("判断节点未完整执行,原因:{}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(), context.getParam());
    }

}
