package plus.easydo.bot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.time.LocalDateTime;

/**
 * 节点配置信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "low_code_node_conf")
public class LowCodeNodeConf {

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 节点配置
     */
    @Column(value = "conf_data")
    private String confData;

    /**
     * 配置名称
     */
    @Column(value = "conf_name")
    private String confName;

    /**
     * 节点数据
     */
    @Column(value = "node_data")
    private String nodeData;

    /**
     * 事件类型
     */
    @Column(value = "event_type")
    private String eventType;

    /**
     * 创建时间
     */
    @Column(value = "create_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(value = "update_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    @Column(value = "delete_flag",isLogicDelete = true)
    private Boolean deleteFlag;

}
