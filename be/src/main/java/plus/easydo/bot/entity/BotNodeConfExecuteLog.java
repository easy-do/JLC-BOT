package plus.easydo.bot.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 节点配置执行日志 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "low_code_bot_node_conf_execute_log")
public class BotNodeConfExecuteLog {

    /**
     * 配置id
     */
    @Column(value = "conf_id")
    private Long confId;

    /**
     * 节点名
     */
    @Column(value = "conf_name")
    private String confName;

    /**
     * 执行时间
     */
    @Column(value = "execute_time")
    private Long executeTime;

    private LocalDateTime createTime;
}
