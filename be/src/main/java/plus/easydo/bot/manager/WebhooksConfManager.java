package plus.easydo.bot.manager;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.bot.entity.WebhooksConf;
import plus.easydo.bot.mapper.WebhooksConfMapper;
import plus.easydo.bot.qo.WebhooksConfQo;

import static plus.easydo.bot.entity.table.WebhooksConfTableDef.WEBHOOKS_CONF;

/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class WebhooksConfManager extends ServiceImpl<WebhooksConfMapper, WebhooksConf> {

    public Page<WebhooksConf> pageWebhooksConf(WebhooksConfQo webhooksConfQo) {
        QueryWrapper queryWrapper = query();
        queryWrapper.where(WEBHOOKS_CONF.CONF_NAME.eq(webhooksConfQo.getId()));
        queryWrapper.where(WEBHOOKS_CONF.REMARK.eq(webhooksConfQo.getRemark()));
        return page(new Page<>(webhooksConfQo.getCurrent(), webhooksConfQo.getPageSize()), queryWrapper);
    }
}
