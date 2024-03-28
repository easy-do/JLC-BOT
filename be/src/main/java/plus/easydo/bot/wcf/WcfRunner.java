package plus.easydo.bot.wcf;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.iamteer.Wcf;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.BotPostLog;
import plus.easydo.bot.manager.BotPostLogServiceManager;
import plus.easydo.bot.manager.CacheManager;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author yuzhanfeng
 * @Date 2024-03-28
 * @Description 启动后运行wcf
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WcfRunner implements ApplicationRunner {

    private final BotPostLogServiceManager botPostLogServiceManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String os = System.getProperty("os.name");
        //如果是windows则启动
        if (os.toLowerCase().startsWith("win")) {
            // 本地启动 RPC
            Client client = new Client(true); // 默认 10086 端口
            CacheManager.CLIENT_CACHE.add(client);
            // 接收消息，并调用 printWxMsg 处理
            client.enableRecvMsg(100);
            log.info("wcf启动完成");
            Thread thread = new Thread(() -> {
                log.info("开始监听wcf消息");
                while (client.getIsReceivingMsg()) {
                    Wcf.WxMsg msg = client.getMsg();
                    try {
                        JSONObject messageJson = wcfAdApter(msg, client);
                        CompletableFuture.runAsync(()->botPostLogServiceManager.save(BotPostLog.builder().postTime(LocalDateTimeUtil.now()).platform("wx").message(messageJson.toJSONString(0)).build()));

                    }catch (Exception e){
                        log.error("wcf消息处理异常,{}", ExceptionUtil.getMessage(e));
                    }
                }
            });
            thread.start();
            client.keepRunning();
        }else {
            log.warn("非windows运行,不启动wcf");
        }
    }


    private JSONObject wcfAdApter(Wcf.WxMsg msg, Client client) {
        JSONObject messageJson = JSONUtil.createObj();
        messageJson.set(OneBotConstants.MESSAGE_ID,msg.getId());
        messageJson.set(OneBotConstants.MESSAGE,msg.getContent());
        messageJson.set(OneBotConstants.MESSAGE_TYPE,msg.getType());
        messageJson.set(OneBotConstants.GROUP_ID,msg.getRoomid());
        messageJson.set(OneBotConstants.TIME,msg.getTs());
        messageJson.set("is_group",msg.getIsGroup());
        messageJson.set("is_self",msg.getIsSelf());
        messageJson.set("extra",msg.getExtra());
        messageJson.set("xml",msg.getXml());
        BotInfo botInfo = CacheManager.BOT_CACHE.get(client.getSelfWxid());
        if(Objects.nonNull(botInfo)){
            messageJson.set(OneBotConstants.SELF_ID,botInfo.getBotNumber());
        }
        return messageJson;
    }

}
