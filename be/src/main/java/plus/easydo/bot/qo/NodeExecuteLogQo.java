package plus.easydo.bot.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 节点执行日志
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class NodeExecuteLogQo extends PageQo {

    /**
     * 配置id
     */
    private Long confId;

    /**
     * 节点名
     */
    private String confName;

    /**
     * 节点编码
     */
    private String nodeCode;

    /**
     * 节点名
     */
    private String nodeName;

    /**
     * 执行时间
     */
    private Long executeTime;

}
