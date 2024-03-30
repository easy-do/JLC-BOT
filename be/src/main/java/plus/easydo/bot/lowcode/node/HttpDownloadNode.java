package plus.easydo.bot.lowcode.node;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.util.FileUtils;
import plus.easydo.bot.util.ParamReplaceUtils;

import java.util.List;
import java.util.Objects;

/**
 * 字段判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id = "httpDownloadNode", name = "http下载")
public class HttpDownloadNode extends NodeComponent {

    @Override
    public void process() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("http下载节点: param:{},conf:{}", paramJson, confJson);
        if (Objects.nonNull(paramJson) && Objects.nonNull(confJson)) {
            String address = confJson.getStr("address");
            Integer timeout = confJson.getInt("timeout");
            String resSaveFiled = confJson.getStr("resSaveFiled");
            String fileSuffix = confJson.getStr("fileSuffix");
            if (Objects.nonNull(address) && Objects.nonNull(resSaveFiled) && Objects.nonNull(timeout) && Objects.nonNull(fileSuffix)) {
                //对url参数占位进行替换
                address = ParamReplaceUtils.replaceParam(address, paramJson);
                try {
                    HttpResponse res = HttpRequest.get(address).timeout(timeout).execute();
                    byte[] bytes;
                    int status = res.getStatus();
                    if(HttpStatus.isRedirected(status)){
                        String location = res.header("Location");
                        HttpResponse newRes = HttpRequest.get(location).timeout(timeout).execute();
                        bytes = newRes.bodyBytes();
                    }else {
                        bytes = res.bodyBytes();
                    }
                    String fileName = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.PURE_DATETIME_MS_PATTERN) + "." + fileSuffix;
                    String path = FileUtils.saveFileToCachePath(bytes, fileName);
                    paramJson.set(resSaveFiled,path);
                } catch (Exception e) {
                    String message = ExceptionUtil.getMessage(e);
                    log.warn("http请求节点未完整执行,原因:http请求报错,{}", message);
                    throw new BaseException("http请求失败," + message);
                }
            } else {
                log.warn("http下载节点未完整执行,原因:节点配置不全,conf:{}", confJson);
                throw new BaseException("节点配置不全");
            }
        } else {
            log.warn("http下载节点未完整执行,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            throw new BaseException("参数或节点配置为空");
        }
    }

    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(), context.getParam());
    }
}
