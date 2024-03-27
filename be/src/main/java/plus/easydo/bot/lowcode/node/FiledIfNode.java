package plus.easydo.bot.lowcode.node;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeIfComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.exception.BaseException;
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
@LiteflowComponent(id = "filedIfNode", name = "字段判断节点")
public class FiledIfNode extends NodeIfComponent {

    private static final String TARGET_VALUE_NOT_CONFIG = "要判断的内容未配置";

    @Override
    public boolean processIf() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("判断节点: param:{},conf:{}", paramJson, confJson);
        if (Objects.nonNull(paramJson) && Objects.nonNull(confJson)) {
            String type = confJson.getStr("type");
            if (Objects.nonNull(type)) {
                String targetFiled = confJson.getStr("targetFiled");
                if (Objects.nonNull(targetFiled)) {
                    Object value = paramJson.getByPath(targetFiled);
                    String targetValue = confJson.getStr("targetValue");
                    // 开始根据类型执行不同逻辑
                    switch (type) {
                        case "contains" -> {
                            if (checkTargetValue(targetValue)) {
                                targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                return Objects.nonNull(value) && CharSequenceUtil.contains((String) value, targetValue);
                            } else {
                                throwNotConfigTargetValue();
                            }
                        }
                        case "equals" -> {
                            if (checkTargetValue(targetValue)) {
                                targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                return Objects.nonNull(value) && CharSequenceUtil.equals(String.valueOf(value), targetValue);
                            } else {
                                throwNotConfigTargetValue();
                            }
                        }
                        case "notEquals" -> {
                            if (checkTargetValue(targetValue)) {
                                targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                return Objects.isNull(value) || !CharSequenceUtil.equals(String.valueOf(value), targetValue);
                            } else {
                                throwNotConfigTargetValue();
                            }
                        }
                        case "lengthEquals" -> {
                            if (checkTargetValue(targetValue)) {
                                return Objects.nonNull(value) && String.valueOf(value).length() == Integer.parseInt(targetValue);
                            } else {
                                throwNotConfigTargetValue();
                            }
                        }
                        case "toListContains" -> {
                            if (checkTargetValue(targetValue)) {
                                if (Objects.nonNull(value)) {
                                    List<String> list = StrUtil.split(String.valueOf(value), ",");
                                    return list.contains(targetValue);
                                }
                                return false;
                            } else {
                                throwNotConfigTargetValue();
                            }
                        }
                        case "isNull" -> {
                            return Objects.nonNull(value);
                        }
                        case "isNotNull" -> {
                            return Objects.isNull(value);
                        }
                        case "isBlank" -> {
                            return Objects.isNull(value) || CharSequenceUtil.isBlank(String.valueOf(value));
                        }
                        case "isNotBlank" -> {
                            return Objects.nonNull(value) && CharSequenceUtil.isNotBlank(String.valueOf(value));
                        }
                        default -> throw new BaseException("没有匹配到类型[" + type + "]");
                    }
                } else {
                    log.warn("判断节点未完整执行,返回false,原因:目标字段未定义");
                    throw new BaseException("目标字段未定义");
                }
            } else {
                log.warn("判断节点未完整执行,返回false,原因:判断类型未设置");
                throw new BaseException("判断类型未设置");
            }
        } else {
            log.warn("判断节点未完整执行,返回false,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            throw new BaseException("参数或节点配置为空");
        }
        return false;
    }

    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(),context.getParam());
    }

    private boolean checkTargetValue(String targetValue) {
        boolean res = CharSequenceUtil.isNotBlank(targetValue);
        if (!res) {
            log.warn("判断节点未完整执行,返回false,原因:要判断的内容未配置");
        }
        return res;
    }

    private void throwNotConfigTargetValue() {
        throw new BaseException(TARGET_VALUE_NOT_CONFIG);
    }

}
