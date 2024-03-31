package plus.easydo.bot.service.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.mapper.GameConfigMapper;
import plus.easydo.bot.qo.SystemConfigQo;
import plus.easydo.bot.service.SystemConfService;
import plus.easydo.bot.util.OneBotUtils;

import java.util.List;

import static plus.easydo.bot.entity.table.SystemConfTableDef.SYSTEM_CONF;


/**
 * 游戏配置 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemConfServiceImpl extends ServiceImpl<GameConfigMapper, SystemConf> implements SystemConfService {


    @Override
    public Page<SystemConf> confPage(SystemConfigQo gameConfigQo) {
        QueryWrapper query = query()
                .and(SYSTEM_CONF.CONF_NAME.like(gameConfigQo.getConfName())
                        .and(SYSTEM_CONF.CONF_KEY.like(gameConfigQo.getConfKey())));
        return page(new Page<>(gameConfigQo.getCurrent(), gameConfigQo.getPageSize()), query);
    }

    @Override
    public boolean saveConf(SystemConf systemConf) {
        boolean result = save(systemConf);
        if (result) {
            cacheSystemConf();
        }
        return result;
    }

    @Override
    public boolean updateConf(SystemConf systemConf) {
        boolean result = updateById(systemConf);
        if (result) {
            cacheSystemConf();
        }
        return result;
    }

    @Override
    public SystemConf getByConfKey(String confKey) {
        QueryWrapper query = query().and(SYSTEM_CONF.CONF_KEY.eq(confKey));
        return getOne(query);
    }


    @Override
    public void cacheSystemConf() {
        log.info("初始化缓存游戏配置 start");
        List<SystemConf> confList = list();
        OneBotUtils.cacheSystemConf(confList);
        log.info("初始化缓存游戏配置 end");
    }
}
