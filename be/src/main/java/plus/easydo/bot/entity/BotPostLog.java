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
 * 接收消息日志 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "bot_post_log")
public class BotPostLog {

    /**
     * 编号
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 推送时间
     */
    @Column(value = "post_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postTime;

    /**
     * 消息
     */
    @Column(value = "message")
    private String message;

    /**
     * 平台
     */
    @Column(value = "platform")
    private String platform;

}
