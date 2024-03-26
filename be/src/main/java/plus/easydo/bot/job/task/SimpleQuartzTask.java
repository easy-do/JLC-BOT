package plus.easydo.bot.job.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import plus.easydo.bot.job.QuartzJob;

/**
 * @author yuzhanfeng
 * @Date 2024-03-26
 * @Description 简单任务示例
 */
@Slf4j
@Component("simpleQuartzTask")
public class SimpleQuartzTask implements QuartzTask{
    @Override
    public void executeInternal(JobExecutionContext context, QuartzJob quartzJob) {
        log.info("simpleQuartzTask execute ......");
    }
}
