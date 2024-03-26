package plus.easydo.bot.job;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author yuzhanfeng
 * @Date 2024-03-25
 * @Description QuartzJob对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "quartz_job_log")
public class QuartzJobLog {

    /**
     * 日志id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 任务id
     */
    @Column(value = "job_id")
    private Long jobId;
    /**
     * 任务名称
     */
    @Column(value = "job_name")
    private String jobName;

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
     * 执行时间
     */
    @Column(value = "execute_time")
    private Long executeTime;

    /**
     * 状态
     */
    @Column(value = "status")
    private Boolean status;
    /**
     * 状态
     */
    @Column(value = "error_message")
    private String errorMessage;

    /**
     * 保存时间
     */
    @Column(value = "create_time")
    private LocalDateTime createTime;

}
