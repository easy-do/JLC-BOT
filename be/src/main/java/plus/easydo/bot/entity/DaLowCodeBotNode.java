package plus.easydo.bot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.NoArgsConstructor;

/**
 * 机器人与节点配置关联表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "da_low_code_bot_node")
public class DaLowCodeBotNode {

    /**
     * 机器人id
     */
    @Column(value = "bot_id")
    private Long botId;

    /**
     * 节点配置id
     */
    @Column(value = "conf_id")
    private Long confId;


}
