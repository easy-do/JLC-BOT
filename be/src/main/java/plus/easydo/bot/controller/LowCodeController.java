package plus.easydo.bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.entity.DaLowCodeNodeConf;
import plus.easydo.bot.dto.BotNodeDto;
import plus.easydo.bot.dto.DebugBotNodeDto;
import plus.easydo.bot.lowcode.model.NodeExecuteResult;
import plus.easydo.bot.dto.SetBotNodeDto;
import plus.easydo.bot.service.DaLowCodeService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;
import plus.easydo.bot.qo.PageQo;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 低代码相关接口
 * @date 2024/3/5
 */
@RestController
@RequestMapping("/api/lowcode")
@RequiredArgsConstructor
public class LowCodeController {

    private final DaLowCodeService daLowCodeService;


    /**
     * 分页查询节点配置
     *
     * @param pageQo pageQo
     * @return vo.plus.easydo.lowcode.bot.R<java.util.List<model.bot.lowcode.plus.easydo.lowcode.bot.BotNodeDto>>
     * @author laoyu
     * @date 2024-03-06
     */
    @Operation(summary = "分页查询节点配置")
    @PostMapping("/pageNodeConf")
    public R<List<DaLowCodeNodeConf>> pageNodeConf(@RequestBody PageQo pageQo) {
        return DataResult.ok(daLowCodeService.pageNodeConf(pageQo));
    }

    /**
     * 所有节点配置
     *
     * @return vo.plus.easydo.lowcode.bot.R<java.util.List<entity.bot.lowcode.plus.easydo.lowcode.bot.DaLowCodeNodeConf>>
     * @author laoyu
     * @date 2024-03-07
     */
    @Operation(summary = "所有节点配置")
    @PostMapping("/listNodeConf")
    public R<List<DaLowCodeNodeConf>> listNodeConf() {
        return DataResult.ok(daLowCodeService.listNodeConf());
    }


    /**
     * 获取配置
     *
     * @param id id
     * @return vo.plus.easydo.lowcode.bot.R<model.bot.lowcode.plus.easydo.lowcode.bot.BotNodeDto>
     * @author laoyu
     * @date 2024/3/5
     */
    @Operation(summary = "获取节点配置")
    @GetMapping("/getNodeConf/{id}")
    public R<BotNodeDto> getNodeConf(@PathVariable("id") Long id) {
        return DataResult.ok(daLowCodeService.getNodeConf(id));
    }

    /**
     * 获取机器人的节点配置
     *
     * @param botId botId
     * @return vo.plus.easydo.lowcode.bot.R<java.util.List<java.lang.Long>>
     * @author laoyu
     * @date 2024-03-07
     */
    @Operation(summary = "获取机器人的节点配置")
    @GetMapping("/getBotNode/{id}")
    public R<List<Long>> getBotNode(@PathVariable("id") Long botId) {
        return DataResult.ok(daLowCodeService.getBotNode(botId));
    }

    /**
     * 保存配置
     *
     * @param botNodeDto botNodeDto
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/3/5
     */
    @Operation(summary = "保存节点配置")
    @PostMapping("/saveNodeConf")
    public R<Long> saveNodeConf(@RequestBody BotNodeDto botNodeDto) {
        return DataResult.ok(daLowCodeService.saveNodeConf(botNodeDto));
    }

    /**
     * 更新配置
     *
     * @param botNodeDto botNodeDto
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/3/5
     */
    @Operation(summary = "更新节点配置")
    @PostMapping("/updateNodeConf")
    public R<Boolean> updateNodeConf(@RequestBody BotNodeDto botNodeDto) {
        return DataResult.ok(daLowCodeService.updateNodeConf(botNodeDto));
    }

    /**
     * 删除配置
     *
     * @param id id
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-03-06
     */
    @Operation(summary = "删除节点配置")
    @GetMapping("/removeNodeConf/{id}")
    public R<Boolean> removeNodeConf(@PathVariable("id") Long id) {
        return DataResult.ok(daLowCodeService.removeNodeConf(id));
    }

    /**
     * 调试节点配置
     *
     * @param debugBotNodeDto debugBotNodeDto
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Object>
     * @author laoyu
     * @date 2024-03-06
     */
    @Operation(summary = "调试节点配置")
    @PostMapping("/debugNodeConf")
    public R<List<NodeExecuteResult>> debugNodeConf(@RequestBody DebugBotNodeDto debugBotNodeDto) {
        return DataResult.ok(daLowCodeService.debugNodeConf(debugBotNodeDto));
    }

    /**
     * 设置机器人与节点配置关联关系
     *
     * @param setBotNodeDto setBotNodeDto
     * @return vo.plus.easydo.lowcode.bot.R<java.lang.Object>
     * @author laoyu
     * @date 2024-03-07
     */
    @Operation(summary = "设置机器人与节点配置关联关系")
    @PostMapping("/setBotNode")
    public R<Boolean> setBotNode(@RequestBody SetBotNodeDto setBotNodeDto) {
        return DataResult.ok(daLowCodeService.setBotNode(setBotNodeDto));
    }
}
