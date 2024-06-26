package plus.easydo.bot.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 消息记录 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BotMessageQo extends PageQo {


    /**
     * 消息id
     */
    private String messageId;


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
     * 接收到的时间
     */
    private LocalDateTime selfTime;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息格式
     */
    private String messageFormat;


}
