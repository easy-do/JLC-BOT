package plus.easydo.bot.lowcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description 节点执行结果封装
 * @date 2024/3/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteResult {


    private boolean success;

    private String message;

    private Object data;

    public static ExecuteResult ok(){
        return ExecuteResult.builder().success(true).message("执行成功").build();
    }
    public static ExecuteResult ok(Object data){
        return ExecuteResult.builder().success(true).message("执行成功").data(data).build();
    }

    public static ExecuteResult fail(String message){
        return ExecuteResult.builder().success(false).message(message).build();
    }
    public static ExecuteResult fail(Object data){
        return ExecuteResult.builder().success(false).message("执行失败").data(data).build();
    }

    public static ExecuteResult fail(String message, Object data){
        return ExecuteResult.builder().success(false).message(message).data(data).build();
    }

}
