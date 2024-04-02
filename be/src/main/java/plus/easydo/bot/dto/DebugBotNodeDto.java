package plus.easydo.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author yuzhanfeng
 * @Date 2024-03-06
 * @Description 调试节点参数封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebugBotNodeDto {

    private Long id;

    private Object params;
}
