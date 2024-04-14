package plus.easydo.bot.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2024-04-01
 * @Description LiteFlow常量
 */
public class LiteFlowConstants {

    private LiteFlowConstants() {
    }

    public static final String IF_SCRIPT = "if_script";

    public static final String SCRIPT = "script";

    public static final String JAVA_SCRIPT_DATA = """
            import cn.hutool.json.JSONObject;
            import com.yomahub.liteflow.core.NodeComponent;
            import com.yomahub.liteflow.script.ScriptExecuteWrap;
            import com.yomahub.liteflow.script.body.JaninoCommonScriptBody;
            import plus.easydo.bot.lowcode.context.JLCLiteFlowContext;
            import plus.easydo.bot.util.ParamReplaceUtils;
                        
            /**
             * @author laoyu
             * @Description 普通脚本节点 https://liteflow.cc/pages/2b8afb/#%E4%BB%8B%E7%BB%8D
             */
            public class BasicScriptNode implements JaninoCommonScriptBody {
                        
                /**
                 * 脚本逻辑在body方法内写
                 *
                 * @param scriptExecuteWrap scriptExecuteWrap
                 * @return null
                 * @author laoyu
                 */
                @Override
                public Void body(ScriptExecuteWrap scriptExecuteWrap) {
                    //获取NodeComponent
                    NodeComponent cpm = scriptExecuteWrap.getCmp();
                    //获取框架自定义上下文JLCLiteFlowContext 脚本里必须加类型转换(JLCLiteFlowContext)
                    JLCLiteFlowContext context = (JLCLiteFlowContext)cpm.getContextBean(JLCLiteFlowContext.class);
                    // paramJson是上下文中传递的参数,消息数据就在这里
                    JSONObject paramJson = context.getParam();
                    // 通过tag拿到当前节点的表单配置信息
                    //String tag = scriptExecuteWrap.getTag();
                    //JSONObject nodeConf = context.getNodeConf();
                    //JSONObject confJson = nodeConf.getJSONObject(tag);
                    //通过表单配置的dataIndex获取表单填写的参数
                    //String confValue = confJson.getStr("confKey");
                    //通过ParamReplaceUtils的replaceParam方法从上下文中取值替换${xxx}占位符的内容
                    //ParamReplaceUtils.replaceParam(confValue,paramJson);
                    //打印上下文和表单配置信息
                    System.out.println("脚本节点["+cpm.getName()+"]=>上下文:"+paramJson.toJSONString(0));
                    //System.out.println("脚本节点["+cpm.getName()+"]=>表单配置:"+confJson.toJSONString(0));
                    //完成节点逻辑后应当将上下文参数存到对应的缓存中,以方便调试节点执行情况
                    //context.getNodeParamCache().put(tag, context.getParam());
                    // 固定返回null
                    return null;
                }
                        
            }
            """;

