package plus.easydo.bot.lowcode.context;

import cn.hutool.json.JSONObject;
import lombok.Data;
import lombok.Setter;
import plus.easydo.bot.lowcode.model.CmpLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2024-03-27
 * @Description 低代码上下文
 */
@Data
@ContextBeanDesc("自定义上下文,可拿到全局参数")
public class JLCLiteFlowContext {

    private JSONObject param;

    private JSONObject nodeConf;

    private Map<String, JSONObject> nodeParamCache = new HashMap<>();

    private List<CmpLog> logCache = new ArrayList<>();

}
