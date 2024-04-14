package plus.easydo.bot.lowcode.context;

import cn.hutool.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2024-03-27
 * @Description 低代码上下文
 */
@ContextBeanDesc("自定义上下文,可拿到全局参数")
public class JLCLiteFlowContext {

    private JSONObject param;

    private JSONObject nodeConf;

    private Map<String, JSONObject> nodeParamCache = new HashMap<>();

    @ContextBeanMethodDesc("获取全局参数")
    public JSONObject getParam() {
        return param;
    }

    @ContextBeanMethodDesc("设置全局参数")
    public void setParam(JSONObject param) {
        this.param = param;
    }

    @ContextBeanMethodDesc("获取节点配置")
    public JSONObject getNodeConf() {
        return nodeConf;
    }

    @ContextBeanMethodDesc("设置节点配置")
    public void setNodeConf(JSONObject nodeConf) {
        this.nodeConf = nodeConf;
    }

    @ContextBeanMethodDesc("获得节点参数配置")
    public Map<String, JSONObject> getNodeParamCache() {
        return nodeParamCache;
    }

    @ContextBeanMethodDesc("设置节点参数配置")
    public void setNodeParamCache(Map<String, JSONObject> nodeParamCache) {
        this.nodeParamCache = nodeParamCache;
    }
}
