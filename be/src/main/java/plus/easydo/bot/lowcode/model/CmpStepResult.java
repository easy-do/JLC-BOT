package plus.easydo.bot.lowcode.model;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-03-27
 * @Description liteflow执行结果封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmpStepResult {

    private String nodeId;
    private String nodeName;
    private String tag;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Long timeSpent;
    private boolean success;
    private String message;
    private JSONObject param;
    private Long rollbackTimeSpent;
    private List<CmpContextBean> contextBeanList;
}
