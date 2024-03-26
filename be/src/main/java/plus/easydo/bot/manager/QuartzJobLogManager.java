package plus.easydo.bot.manager;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.job.QuartzJobLog;
import plus.easydo.bot.mapper.QuartzJobLogMapper;

/**
 * @author yuzhanfeng
 * @Date 2024-03-26
 * @Description QuartzJobLogManager
 */
@Component
public class QuartzJobLogManager extends ServiceImpl<QuartzJobLogMapper, QuartzJobLog> {
}
