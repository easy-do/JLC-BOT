package plus.easydo.bot.lowcode.node;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import plus.easydo.bot.util.ParamReplaceUtils;


/**
 * 字段判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id = "ollamaChatNode", name = "ollama模型调用")
public class OllamaChatNode extends NodeComponent {

    @Override
    public void process() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("ollama模型调用节点: param:{},conf:{}", paramJson, confJson);
        try {
            Assert.notNull(paramJson, "参数配置为空");
            Assert.notNull(confJson, "节点配置为空");
            String message = confJson.getStr("message");
            String model = confJson.getStr("model");
            String saveResFiled = confJson.getStr("saveResFiled");
            String baseUrl = confJson.getStr("baseUrl");
            Float temperature = confJson.getFloat("temperature");
            Assert.notBlank(message, "消息配置为空");
            Assert.notBlank(model, "模型配置为空");
            Assert.notBlank(saveResFiled, "暂存字段为空");
            Assert.notBlank(baseUrl, "调用地址为空");
            Assert.notNull(baseUrl, "temperature为空");
            message = ParamReplaceUtils.replaceParam(message, paramJson);
            var ollamaApi = new OllamaApi(baseUrl);
            var chatClient = new OllamaChatClient(ollamaApi).withDefaultOptions(OllamaOptions.create().withModel(model)
                    .withTemperature(temperature));
            String res = chatClient.call(message);
            paramJson.set(saveResFiled, res);
        } catch (Exception e) {
            log.warn("ollama模型调用节点未完整执行,原因:{}", e.getMessage());
            throw e;
        }
    }


    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(), context.getParam());
    }
}
