package plus.easydo.bot.lowcode.exec;

import cn.hutool.json.JSONObject;
import plus.easydo.bot.lowcode.model.ExecuteResult;

/**
 * @author yuzhanfeng
 * @Date 2024-03-06 11:34
 * @Description 节点执行器
 */
public interface NodeExecute {

    ExecuteResult execute(JSONObject paramJson, JSONObject confJson);
}
