package plus.easydo.bot.manager;


import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.HighLevelDevelopConf;
import plus.easydo.bot.entity.LowCodeNodeConf;
import plus.easydo.bot.entity.SimpleCmdDevelopConf;
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.entity.WebhooksConf;

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


    /**
     * 所有系统游戏配置缓存
     */
    public static final List<SystemConf> SYSTEM_CONF_LIST = new ArrayList<>();
    /**
     * 系统游戏配置Map缓存
     */
    public static final Map<String, SystemConf> SYSTEM_CONF_MAP = new HashMap<>();
    /**
     * 机器人number缓存
     */
    public static final Map<String, Long> BOT_NUMBER_CACHE = new HashMap<>();
    /**
     * 密钥与机器人缓存
     */
    public static final Map<String, Long> BOT_SECRET_CACHE = new HashMap<>();
    /**
     * 机器人id缓存
     */
    public static final Map<Long, BotInfo> BOT_CACHE = new HashMap<>();
    /**
     * 机器人配置缓存
     */
    public static final Map<String, Map<String, String>> BOT_CONF_CACHE = new HashMap<>();
    /**
     * 所有节点配置缓存
     */
    public static final Map<Long, LowCodeNodeConf> NODE_CONF_CACHE = new HashMap<>();
    /**
     * 所有机器人与节点关联缓存
     */
    public static final Map<Long, List<Long>> BOT_NODE_CONF_CACHE = new HashMap<>();

    /**
     * 所有简单指令缓存
     */
    public static final Map<Long, SimpleCmdDevelopConf> SIMPLE_CMD_DEV_CONF_CACHE = new HashMap<>();
    /**
     * 机器人与简单指令关联关系缓存
     */
    public static final Map<Long, List<Long>> BOT_SIMPLE_CMD_DEV_CONF_CACHE = new HashMap<>();

    /**
     * 所有高级开发缓存
     */
    public static final Map<Long, HighLevelDevelopConf> HIGH_LEVEL_DEV_CONF_CACHE = new HashMap<>();
    /**
     * 机器人与高级开发关联关系缓存
     */
    public static final Map<Long, List<Long>> BOT_HIGH_LEVEL_DEV_CONF_CACHE = new HashMap<>();
    /**
     * webhook配置缓存
     */
    public static final Map<Long, WebhooksConf> WEBHOOKS_CONF_CACHE = new HashMap<>();

    private CacheManager() {
    }


}
