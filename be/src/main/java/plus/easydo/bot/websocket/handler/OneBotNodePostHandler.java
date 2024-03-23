package plus.easydo.bot.websocket.handler;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.DaBotInfo;
import plus.easydo.bot.entity.DaLowCodeNodeConf;
import plus.easydo.bot.lowcode.exec.NodeExecuteServer;
import plus.easydo.bot.manager.CacheManager;

import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报消息使用节点处理
 * @date 2024/3/7
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OneBotNodePostHandler {

    private final NodeExecuteServer nodeExecuteServer;
    public void handler(String evenType, JSONObject postData){
        //通过机器人编码找到机器人id
        String botNumber = postData.getStr(OneBotConstants.SELF_ID);
        DaBotInfo botInfo = CacheManager.BOT_CACHE.get(botNumber);
        if(Objects.nonNull(botInfo)){
            List<Long> nodeIdList = CacheManager.BOT_NODE_CONF_CACHE.get(botInfo.getId());
            if(Objects.nonNull(nodeIdList)){
                log.debug("机器人节点处理器,为机器人[{}]找到{}个节点配置",botNumber,nodeIdList.size());
                //开始执行流程
                nodeIdList.forEach(nodeConfId->{
                    DaLowCodeNodeConf nodeConf = CacheManager.NODE_CONF_CACHE.get(nodeConfId);
                    if(Objects.nonNull(nodeConf) && (CharSequenceUtil.equals(evenType,nodeConf.getEventType()) || CharSequenceUtil.equals("all",nodeConf.getEventType()))){
                        nodeExecuteServer.execute(nodeConf,postData);
                    }
                });
            }
        }
    }
}
