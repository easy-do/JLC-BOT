package plus.easydo.bot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 消息记录 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "bot_message")
public class BotMessage {

    /**
     * 自增主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 消息id
     */
    @Column(value = "message_id")
    private String messageId;

    /**
     * 群组id
     */
    @Column(value = "group_id")
    private String groupId;

    /**
     * 发送用户
     */
    @Column(value = "send_user")
    private String sendUser;
    /**
     * 接收用户
     */
    @Column(value = "self_user")
    private String selfUser;

    /**
     * 接收到的时间
     */
    @Column(value = "self_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime selfTime;

    /**
     * 消息内容
     */
    @Column(value = "message")
    private String message;

    /**
     * 消息格式
     */
    @Column(value = "message_format")
    private String messageFormat;


}
