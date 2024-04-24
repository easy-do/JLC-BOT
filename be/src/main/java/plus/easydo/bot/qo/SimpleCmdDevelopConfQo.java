package plus.easydo.bot.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SimpleCmdDevelopConfQo extends PageQo {

    /**
     * 配置名称
     */
    private String confName;

    /**
     * 指令
     */
    private String cmd;

    /**
     * 指令类型
     */
    private String cmdType;

    private String remark;

}