    public static final String JAVA_IF_SCRIPT_DATA = """ 
            import cn.hutool.json.JSONObject;
            import com.yomahub.liteflow.core.NodeComponent;
            import com.yomahub.liteflow.script.ScriptExecuteWrap;
            import com.yomahub.liteflow.script.body.JaninoIfScriptBody;
            import plus.easydo.bot.lowcode.context.JLCLiteFlowContext;
            import plus.easydo.bot.util.ParamReplaceUtils;
                        
            import java.util.Objects;
                        
            /**
             * @author laoyu
             * @Description 判断脚本节点 https://liteflow.cc/pages/2b8afb/#%E4%BB%8B%E7%BB%8D
             */
            public class IfScriptNode implements JaninoIfScriptBody {
                        
                /**
                 * 脚本逻辑在body方法内写
                 *
                 * @param scriptExecuteWrap scriptExecuteWrap
                 * @return null
                 * @author laoyu
                 */
                @Override
                public Boolean body(ScriptExecuteWrap scriptExecuteWrap) {
                    //获取NodeComponent
                    NodeComponent cpm = scriptExecuteWrap.getCmp();
                    //获取框架自定义上下文JLCLiteFlowContext  脚本里必须加类型转换(JLCLiteFlowContext)
                    JLCLiteFlowContext context = (JLCLiteFlowContext)cpm.getContextBean(JLCLiteFlowContext.class);
                    // paramJson是上下文中传递的参数,消息数据就在这里
                    JSONObject paramJson = context.getParam();
                    // 通过tag拿到当前节点的表单配置信息
                    String tag = scriptExecuteWrap.getTag();
                    JSONObject nodeConf = context.getNodeConf();
                    JSONObject confJson = nodeConf.getJSONObject(tag);
                    //通过表单配置的dataIndex获取表单填写的参数
                    String confValue = confJson.getStr("confKey");
                    //通过ParamReplaceUtils的replaceParam方法从上下文中取值替换${xxx}占位符的内容
                    //ParamReplaceUtils.replaceParam(confValue,paramJson);
                    //打印上下文和表单配置信息
                    System.out.println("条件脚本节点["+cpm.getName()+"]=>上下文:"+paramJson.toJSONString(0));
                    System.out.println("条件脚本节点["+cpm.getName()+"]=>表单配置:"+confJson.toJSONString(0));
                    //完成节点逻辑后应当将上下文参数存到对应的缓存中,以方便调试节点执行情况
                    context.getNodeParamCache().put(tag, context.getParam());
                    // 需要返回判断结果 true或false
                    boolean result = Objects.nonNull(confValue);
                    System.out.println("条件脚本节点["+cpm.getName()+"]=>执行结果:"+result);
                    return result;
                }
                        
            }
            """;

    public static final String GROOVY_SCRIPT_DATA = """ 
                import cn.hutool.core.collection.ListUtil
                import cn.hutool.core.date.DateUtil
                        
                import java.util.function.Consumer
                import java.util.function.Function
                import java.util.stream.Collectors
                        
                def date = DateUtil.parse("2022-10-17 13:31:43")
                println(date)
                // 获取上下文信息
                println(jLCLiteFlowContext.getParam())
            """;
    public static final String JAVASCRIPT_SCRIPT_DATA = """ 
            var a=3;
            var b=2;
            var c=1;
            var d=5;

            function addByArray(values) {
                var sum = 0;
                for (var i = 0; i < values.length; i++) {
                    sum += values[i];
                }
                return sum;
            }

            var result = addByArray([a,b,c,d]);

            defaultContext.setData("s1",parseInt(result));
                """;
    public static final String PYTHON_SCRIPT_DATA = """
            import json
                            
                            
            x='{"name": "张三", "age": 25, "nationality": "China"}'
            jsonData=json.loads(x)
            name=jsonData['name']
            print name.decode('UTF-8')
            print 'jLCLiteFlowContext.getParam():'
            ## 获取上下文信息
            print jLCLiteFlowContext.getParam()
                """;
    public static final String LUA_SCRIPT_DATA = """
            local a=6
            local b=10
            if(a>5) then
                b=5
            else
                b=2
            end
            print(a)
            print(b)
            --获取上下文信息
            local param = jLCLiteFlowContext:getParam()
            print(param)
                """;
    public static final String AVIATOR_SCRIPT_DATA = """
            use java.util.Date;
            use cn.hutool.core.date.DateUtil;
            let d = DateUtil.formatDateTime(new Date());
            println(d);
            ##获取上下文信息
            let param = getParam(jLCLiteFlowContext);
            println(param);
                """;

    public static final Map<String,String> SCRIPT_DATA_MAP = new HashMap<>();

    static {
        SCRIPT_DATA_MAP.put("java",JAVA_SCRIPT_DATA);
        SCRIPT_DATA_MAP.put("groovy",GROOVY_SCRIPT_DATA);
        SCRIPT_DATA_MAP.put("js",JAVASCRIPT_SCRIPT_DATA);
        SCRIPT_DATA_MAP.put("python",PYTHON_SCRIPT_DATA);
        SCRIPT_DATA_MAP.put("lua",LUA_SCRIPT_DATA);
        SCRIPT_DATA_MAP.put("aviator",AVIATOR_SCRIPT_DATA);
    }

}
