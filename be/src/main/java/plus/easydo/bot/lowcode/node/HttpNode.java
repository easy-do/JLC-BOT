package plus.easydo.bot.lowcode.node;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.util.ParamReplaceUtils;

import java.util.Objects;

/**
 * 字段判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id="httpNode",name="http节点")
public class HttpNode extends NodeComponent {

    @Override
    public void process() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("http请求节点: param:{},conf:{}", paramJson, confJson);
        if (Objects.nonNull(paramJson) && Objects.nonNull(confJson)) {
            String address = confJson.getStr("address");
            String resSaveFiled = confJson.getStr("resSaveFiled");
            address = ParamReplaceUtils.replaceParam(address, paramJson);
            try {
                String res = HttpRequest.get(address).timeout(3000).execute().body();
                paramJson.set(resSaveFiled, res);
            } catch (Exception e) {
                String message = ExceptionUtil.getMessage(e);
                log.warn("http请求节点未完整执行,原因:http请求报错,{}", message);
                throw new BaseException("http请求失败," + message);
            }
        } else {
            log.warn("http请求节点未完整执行,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            throw new BaseException("参数或节点配置为空");
        }
    }

    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(),context.getParam());
    }
}
