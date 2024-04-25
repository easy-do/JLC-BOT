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
public class WebhooksConfQo extends PageQo {

    private Long id;

    /**
     * 配置名称
     */
    private String confName;

    /**
     * 备注
     */
    private String remark;


}
