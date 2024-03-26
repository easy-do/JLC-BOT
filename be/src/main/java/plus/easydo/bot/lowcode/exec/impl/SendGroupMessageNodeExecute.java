package plus.easydo.bot.lowcode.exec.impl;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.lowcode.exec.NodeExecute;
import plus.easydo.bot.lowcode.model.ExecuteResult;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.util.ParamReplaceUtils;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-06
 * @Description 节点执行器
 */
@Slf4j
@Service("send_group_msg_node")
public class SendGroupMessageNodeExecute implements NodeExecute {

    @Override
    public ExecuteResult execute(JSONObject paramJson, JSONObject confJson) {
        log.debug("发送群消息节点: param:{},conf:{}",paramJson,confJson);
        if(Objects.nonNull(paramJson) && Objects.nonNull(confJson)){
            String message = confJson.getStr("message");
            if(Objects.nonNull(message)){
                String botNumber = paramJson.getStr(OneBotConstants.SELF_ID);
                String groupId = paramJson.getStr(OneBotConstants.GROUP_ID);
                message = ParamReplaceUtils.replaceParam(message,paramJson);
                OneBotUtils.sendGroupMessage(botNumber,groupId,message,true);
                return ExecuteResult.ok();
            }else {
                log.warn("发送群消息节点未完整执行,原因:没有找到要发送的消息配置");
                return ExecuteResult.fail("没有找到要发送的消息配置");
            }
        }else {
            log.warn("发送群消息节点未完整执行,原因:参数或节点配置为空,param:{},conf:{}",paramJson,confJson);
            return ExecuteResult.fail("参数或节点配置为空");
        }
    }
}
