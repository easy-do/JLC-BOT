package plus.easydo.bot.job;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

/**
 * @author yuzhanfeng
 * @Date 2024-03-26
 * @Description QuartzJob工具类
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class QuartzJobUtils {

    private final Scheduler scheduler;

    private TriggerKey getTriggerKey(QuartzJob quartzJob) {
        return TriggerKey.triggerKey(quartzJob.getId()+"");
    }

    private JobKey getJobKey(QuartzJob quartzJob) {
        return JobKey.jobKey(quartzJob.getId()+"");
    }

    public boolean createJob(QuartzJob quartzJob) {
        try {
            // 构建任务
            JobDetail jobDetail = JobBuilder.newJob(QuartzRecord.class).withIdentity(getJobKey(quartzJob)).build();
            // 构建Cron调度器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            // 任务触发器
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(quartzJob)).withSchedule(scheduleBuilder).build();
            jobDetail.getJobDataMap().put(QuartzJob.JOB_PARAM_KEY, quartzJob);
            scheduler.scheduleJob(jobDetail, trigger);
            if (Boolean.TRUE.equals(quartzJob.getStatus())) {
                resumeJob(quartzJob);
            } else {
                pauseJob(quartzJob);
            }
            return true;
        } catch (SchedulerException e) {
            log.warn("创建任务失败:{}", ExceptionUtil.getMessage(e));
        }
        return false;
    }

    public boolean rescheduleJob(QuartzJob quartzJob) {
        try {
            // 查询触发器Key
            TriggerKey triggerKey = getTriggerKey(quartzJob);
            // 构建Cron调度器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            // 任务触发器
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //参数
            trigger.getJobDataMap().put(QuartzJob.JOB_PARAM_KEY, quartzJob);
            scheduler.rescheduleJob(triggerKey, trigger);
            if (Boolean.TRUE.equals(quartzJob.getStatus())) {
                resumeJob(quartzJob);
            } else {
                pauseJob(quartzJob);
            }
            return true;
        } catch (SchedulerException e) {
            log.warn("更新任务失败:{}", ExceptionUtil.getMessage(e));
        }
        return false;
    }

    public boolean triggerJob(QuartzJob quartzJob) {
        try {
            scheduler.triggerJob(getJobKey(quartzJob));
            return true;
        } catch (SchedulerException e) {
            log.warn("暂停任务失败:{}", ExceptionUtil.getMessage(e));
        }
        return false;
    }

    public boolean pauseJob(QuartzJob quartzJob) {
        try {
            scheduler.pauseJob(getJobKey(quartzJob));
            quartzJob.setStatus(false);
            return true;
        } catch (SchedulerException e) {
            log.warn("暂停任务失败:{}", ExceptionUtil.getMessage(e));
        }
        return false;
    }

    public boolean resumeJob(QuartzJob quartzJob) {
        try {
            scheduler.resumeJob(getJobKey(quartzJob));
            return true;
        } catch (SchedulerException e) {
            log.warn("恢复任务失败:{}", ExceptionUtil.getMessage(e));
        }
        return false;
    }


    public boolean removeJob(QuartzJob quartzJob) {
        try {
            scheduler.deleteJob(getJobKey(quartzJob));
            return true;
        } catch (SchedulerException e) {
            log.warn("删除任务失败:{}", ExceptionUtil.getMessage(e));
        }
        return false;
    }
}
