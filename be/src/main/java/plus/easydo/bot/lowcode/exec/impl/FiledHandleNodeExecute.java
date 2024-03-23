package plus.easydo.bot.lowcode.exec.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.lowcode.exec.NodeExecute;
import plus.easydo.bot.lowcode.model.ExecuteResult;
import plus.easydo.bot.util.ParamReplaceUtils;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-09
 * @Description 字段处理节点
 */
@Slf4j
@Service("filed_handle_node")
public class FiledHandleNodeExecute implements NodeExecute {

    @Override
    public ExecuteResult execute(JSONObject paramJson, JSONObject confJson) {
        log.debug("字段处理节点: param:{},conf:{}", paramJson, confJson);
        if (Objects.nonNull(paramJson) && Objects.nonNull(confJson)) {
            String type = confJson.getStr("type");
            if (Objects.nonNull(type)) {
                Object targetFiled = confJson.getByPath("targetFiled");
                String saveFiled = confJson.getStr("saveFiled");
                if (Objects.nonNull(targetFiled) && Objects.nonNull(saveFiled)) {
                    Object value = paramJson.getByPath(String.valueOf(targetFiled));
                    String targetValue = confJson.getStr("targetValue");
                    String sourceValue = confJson.getStr("sourceValue");
                    if (Objects.nonNull(value)) {
                        // 开始根据类型执行不同逻辑
                        switch (type) {
                            case "replace":
                                if (Objects.nonNull(targetValue) && Objects.nonNull(sourceValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, StrUtil.replace(String.valueOf(value), sourceValue, targetValue));
                                    return ExecuteResult.ok();
                                } else {
                                    log.warn("字段处理节点未完整执行,返回false,原因:要替换字段为空或要替换的内容未配置");
                                    return ExecuteResult.fail("要替换字段为空或要替换的内容未配置");
                                }
                            case "subBefore":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, CharSequenceUtil.subBefore(String.valueOf(value), targetValue, false));
                                    return ExecuteResult.ok();
                                } else {
                                    log.debug("字段处理节点未完整执行,返回false,原因:要处理字段为空或分隔符未配置");
                                    return ExecuteResult.fail("要处理字段为空或分隔符未配置");
                                }
                            case "subAfter":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, CharSequenceUtil.subAfter(String.valueOf(value), targetValue, false));
                                    return ExecuteResult.ok();
                                } else {
                                    log.warn("字段处理节点未完整执行,返回false,原因:要处理字段为空或分隔符未配置");
                                    return ExecuteResult.fail("要处理字段为空或分隔符未配置");
                                }
                            case "split":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, CharSequenceUtil.split(String.valueOf(value), targetValue));
                                    return ExecuteResult.ok();
                                } else {
                                    log.warn("字段处理节点未完整执行,返回false,原因:要处理字段为空或分隔符未配置");
                                    return ExecuteResult.fail("要处理字段为空或分隔符未配置");
                                }
                            case "appendBefore":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, targetValue + value);
                                    return ExecuteResult.ok();
                                } else {
                                    log.warn("字段处理节点未完整执行,返回false,原因:要处理字段为空或分隔符未配置");
                                    return ExecuteResult.fail("要处理字段为空或分隔符未配置");
                                }
                            case "appendAfter":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, value + targetValue);
                                    return ExecuteResult.ok();
                                } else {
                                    log.warn("字段处理节点未完整执行,返回false,原因:要处理字段为空或分隔符未配置");
                                    return ExecuteResult.fail("要处理字段为空或分隔符未配置");
                                }
                            case "strLength":
                                paramJson.set(saveFiled, String.valueOf(value).length());
                                return ExecuteResult.ok();
                            case "listLength":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, CharSequenceUtil.split(String.valueOf(value), targetValue).size());
                                    return ExecuteResult.ok();
                                } else {
                                    log.warn("字段处理节点未完整执行,返回false,原因:要处理字段为空或分隔符未配置");
                                    return ExecuteResult.fail("要处理字段为空或分隔符未配置");
                                }
                            case "toJson":
                                paramJson.set(saveFiled, JSONUtil.parseObj(value));
                                return ExecuteResult.ok();
                            default:
                                return ExecuteResult.fail("没有匹配到类型[" + type + "]");
                        }
                    } else {
                        log.warn("字段处理节点未完整执行,原因:目标字段不存在");
                        return ExecuteResult.fail("目标字段不存在");
                    }
                } else {
                    log.warn("字段处理节点未完整执行,原因:目标字段获赋值字段未定义");
                    return ExecuteResult.fail("目标字段获赋值字段未定义");
                }
            } else {
                log.warn("字段处理节点未完整执行,原因:判断类型未设置");
                return ExecuteResult.fail("判断类型未设置");
            }
        } else {
            log.warn("字段处理节点未完整执行,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            return ExecuteResult.fail("参数或节点配置为空");
        }
    }
}
