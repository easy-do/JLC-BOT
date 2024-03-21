package plus.easydo.bot.manager;


import plus.easydo.bot.entity.DaBotEventScript;
import plus.easydo.bot.entity.DaBotInfo;
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.entity.DaLowCodeNodeConf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author laoyu
 * @version 1.0
 * @description 系统管理
 * @date 2023/10/12
 */

public class CacheManager {


    /** 所有系统游戏配置缓存 */
    public static final List<SystemConf> GAME_CONF_LIST = new ArrayList<>();
    /** 系统游戏配置Map缓存 */
    public static final Map<String, SystemConf> GAME_CONF_MAP = new HashMap<>();
    /** 机器人缓存 */
    public static final Map<String, DaBotInfo> BOT_CACHE = new HashMap<>();
    /** 机器人配置缓存 */
    public static final Map<String, Map<String,String>> BOT_CONF_CACHE = new HashMap<>();
    /** 事件与脚本对应关系 */
    public static final Map<String, List<Long>> EVENT_SCRIPT_ID_CACHE = new HashMap<>();
    /** 机器人与脚本对应关系 */
    public static final Map<String, List<Long>> BOT_SCRIPT_ID_CACHE = new HashMap<>();
    /** 所有脚本缓存 */
    public static final Map<Long, DaBotEventScript> EVENT_SCRIPT_CACHE = new HashMap<>();
    /** 所有节点配置缓存 */
    public static final Map<Long, DaLowCodeNodeConf> NODE_CONF_CACHE = new HashMap<>();
    /** 所有机器人与节点关联缓存 */
    public static final Map<Long, List<Long>> BOT_NODE_CONF_CACHE = new HashMap<>();

    private CacheManager() {
    }


}
