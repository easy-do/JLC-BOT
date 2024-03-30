package plus.easydo.bot.lowcode.node;

import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.util.OneBotApiUtils;
import plus.easydo.bot.util.ParamReplaceUtils;

import java.util.Objects;

/**
 * 字段判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id = "sendGroupFileNode", name = "发送群文件")
public class SendGroupFileNode extends NodeComponent {

    @Override
    public void process() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("发送群文件节点: param:{},conf:{}", paramJson, confJson);
        if (Objects.nonNull(paramJson) && Objects.nonNull(confJson)) {
            String path = confJson.getStr("path");
            if (Objects.nonNull(path)) {
                String botNumber = paramJson.getStr(OneBotConstants.SELF_ID);
                String groupId = paramJson.getStr(OneBotConstants.GROUP_ID);
                path = ParamReplaceUtils.replaceParam(path,paramJson);
                OneBotApiUtils.sendGroupFile(botNumber, path, groupId);
            } else {
                log.warn("发发送群文件未完整执行,原因:没有找到要发送的路径配置");
                throw new BaseException("没有找到要发送的文件路径配置");
            }
        } else {
            log.warn("发送群文件未完整执行,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            throw new BaseException("参数或节点配置为空");
        }
    }


    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(),context.getParam());
    }
}
