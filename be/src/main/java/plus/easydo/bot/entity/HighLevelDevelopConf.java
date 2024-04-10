package plus.easydo.bot.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "high_level_develop_conf")
public class HighLevelDevelopConf {

    @Column(value = "id")
    private Long id;

    @Column(value = "conf_name")
    private String confName;

    @Column(value = "event_type")
    private String eventType;

    @Column(ignore = true)
    private LiteFlowScript script;

}
