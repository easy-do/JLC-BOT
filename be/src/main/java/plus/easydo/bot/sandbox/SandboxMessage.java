package plus.easydo.bot.sandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuzhanfeng
 * @Date 2024-03-30
 * @Description 沙箱消息封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SandboxMessage {

    private String messageId;

    private Boolean isSelf;

    private String type;

    private String message;

    private String time;

    private Long confId;

}
