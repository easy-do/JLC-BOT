package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.WebhooksConf;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.qo.WebhooksConfQo;
import plus.easydo.bot.service.WebhooksConfService;
import plus.easydo.bot.util.OneBotUtils;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description webhook接口
 * @date 2024/4/4
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "webhook")
@RequestMapping("/api/webhooks")
public class WebHookController {


    private final WebhooksConfService webhooksConfService;



    /**
     * webhook post请求
     *
     * @param action action
     * @param secret secret
     * @param paramsJson paramJson
     * @return plus.easydo.bot.vo.R<java.lang.Object>
     * @author laoyu
     * @date 2024/4/5
     */
    @PostMapping("/call/{secret}/{action}")
    @Operation(summary = "webhook post请求")
    public R<Object> hookPost(HttpServletRequest request, @PathVariable("action") Long action, @PathVariable("secret") String secret, @RequestBody JSONObject paramsJson) {
        BotInfo botInfo = OneBotUtils.getBotInfoBySecret(secret);
        Assert.notNull(botInfo, "无效秘钥");
        WebhooksConf webhooksConf = CacheManager.WEBHOOKS_CONF_CACHE.get(action);
        Assert.notNull(webhooksConf, "无效webhooks配置");
        paramsJson.set("botNumber", botInfo.getBotNumber());
        //将请求头透传到脚本参数
        JSONObject headerJson = JSONUtil.createObj();
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getHeader(name);
            headerJson.set(name, value);
        }
        paramsJson.set(LowCodeConstants.WEBHOOKS_REQUEST_HEADER, headerJson);
        webhooksConfService.execute(webhooksConf, paramsJson);
        return DataResult.fail("调用失败");
    }

    @SaCheckLogin
    @Operation(summary = "调试配置")
    @PostMapping("/debug")
    public R<CmpStepResult> debugWebhooks(@RequestBody DebugDto debugDto) {
        return DataResult.ok(webhooksConfService.debug(debugDto));
    }

    /**
     * 添加
     *
     * @param webhooksConf
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @SaCheckLogin
    @PostMapping("/save")
    @Operation(summary = "添加")
    public R<Boolean> saveWebhooksConf(@RequestBody WebhooksConf webhooksConf) {
        return DataResult.ok(webhooksConfService.saveWebhooksConf(webhooksConf));
    }


    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @SaCheckLogin
    @GetMapping("/remove/{id}")
    @Operation(summary = "根据主键删除")
    public R<Boolean> removeWebhooksConf(@PathVariable Long id) {
        return DataResult.ok(webhooksConfService.removeWebhooksConf(id));
    }


    /**
     * 根据主键更新
     *
     * @param webhooksConf
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @SaCheckLogin
    @PostMapping("/update")
    @Operation(summary = "根据主键更新")
    public R<Boolean> updateWebhooksConf(@RequestBody WebhooksConf webhooksConf) {
        return DataResult.ok(webhooksConfService.updateWebhooksConf(webhooksConf));
    }


    /**
     * 查询所有
     *
     * @return 所有数据
     */
    @SaCheckLogin
    @GetMapping("/list")
    @Operation(summary = "查询所有")
    public R<List<WebhooksConf>> listWebhooksConf() {
        return DataResult.ok(webhooksConfService.listWebhooksConf());
    }


    /**
     * 根据主键获取详细信息。
     *
     * @param id webhooksConf主键
     * @return 详情
     */
    @SaCheckLogin
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "详情")
    public R<WebhooksConf> getWebhooksConfInfo(@PathVariable Long id) {
        return DataResult.ok(webhooksConfService.getWebhooksConfInfo(id));
    }


    /**
     * 分页查询
     *
     * @param webhooksConfQo 分页对象
     * @return 分页对象
     */
    @SaCheckLogin
    @PostMapping("/page")
    @Operation(summary = "分页查询")
    public R<List<WebhooksConf>> pageWebhooksConf(WebhooksConfQo webhooksConfQo) {
        return DataResult.ok(webhooksConfService.pageWebhooksConf(webhooksConfQo));
    }

    /**
     * 生成hookUrl
     *
     * @param botId  botId
     * @param confId confId
     * @return plus.easydo.bot.vo.R<java.lang.String>
     * @author laoyu
     * @date 2024/4/26
     */
    @SaCheckLogin
    @GetMapping("/generateHookUrl/{botId}/{confId}")
    @Operation(summary = "生成hookUrl")
    public R<String> generateHookUrl(@PathVariable Long botId, @PathVariable Long confId) {
        BotInfo botInfo = OneBotUtils.getBotInfoById(botId);
        WebhooksConf conf = CacheManager.WEBHOOKS_CONF_CACHE.get(confId);
        Assert.notNull(conf, "配置信息不存在");
        return DataResult.ok(CharSequenceUtil.format("/api/webhooks/call/{}/{}", botInfo.getBotSecret(), confId));
    }

    /**
     * 导入配置
     *
     * @param file file
     * @return plus.easydo.bot.vo.R<java.lang.Long>
     * @author laoyu
     * @date 2024-04-11
     */
    @SaCheckLogin
    @Operation(summary = "导入配置")
    @PostMapping("/importConf")
    public R<Long> importWebhookConf(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream ip = file.getInputStream();
        byte[] bytes = ip.readAllBytes();
        String content = new String(bytes);
        try {
            WebhooksConf conf = JSONUtil.toBean(content, WebhooksConf.class);
            return DataResult.ok(webhooksConfService.importConf(conf));
        } catch (Exception e) {
            return DataResult.fail("解析配置内容失败:"+ ExceptionUtil.getMessage(e));
        }
    }

}
