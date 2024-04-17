package plus.easydo.bot;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.python.util.PythonInterpreter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;


/**
 * @author yuzhanfeng
 */
@Slf4j
@EnableScheduling
@MapperScan({"plus.easydo.bot.mapper"})
@SpringBootApplication
public class JLCBotApplication {


    public static void main(String[] args) {
        SpringApplication.run(JLCBotApplication.class, args);
//        //初始化jython环境变量 https://www.jython.org/download
        Properties props = new Properties();
        Properties properties = System.getProperties();
        props.put("python.home", "./jython2.7.3");
        PythonInterpreter.initialize (properties, props, new String[]{});
        log.info(
                "服务启动成功," +
                        "\n\t本地访问地址: \t\t{}"
                , "http://localhost:8888"
        );
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            //如果是Windows系统
            RuntimeUtil.exec("cmd /c start " + "http://localhost:8888");
        }

    }

}
