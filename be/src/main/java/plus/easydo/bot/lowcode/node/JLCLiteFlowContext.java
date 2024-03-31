package plus.easydo.bot.lowcode.node;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2024-03-27
 * @Description 低代码上下文
 */
@Data
public class JLCLiteFlowContext {

    private JSONObject param;

    private JSONObject nodeConf;

    private Map<String, JSONObject> nodeParamCache = new HashMap<>();

}
