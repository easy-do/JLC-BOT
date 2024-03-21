package plus.easydo.bot.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(value = "da_bot_platform_bind")
public class DaBotPlatformBind {

    /**
     * 平台账号
     */
    @Column(value = "bot_number")
    private String botNumber;

    /**
     * 账号id
     */
    @Column(value = "uid")
    private Long uid;

    /**
     * 角色ID
     */
    @Column(value = "role_id")
    private Long roleId;


}
