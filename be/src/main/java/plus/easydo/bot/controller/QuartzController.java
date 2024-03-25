package plus.easydo.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.job.QuartzJob;
import plus.easydo.bot.job.QuartzJobService;

/**
 * @author yuzhanfeng
 * @Date 2024-03-25 23:11
 * @Description TODO
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/quartz/")
public class QuartzController {
    //注入任务调度
    private final QuartzJobService quartzJobService;

    @PostMapping("/createJob")
    public Boolean createJob(@RequestBody QuartzJob quartzJob) {
        //进行测试所以写死
        quartzJob = new QuartzJob();
        quartzJob.setId(1L);
        quartzJob.setJobName("test");
        quartzJob.setJobClass("plus.easydo.bot.job.task.testTask");
        quartzJob.setCronExpression("*/10 * * * * ?");
        quartzJob.setStatus(false);
        return quartzJobService.createJob(quartzJob);
    }

    @GetMapping("/pauseJob")
    public Boolean pauseJob() {
        QuartzJob quartzJob = new QuartzJob();
        quartzJob.setId(1L);
        quartzJob.setJobName("test");
        quartzJob.setJobClass("plus.easydo.bot.job.task.testTask");
        quartzJob.setCronExpression("*/10 * * * * ?");
        quartzJob.setStatus(false);
        return quartzJobService.pauseJob(quartzJob);
    }

    @GetMapping("/triggerJob")
    public Boolean triggerJob() {
        QuartzJob quartzJob = new QuartzJob();
        quartzJob.setId(1L);
        quartzJob.setJobName("test");
        quartzJob.setJobClass("plus.easydo.bot.job.task.testTask");
        quartzJob.setCronExpression("*/10 * * * * ?");
        quartzJob.setStatus(false);
        return quartzJobService.triggerJob(quartzJob);
    }

    @GetMapping("/resume")
    public Boolean resume() {
        QuartzJob quartzJob = new QuartzJob();
        quartzJob.setId(1L);
        quartzJob.setJobName("test");
        quartzJob.setJobClass("plus.easydo.bot.job.task.testTask");
        quartzJob.setCronExpression("*/10 * * * * ?");
        quartzJob.setStatus(false);
        return quartzJobService.resumeJob(quartzJob);
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody QuartzJob quartzJob) {
        //进行测试所以写死
        quartzJob = new QuartzJob();
        quartzJob.setId(1L);
        quartzJob.setJobName("test");
        quartzJob.setJobClass("plus.easydo.bot.job.task.testTask");
        quartzJob.setCronExpression("*/10 * * * * ?");
        quartzJob.setStatus(false);
        return quartzJobService.updateJob(quartzJob);
    }

    @GetMapping("/delete")
    public Boolean deleteJob() {
        //进行测试所以写死
        QuartzJob quartzJob = new QuartzJob();
        quartzJob.setId(1L);
        quartzJob.setJobName("test");
        quartzJob.setJobClass("plus.easydo.bot.job.task.testTask");
        quartzJob.setCronExpression("*/10 * * * * ?");
        quartzJob.setStatus(true);
        return quartzJobService.deleteJob(quartzJob);
    }
}
