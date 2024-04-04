package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.entity.BotEventScript;
import plus.easydo.bot.qo.BotEventScriptQo;
import plus.easydo.bot.service.BotScriptService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.util.List;

/**
 * 机器人脚本 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "机器人脚本")
@RequestMapping("/api/botScript")
public class BotScriptController {

    private final BotScriptService botScriptService;

    /**
     * 分页查询机器人脚本
     *
     * @param botEventScriptQo botEventScriptQo
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "分页查询")
    @PostMapping("/page")
    public R<List<BotEventScript>> pageBotScript(@RequestBody BotEventScriptQo botEventScriptQo) {
        return DataResult.ok(botScriptService.pageBotScript(botEventScriptQo));
    }

    /**
     * 所有脚本列表
     *
     * @return vo.plus.easydo.lowcode.bot.R<java.util.List < plus.easydo.bot.entity.BotEventScript>>
     * @author laoyu
     * @date 2024/2/24
     */
    @SaCheckLogin
    @Operation(summary = "所有脚本列表")
    @GetMapping("/botScriptList")
    public R<List<BotEventScript>> botScriptList() {
        return DataResult.ok(botScriptService.botScriptList());
    }

    /**
     * 机器人脚本详情
     *
     * @param id id
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/2/21
     */
    @SaCheckLogin
    @Operation(summary = "机器人脚本详情")
    @PostMapping("/info/{id}")
    public R<BotEventScript> infoBotScript(@PathVariable("id") Long id) {
        return DataResult.ok(botScriptService.infoBotScript(id));
    }

    /**
     * 添加机器人脚本配置
     *
     * @param botEventScript daCdk
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "添加机器人脚本配置")
    @PostMapping("/add")
    public R<Boolean> addBotScript(@RequestBody BotEventScript botEventScript) {
        return DataResult.ok(botScriptService.addBotScript(botEventScript));
    }

    /**
     * 添加机器人脚本配置
     *
     * @param botEventScript daCdk
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "更新机器人脚本配置")
    @PostMapping("/update")
    public R<Boolean> updateBotScript(@RequestBody BotEventScript botEventScript) {
        return DataResult.ok(botScriptService.updateBotScript(botEventScript));
    }


    /**
     * 根据主键删除机器人脚本
     *
     * @param ids 主键集合
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @SaCheckLogin
    @Operation(summary = "删除机器人脚本")
    @PostMapping("/remove")
    public R<Boolean> removeBotScript(@RequestBody List<String> ids) {
        return DataResult.ok(botScriptService.removeBotScript(ids));
    }

}
