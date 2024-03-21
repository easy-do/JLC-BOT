package plus.easydo.bot.lowcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 节点执行返回结果
 * @date 2024/3/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeExecuteResult {

    private String nodeId;
    private String nodeName;
    private String nodeCode;
    private Object data;
    private String message;
    private Integer status;
    private Long executeTime = 0L;

}
