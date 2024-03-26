package plus.easydo.bot.job;

import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import plus.easydo.bot.manager.QuartzJobManager;
import plus.easydo.bot.qo.PageQo;

import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-25
 * @Description QuartzJobService
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class QuartzJobService {

    private final QuartzJobUtils quartzJobUtils;

    private final QuartzJobManager quartzJobManager;

    @PostConstruct
    public void initJob() {
        log.info("开始初始化定时任务......");
        List<QuartzJob> jobList = quartzJobManager.list();
        jobList.forEach(quartzJob -> {
            boolean res = quartzJobUtils.createJob(quartzJob);
            if (Boolean.FALSE.equals(quartzJob.getStatus())) {
                quartzJobUtils.pauseJob(quartzJob);
            }
            log.info("添加任务==> {},{}", quartzJob.getJobName(), res);
        });
        log.info("初始化定时任务结束......");
    }

    /**
     * 添加任务
     *
     * @param quartzJob quartzJob
     * @return boolean
     * @author laoyu
     * @date 2024-03-26
     */
    public boolean addJob(QuartzJob quartzJob) {
        boolean res = quartzJobManager.save(quartzJob);
        if (res) {
            return quartzJobUtils.createJob(quartzJob);
        }
        return false;
    }

    /**
     * 运行一次
     *
     * @param id id
     * @return boolean
     * @author laoyu
     * @date 2024-03-26
     */
    public boolean runOneJob(Long id) {
        QuartzJob quartzJob = quartzJobManager.getById(id);
        if (Objects.nonNull(quartzJob)) {
            return quartzJobUtils.triggerJob(quartzJob);
        }
        return false;
    }

    /**
     * 开启定时任务
     *
     * @param id id
     * @return boolean
     * @author laoyu
     * @date 2024-03-26
     */
    public boolean startJob(Long id) {
        QuartzJob quartzJob = quartzJobManager.getById(id);
        if (Objects.nonNull(quartzJob) && Boolean.FALSE.equals(quartzJob.getStatus())) {
            if (quartzJobUtils.resumeJob(quartzJob)) {
                quartzJob.setStatus(true);
                return quartzJobManager.updateById(quartzJob);
            }
        }
        return false;
    }

    /**
     * 停止任务
     *
     * @param id id
     * @return boolean
     * @author laoyu
     * @date 2024-03-26
     */
    public boolean stopJob(Long id) {
        QuartzJob quartzJob = quartzJobManager.getById(id);
        if (Objects.nonNull(quartzJob)) {
            if (quartzJobUtils.pauseJob(quartzJob)) {
                quartzJob.setStatus(false);
                return quartzJobManager.updateById(quartzJob);
            }
        }
        return false;
    }

    /**
     * 更新任务
     *
     * @param quartzJob quartzJob
     * @return boolean
     * @author laoyu
     * @date 2024-03-26
     */
    public boolean updateJob(QuartzJob quartzJob) {
        if (quartzJobManager.updateById(quartzJob)) {
            return quartzJobUtils.rescheduleJob(quartzJob);
        }
        return false;
    }


    public boolean removeJob(Long id) {
        QuartzJob quartzJob = quartzJobManager.getById(id);
        if (Objects.nonNull(quartzJob)) {
            if (quartzJobUtils.removeJob(quartzJob)) {
                return quartzJobManager.removeById(id);
            }
        }
        return false;
    }

    public Page<QuartzJob> jobPage(PageQo pageQo) {
        return quartzJobManager.page(new Page<>(pageQo.getCurrent(), pageQo.getPageSize()));
    }

    public QuartzJob jobInfo(Long id) {
        return quartzJobManager.getById(id);
    }
}
