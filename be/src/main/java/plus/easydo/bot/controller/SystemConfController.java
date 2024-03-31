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
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.qo.SystemConfigQo;
import plus.easydo.bot.service.SystemConfService;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏配置 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conf")
public class SystemConfController {

    private final SystemConfService daGameConfigService;


    /**
     * 分页查询游戏配置
     *
     * @param gameConfigQo gameConfigQo
     * @return 分页对象
     */
    @SaCheckLogin
    @Operation(summary = "分页")
    @PostMapping("/page")
    public R<List<SystemConf>> pageConf(@RequestBody SystemConfigQo gameConfigQo) {
        return DataResult.ok(daGameConfigService.confPage(gameConfigQo));
    }

    /**
     * 根据游戏配置主键获取详细信息。
     *
     * @param id daGameConfig主键
     * @return 游戏配置详情
     */
    @SaCheckLogin
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    public R<SystemConf> getConfInfo(@PathVariable Serializable id) {
        return DataResult.ok(daGameConfigService.getById(id));
    }

    /**
     * 添加 游戏配置
     *
     * @param systemConf 游戏配置
     * @return {@code true} 添加成功，{@code false} 添加失败
     */

    @SaCheckLogin
    @Operation(summary = "添加")
    @PostMapping("/save")
    public R<Object> saveConf(@RequestBody SystemConf systemConf) {
        return DataResult.ok(daGameConfigService.saveConf(systemConf));
    }

    /**
     * 更新
     *
     * @param systemConf 游戏配置
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @SaCheckLogin
    @Operation(summary = "更新")
    @PostMapping("/update")
    public R<Object> updateConf(@RequestBody SystemConf systemConf) {
        return DataResult.ok(daGameConfigService.updateConf(systemConf));
    }


}
