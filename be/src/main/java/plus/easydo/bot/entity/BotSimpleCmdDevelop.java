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
@Table(value = "bot_simple_cmd_develop")
public class BotSimpleCmdDevelop {

    /**
     * 配置id
     */
    @Column(value = "conf_id")
    private Long confId;

    /**
     * 机器人id
     */
    @Column(value = "bot_id")
    private Long botId;


}
