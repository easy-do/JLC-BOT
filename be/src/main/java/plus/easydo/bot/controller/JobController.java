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
import plus.easydo.bot.job.QuartzJob;
import plus.easydo.bot.job.QuartzJobService;
import plus.easydo.bot.qo.PageQo;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;

import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-03-25
 * @Description 定时任务相关
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/systemJob")
public class JobController {
    //注入任务调度
    private final QuartzJobService quartzJobService;


    @SaCheckLogin
    @PostMapping("/page")
    @Operation(summary = "分页查询任务")
    public R<List<QuartzJob>> pageJob(@RequestBody PageQo pageQo) {
        return DataResult.ok(quartzJobService.jobPage(pageQo));
    }
    @SaCheckLogin
    @GetMapping("/info/{id}")
    @Operation(summary = "任务详情")
    public R<QuartzJob> jobInfo(@PathVariable("id") Long id) {
        return DataResult.ok(quartzJobService.jobInfo(id));
    }

    @SaCheckLogin
    @PostMapping("/addJob")
    @Operation(summary = "创建任务")
    public R<Boolean> addJob(@RequestBody QuartzJob quartzJob) {
        return DataResult.ok(quartzJobService.addJob(quartzJob));
    }

    @SaCheckLogin
    @PostMapping("/update")
    @Operation(summary = "更新任务")
    public R<Boolean> updateJob(@RequestBody QuartzJob quartzJob) {
        return DataResult.ok(quartzJobService.updateJob(quartzJob));
    }

    @SaCheckLogin
    @GetMapping("/runOne/{id}")
    @Operation(summary = "执行一次")
    public R<Boolean> runOneJob(@PathVariable("id") Long id) {
        return DataResult.ok(quartzJobService.runOneJob(id));
    }

    @SaCheckLogin
    @GetMapping("/start/{id}")
    @Operation(summary = "启动任务")
    public R<Boolean> startJob(@PathVariable("id") Long id) {
        return DataResult.ok(quartzJobService.startJob(id));
    }

    @SaCheckLogin
    @GetMapping("/stop/{id}")
    @Operation(summary = "停止任务")
    public R<Boolean> stopJob(@PathVariable("id") Long id) {
        return DataResult.ok(quartzJobService.stopJob(id));
    }

    @SaCheckLogin
    @GetMapping("/remove/{id}")
    @Operation(summary = "删除任务")
    public R<Boolean> removeJob(@PathVariable("id") Long id) {
        return DataResult.ok(quartzJobService.removeJob(id));
    }
}
