package plus.easydo.bot.service.impl;


import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.bot.constant.LowCodeConstants;
import plus.easydo.bot.dto.DebugDto;
import plus.easydo.bot.entity.LiteFlowScript;
import plus.easydo.bot.entity.WebhooksConf;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.lowcode.execute.WebhooksExecuteServer;
import plus.easydo.bot.lowcode.model.CmpStepResult;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.manager.LiteFlowScriptManager;
import plus.easydo.bot.manager.WebhooksConfManager;
import plus.easydo.bot.qo.WebhooksConfQo;
import plus.easydo.bot.service.WebhooksConfService;
import plus.easydo.bot.util.LiteFlowUtils;

import java.util.List;
import java.util.Objects;

/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class WebhooksConfServiceImpl implements WebhooksConfService {

    private final WebhooksConfManager webhooksConfManager;

    private final LiteFlowScriptManager liteFlowScriptManager;

    private final WebhooksExecuteServer webhooksExecuteServer;

    @Override
    public boolean saveWebhooksConf(WebhooksConf webhooksConf) {
        boolean res = webhooksConfManager.save(webhooksConf);
        if (res) {
            try {
                liteFlowScriptManager.createData(webhooksConf);
                reloadConf(webhooksConf.getId());
            } catch (Exception e) {
                webhooksConfManager.removeById(webhooksConf.getId());
                throw new BaseException("创建脚本失败:" + e.getMessage());
            }
        }
        return res;

    }

    @Override
    public boolean removeWebhooksConf(Long id) {
        boolean res = webhooksConfManager.removeById(id);
        if (res) {
            liteFlowScriptManager.removeByScriptId(LowCodeConstants.WEBHOOKS + id);
            initCache();
        }
        return res;
    }

    @Override
    public boolean updateWebhooksConf(WebhooksConf webhooksConf) {
        WebhooksConf old = webhooksConfManager.getById(webhooksConf.getId());
        boolean res = webhooksConfManager.updateById(webhooksConf);
        if (res) {
            LiteFlowScript liteFlowScript = liteFlowScriptManager.getByScriptId(LowCodeConstants.WEBHOOKS + webhooksConf.getId());
            liteFlowScript.setScriptLanguage(webhooksConf.getScriptLanguage());
            liteFlowScript.setScriptName(webhooksConf.getConfName());
            try {
                liteFlowScriptManager.updateScriptData(liteFlowScript);
                reloadConf(webhooksConf.getId());
            } catch (Exception e) {
                webhooksConfManager.updateById(old);
                throw new BaseException("更新脚本失败:" + e.getMessage());
            }
        }
        return res;
    }

    @Override
    public List<WebhooksConf> listWebhooksConf() {
        return webhooksConfManager.list();
    }

    @Override
    public WebhooksConf getWebhooksConfInfo(Long id) {
        WebhooksConf res = webhooksConfManager.getById(id);
        if (Objects.nonNull(res)) {
            LiteFlowScript script = liteFlowScriptManager.getByScriptId(LowCodeConstants.WEBHOOKS + res.getId());
            Assert.notNull(script, "脚本不存在");
            res.setScript(script);
            res.setScriptLanguage(script.getScriptLanguage());
        }
        return res;
    }

    @Override
    public Page<WebhooksConf> pageWebhooksConf(WebhooksConfQo webhooksConfQo) {
        return webhooksConfManager.pageWebhooksConf(webhooksConfQo);
    }

    @Override
    public CmpStepResult debug(DebugDto debugDto) {
        WebhooksConf webhooksConf = getWebhooksConfInfo(debugDto.getId());
        JSONObject paramsJson = JSONUtil.parseObj(debugDto.getParams());
        return LiteFlowUtils.getCmpStepResult(paramsJson, webhooksExecuteServer.execute(webhooksConf, paramsJson));
    }

    @Override
    public void execute(WebhooksConf webhooksConf, JSONObject paramsJson) {
        webhooksExecuteServer.execute(webhooksConf, paramsJson);
    }

    @Override
    public Long importConf(WebhooksConf conf) {
        conf.setId(null);
        boolean res = webhooksConfManager.save(conf);
        if (res) {
            try {
                LiteFlowScript newScript = liteFlowScriptManager.createData(conf);
                newScript.setScriptData(conf.getScript().getScriptData());
                liteFlowScriptManager.updateScriptData(newScript);
                initCache();
            }catch (Exception e){
                webhooksConfManager.removeById(conf.getId());
                throw new BaseException("创建脚本失败:"+e.getMessage());
            }
        }
        return conf.getId();
    }


    @PostConstruct
    private void initCache() {
        List<WebhooksConf> list = webhooksConfManager.list();
        for (WebhooksConf conf : list) {
            LiteFlowScript script = liteFlowScriptManager.getByScriptId(LowCodeConstants.WEBHOOKS + conf.getId());
            Assert.notNull(script, "脚本不存在");
            conf.setScriptLanguage(script.getScriptLanguage());
            conf.setScript(script);
        }
        CacheManager.WEBHOOKS_CONF_CACHE.clear();
        list.forEach(webhooksConf -> CacheManager.WEBHOOKS_CONF_CACHE.put(webhooksConf.getId(), webhooksConf));
    }

    private void reloadConf(Long id) {
        WebhooksConf webhooksConf = getWebhooksConfInfo(id);
        CacheManager.WEBHOOKS_CONF_CACHE.put(webhooksConf.getId(), webhooksConf);
    }
}
