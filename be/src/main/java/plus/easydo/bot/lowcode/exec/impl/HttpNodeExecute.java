package plus.easydo.bot.lowcode.exec.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.lowcode.exec.NodeExecute;
import plus.easydo.bot.lowcode.model.ExecuteResult;
import plus.easydo.bot.util.ParamReplaceUtils;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-08
 * @Description http请求节点
 */
@Slf4j
@Service("http_node")
public class HttpNodeExecute implements NodeExecute {

    @Override
    public ExecuteResult execute(JSONObject paramJson, JSONObject confJson) {
        log.info("http请求节点: param:{},conf:{}", paramJson, confJson);
        if (Objects.nonNull(paramJson) && Objects.nonNull(confJson)) {
            String address = confJson.getStr("address");
            String resSaveFiled = confJson.getStr("resSaveFiled");
            address = ParamReplaceUtils.replaceParam(address, paramJson);
            try {
                String res = HttpRequest.get(address).timeout(1000).execute().body();
                paramJson.set(resSaveFiled, res);
                return ExecuteResult.ok(res);
            } catch (Exception e) {
                String message = ExceptionUtil.getMessage(e);
                log.warn("http请求节点未完整执行,原因:http请求报错,{}", message);
                return ExecuteResult.fail("http请求报错," + message, false);
            }
        } else {
            log.warn("http请求节点未完整执行,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            return ExecuteResult.fail("参数或节点配置为空", false);
        }
    }
}
