package plus.easydo.bot.service;


import com.mybatisflex.core.paginate.Page;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.qo.PageQo;

import java.util.List;

/**
 * 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface SimpleDevelopService {

    List<SimpleCmdDevelopConf> listSimpleDevelop();

    Page<SimpleCmdDevelopConf> pageSimpleDevelop(PageQo pageQo);

    SimpleCmdDevelopConf getSimpleDevelopInfo(Long id);

    boolean saveSimpleDevelop(SimpleCmdDevelopConf simpleCmdDevelopConf);

    boolean updateSimpleDevelop(SimpleCmdDevelopConf simpleCmdDevelopConf);

    boolean removeSimpleDevelop(Long id);
}
