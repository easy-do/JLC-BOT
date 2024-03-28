package plus.easydo.bot.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.BotPostLog;
import plus.easydo.bot.manager.BotPostLogServiceManager;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBotHttp上报接收
 * @date 2024/2/25
 */
@Slf4j
@RestController
@RequestMapping("/api/oneBot")
@RequiredArgsConstructor
public class OneBotController {


    private final BotPostLogServiceManager botPostLogServiceManager;

//    @PostMapping("/v11/post")
    public void post(HttpServletRequest request) throws IOException {

        Enumeration<String> names = request.getHeaderNames();
        for (Iterator<String> it = names.asIterator(); it.hasNext(); ) {
            String name = it.next();
            log.info("hearder=>{},{}",name,request.getHeader(name));
        }
        log.info("body:{}",new String(getBodyByte(request)));
    }


    /**
     * 获取请问内容的字节数组
     *
     * @param request request
     * @return byte[]
     * @author laoyu
     * @date 2024-02-22
     */
    private static byte[] getBodyByte(HttpServletRequest request) throws IOException {
        ServletInputStream in = request.getInputStream();
        int length = request.getContentLength();
        ByteArrayOutputStream opt = new ByteArrayOutputStream(length);
        IoUtil.copy(in,opt);
        IoUtil.close(in);
        byte[] bodyByte = opt.toByteArray();
        IoUtil.close(opt);
        return bodyByte;
    }


    @RequestMapping("/wcfPost")
    public R<Boolean> post(@RequestBody JSONObject messageJson, @RequestParam("token")String token) throws IOException {
        BotInfo botInfo = CacheManager.SECRET_BOT_CACHE.get(token);
        if(Objects.nonNull(botInfo)){
            wcfAdApter(messageJson,botInfo);
            CompletableFuture.runAsync(()->botPostLogServiceManager.save(BotPostLog.builder().postTime(LocalDateTimeUtil.now()).platform("wx").message(messageJson.toJSONString(0)).build()));
            return DataResult.ok();
        }
        return DataResult.fail("鉴权失败");
    }

    private void wcfAdApter(JSONObject messageJson, BotInfo botInfo) {
        String messageId = messageJson.getStr("id");
        messageJson.set(OneBotConstants.MESSAGE_ID,messageId);
        messageJson.set(OneBotConstants.SELF_ID,botInfo.getBotNumber());
        Boolean isGroup = messageJson.getBool("is_group");
        if(isGroup){
            String groupId = messageJson.getStr("roomid");
            messageJson.set(OneBotConstants.GROUP_ID,groupId);
        }

    }

}
