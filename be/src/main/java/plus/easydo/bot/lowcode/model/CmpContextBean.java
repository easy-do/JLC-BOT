package plus.easydo.bot.lowcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024/4/14
 * @Description 上下文实例
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmpContextBean {

    private String name;

    private String desc;

    private List<CmpContextBeanMethod> methods;

    @Data
    @Builder
    public static class CmpContextBeanMethod {
        private String name;
        private String returnType;
        private String demo;
        private String desc;
    }
}
