package plus.easydo.bot.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BotRequestQo extends PageQo {


    /**
     * 请求类型
     */
    private String requestType;


    /**
     * 群组id
     */
    private String groupId;

    /**
     * 发送用户
     */
    private String sendUser;

    /**
     * 接收用户
     */
    private String selfUser;

    /**
     * 接收时间
     */
    private LocalDateTime selfTime;

    /**
     * 验证信息
     */
    private String comment;

    /**
     * 请求标识
     */
    private String flag;


}
