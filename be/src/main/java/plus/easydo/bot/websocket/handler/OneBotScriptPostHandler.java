package plus.easydo.bot.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotEventScript;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.util.AviatorScriptUtil;
import plus.easydo.bot.util.MessageParseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报消息js脚本处理处理
 * @date 2024/2/25
 */
@Slf4j
@Component
public class OneBotScriptPostHandler {
    public void handler(String postType, JSONObject postData) {
        //通过事件找函数
        String botNumber = postData.getStr(OneBotConstants.SELF_ID);
        List<Long> scriptIds = CacheManager.EVENT_SCRIPT_ID_CACHE.get(postType);
        List<Long> botScriptIds = CacheManager.BOT_SCRIPT_ID_CACHE.get(botNumber);
        if (Objects.nonNull(scriptIds) && Objects.nonNull(botScriptIds)) {
            List<Long> execFunIds = new ArrayList<>();
            //取到对应上的脚本
            scriptIds.forEach(scriptId -> {
                if (botScriptIds.contains(scriptId)) {
                    execFunIds.add(scriptId);
                }
            });
            execFunIds.forEach(functionId -> {
                BotEventScript function = CacheManager.EVENT_SCRIPT_CACHE.get(functionId);
                if (Objects.nonNull(function) && CharSequenceUtil.isNotBlank(function.getScriptContent())) {
                    log.info("执行脚本===============");
                    try {
                        Expression expression = AviatorScriptUtil.compile(function.getScriptContent());

                        //消息解析
                        MessageParseUtil.parseMessage(postData);

                        expression.execute(AviatorEvaluator.newEnv("postData", postData, OneBotConstants.BOT_NUMBER, botNumber));
                        log.info("脚本执行结束===============");
                    } catch (Exception e) {
                        log.warn("执行脚本异常：{}", ExceptionUtil.getMessage(e));
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
