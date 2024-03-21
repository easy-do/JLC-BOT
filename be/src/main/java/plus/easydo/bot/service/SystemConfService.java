package plus.easydo.bot.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.qo.DaGameConfigQo;

/**
 * 游戏配置 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface SystemConfService extends IService<SystemConf> {

    Page<SystemConf> confPage(DaGameConfigQo gameConfigQo);

    boolean saveConf(SystemConf systemConf);

    boolean updateConf(SystemConf systemConf);

    SystemConf getByConfKey(String confKey);

    void cacheGameConf();
}
