package plus.easydo.bot.lowcode.context;

import com.yomahub.liteflow.context.ContextBean;
import lombok.Data;
import plus.easydo.bot.util.BotConfUtil;

import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2024/4/16
 * @Description 机器人配置api上下文
 */
@Data
@ContextBean("botConfApi")
@ContextBeanDesc("机器人配置")
public class BotConfApiContext {

    private String botNumber;


    public BotConfApiContext(String botNumber) {
        this.botNumber = botNumber;
    }


    /**
     * 获得包含机器人所有配置信息的Map对象，键为配置项名称，值为配置项的值
     *
     * @return 包含机器人所有配置信息的Map对象
     */
    @ContextBeanMethodDesc("获得包含机器人所有配置信息的Map对象，键为配置项名称，值为配置项的值")
    public Map<String, String> getAllBotConf() {
        return BotConfUtil.getAllBotConf(botNumber);
    }

    /**
     * 根据配置项的键获取机器人的配置信息
     *
     * @param key 配置项的键
     * @return 返回与键对应的具体配置信息
     */
    @ContextBeanMethodDesc("返回与键对应的具体配置信息")
    public String getBotConf(String key) {
        return BotConfUtil.getBotConf(botNumber, key);
    }

    /**
     * 获取机器人的指定配置项信息，若该配置项不存在则返回空字符串
     *
     * @param key 配置项的键
     * @return 返回与键对应的具体配置信息，若该配置项不存在则返回空字符串
     */
    @ContextBeanMethodDesc("返回与键对应的具体配置信息，若该配置项不存在则返回空字符串")
    public String getBotConfNull(String key) {
        return BotConfUtil.getBotConf(botNumber, key);
    }

    /**
     * 保存机器人的指定配置项信息
     *
     * @param key   配置项的键
     * @param value 配置项的值
     * @return 如果保存成功返回true，否则返回false
     */
    @ContextBeanMethodDesc("保存机器人的指定配置项信息")
    public boolean saveBotConf(String key, String value) {
        return BotConfUtil.saveBotConf(botNumber, key, value);
    }

    /**
     * 保存机器人的指定配置项信息，并附带备注信息
     *
     * @param key     配置项的键
     * @param value   配置项的值
     * @param remark  备注信息
     * @return 如果保存成功返回true，否则返回false
     */
    @ContextBeanMethodDesc("保存指定机器人的指定配置项信息，并附带备注信息")
    public boolean saveBotConf(String key, String value, String remark) {
        return BotConfUtil.saveBotConf(botNumber, key, value, remark);
    }

    /**
     * 更新机器人的指定配置项信息
     *
     * @param key   配置项的键
     * @param value 配置项的新值
     * @return 如果更新成功返回true，否则返回false
     */
    @ContextBeanMethodDesc("更新机器人的指定配置项信息")
    public boolean updateBotConf(String key, String value) {
        return BotConfUtil.updateBotConf(botNumber, key, value);
    }

    /**
     * 从指定机器人的配置中删除指定配置项
     *
     * @param key 要删除的配置项的键
     * @return 如果删除成功返回true，否则返回false
     */
    @ContextBeanMethodDesc("删除指定配置项")
    public boolean removeBotConf(String key) {
        return BotConfUtil.removeBotConf(botNumber, key);
    }

    /**
     * 从指定机器人的配置中删除与给定键匹配的所有配置项
     *
     * @param key 用于匹配的配置项键
     * @return 如果删除成功返回true，否则返回false
     */
    @ContextBeanMethodDesc("删除与给定键匹配的所有配置项")
    public boolean removeBotConfLike(String key) {
        return BotConfUtil.removeBotConfLike(botNumber, key);
    }
}
