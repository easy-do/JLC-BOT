package plus.easydo.bot.lowcode.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author yuzhanfeng
 * @Date 2024/4/18
 * @Description 日志对象
 */
@Data
@Builder
public class CmpLog {

    private String type;

    private String context;
}
