package plus.easydo.bot.lowcode.node;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.lowcode.context.JLCLiteFlowContext;
import plus.easydo.bot.util.ParamReplaceUtils;


/**
 * @author yuzhanfeng
 * @Date 2024-03-27
 * @Description 字段处理节点
 */
@Slf4j
@LiteflowComponent(id = "filedHandlerNode", name = "字段处理")
public class FiledHandlerNode extends NodeComponent {
    @Override
    public void process() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("字段处理节点: param:{},conf:{}", paramJson, confJson);
        try {
            Assert.notNull(paramJson, "参数配置为空");
            Assert.notNull(confJson, "节点配置为空");
            String type = confJson.getStr("type");
            Assert.notNull(type, "处理类型未设置");
            Object targetFiled = confJson.getByPath("targetFiled");
            String saveFiled = confJson.getStr("saveFiled");
            Assert.notNull(targetFiled, "目标字段未定义");
            Assert.notNull(saveFiled, "赋值字段未定义");
            Object value = paramJson.getByPath(String.valueOf(targetFiled));
            Assert.notNull(value, "目标字段不存在");
            //要操作的内容
            String sourceValue = confJson.getStr("sourceValue");
            //目标值
            String targetValue = confJson.getStr("targetValue");
            // 开始根据类型执行不同逻辑
            switch (type) {
                case "replace":
                    Assert.notNull(sourceValue, "要操作的内容为空");
                    Assert.notNull(targetValue, "目标值为空");
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    sourceValue = ParamReplaceUtils.replaceParam(sourceValue, paramJson);
                    paramJson.set(saveFiled, StrUtil.replace(String.valueOf(value), sourceValue, targetValue));
                    break;
                case "subBefore":
                    Assert.notNull(targetValue, "分隔符为空");
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    paramJson.set(saveFiled, CharSequenceUtil.subBefore(String.valueOf(value), targetValue, false));
                    break;
                case "subAfter":
                    Assert.notNull(targetValue, "分隔符为空");
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    paramJson.set(saveFiled, CharSequenceUtil.subAfter(String.valueOf(value), targetValue, false));
                    break;
                case "split":
                    Assert.notNull(targetValue, "分隔符为空");
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    paramJson.set(saveFiled, CharSequenceUtil.split(String.valueOf(value), targetValue));
                    break;
                case "appendBefore":
                    Assert.notNull(targetValue, "目标值为空");
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    paramJson.set(saveFiled, targetValue + value);
                    break;
                case "appendAfter":
                    Assert.notNull(targetValue, "目标值为空");
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    paramJson.set(saveFiled, value + targetValue);
                    break;
                case "strLength":
                    paramJson.set(saveFiled, String.valueOf(value).length());
                    break;
                case "listLength":
                    Assert.notNull(targetValue, "分隔符为空");
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    paramJson.set(saveFiled, CharSequenceUtil.split(String.valueOf(value), targetValue).size());
                    break;
                case "trim":
                    paramJson.set(saveFiled, CharSequenceUtil.trim(String.valueOf(value)));
                    break;
                case "trimStart":
                    paramJson.set(saveFiled, CharSequenceUtil.trimStart(String.valueOf(value)));
                    break;
                case "trimEnd":
                    paramJson.set(saveFiled, CharSequenceUtil.trimEnd(String.valueOf(value)));
                    break;
                case "removeAll":
                    Assert.notNull(targetValue, "目标值为空");
                    paramJson.set(saveFiled, CharSequenceUtil.removeAll(String.valueOf(value),targetValue));
                    break;
                case "removePrefix":
                    Assert.notNull(targetValue, "前缀未定义");
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    paramJson.set(saveFiled, CharSequenceUtil.removePrefix(String.valueOf(value), targetValue));
                    break;
                case "removeSuffix":
                    Assert.notNull(targetValue, "后缀未定义");
                    targetValue = ParamReplaceUtils.replaceParam(targetValue, paramJson);
                    paramJson.set(saveFiled, CharSequenceUtil.removeSuffix(String.valueOf(value), targetValue));
                    break;
                case "toJson":
                    paramJson.set(saveFiled, JSONUtil.parseObj(value));
                    break;
                default:
                    throw new BaseException("没有匹配到类型[" + type + "]");
            }
        } catch (Exception e) {
            log.warn("字段处理节点未完整执行,原因:{}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(), context.getParam());
    }
}
