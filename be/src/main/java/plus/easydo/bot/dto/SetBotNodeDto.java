package plus.easydo.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-03-07
 * @Description 设置机器人与节点配置关联关系参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetBotNodeDto {

    private Long botId;

    private List<Long> confIdList;
}
