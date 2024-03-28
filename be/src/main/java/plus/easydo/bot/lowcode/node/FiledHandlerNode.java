package plus.easydo.bot.lowcode.node;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.util.ParamReplaceUtils;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-27
 * @Description 字段处理节点
 */
@Slf4j
@LiteflowComponent(id="filedHandlerNode",name="字段处理")
public class FiledHandlerNode extends NodeComponent {
    @Override
    public void process() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
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
                                } else {
                                    log.warn("字段处理节点未完整执行,原因:要替换字段为空或要替换的内容未配置");
                                    throw new BaseException("要替换字段为空或要替换的内容未配置");
                                }
                                break;
                            case "subBefore":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, CharSequenceUtil.subBefore(String.valueOf(value), targetValue, false));
                                } else {
                                    log.debug("字段处理节点未完整执行,原因:要处理字段为空或分隔符未配置");
                                    throw new BaseException("要处理字段为空或分隔符未配置");
                                }
                                break;
                            case "subAfter":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, CharSequenceUtil.subAfter(String.valueOf(value), targetValue, false));
                                } else {
                                    log.warn("字段处理节点未完整执行,原因:要处理字段为空或分隔符未配置");
                                    throw new BaseException("要处理字段为空或分隔符未配置");
                                }
                                break;
                            case "split":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, CharSequenceUtil.split(String.valueOf(value), targetValue));
                                } else {
                                    log.warn("字段处理节点未完整执行,原因:要处理字段为空或分隔符未配置");
                                    throw new BaseException("要处理字段为空或分隔符未配置");
                                }
                                break;
                            case "appendBefore":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, targetValue + value);
                                } else {
                                    log.warn("字段处理节点未完整执行,原因:要处理字段为空或分隔符未配置");
                                    throw new BaseException("要处理字段为空或分隔符未配置");
                                }
                                break;
                            case "appendAfter":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, value + targetValue);
                                } else {
                                    log.warn("字段处理节点未完整执行,原因:要处理字段为空或分隔符未配置");
                                    throw new BaseException("要处理字段为空或分隔符未配置");
                                }
                                break;
                            case "strLength":
                                paramJson.set(saveFiled, String.valueOf(value).length());
                                break;
                            case "listLength":
                                if (Objects.nonNull(targetValue)) {
                                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                                    paramJson.set(saveFiled, CharSequenceUtil.split(String.valueOf(value), targetValue).size());
                                } else {
                                    log.warn("字段处理节点未完整执行,原因:要处理字段为空或分隔符未配置");
                                    throw new BaseException("要处理字段为空或分隔符未配置");
                                }
                                break;
                            case "toJson":
                                paramJson.set(saveFiled, JSONUtil.parseObj(value));
                                break;
                            default:
                                throw new BaseException("没有匹配到类型[" + type + "]");
                        }
                    } else {
                        log.warn("字段处理节点未完整执行,原因:目标字段不存在");
                        throw new BaseException("目标字段不存在");
                    }
                } else {
                    log.warn("字段处理节点未完整执行,原因:目标字段获赋值字段未定义");
                    throw new BaseException("目标字段获赋值字段未定义");
                }
            } else {
                log.warn("字段处理节点未完整执行,原因:判断类型未设置");
                throw new BaseException("判断类型未设置");
            }
        } else {
            log.warn("字段处理节点未完整执行,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            throw new BaseException("参数或节点配置为空");
        }
    }

    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(),context.getParam());
    }
}
