package plus.easydo.bot.manager;


import plus.easydo.bot.entity.BotEventScript;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.entity.LowCodeNodeConf;

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
    public static final Map<String, BotInfo> BOT_CACHE = new HashMap<>();
    /** 密钥与机器人缓存 */
    public static final Map<String, BotInfo> SECRET_BOT_CACHE = new HashMap<>();
    /** 机器人配置缓存 */
    public static final Map<String, Map<String,String>> BOT_CONF_CACHE = new HashMap<>();
    /** 事件与脚本对应关系 */
    public static final Map<String, List<Long>> EVENT_SCRIPT_ID_CACHE = new HashMap<>();
    /** 机器人与脚本对应关系 */
    public static final Map<String, List<Long>> BOT_SCRIPT_ID_CACHE = new HashMap<>();
    /** 所有脚本缓存 */
    public static final Map<Long, BotEventScript> EVENT_SCRIPT_CACHE = new HashMap<>();
    /** 所有节点配置缓存 */
    public static final Map<Long, LowCodeNodeConf> NODE_CONF_CACHE = new HashMap<>();
    /** 所有机器人与节点关联缓存 */
    public static final Map<Long, List<Long>> BOT_NODE_CONF_CACHE = new HashMap<>();
    public static final Map<String, String> WCF_MESSAGE_TYPE = new HashMap<>();

    static {
        WCF_MESSAGE_TYPE.put("0", "朋友圈消息");
        WCF_MESSAGE_TYPE.put("1", "文字");
        WCF_MESSAGE_TYPE.put("3", "图片");
        WCF_MESSAGE_TYPE.put("34", "语音");
        WCF_MESSAGE_TYPE.put("37", "好友确认");
        WCF_MESSAGE_TYPE.put("40", "POSSIBLEFRIEND_MSG");
        WCF_MESSAGE_TYPE.put("42", "名片");
        WCF_MESSAGE_TYPE.put("43", "视频");
        WCF_MESSAGE_TYPE.put("47", "石头剪刀布 | 表情图片");
        WCF_MESSAGE_TYPE.put("48", "位置");
        WCF_MESSAGE_TYPE.put("49", "共享实时位置、文件、转账、链接");
        WCF_MESSAGE_TYPE.put("50", "VOIPMSG");
        WCF_MESSAGE_TYPE.put("51", "微信初始化");
        WCF_MESSAGE_TYPE.put("52", "VOIPNOTIFY");
        WCF_MESSAGE_TYPE.put("53", "VOIPINVITE");
        WCF_MESSAGE_TYPE.put("62", "小视频");
        WCF_MESSAGE_TYPE.put("66", "微信红包");
        WCF_MESSAGE_TYPE.put("9999", "SYSNOTICE");
        WCF_MESSAGE_TYPE.put("10000", "红包、系统消息");
        WCF_MESSAGE_TYPE.put("10002", "撤回消息");
        WCF_MESSAGE_TYPE.put("1048625", "搜狗表情");
        WCF_MESSAGE_TYPE.put("16777265", "链接");
        WCF_MESSAGE_TYPE.put("436207665", "微信红包");
        WCF_MESSAGE_TYPE.put("536936497", "红包封面");
        WCF_MESSAGE_TYPE.put("754974769", "视频号视频");
        WCF_MESSAGE_TYPE.put("771751985", "视频号名片");
        WCF_MESSAGE_TYPE.put("822083633", "引用消息");
        WCF_MESSAGE_TYPE.put("922746929", "拍一拍");
        WCF_MESSAGE_TYPE.put("973078577", "视频号直播");
        WCF_MESSAGE_TYPE.put("974127153", "商品链接");
        WCF_MESSAGE_TYPE.put("975175729", "视频号直播");
        WCF_MESSAGE_TYPE.put("1040187441", "音乐链接");
        WCF_MESSAGE_TYPE.put("1090519089", "文件");
    }

    private CacheManager() {
    }


}
