package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import plus.easydo.bot.dto.BotNodeDto;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.dto.SetBotConfIdDto;
import plus.easydo.bot.entity.LowCodeNodeConf;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.qo.NodeConfQo;
import plus.easydo.bot.service.LowCodeService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 低代码相关接口
 * @date 2024/3/5
 */
@RestController
@Tag(name = "低代码")
@RequestMapping("/api/lowcode")
@RequiredArgsConstructor
public class LowCodeController {

    private final LowCodeService lowCodeService;


    /**
     * 分页查询节点配置
     *
     * @param nodeConfQo pageQo
     * @return vo.plus.easydo.lowcode.bot.R<java.util.List < model.bot.lowcode.plus.easydo.lowcode.bot.BotNodeDto>>
     * @author laoyu
     * @date 2024-03-06
     */
    @SaCheckLogin
    @Operation(summary = "分页查询节点配置")
    @PostMapping("/pageNodeConf")
    public R<List<LowCodeNodeConf>> pageNodeConf(@RequestBody NodeConfQo nodeConfQo) {
        return DataResult.ok(lowCodeService.pageNodeConf(nodeConfQo));
    }

    /**
     * 所有节点配置
     *
     * @return vo.plus.easydo.lowcode.bot.R<java.util.List < entity.bot.lowcode.plus.easydo.lowcode.bot.LowCodeNodeConf>>
     * @author laoyu
     * @date 2024-03-07
     */
    @SaCheckLogin
    @Operation(summary = "所有节点配置")
    @PostMapping("/listNodeConf")
    public R<List<LowCodeNodeConf>> listNodeConf() {
        return DataResult.ok(lowCodeService.listNodeConf());
    }


    /**
     * 获取配置
     *
     * @param id id
     * @return vo.plus.easydo.lowcode.bot.R<model.bot.lowcode.plus.easydo.lowcode.bot.BotNodeDto>
     * @author laoyu
     * @date 2024/3/5
     */
    @SaCheckLogin
    @Operation(summary = "获取节点配置")
    @GetMapping("/getNodeConf/{id}")
    public R<BotNodeDto> getNodeConf(@PathVariable("id") Long id) {
        return DataResult.ok(lowCodeService.getNodeConf(id));
    }

    /**
     * 复制节点配置
     *
     * @param id id
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-03-24
     */
    @SaCheckLogin
    @Operation(summary = "复制节点配置")
    @GetMapping("/copyNodeConf/{id}")
    public R<Long> copyNodeConf(@PathVariable("id") Long id) {
        return DataResult.ok(lowCodeService.copyNodeConf(id));
    }

    /**
     * 导入节点配置
     *
     * @param file file
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-03-24
     */
    @SaCheckLogin
    @Operation(summary = "导入节点配置")
    @PostMapping("/importNodeConf")
    public R<Long> importNodeConf(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream ip = file.getInputStream();
        byte[] bytes = ip.readAllBytes();
        String content = new String(bytes);
        try {
            BotNodeDto nodeDto = JSONUtil.toBean(content, BotNodeDto.class);
            nodeDto.setId(null);
            return DataResult.ok(lowCodeService.saveNodeConf(nodeDto));
        } catch (Exception e) {
            return DataResult.fail("解析配置信息内容失败");
        }
    }

    /**
     * 保存配置
     *
     * @param botNodeDto botNodeDto
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/3/5
     */
    @SaCheckLogin
    @Operation(summary = "保存节点配置")
    @PostMapping("/saveNodeConf")
    public R<Long> saveNodeConf(@RequestBody BotNodeDto botNodeDto) {
        return DataResult.ok(lowCodeService.saveNodeConf(botNodeDto));
    }

    /**
     * 更新配置
     *
     * @param botNodeDto botNodeDto
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/3/5
     */
    @SaCheckLogin
    @Operation(summary = "更新节点配置")
    @PostMapping("/updateNodeConf")
    public R<Boolean> updateNodeConf(@RequestBody BotNodeDto botNodeDto) {
        return DataResult.ok(lowCodeService.updateNodeConf(botNodeDto));
    }

    /**
     * 删除配置
     *
     * @param id id
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-03-06
     */
    @SaCheckLogin
    @Operation(summary = "删除节点配置")
    @GetMapping("/removeNodeConf/{id}")
    public R<Boolean> removeNodeConf(@PathVariable("id") Long id) {
        return DataResult.ok(lowCodeService.removeNodeConf(id));
    }

    /**
     * 调试节点配置
     *
     * @param debugDto debugDto
     * @return plus.easydo.bot.vo.R<java.util.List < plus.easydo.bot.lowcode.model.CmpStepResult>>
     * @author laoyu
     * @date 2024-03-27
     */
    @SaCheckLogin
    @Operation(summary = "调试节点配置")
    @PostMapping("/debugNodeConf")
    public R<List<CmpStepResult>> debugNodeConf(@RequestBody DebugDto debugDto) {
        return DataResult.ok(lowCodeService.debugNodeConf(debugDto));
    }

    /**
     * 获取机器人的节点配置
     *
     * @param botId botId
     * @return vo.plus.easydo.lowcode.bot.R<java.util.List < java.lang.Long>>
     * @author laoyu
     * @date 2024-03-07
     */
    @SaCheckLogin
    @Operation(summary = "获取机器人的节点配置")
    @GetMapping("/getBotNode/{id}")
    public R<List<Long>> getBotNode(@PathVariable("id") Long botId) {
        return DataResult.ok(lowCodeService.getBotNode(botId));
    }

    /**
     * 设置机器人与节点配置关联关系
     *
     * @param setBotConfIdDto setBotConfIdDto
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Object>
     * @author laoyu
     * @date 2024-03-07
     */
    @SaCheckLogin
    @Operation(summary = "设置机器人与节点配置关联关系")
    @PostMapping("/setBotNode")
    public R<Boolean> setBotNode(@RequestBody SetBotConfIdDto setBotConfIdDto) {
        return DataResult.ok(lowCodeService.setBotNode(setBotConfIdDto));
    }
}
