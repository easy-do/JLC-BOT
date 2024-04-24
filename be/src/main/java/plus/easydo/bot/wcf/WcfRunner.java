package plus.easydo.bot.wcf;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import plus.easydo.bot.config.SystemConfig;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.enums.onebot.OneBotPostMessageTypeEnum;
import plus.easydo.bot.enums.onebot.OneBotPostTypeEnum;
import plus.easydo.bot.manager.BotPostLogManager;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.util.OneBotWcfClientUtils;
import plus.easydo.bot.websocket.OneBotService;

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

    private final BotPostLogManager botPostLogManager;

    private final OneBotService oneBotService;

    private final SystemConfig systemConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //如果配置文件指定启动并且系统是windows则启动wcf
        if(systemConfig.getWcfEnable()){
            if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
                try {
                    // 本地启动 RPC
                    Client client = new Client(false); // 默认 10086 端口
                    OneBotWcfClientUtils.saveClient(client);
                    // 接收消息，并调用 printWxMsg 处理
                    client.enableRecvMsg(100);
                    log.info("wcf启动完成");
                    Thread thread = new Thread(() -> {
                        log.info("开始监听wcf消息");
                        while (client.getIsReceivingMsg()) {
                            Wcf.WxMsg msg = client.getMsg();
                            try {
                                JSONObject messageJson = wcfAdApter(msg, client);
                                CompletableFuture.runAsync(() -> botPostLogManager.saveLog(messageJson));
                                CompletableFuture.runAsync(() -> oneBotService.handlerPost(messageJson));
                            } catch (Exception e) {
                                log.error("wcf消息处理异常,{}", ExceptionUtil.getMessage(e));
                            }
                        }
                    });
                    thread.start();
                    client.keepRunning();
                } catch (Exception e) {
                    log.error("初始化wcf失败:{}", ExceptionUtil.getMessage(e));
                }
            } else {
                log.warn("非windows运行,不启动wcf");
            }
        }else {
            log.warn("配置文件已设置关闭wcf,不启动wcf");
        }

    }

    private JSONObject wcfAdApter(Wcf.WxMsg msg, Client client) {
        JSONObject messageJson = JSONUtil.createObj();
        messageJson.set(OneBotConstants.MESSAGE_ID, msg.getId());
        messageJson.set(OneBotConstants.SENDER, msg.getSender());

        String content = msg.getContent();
        messageJson.set(OneBotConstants.MESSAGE, content);
        messageJson.set(OneBotConstants.RAW_MESSAGE, content);

        messageJson.set(OneBotConstants.MESSAGE_TYPE, msg.getType());
        messageJson.set(OneBotConstants.GROUP_ID, msg.getRoomid());
        messageJson.set(OneBotConstants.TIME, msg.getTs());
        boolean isGroup = msg.getIsGroup();
        messageJson.set(OneBotConstants.POST_TYPE, OneBotPostTypeEnum.MESSAGE.getType());
        messageJson.set(OneBotConstants.MESSAGE_TYPE, isGroup ? OneBotPostMessageTypeEnum.GROUP.getType() : OneBotPostMessageTypeEnum.PRIVATE.getType());
        messageJson.set("is_group", isGroup);
        messageJson.set("extra", msg.getExtra());
        messageJson.set("xml", msg.getXml());
        BotInfo botInfo = OneBotUtils.getBotInfo(client.getSelfWxid());
        messageJson.set(OneBotConstants.SELF_ID, botInfo.getBotNumber());
        return messageJson;
    }

}
