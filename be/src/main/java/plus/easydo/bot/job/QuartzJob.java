package plus.easydo.bot.job;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuzhanfeng
 * @Date 2024-03-25 23:22
 * @Description QuartzJob对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "quartz_job")
public class QuartzJob {

    /**
     * 任务id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 任务名称
     */
    @Column(value = "job_name")
    private String jobName;

    /**
     * cron表达式
     */
    @Column(value = "cron_expression")
    private String cronExpression;

    /**
     * 任务执行类
     */
    @Column(value = "job_class")
    private String jobClass;

    /**
     * 参数
     */
    @Column(value = "job_param")
    private String jobParam;

    /**
     * 状态
     */
    @Column(value = "status")
    private Boolean status;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;

    @Column(ignore = true)
    public static final String JOB_PARAM_KEY = "job_param_key";

}
