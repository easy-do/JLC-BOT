package plus.easydo.bot.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.hook.OneBotWebHook;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description webhook接口
 * @date 2024/4/4
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "webhook")
@RequestMapping("/api/wh")
public class WebHookController {

    @Autowired
    private Map<String, OneBotWebHook> hookMap;

    /**
     * webhook get请求
     *
     * @param action action
     * @param secret secret
     * @param request request
     * @return plus.easydo.bot.vo.R<java.lang.Object>
     * @author laoyu
     * @date 2024/4/5
     */
    @GetMapping("/get/{action}/{secret}")
    @Operation(summary = "webhook get请求")
    public R<Object> hookGet(@PathVariable("action") String action, @PathVariable("secret") String secret, HttpServletRequest request) {
        BotInfo botInfo = OneBotUtils.getBotInfoBySecret(secret);
        if (Objects.nonNull(botInfo)) {
            JSONObject paramJson = JSONUtil.createObj();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                paramJson.set(parameterName, request.getParameter(parameterName));
            }
            return DataResult.ok(hook(botInfo.getBotNumber(), action, paramJson));
        }
        return DataResult.fail("鉴权失败");
    }

    /**
     * webhook post请求
     *
     * @param action action
     * @param secret secret
     * @param paramJson paramJson
     * @return plus.easydo.bot.vo.R<java.lang.Object>
     * @author laoyu
     * @date 2024/4/5
     */
    @GetMapping("/post/{action}/{secret}")
    @Operation(summary = "webhook post请求")
    public R<Object> hookPost(@PathVariable("action") String action, @PathVariable("secret") String secret, @RequestBody JSONObject paramJson) {
        BotInfo botInfo = OneBotUtils.getBotInfoBySecret(secret);
        if (Objects.nonNull(botInfo)) {
            return DataResult.ok(hook(botInfo.getBotNumber(), action, paramJson));
        }
        return DataResult.fail("鉴权失败");
    }

    private Object hook(String botNumber, String action, JSONObject paramJson) {
        OneBotWebHook oneBotWebHook = hookMap.get(action);
        if (Objects.nonNull(oneBotWebHook)) {
            return oneBotWebHook.hook(botNumber,paramJson);
        }
        throw new BaseException("[" + action + "]不支持");
    }

}
