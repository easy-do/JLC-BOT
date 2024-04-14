package plus.easydo.bot.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.context.ContextBean;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.Slot;
import plus.easydo.bot.lowcode.context.ContextBeanDesc;
import plus.easydo.bot.lowcode.context.ContextBeanMethodDesc;
import plus.easydo.bot.lowcode.context.JLCLiteFlowContext;
import plus.easydo.bot.lowcode.model.CmpContextBean;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.service.OneBotApiService;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        //获取api服务
        OneBotApiService apiServer = null;
        try {
            apiServer = OneBotApiUtils.getApiServer(OneBotUtils.getParamBotNumber(paramsJson));
            list.add(apiServer);
        } catch (Exception e) {
            //do nothing
        }
        return list.toArray();
    }

    public static void buildContextBeanList(LiteflowResponse response, CmpStepResult cmpStepResult) {
        if (Objects.nonNull(response)) {
            Slot slot = response.getSlot();
            List<CmpContextBean> cmpContextBeanList = new ArrayList<>();
            List<Object> contextBeanList = slot.getContextBeanList();
            contextBeanList.forEach(o -> {
                Class<?> clazz = o.getClass();
                //获得使用时的名称
                String name = clazz.getSimpleName();
                ContextBean contextBean = clazz.getAnnotation(ContextBean.class);
                if (Objects.nonNull(contextBean)) {
                    name = contextBean.value();
                }
                //获取上下文的描述信息
                String desc = "";
                ContextBeanDesc contextBeanDesc = clazz.getAnnotation(ContextBeanDesc.class);
                if (Objects.nonNull(contextBeanDesc)) {
                    desc = contextBeanDesc.value();
                }
                //构建方法信息
                Method[] methods = clazz.getDeclaredMethods();
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
}