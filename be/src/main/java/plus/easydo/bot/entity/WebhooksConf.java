package plus.easydo.bot.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "WEBHOOKS_CONF")
public class WebhooksConf {

    @Column(value = "ID")
    private Long id;

    /**
     * 配置名称
     */
    @Column(value = "CONF_NAME")
    private String confName;

    /**
     * 备注
     */
    @Column(value = "REMARK")
    private String remark;

    @Column(ignore = true)
    private String scriptLanguage;

    @Column(ignore = true)
    private LiteFlowScript script;


}
