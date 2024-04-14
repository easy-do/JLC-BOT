package plus.easydo.bot.lowcode.context;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuzhanfeng
 * @Date 2024/4/14
 * @Description 上下文Bean描述注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ContextBeanDesc {

    String value() default "";

    String name() default "";
}
