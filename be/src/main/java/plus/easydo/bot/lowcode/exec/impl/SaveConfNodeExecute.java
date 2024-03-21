package plus.easydo.bot.lowcode.exec.impl;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.lowcode.exec.NodeExecute;
import plus.easydo.bot.lowcode.model.ExecuteResult;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.util.BotConfUtil;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-08
 * @Description 保存机器人配置
 */
@Slf4j
@Service("save_conf_node")
public class SaveConfNodeExecute implements NodeExecute {

    @Override
    public ExecuteResult execute(JSONObject paramJson, JSONObject confJson) {
        log.info("保存机器人配置节点: param:{},conf:{}",paramJson,confJson);
        if(Objects.nonNull(paramJson) && Objects.nonNull(confJson)){
            String botNumber = paramJson.getStr(OneBotConstants.SELF_ID);
            String confKey = confJson.getStr("confkKey");
            String confValue = confJson.getStr("confValue");
            if(Objects.nonNull(confKey) && Objects.nonNull(confValue)){
                return ExecuteResult.ok(BotConfUtil.saveBotConf(botNumber,confKey,confValue));
            }else {
                log.warn("保存机器人配置节点未完整执行,原因:配置信息不全,conf:{}",confJson);
                return ExecuteResult.fail("配置信息不全");
            }
        }else {
            log.warn("保存机器人配置节点未完整执行,原因:参数或节点配置为空,param:{},conf:{}",paramJson,confJson);
            return ExecuteResult.fail("参数或节点配置为空");
        }
    }
}
