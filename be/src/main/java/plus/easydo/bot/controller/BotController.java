package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.dto.EnableBotScriptDto;
import plus.easydo.bot.entity.BotConf;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.BotMessage;
import plus.easydo.bot.entity.BotNotice;
import plus.easydo.bot.entity.BotRequest;
import plus.easydo.bot.qo.BotMessageQo;
import plus.easydo.bot.qo.BotNoticeQo;
import plus.easydo.bot.qo.BotQo;
import plus.easydo.bot.qo.BotRequestQo;
import plus.easydo.bot.service.BotService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.util.List;

/**
 * cdk配置 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bot")
public class BotController {

    private final BotService botService;

    /**
     * 分页查询机器人
     *
     * @param botQo 分页对象
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "分页查询")
    @PostMapping("/page")
    public R<List<BotInfo>> pageBot(@RequestBody BotQo botQo) {
        return DataResult.ok(botService.pageBot(botQo));
    }

    /**
     * 机器人详情
     *
     * @param id id
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/2/21
     */
    @SaCheckLogin
    @Operation(summary = "机器人详情")
    @GetMapping("/info/{id}")
    public R<BotInfo> infoBot(@PathVariable("id") Long id) {
        return DataResult.ok(botService.infoBot(id));
    }

    /**
     * 添加机器人配置
     *
     * @param botInfo daCdk
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "添加机器人配置")
    @PostMapping("/add")
    public R<Boolean> addBot(@RequestBody BotInfo botInfo) {
        return DataResult.ok(botService.addBot(botInfo));
    }

    /**
     * 添加机器人配置
     *
     * @param botInfo daCdk
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "更新机器人配置")
    @PostMapping("/update")
    public R<Boolean> updateBot(@RequestBody BotInfo botInfo) {
        return DataResult.ok(botService.updateBot(botInfo));
    }


    /**
     * 根据主键删除机器人
     *
     * @param ids 主键集合
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @SaCheckLogin
    @Operation(summary = "删除机器人")
    @PostMapping("/remove")
    public R<Boolean> removeBot(@RequestBody List<String> ids) {
        return DataResult.ok(botService.removeBot(ids));
    }

    /**
     * 获取机器人配置
     *
     * @param botNumber botNumber
     * @return vo.plus.easydo.lowcode.bot.R<plus.easydo.bot.entity.BotInfo>
     * @author laoyu
     * @date 2024-02-23
     */
    @SaCheckLogin
    @Operation(summary = "获取机器人配置")
    @GetMapping("/getBotConf/{botNumber}")
    public R<List<BotConf>> getBotConf(@PathVariable("botNumber") String botNumber) {
        return DataResult.ok(botService.getBotConf(botNumber));
    }

    @SaCheckLogin
    @Operation(summary = "添加机器人配置")
    @PostMapping("/addBotConf")
    public R<Boolean> addBotConf(@RequestBody BotConf botConf) {
        return DataResult.ok(botService.addBotConf(botConf));
    }

    @SaCheckLogin
    @Operation(summary = "更新机器人配置")
    @PostMapping("/updateBotConf")
    public R<Boolean> updateBotConf(@RequestBody BotConf botConf) {
        return DataResult.ok(botService.updateBotConf(botConf));
    }

    @SaCheckLogin
    @Operation(summary = "删除机器人配置")
    @GetMapping("/removeBotConf/{id}")
    public R<Boolean> removeBotConf(@PathVariable("id") Long id) {
        return DataResult.ok(botService.removeBotConf(id));
    }

    /**
     * 分页查询消息记录
     *
     * @param botMessageQo 分页对象
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "分页查询消息记录")
    @PostMapping("/pageBotMessage")
    public R<List<BotMessage>> pageBotMessage(@RequestBody BotMessageQo botMessageQo) {
        return DataResult.ok(botService.pageBotMessage(botMessageQo));
    }

    /**
     * 清空消息记录
     *
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-04-01
     */
    @SaCheckLogin
    @Operation(summary = "清空消息记录")
    @GetMapping("/cleanBotMessage")
    public R<Boolean> cleanBotMessage() {
        return DataResult.ok(botService.cleanBotMessage());
    }


    /**
     * 分页查询请求记录
     *
     * @param botRequestQo 分页对象
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "分页查询请求记录")
    @PostMapping("/pageBotRequest")
    public R<List<BotRequest>> pageBotRequest(@RequestBody BotRequestQo botRequestQo) {
        return DataResult.ok(botService.pageBotRequest(botRequestQo));
    }

    /**
     * 清空请求记录
     *
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-04-01
     */
    @SaCheckLogin
    @Operation(summary = "清空请求记录")
    @GetMapping("/cleanBotRequest")
    public R<Boolean> cleanBotRequest() {
        return DataResult.ok(botService.cleanBotRequest());
    }

    /**
     * 分页查询通知记录
     *
     * @param botNoticeQo 分页对象
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "分页查询通知记录")
    @PostMapping("/pageBotNotice")
    public R<List<BotNotice>> pageBotNotice(@RequestBody BotNoticeQo botNoticeQo) {
        return DataResult.ok(botService.pageBotNotice(botNoticeQo));
    }

    /**
     * 清空请求记录
     *
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-04-01
     */
    @SaCheckLogin
    @Operation(summary = "清空通知记录")
    @GetMapping("/cleanBotNotice")
    public R<Boolean> cleanBotNotice() {
        return DataResult.ok(botService.cleanBotNotice());
    }

    /**
     * 开启脚本
     *
     * @param enableBotScriptDto
     * @return
     */
    @SaCheckLogin
    @Operation(summary = "启用脚本")
    @PostMapping("/enableBotScript")
    public R<Boolean> enableBotScript(@RequestBody EnableBotScriptDto enableBotScriptDto) {
        return DataResult.ok(botService.enableBotScript(enableBotScriptDto));
    }

    /**
     * 已开启脚本
     *
     * @param id id
     * @return vo.plus.easydo.lowcode.bot.R<java.util.List < java.lang.Long>>
     * @author laoyu
     * @date 2024/2/24
     */
    @SaCheckLogin
    @Operation(summary = "已开启脚本")
    @PostMapping("/getEnableBotScript/{id}")
    public R<List<Long>> getEnableBotScript(@PathVariable("id") Long id) {
        return DataResult.ok(botService.getEnableBotScript(id));
    }

}
