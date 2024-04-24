package plus.easydo.bot.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统节点信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SysNodeQo extends PageQo {


    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 分组名称
     */
    private String groupType;

    /**
     * 节点编号
     */
    private String nodeCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 节点类型 是否系统节点
     */
    private Boolean systemNode;

}
