package plus.easydo.bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author yuzhanfeng
 * @Date 2023-11-24 10:50
 * @Description 系统配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jcl")
public class SystemConfig {

    private String currentVersion;
    private String mode;
}
