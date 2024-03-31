package plus.easydo.bot.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.enums.onebot.OneBotPostMessageTypeEnum;
import plus.easydo.bot.enums.onebot.OneBotPostTypeEnum;
import plus.easydo.bot.manager.BotPostLogServiceManager;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;
import plus.easydo.bot.websocket.OneBotService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    private final OneBotService oneBotService;

    @PostMapping("/v11/post")
    public void v11Post(@RequestHeader("x-self-id") String selfId, @RequestHeader("x-signature") String signature, HttpServletRequest request) throws IOException {
        BotInfo bot = OneBotUtils.getBotInfo(selfId);
        if (Objects.nonNull(bot)) {
            // 获取内容长度
            byte[] bodyByte = getBodyByte(request);
            if (verifySignature(bot.getBotSecret(), bodyByte, signature)) {
                JSONObject messageJson = JSONUtil.parseObj(new String(bodyByte));
                CompletableFuture.runAsync(() -> botPostLogServiceManager.saveLog(messageJson));
                CompletableFuture.runAsync(() -> oneBotService.handlerPost(messageJson));
            }
        }
    }


    /**
     * 验签
     *
     * @param secret    secret
     * @param body      body
     * @param signature signature
     * @return boolean
     * @author laoyu
     * @date 2024-02-22
     */
    private boolean verifySignature(String secret, byte[] body, String signature) {
        HMac hMac = SecureUtil.hmacSha1(secret);
        return CharSequenceUtil.equals(OneBotConstants.SHA1_PRE + hMac.digestHex(body), signature);
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
        IoUtil.copy(in, opt);
        IoUtil.close(in);
        byte[] bodyByte = opt.toByteArray();
        IoUtil.close(opt);
        return bodyByte;
    }


    @RequestMapping("/wcfPost")
    public R<Boolean> wcfPost(@RequestBody JSONObject postData, @RequestParam("token") String token) {
        BotInfo botInfo = OneBotUtils.getBotInfoBySecret(token);
        if (Objects.nonNull(botInfo)) {
            JSONObject messageJson = wcfAdApter(postData, botInfo);
            CompletableFuture.runAsync(() -> botPostLogServiceManager.saveLog(messageJson));
            CompletableFuture.runAsync(() -> oneBotService.handlerPost(messageJson));
            return DataResult.ok();
        }
        return DataResult.fail("鉴权失败");
    }


    private JSONObject wcfAdApter(JSONObject sourceMessage, BotInfo botInfo) {
        JSONObject messageJson = JSONUtil.createObj();
        messageJson.set(OneBotConstants.POST_TYPE, OneBotPostTypeEnum.MESSAGE.getType());
        messageJson.set(OneBotConstants.SELF_ID, botInfo.getBotNumber());
        messageJson.set(OneBotConstants.MESSAGE_ID, sourceMessage.get("id"));
        messageJson.set(OneBotConstants.SENDER, sourceMessage.get("sender"));

        String content = sourceMessage.getStr("content");
        messageJson.set(OneBotConstants.MESSAGE, OneBotUtils.buildMessageJson(content));
        messageJson.set(OneBotConstants.RAW_MESSAGE, content);

        messageJson.set(OneBotConstants.MESSAGE_TYPE, sourceMessage.get("type"));
        messageJson.set(OneBotConstants.GROUP_ID, sourceMessage.get("roomid"));
        messageJson.set(OneBotConstants.TIME, sourceMessage.get("ts"));
        boolean isGroup = sourceMessage.getBool("isGroup");
        messageJson.set(OneBotConstants.MESSAGE_TYPE, isGroup ? OneBotPostMessageTypeEnum.GROUP.getType() : OneBotPostMessageTypeEnum.PRIVATE.getType());
        messageJson.set("is_group", isGroup);
        messageJson.set("extra", sourceMessage.get("extra"));
        messageJson.set("xml", sourceMessage.get("xml"));
        return messageJson;
    }

}
