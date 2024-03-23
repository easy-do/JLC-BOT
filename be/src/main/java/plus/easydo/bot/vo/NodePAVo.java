package plus.easydo.bot.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 节点执行时间分析
 * @date 2024/3/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodePAVo {

    private String nodeName;
    private String confName;
    private Integer executeTime;

}
