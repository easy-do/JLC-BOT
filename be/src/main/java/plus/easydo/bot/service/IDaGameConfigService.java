package plus.easydo.bot.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import plus.easydo.bot.entity.DaGameConfig;
import plus.easydo.bot.qo.DaGameConfigQo;

/**
 * 游戏配置 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaGameConfigService extends IService<DaGameConfig> {

    Page<DaGameConfig> confPage(DaGameConfigQo gameConfigQo);

    boolean saveConf(DaGameConfig daGameConfig);

    boolean updateConf(DaGameConfig daGameConfig);

    DaGameConfig getByConfKey(String confKey);

    void cacheGameConf();
}
