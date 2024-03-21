package plus.easydo.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author laoyu
 * @version 1.0
 * @description 节点配置传输对象
 * @date 2024/3/4
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotNodeDto {

    private Long id;
    private String eventType;
    private String confName;
    private Object nodes;
    private Object nodeConf;

}
