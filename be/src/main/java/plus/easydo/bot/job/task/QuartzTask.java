package plus.easydo.bot.job.task;

import org.quartz.JobExecutionContext;
import plus.easydo.bot.job.QuartzJob;

/**
 * @author yuzhanfeng
 * @Date 2024-03-26
 * @Description Quartz的任务抽象
 */
public  interface QuartzTask {

    void executeInternal(JobExecutionContext context, QuartzJob quartzJob);
}
