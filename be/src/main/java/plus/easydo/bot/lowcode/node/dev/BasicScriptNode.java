package plus.easydo.bot.lowcode.node.dev;

import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.script.ScriptExecuteWrap;
import com.yomahub.liteflow.script.body.JaninoCommonScriptBody;
import plus.easydo.bot.lowcode.context.JLCLiteFlowContext;

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
        //获取框架自定义上下文JLCLiteFlowContext   脚本里必须加类型转换(JLCLiteFlowContext)
        JLCLiteFlowContext context = (JLCLiteFlowContext) cpm.getContextBean(JLCLiteFlowContext.class);
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
        System.out.println("脚本节点[" + cpm.getName() + "]=>上下文:" + paramJson.toJSONString(0));
        System.out.println("脚本节点[" + cpm.getName() + "]=>表单配置:" + confJson.toJSONString(0));
        //完成节点逻辑后应当将上下文参数存到对应的缓存中,以方便调试节点执行情况
        context.getNodeParamCache().put(tag, context.getParam());
        // 固定返回null
        return null;
    }

}
