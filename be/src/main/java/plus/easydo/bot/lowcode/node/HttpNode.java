package plus.easydo.bot.lowcode.node;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.util.ParamReplaceUtils;


/**
 * 字段判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id = "httpNode", name = "http节点")
public class HttpNode extends NodeComponent {

    @Override
    public void process() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("http请求节点: param:{},conf:{}", paramJson, confJson);
        try {
            Assert.notNull(paramJson, "参数配置为空");
            Assert.notNull(confJson, "节点配置为空");
            String address = confJson.getStr("address");
            Integer timeout = confJson.getInt("timeout");
            String resSaveFiled = confJson.getStr("resSaveFiled");
            Assert.notNull(address, "请求地址未配置");
            Assert.notNull(timeout, "超时时间未配置");
            Assert.notNull(resSaveFiled, "暂存字段未配置");
            address = ParamReplaceUtils.replaceParam(address, paramJson);
            String res = HttpRequest.get(address).timeout(timeout).execute().body();
            paramJson.set(resSaveFiled, res);
        } catch (Exception e) {
            log.warn("http请求节点未完整执行,原因:{}", e.getMessage());
            throw e;
        }

    }

    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(), context.getParam());
    }
}
