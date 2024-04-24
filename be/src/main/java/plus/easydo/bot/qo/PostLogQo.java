package plus.easydo.bot.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * 接收消息日志 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PostLogQo extends PageQo {


    /**
     * 消息
     */
    private String message;

    /**
     * 平台
     */
    private String platform;

}
