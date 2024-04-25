package plus.easydo.bot.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;
import com.yomahub.liteflow.slot.Slot;
import plus.easydo.bot.lowcode.context.BotApiContext;
import plus.easydo.bot.lowcode.context.BotConfApiContext;
import plus.easydo.bot.lowcode.context.ContextBeanDesc;
import plus.easydo.bot.lowcode.context.ContextBeanMethodDesc;
import plus.easydo.bot.lowcode.context.DbApiContext;
import plus.easydo.bot.lowcode.context.JLCLiteFlowContext;
import plus.easydo.bot.lowcode.context.LogContext;
import plus.easydo.bot.lowcode.model.CmpContextBean;
import plus.easydo.bot.lowcode.model.CmpStepResult;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * @author yuzhanfeng
 * @Date 2024/4/14
 * @Description LiteFlowUtils
 */
public class LiteFlowUtils {

    private LiteFlowUtils() {
    }

    public static Object[] buildContext(JSONObject paramsJson) {
        List<Object> list = new ArrayList<>();
        //自定义上下文
        JLCLiteFlowContext context = new JLCLiteFlowContext();
        context.setParam(paramsJson);
        list.add(context);
        //日志
        LogContext logContext = new LogContext(context);
        list.add(logContext);
        //获取api服务
        BotApiContext apiContext = new BotApiContext(OneBotUtils.getParamBotNumber(paramsJson));
        list.add(apiContext);
        BotConfApiContext botConfApiContext = new BotConfApiContext(OneBotUtils.getParamBotNumber(paramsJson));
        list.add(botConfApiContext);
        DbApiContext dbApiContext = new DbApiContext();
        list.add(dbApiContext);
        return list.toArray();
    }

    public static void buildContextBeanList(LiteflowResponse response, CmpStepResult cmpStepResult) {
        if (Objects.nonNull(response)) {
            Slot slot = response.getSlot();
            List<CmpContextBean> cmpContextBeanList = new ArrayList<>();
            List<Tuple> contextBeanList = slot.getContextBeanList();
            contextBeanList.forEach(tuple -> {
                //获得使用时的名称
                String name = tuple.get(0);
                Object object = tuple.get(1);
                if (object instanceof JLCLiteFlowContext context) {
                    cmpStepResult.setLogs(context.getLogCache());
                }
                Class<?> clazz = object.getClass();
                //获取上下文的描述信息
                String desc = "";
                ContextBeanDesc contextBeanDesc = clazz.getAnnotation(ContextBeanDesc.class);
                if (Objects.nonNull(contextBeanDesc)) {
                    desc = contextBeanDesc.value();
                }
                //构建方法信息
                Method[] methods = ReflectUtil.getPublicMethods(clazz);
                List<CmpContextBean.CmpContextBeanMethod> methodNameList = Arrays.stream(methods).map(method -> {
                    Class<?> returnClazz = method.getReturnType();
                    String returnType = returnClazz.getSimpleName();
                    Parameter[] parameters = method.getParameters();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(method.getName()).append("(");

                    for (Parameter parameter : parameters) {
                        stringBuilder.append(parameter.getName()).append(",");
                    }
                    String result = StrUtil.removeSuffix(stringBuilder, ",");
                    stringBuilder.setLength(0);
                    stringBuilder.append(result).append(")");
                    ContextBeanMethodDesc contextBeanMethodDesc = method.getAnnotation(ContextBeanMethodDesc.class);
                    String methodDesc = "";
                    if (Objects.nonNull(contextBeanMethodDesc)) {
                        methodDesc = contextBeanMethodDesc.value();
                    }
                    return CmpContextBean.CmpContextBeanMethod.builder().name(method.getName()).returnType(returnType).demo(stringBuilder.toString()).desc(methodDesc).build();
                }).toList();
                cmpContextBeanList.add(CmpContextBean.builder().name(StrUtil.lowerFirst(name)).desc(desc).methods(methodNameList).build());
            });
            cmpStepResult.setContextBeanList(cmpContextBeanList);
        }
    }

    public static CmpStepResult getCmpStepResult(JSONObject paramsJson, LiteflowResponse response) {
        if (Objects.nonNull(response)) {
            Queue<CmpStep> cmpSteps = response.getExecuteStepQueue();
            CmpStep cmpStep = cmpSteps.poll();
            CmpStepResult cmpStepResult = BeanUtil.copyProperties(cmpStep, CmpStepResult.class);
            cmpStepResult.setParam(paramsJson);
            if (cmpStep != null && !cmpStep.isSuccess()) {
                cmpStepResult.setMessage(ExceptionUtil.getMessage(cmpStep.getException()));
            }
            //构建可用上下文
            buildContextBeanList(response, cmpStepResult);
            return cmpStepResult;
        } else {
            return CmpStepResult.builder().success(false).message("执行响应为空").build();
        }
    }
}
