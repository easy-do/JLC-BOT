package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.dto.SetBotConfIdDto;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.SimpleDevelopService;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@Tag(name = "简单指令开发")
@RequiredArgsConstructor
@RequestMapping("/api/simpleCmdDevelop")
public class SimpleDevelopConfController {


    private final SimpleDevelopService simpleDevelopService;

    /**
     * 所有列表
     *
     * @return plus.easydo.bot.vo.R<java.util.List<plus.easydo.bot.entity.SimpleCmdDevelopConf>>
     * @author laoyu
     * @date 2024-04-10
     */
    @SaCheckLogin
    @Operation(summary = "所有列表")
    @GetMapping("/list")
    public R<List<SimpleCmdDevelopConf>> listSimpleDevelop() {
        return DataResult.ok(simpleDevelopService.listSimpleDevelop());
    }


    /**
     * 分页查询
     *
     * @param pageQo pageQo
     * @return plus.easydo.bot.vo.R<java.util.List<plus.easydo.bot.entity.SimpleCmdDevelopConf>>
     * @author laoyu
     * @date 2024-04-10
     */
    @SaCheckLogin
    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public R<List<SimpleCmdDevelopConf>> pageSimpleDevelop(PageQo pageQo) {
        return DataResult.ok(simpleDevelopService.pageSimpleDevelop(pageQo));
    }

    /**
     * 详细信息
     * @param id
     * @return
     */
    @GetMapping("/getInfo/{id}")
    public R<SimpleCmdDevelopConf> getSimpleDevelopInfo(@PathVariable Long id) {
        return DataResult.ok(simpleDevelopService.getSimpleDevelopInfo(id));
    }

    /**
     * 添加
     *
     * @param simpleCmdDevelopConf simpleCmdDevelopConf
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-04-10
     */
    @SaCheckLogin
    @Operation(summary = "添加")
    @PostMapping("/save")
    public R<Boolean> saveSimpleDevelop(@RequestBody SimpleCmdDevelopConf simpleCmdDevelopConf) {
        return DataResult.ok(simpleDevelopService.saveSimpleDevelop(simpleCmdDevelopConf));
    }

    /**
     * 更新
     *
     * @param simpleCmdDevelopConf simpleCmdDevelopConf
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-04-10
     */
    @SaCheckLogin
    @Operation(summary = "更新")
    @PutMapping("/update")
    public R<Boolean> updateSimpleDevelop(@RequestBody SimpleCmdDevelopConf simpleCmdDevelopConf) {
        return DataResult.ok(simpleDevelopService.updateSimpleDevelop(simpleCmdDevelopConf));
    }


    /**
     * 删除
     *
     * @param id id
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-04-10
     */
    @SaCheckLogin
    @Operation(summary = "删除")
    @DeleteMapping("/remove/{id}")
    public R<Boolean> removeSimpleDevelop(@PathVariable Long id) {
        return DataResult.ok(simpleDevelopService.removeSimpleDevelop(id));
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
    public R<Long> importSimpleCmdDevelop(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream ip = file.getInputStream();
        byte[] bytes = ip.readAllBytes();
        String content = new String(bytes);
        try {
            SimpleCmdDevelopConf conf = JSONUtil.toBean(content, SimpleCmdDevelopConf.class);
            return DataResult.ok(simpleDevelopService.importConf(conf));
        } catch (Exception e) {
            return DataResult.fail("解析配置内容失败:"+ ExceptionUtil.getMessage(e));
        }
    }

    @SaCheckLogin
    @Operation(summary = "调试配置")
    @PostMapping("/debug")
    public R<CmpStepResult> debugSimpleCmdDevelop(@RequestBody DebugDto debugDto) {
        return DataResult.ok(simpleDevelopService.debug(debugDto));
    }

    /**
     * 获取机器人简单指令开发配置
     *
     * @param botId botId
     * @return plus.easydo.bot.vo.R<java.util.List<java.lang.Long>>
     * @author laoyu
     * @date 2024/4/12
     */
    @SaCheckLogin
    @Operation(summary = "获取机器人的节点配置")
    @GetMapping("/getBotSimpleCmdDevelop/{id}")
    public R<List<Long>> getBotSimpleCmdDevelop(@PathVariable("id") Long botId) {
        return DataResult.ok(simpleDevelopService.getSimpleCmdDevelop(botId));
    }

    /**
     * 设置机器人简单指令开发配置
     *
     * @param setBotConfIdDto setBotConfIdDto
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/4/12
     */
    @SaCheckLogin
    @Operation(summary = "设置机器人与节点配置关联关系")
    @PostMapping("/setBotSimpleCmdDevelop")
    public R<Boolean> setBotSimpleCmdDevelop(@RequestBody SetBotConfIdDto setBotConfIdDto) {
        return DataResult.ok(simpleDevelopService.setBotSimpleCmdDevelop(setBotConfIdDto));
    }

}
