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
@Table(value = "simple_cmd_develop_conf")
public class SimpleCmdDevelopConf {

    /**
     * 编号
     */
    @Column(value = "id")
    private Long id;

    /**
     * 配置名称
     */
    @Column(value = "conf_name")
    private String confName;

    /**
     * 命令
     */
    @Column(value = "cmd")
    private String cmd;

    @Column(ignore = true)
    private LiteFlowScript script;

}
