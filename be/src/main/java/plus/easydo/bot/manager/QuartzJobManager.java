package plus.easydo.bot.manager;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.job.QuartzJob;
import plus.easydo.bot.mapper.QuartzJobMapper;

/**
 * @author yuzhanfeng
 * @Date 2024-03-26
 * @Description QuartzJobManager
 */
@Component
public class QuartzJobManager extends ServiceImpl<QuartzJobMapper, QuartzJob> {
}
