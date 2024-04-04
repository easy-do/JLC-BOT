package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
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
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.entity.LowCodeSysNode;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.LowCodeSysNodeService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 系统节点信息 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequestMapping("/api/sysNode")
@RequiredArgsConstructor
public class LowCodeSysNodeController {

    private final LowCodeSysNodeService lowCodeSysNodeService;

    /**
     * 添加 系统节点信息
     *
     * @param lowCodeSysNode 系统节点信息
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @SaCheckLogin
    @Operation(summary = "添加")
    @PostMapping("/save")
    public R<Boolean> saveSysNode(@RequestBody LowCodeSysNode lowCodeSysNode) {
        return DataResult.ok(lowCodeSysNodeService.saveSysNode(lowCodeSysNode));
    }


    /**
     * 根据主键删除系统节点信息
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @SaCheckLogin
    @Operation(summary = "删除")
    @GetMapping("/remove/{id}")
    public R<Boolean> removeSysNode(@PathVariable Serializable id) {
        return DataResult.ok(lowCodeSysNodeService.removeSysNode(id));
    }


    /**
     * 根据主键更新系统节点信息
     *
     * @param lowCodeSysNode 系统节点信息
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @SaCheckLogin
    @Operation(summary = "更新")
    @PostMapping("/update")
    public R<Boolean> updateSysNode(@RequestBody LowCodeSysNode lowCodeSysNode) {
        return DataResult.ok(lowCodeSysNodeService.updateSysNode(lowCodeSysNode));
    }

    /**
     * 根据主键更新系统节点信息
     *
     * @param lowCodeSysNode 系统节点信息
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @SaCheckLogin
    @Operation(summary = "更新表单配置")
    @PostMapping("/updateFormData")
    public R<Boolean> updateSysNodeFormData(@RequestBody LowCodeSysNode lowCodeSysNode) {
        return DataResult.ok(lowCodeSysNodeService.updateSysNodeFormData(lowCodeSysNode));
    }

    /**
     * 根据主键更新系统节点信息
     *
     * @param lowCodeSysNode 系统节点信息
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @SaCheckLogin
    @Operation(summary = "更新脚本配置")
    @PostMapping("/updateScriptData")
    public R<Boolean> updateSysNodeScriptData(@RequestBody LiteFlowScript lowCodeSysNode) {
        return DataResult.ok(lowCodeSysNodeService.updateSysNodeScriptData(lowCodeSysNode));
    }

    /**
     * 根据系统节点信息主键获取详细信息。
     *
     * @param id daLowCodeSysNode主键
     * @return 系统节点信息详情
     */
    @SaCheckLogin
    @Operation(summary = "获取脚本信息")
    @GetMapping("/getScriptData/{id}")
    public R<LiteFlowScript> getSysNodeScriptData(@PathVariable Serializable id) {
        return DataResult.ok(lowCodeSysNodeService.getSysNodeScriptData(id));
    }


    /**
     * 查询所有系统节点信息
     *
     * @return 所有数据
     */
    @SaCheckLogin
    @Operation(summary = "所有数据")
    @GetMapping("/list")
    public R<Map<String, List<LowCodeSysNode>>> listSysNode() {
        return DataResult.ok(lowCodeSysNodeService.listSysNode());
    }


    /**
     * 根据系统节点信息主键获取详细信息。
     *
     * @param id daLowCodeSysNode主键
     * @return 系统节点信息详情
     */
    @SaCheckLogin
    @Operation(summary = "详细信息")
    @GetMapping("/info/{id}")
    public R<LowCodeSysNode> getSysNodeInfo(@PathVariable Serializable id) {
        return DataResult.ok(lowCodeSysNodeService.getSysNodeInfo(id));
    }


    /**
     * 分页查询系统节点信息
     *
     * @param pageQo 分页对象
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "分页")
    @PostMapping("/page")
    public R<List<LowCodeSysNode>> pageSysNode(@RequestBody PageQo pageQo) {
        return DataResult.ok(lowCodeSysNodeService.page(new Page<>(pageQo.getCurrent(), pageQo.getPageSize())));
    }

    /**
     * 导入系统节点
     *
     * @param file file
     * @return plus.easydo.bot.vo.R<java.lang.Long>
     * @author laoyu
     * @date 2024/4/4
     */
    @SaCheckLogin
    @Operation(summary = "导入系统节点")
    @PostMapping("/importNode")
    public R<Long> importNode(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream ip = file.getInputStream();
        byte[] bytes = ip.readAllBytes();
        String content = new String(bytes);
        try {
            LowCodeSysNode sysNode = JSONUtil.toBean(content, LowCodeSysNode.class);
            return DataResult.ok(lowCodeSysNodeService.importNode(sysNode));
        } catch (Exception e) {
            return DataResult.fail("解析节点内容失败:"+ExceptionUtil.getMessage(e));
        }
    }
}
