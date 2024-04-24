package plus.easydo.bot.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * 节点配置信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class NodeConfQo extends PageQo {


    /**
     * 配置名称
     */
    private String confName;


    /**
     * 事件类型
     */
    private String eventType;

}
