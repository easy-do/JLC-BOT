package plus.easydo.bot.manager;


import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.yomahub.liteflow.script.ScriptExecutorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.LiteFlowConstants;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.entity.LowCodeSysNode;
import plus.easydo.bot.mapper.LiteFlowScriptMapper;

/**
 * liteflow脚本节点 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class LiteFlowScriptManager extends ServiceImpl<LiteFlowScriptMapper, LiteFlowScript> {

    @Value("${spring.application.name:jlc-bot}")
    private String applicationName;

    public void createData(LowCodeSysNode lowCodeSysNode) {
        LiteFlowScript entity = LiteFlowScript.builder()
                .id(lowCodeSysNode.getId())
                .applicationName(applicationName)
                .scriptId(lowCodeSysNode.getNodeCode())
                .scriptName(lowCodeSysNode.getNodeName())
                .scriptLanguage("java")
                .scriptData("")
                .enable(true)
                .build();
        buildScriptData(lowCodeSysNode, entity);
        boolean res = save(entity);
        if (res) {
            ScriptExecutorFactory.loadInstance().getScriptExecutor("java").load(entity.getScriptId(), entity.getScriptData());
        }
    }

    private void buildScriptData(LowCodeSysNode lowCodeSysNode, LiteFlowScript liteFlowScript) {
        boolean nodeType = CharSequenceUtil.containsAny(lowCodeSysNode.getNodeCode(), "if", "If");
        liteFlowScript.setScriptType(nodeType ? LiteFlowConstants.IF_SCRIPT : LiteFlowConstants.SCRIPT);
        liteFlowScript.setScriptData(nodeType ? LiteFlowConstants.IF_SCRIPT_DATA : LiteFlowConstants.SCRIPT_DATA);
    }

    public boolean updateScriptData(LiteFlowScript liteFlowScript) {
        boolean res = updateById(liteFlowScript);
        if (res) {
            ScriptExecutorFactory.loadInstance().getScriptExecutor("java").load(liteFlowScript.getScriptId(), liteFlowScript.getScriptData());
        }
        return res;
    }
}


