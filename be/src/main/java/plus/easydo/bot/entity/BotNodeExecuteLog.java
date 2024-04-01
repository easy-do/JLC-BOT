package plus.easydo.bot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 节点执行日志 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "low_code_bot_node_execute_log")
public class BotNodeExecuteLog {

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
     * 节点编码
     */
    @Column(value = "node_code")
    private String nodeCode;

    /**
     * 节点名
     */
    @Column(value = "node_name")
    private String nodeName;

    /**
     * 执行时间
     */
    @Column(value = "execute_time")
    private Long executeTime;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(value = "create_time")
    private LocalDateTime createTime;

}
