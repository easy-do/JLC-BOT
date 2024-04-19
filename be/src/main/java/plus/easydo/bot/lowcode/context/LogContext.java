package plus.easydo.bot.lowcode.context;

import com.yomahub.liteflow.context.ContextBean;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.lowcode.model.CmpLog;

/**
 * @author yuzhanfeng
 * @Date 2024/4/17
 * @Description 日志工具
 */
@ContextBean("logUtil")
@ContextBeanDesc("日志工具")
@Slf4j
public class LogContext {
    
    private final JLCLiteFlowContext jlcLiteFlowContext;

    private static final String LOG_CONTEXT = "{}";

    public LogContext(JLCLiteFlowContext jlcLiteFlowContext){
        this.jlcLiteFlowContext = jlcLiteFlowContext;
    }

    @ContextBeanMethodDesc("打印调试日志")
    public void debug(Object obj){
        log.debug(LOG_CONTEXT,obj);
        jlcLiteFlowContext.getLogCache().add(CmpLog.builder().type("debug").context(String.valueOf(obj)).build());
    }

    @ContextBeanMethodDesc("打印详情日志")
    public void info(Object obj){
        log.info(LOG_CONTEXT,obj);
        jlcLiteFlowContext.getLogCache().add(CmpLog.builder().type("info").context(String.valueOf(obj)).build());
    }

    @ContextBeanMethodDesc("打印警告日志")
    public void warn(Object obj){
        log.warn(LOG_CONTEXT,obj);
        jlcLiteFlowContext.getLogCache().add(CmpLog.builder().type("warn").context(String.valueOf(obj)).build());
    }

    @ContextBeanMethodDesc("打印异常日志")
    public void error(Object obj){
        log.error(LOG_CONTEXT,obj);
        jlcLiteFlowContext.getLogCache().add(CmpLog.builder().type("error").context(String.valueOf(obj)).build());
    }

}
