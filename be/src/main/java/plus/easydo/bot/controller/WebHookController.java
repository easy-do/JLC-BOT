package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.lang.Assert;
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
import org.springframework.web.bind.annotation.RestController;
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
    @GetMapping("/call/{secret}/{action}")
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
    public R<CmpStepResult> debugSimpleCmdDevelop(@RequestBody DebugDto debugDto) {
        return DataResult.ok(webhooksConfService.debug(debugDto));
    }

    /**
     * 添加
     *
     * @param webhooksConf
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public R<Boolean> saveWebhooksConf(@RequestBody WebhooksConf webhooksConf) {
        return DataResult.ok(webhooksConfService.saveWebhooksConf(webhooksConf));
    }


    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @GetMapping("/remove/{id}")
    public R<Boolean> removeWebhooksConf(@PathVariable Long id) {
        return DataResult.ok(webhooksConfService.removeWebhooksConf(id));
    }


    /**
     * 根据主键更新
     *
     * @param webhooksConf
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PostMapping("/update")
    public R<Boolean> updateWebhooksConf(@RequestBody WebhooksConf webhooksConf) {
        return DataResult.ok(webhooksConfService.updateWebhooksConf(webhooksConf));
    }


    /**
     * 查询所有
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public R<List<WebhooksConf>> listWebhooksConf() {
        return DataResult.ok(webhooksConfService.listWebhooksConf());
    }


    /**
     * 根据主键获取详细信息。
     *
     * @param id webhooksConf主键
     * @return 详情
     */
    @GetMapping("/getInfo/{id}")
    public R<WebhooksConf> getWebhooksConfInfo(@PathVariable Long id) {
        return DataResult.ok(webhooksConfService.getWebhooksConfInfo(id));
    }


    /**
     * 分页查询
     *
     * @param webhooksConfQo 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<List<WebhooksConf>> pageWebhooksConf(WebhooksConfQo webhooksConfQo) {
        return DataResult.ok(webhooksConfService.pageWebhooksConf(webhooksConfQo));
    }

}
