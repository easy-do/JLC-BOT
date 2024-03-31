package plus.easydo.bot.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统节点信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "low_code_sys_node")
public class LowCodeSysNode {

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 节点名称
     */
    @Column(value = "node_name")
    private String nodeName;

    /**
     * 节点类型
     */
    @Column(value = "group_type")
    private String groupType;

    /**
     * 节点编号
     */
    @Column(value = "node_code")
    private String nodeCode;

    /**
     * 节点颜色
     */
    @Column(value = "node_color")
    private String nodeColor;
    /**
     * 节点图标
     */
    @Column(value = "node_icon")
    private String nodeIcon;

    /**
     * 节点port配置
     */
    @Column(value = "node_port")
    private String nodePort;

    /**
     * 最大数量
     */
    @Column(value = "max_size")
    private Integer maxSize;

    /**
     * 表单配置
     */
    @Column(value = "form_data")
    private String formData;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;

    /**
     * 删除标记
     */
    @Column(value = "delete_flag", isLogicDelete = true)
    private Boolean deleteFlag;

}
