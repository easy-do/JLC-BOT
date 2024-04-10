package plus.easydo.bot.lowcode.node;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.util.BotConfUtil;
import plus.easydo.bot.util.ParamReplaceUtils;

import java.util.List;
import java.util.Objects;

/**
 * 字段判断节点
 *
 * @author laoyu
 * @date 2024-03-27
 */
@Slf4j
@LiteflowComponent(id = "botConfNode", name = "机器人配置")
public class BotConfNode extends NodeComponent {

    @Override
    public void process() {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        JSONObject paramJson = context.getParam();
        JSONObject nodeConf = context.getNodeConf();
        String tag = getTag();
        JSONObject confJson = nodeConf.getJSONObject(tag);
        log.debug("保存机器人配置节点: param:{},conf:{}", paramJson, confJson);
        try {
            Assert.notNull(paramJson, "参数配置为空");
            Assert.notNull(confJson, "节点配置为空");
            String botNumber = paramJson.getStr(OneBotConstants.SELF_ID);
            String confKey = confJson.getStr("confKey");
            String confValue = confJson.getStr("confValue");
            String type = confJson.getStr("type");
            Assert.notNull(confKey, "机器人配置key为空");
            Assert.notNull(confValue, "机器人配置参数为空");
            Assert.notNull(type, "处理类型为空");
            String dbConfValue = BotConfUtil.getBotConfNull(botNumber, confKey);
            confValue = ParamReplaceUtils.replaceParam(confValue, paramJson);
            switch (type) {
                case "override" -> BotConfUtil.saveBotConf(botNumber, confKey, confValue);
                case "noOverride" -> {
                    if (Objects.isNull(dbConfValue)) {
                        BotConfUtil.saveBotConf(botNumber, confKey, confValue);
                    }
                }
                case "remove" -> BotConfUtil.removeBotConf(botNumber, confKey);
                case "subAppend" -> {
                    if (Objects.isNull(dbConfValue)) {
                        BotConfUtil.saveBotConf(botNumber, confKey, confValue);
                    } else {
                        BotConfUtil.saveBotConf(botNumber, confKey, confValue + dbConfValue);
                    }
                }
                case "afterAppend" -> {
                    if (Objects.isNull(dbConfValue)) {
                        BotConfUtil.saveBotConf(botNumber, confKey, confValue);
                    } else {
                        BotConfUtil.saveBotConf(botNumber, confKey, dbConfValue + confValue);
                    }
                }
                case "toListAdd" -> {
                    if (Objects.nonNull(dbConfValue)) {
                        List<String> list = CharSequenceUtil.split(dbConfValue, ",");
                        list.add(confValue);
                        BotConfUtil.saveBotConf(botNumber, confKey, CharSequenceUtil.join(",", list));
                    } else {
                        BotConfUtil.saveBotConf(botNumber, confKey, confValue);
                    }
                }
                case "toListRemove" -> {
                    if (Objects.nonNull(dbConfValue)) {
                        List<String> list = CharSequenceUtil.split(dbConfValue, ",");
                        list.remove(confValue);
                        BotConfUtil.saveBotConf(botNumber, confKey, CharSequenceUtil.join(",", list));
                    }
                }
                default -> throw new BaseException("没有匹配到类型[" + type + "]");
            }

            boolean res = BotConfUtil.saveBotConf(botNumber, confKey, confValue);

            if (!res) {
                throw new BaseException("配置信息入库失败。");
            }
        } catch (Exception e) {
            log.warn("保存机器人配置节点未完整执行,原因:{}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void onSuccess() throws Exception {
        JLCLiteFlowContext context = getContextBean(JLCLiteFlowContext.class);
        context.getNodeParamCache().put(getTag(), context.getParam());
    }

}
