package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
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
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.service.SimpleDevelopService;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

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

}
