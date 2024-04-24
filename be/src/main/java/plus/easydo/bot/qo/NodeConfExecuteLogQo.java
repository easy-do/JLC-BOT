package plus.easydo.bot.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * 节点配置执行日志 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class NodeConfExecuteLogQo extends PageQo {

    /**
     * 配置id
     */
    private Long confId;

    /**
     * 节点名
     */
    private String confName;

    /**
     * 执行时间
     */
    private Long executeTime;

}
