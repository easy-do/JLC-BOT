package plus.easydo.bot.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
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
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 配置名称
     */
    @Column(value = "conf_name")
    private String confName;

    /**
     * 指令
     */
    @Column(value = "cmd")
    private String cmd;

    /**
     * 指令类型
     */
    @Column(value = "cmd_type")
    private String cmdType;

    @Column(value = "remark")
    private String remark;

    @Column(ignore = true)
    private String scriptLanguage;

    @Column(ignore = true)
    private LiteFlowScript script;

}
