package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.HighLevelDevelopService;
import plus.easydo.bot.entity.HighLevelDevelopConf;
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
@Tag(name = "高级开发")
@RequiredArgsConstructor
@RequestMapping("/api/highLevelDevelop")
public class HighLevelDevelopController {

    private final HighLevelDevelopService highLevelDevelopService;


    /**
     * 所有列表
     *
     * @return plus.easydo.bot.vo.R<java.util.List<plus.easydo.bot.entity.HighLevelDevelopConf>>
     * @author laoyu
     * @date 2024-04-10
     */
    @SaCheckLogin
    @Operation(summary = "所有列表")
    @GetMapping("/list")
    public R<List<HighLevelDevelopConf>> highLevelDevList() {
        return DataResult.ok(highLevelDevelopService.highLevelDevList());
    }

    /**
     * 分页查询
     *
     * @param pageQo pageQo
     * @return plus.easydo.bot.vo.R<java.util.List<plus.easydo.bot.entity.HighLevelDevelopConf>>
     * @author laoyu
     * @date 2024-04-10
     */
    @SaCheckLogin
    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public R<List<HighLevelDevelopConf>> highLevelDevPage(PageQo pageQo) {
        return DataResult.ok(highLevelDevelopService.highLevelDevPage(pageQo));
    }

    /**
     * 添加
     *
     * @param highLevelDevelopConf highLevelDevelopConf
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-04-10
     */
    @SaCheckLogin
    @Operation(summary = "添加")
    @PostMapping("/save")
    public R<Boolean> saveHighLevelDev(@RequestBody HighLevelDevelopConf highLevelDevelopConf) {
        return DataResult.ok(highLevelDevelopService.saveHighLevelDev(highLevelDevelopConf));
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
    @GetMapping("/remove/{id}")
    public R<Boolean> removeHighLevelDev(@PathVariable Long id) {
        return DataResult.ok(highLevelDevelopService.removeHighLevelDev(id));
    }


    /**
     * 更新
     *
     * @param highLevelDevelopConf highLevelDevelopConf
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-04-10
     */
    @SaCheckLogin
    @Operation(summary = "更新")
    @PutMapping("/update")
    public R<Boolean> updateHighLevelDev(@RequestBody HighLevelDevelopConf highLevelDevelopConf) {
        return DataResult.ok(highLevelDevelopService.updateHighLevelDev(highLevelDevelopConf));
    }

    /**
     * 详细信息
     *
     * @param id id
     * @return plus.easydo.bot.vo.R<plus.easydo.bot.entity.HighLevelDevelopConf>
     * @author laoyu
     * @date 2024-04-10
     */
    @GetMapping("/getInfo/{id}")
    public R<HighLevelDevelopConf> getHighLevelDevInfo(@PathVariable Long id) {
        return DataResult.ok(highLevelDevelopService.getHighLevelDevInfo(id));
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
    public R<Long> importHighLevelDevelop(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream ip = file.getInputStream();
        byte[] bytes = ip.readAllBytes();
        String content = new String(bytes);
        try {
            HighLevelDevelopConf conf = JSONUtil.toBean(content, HighLevelDevelopConf.class);
            return DataResult.ok(highLevelDevelopService.importConf(conf));
        } catch (Exception e) {
            return DataResult.fail("解析配置内容失败:"+ ExceptionUtil.getMessage(e));
        }
    }


    @SaCheckLogin
    @Operation(summary = "调试配置")
    @PostMapping("/debug")
    public R<CmpStepResult> debugHighLevelDevelop(@RequestBody DebugDto debugDto) {
        return DataResult.ok(highLevelDevelopService.debug(debugDto));
    }

    /**
     * 获取机器人高级开发配置
     *
     * @param botId botId
     * @return plus.easydo.bot.vo.R<java.util.List<java.lang.Long>>
     * @author laoyu
     * @date 2024/4/12
     */
    @SaCheckLogin
    @Operation(summary = "获取机器人的节点配置")
    @GetMapping("/getBotHighLevelDevelop/{id}")
    public R<List<Long>> getBotHighLevelDevelop(@PathVariable("id") Long botId) {
        return DataResult.ok(highLevelDevelopService.getBotHighLevelDevelop(botId));
    }

    /**
     * 设置机器人高级开发配置
     *
     * @param setBotConfIdDto setBotConfIdDto
     * @return plus.easydo.bot.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/4/12
     */
    @SaCheckLogin
    @Operation(summary = "设置机器人与节点配置关联关系")
    @PostMapping("/setBotHighLevelDevelop")
    public R<Boolean> setBotHighLevelDevelop(@RequestBody SetBotConfIdDto setBotConfIdDto) {
        return DataResult.ok(highLevelDevelopService.setBotHighLevelDevelop(setBotConfIdDto));
    }



}
