package plus.easydo.bot.lowcode.exec.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.lowcode.exec.NodeExecute;
import plus.easydo.bot.lowcode.model.ExecuteResult;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.websocket.model.OneBotMessageParse;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-09
 * @Description 判断消息格式
 */
@Slf4j
@Service("if_else_message_node")
public class IfElseMessageNodeExecute implements NodeExecute {

    @Override
    public ExecuteResult execute(JSONObject paramJson, JSONObject confJson) {
        log.debug("判断消息节点: param:{},conf:{}", paramJson, confJson);
        if (Objects.nonNull(paramJson) && Objects.nonNull(confJson)) {
            String type = confJson.getStr("type");
            if (Objects.nonNull(type)) {
                JSONObject message = paramJson.getJSONObject(OneBotConstants.MESSAGE);
                if(Objects.nonNull(message)){
                    OneBotMessageParse messageParse = message.toBean(OneBotMessageParse.class);
                    boolean res = Objects.nonNull(messageParse) && CharSequenceUtil.equals(messageParse.getType(), type);
                    return ExecuteResult.ok(res);
                }else {
                    log.warn("判断消息节点未完整执行,返回false,原因:消息字段不存在");
                    return ExecuteResult.fail("判断类型未设置",false);
                }
            } else {
                log.warn("判断消息节点未完整执行,返回false,原因:判断类型未设置");
                return ExecuteResult.fail("判断类型未设置",false);
            }
        } else {
            log.warn("判断消息节点未完整执行,返回false,原因:参数或节点配置为空,param:{},conf:{}", paramJson, confJson);
            return ExecuteResult.fail("参数或节点配置为空",false);
        }
    }
}
