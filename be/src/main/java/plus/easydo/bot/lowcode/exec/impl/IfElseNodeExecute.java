package plus.easydo.bot.lowcode.exec.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.lowcode.exec.NodeExecute;
import plus.easydo.bot.lowcode.model.ExecuteResult;
import plus.easydo.bot.util.ParamReplaceUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-06 11:34
 * @Description 通用判断节点
 */
@Slf4j
@Service("if_else_node")
public class IfElseNodeExecute implements NodeExecute {

    private static final String TARGET_VALUE_NOT_CONFIG = "要判断的内容未配置";

    @Override
    public ExecuteResult execute(JSONObject paramJson, JSONObject confJson) {
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
                                return ExecuteResult.ok(Objects.nonNull(value) && CharSequenceUtil.contains((String) value, targetValue));
                            } else {
                                return returnNotConfigTargetValue();
                            }
                        }
                        case "equals" -> {
                            if (checkTargetValue(targetValue)) {
                                targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                return ExecuteResult.ok(Objects.nonNull(value) && CharSequenceUtil.equals(String.valueOf(value), targetValue));
                            } else {
                                return returnNotConfigTargetValue();
                            }
                        }
                        case "notEquals" -> {
                            if (checkTargetValue(targetValue)) {
                                targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                return ExecuteResult.ok(Objects.isNull(value) || !CharSequenceUtil.equals(String.valueOf(value), targetValue));
                            } else {
                                return returnNotConfigTargetValue();
                            }
                        }
                        case "lengthEquals" -> {
                            if (checkTargetValue(targetValue)) {
                                return ExecuteResult.ok(Objects.nonNull(value) && String.valueOf(value).length() == Integer.parseInt(targetValue));
                            } else {
                                return returnNotConfigTargetValue();
                            }
                        }
                        case "toListContains" -> {
                            if (checkTargetValue(targetValue)) {
                                if (Objects.nonNull(value)) {
                                    List<String> list = StrUtil.split(String.valueOf(value), ",");
                                    return ExecuteResult.ok(list.contains(targetValue));
                                }
                                return ExecuteResult.ok(false);
                            } else {
                                return returnNotConfigTargetValue();
                            }
                        }
                        case "isNull" -> {
                            return ExecuteResult.ok(Objects.nonNull(value));
                        }
                        case "isNotNull" -> {
                            return ExecuteResult.ok(Objects.isNull(value));
                        }
                        case "isBlank" -> {
                            return ExecuteResult.ok(Objects.isNull(value) || CharSequenceUtil.isBlank(String.valueOf(value)));
                        }
                        case "isNotBlank" -> {
                            return ExecuteResult.ok(Objects.nonNull(value) && CharSequenceUtil.isNotBlank(String.valueOf(value)));
                        }
                        default -> {
                            return ExecuteResult.fail("没有匹配到类型[" + type + "]", false);
                        }
                    }
                } else {
                    log.warn("判断节点未完整执行,返回false,原因:目标字段未定义");
                    return ExecuteResult.fail("目标字段未定义",false);
                }
            } else {
                log.warn("判断节点未完整执行,返回false,原因:判断类型未设置");
                return ExecuteResult.fail("判断类型未设置",false);
            }
        } else {
            log.warn("判断节点未完整执行,返回false,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            return ExecuteResult.fail("参数或节点配置为空",false);
        }
    }

    private boolean checkTargetValue(String targetValue){
        boolean res = CharSequenceUtil.isNotBlank(targetValue);
        if(!res){
            log.warn("判断节点未完整执行,返回false,原因:要判断的内容未配置");
        }
        return res;
    }
    private ExecuteResult returnNotConfigTargetValue(){
        return ExecuteResult.fail(TARGET_VALUE_NOT_CONFIG,false);
    }
}
