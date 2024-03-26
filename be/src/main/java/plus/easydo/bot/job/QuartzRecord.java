package plus.easydo.bot.job;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import plus.easydo.bot.constant.SystemConstant;
import plus.easydo.bot.job.task.QuartzTask;
import plus.easydo.bot.manager.QuartzJobLogManager;

import java.util.Objects;


/**
 * @author yuzhanfeng
 * @Date 2024-03-25
 * @Description 定时任务
 */
@Slf4j
public class QuartzRecord extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        QuartzJob quartzJob = (QuartzJob)context.getMergedJobDataMap().get(QuartzJob.JOB_PARAM_KEY) ;
        QuartzJobLogManager logManager = SpringUtil.getBean(QuartzJobLogManager.class) ;
        // 定时器日志记录
        QuartzJobLog quartzJobLog = new QuartzJobLog () ;
        quartzJobLog.setJobId(quartzJob.getId());
        quartzJobLog.setJobClass(quartzJob.getJobClass());
        quartzJobLog.setJobName(quartzJob.getJobParam());
        quartzJobLog.setCreateTime(LocalDateTimeUtil.now());
        long beginTime = System.currentTimeMillis() ;
        try {
            // 加载并执行
            String jobClass = quartzJob.getJobClass();
            QuartzTask taskBean = SpringUtil.getBean(jobClass);
            if(Objects.nonNull(taskBean)){
                taskBean.executeInternal(context,quartzJob);
            }else {
                log.warn("没有找到任务类[{}]",jobClass);
            }
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
            logManager.save(quartzJobLog) ;
        }
    }
}
