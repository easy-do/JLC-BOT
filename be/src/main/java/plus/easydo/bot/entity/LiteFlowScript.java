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
 * liteflow脚本节点 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "liteflow_script")
public class LiteFlowScript {

    /**
     * id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 应用名称
     */
    @Column(value = "application_name")
    private String applicationName;

    /**
     * 脚本id
     */
    @Column(value = "script_id")
    private String scriptId;

    /**
     * 脚本名
     */
    @Column(value = "script_name")
    private String scriptName;

    /**
     * 脚本内容
     */
    @Column(value = "script_data")
    private String scriptData;

    /**
     * 脚本类型
     */
    @Column(value = "script_type")
    private String scriptType;

    /**
     * 脚本语言
     */
    @Column(value = "script_language")
    private String scriptLanguage;

    /**
     * 是否启用
     */
    @Column(value = "enable")
    private Boolean enable;

}
