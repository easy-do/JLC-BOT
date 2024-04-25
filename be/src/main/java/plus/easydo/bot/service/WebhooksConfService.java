package plus.easydo.bot.service;


import cn.hutool.json.JSONObject;
import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.entity.WebhooksConf;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.qo.WebhooksConfQo;

import java.util.List;

/**
 * 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface WebhooksConfService {

    boolean saveWebhooksConf(WebhooksConf webhooksConf);

    boolean removeWebhooksConf(Long id);

    boolean updateWebhooksConf(WebhooksConf webhooksConf);

    List<WebhooksConf> listWebhooksConf();

    WebhooksConf getWebhooksConfInfo(Long id);

    Page<WebhooksConf> pageWebhooksConf(WebhooksConfQo webhooksConfQo);

    CmpStepResult debug(DebugDto debugDto);

    void execute(WebhooksConf webhooksConf, JSONObject paramsJson);
}
