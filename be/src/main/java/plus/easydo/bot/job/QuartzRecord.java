package plus.easydo.bot.job;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import plus.easydo.bot.constant.SystemConstant;


/**
 * @author yuzhanfeng
 * @Date 2024-03-25 23:55
 * @Description 定时任务
 */
@Slf4j
public class QuartzRecord extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        QuartzJob quartzJob = (QuartzJob)context.getMergedJobDataMap().get(QuartzJob.JOB_PARAM_KEY) ;
        QuartzJobLogService quartzJobLogService = SpringUtil.getBean(QuartzJobLogService.class) ;
        // 定时器日志记录
        QuartzJobLog quartzJobLog = new QuartzJobLog () ;
        quartzJobLog.setJobId(quartzJob.getId());
        quartzJobLog.setJobClass(quartzJob.getJobClass());
        quartzJobLog.setJobName(quartzJob.getJobParam());
        quartzJobLog.setCreateTime(LocalDateTimeUtil.now());
        long beginTime = System.currentTimeMillis() ;
        try {
            // 加载并执行
            log.info("执行任务......");
            log.info("{}",quartzJob);
            long executeTime = System.currentTimeMillis() - beginTime;
            quartzJobLog.setExecuteTime(executeTime);
            quartzJobLog.setStatus(true);
        } catch (Exception e){
            // 异常信息
            long executeTime = System.currentTimeMillis() - beginTime;
            quartzJobLog.setExecuteTime(executeTime);
            quartzJobLog.setStatus(false);
            quartzJobLog.setErrorMessage(e.getMessage());
        } finally {
            // 保存执行日志
            quartzJobLogService.save(quartzJobLog) ;
        }
    }
}
