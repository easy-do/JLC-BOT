package plus.easydo.bot.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 机器人与脚本关联表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "bot_script_bot")
public class DaBotScriptBot {

    /**
     * 脚本id
     */
    @Column(value = "script_id")
    private Long scriptId;

    /**
     * 机器人编码
     */
    @Column(value = "bot_number")
    private String botNumber;


}
