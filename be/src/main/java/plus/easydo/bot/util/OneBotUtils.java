package plus.easydo.bot.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.entity.BotInfo;
import plus.easydo.bot.entity.SystemConf;
import plus.easydo.bot.enums.onebot.OneBotMessageTypeEnum;
import plus.easydo.bot.exception.BaseException;
import plus.easydo.bot.manager.CacheManager;
import plus.easydo.bot.websocket.model.OneBotMessage;
import plus.easydo.bot.websocket.model.OneBotMessageParse;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description bot工具
 */
public class OneBotUtils {


    private OneBotUtils() {
    }

    public static BotInfo getBotInfo(String botNumber) {
        BotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("机器人[" + botNumber + "]不存在");
        }
        return bot;
    }

    public static BotInfo getBotInfoBySecret(String secret) {
        BotInfo botInfo = CacheManager.SECRET_BOT_CACHE.get(secret);
        if (Objects.isNull(botInfo)) {
            throw new BaseException("机器人不存在");
        }
        return botInfo;
    }

    public static void saveSecretBotCache(Map<String, BotInfo> secretMap) {
        CacheManager.SECRET_BOT_CACHE.putAll(secretMap);
    }

    public static void saveBotNumberBotCache(Map<String, BotInfo> botMap) {
        CacheManager.BOT_CACHE.putAll(botMap);
    }

    public static void cacheSystemConf(List<SystemConf> confList) {
        CacheManager.SYSTEM_CONF_LIST.clear();
        CacheManager.SYSTEM_CONF_LIST.addAll(confList);
        confList.forEach(conf -> CacheManager.SYSTEM_CONF_MAP.put(conf.getConfKey(), conf));
    }


    public static long getPostTime(JSONObject postData) {
        Long time = postData.getLong(OneBotConstants.TIME);
        String timeStr = String.valueOf(time);
        if (timeStr.length() == 10) {
            return time * 1000;
        }
        return time;
    }

    public static void parseMessage(JSONObject postData, Logger log) {
        String message = postData.getStr(OneBotConstants.MESSAGE);
        if (Objects.nonNull(message)) {
            try {
                OneBotMessageParse messageParse = OneBotUtils.parseMessage(message);
                postData.set(OneBotConstants.MESSAGE_PARSE, messageParse);
            } catch (Exception e) {
//                log.warn("parseMessage失败:{}", ExceptionUtil.getMessage(e));
            }
        }
    }

    /**
     * 消息段解析
     *
     * @param message message
     * @return model.onebot.plus.easydo.lowcode.bot.OneBotMessageParse
     * @author laoyu
     * @date 2024/2/25
     */
    public static OneBotMessageParse parseMessage(String message) {
        JSONArray messageArray = JSONUtil.parseArray(message);
        OneBotMessageParse oneBotMessageParse = new OneBotMessageParse();
        oneBotMessageParse.setSourceMessage(message);
        oneBotMessageParse.setSegmentSize(messageArray.size());
        //是个空数组的情况
        if (messageArray.isEmpty()) {
            oneBotMessageParse.setType(OneBotMessageTypeEnum.EMPTY.getType());
            oneBotMessageParse.setSimpleMessage(message);
            return oneBotMessageParse;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < messageArray.size(); i++) {
            Object messageObj = messageArray.get(i);
            String sp = "|<-";
            OneBotMessage oneBotMessage = JSONUtil.toBean(messageObj.toString(), OneBotMessage.class);
            if (oneBotMessage.getType().equals(OneBotMessageTypeEnum.AT.getType())) {
                stringBuilder.append(oneBotMessage.getType()).append("->|").append(oneBotMessage.getData().getMention());
            } else if (oneBotMessage.getType().equals(OneBotMessageTypeEnum.TEXT.getType())) {
                stringBuilder.append(oneBotMessage.getType()).append("->|").append(oneBotMessage.getData().getText());
            } else if (oneBotMessage.getType().equals(OneBotMessageTypeEnum.FACE.getType())) {
                stringBuilder.append(oneBotMessage.getType()).append("->|").append(oneBotMessage.getData().getId());
            } else if (oneBotMessage.getType().equals(OneBotMessageTypeEnum.IMAGE.getType())) {
                stringBuilder.append(oneBotMessage.getType()).append("->|").append(oneBotMessage.getData().getUrl());
            } else {
                stringBuilder.append(oneBotMessage.getType()).append("->|").append(oneBotMessage.getData());
            }
            if (messageArray.size() > 1 && i != messageArray.size() - 1) {
                stringBuilder.append(sp);
            }
        }
        oneBotMessageParse.setParseMessage(stringBuilder.toString());
        //就一个消息段
        if (messageArray.size() == 1) {
            OneBotMessage oneBotMessage = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
            oneBotMessageParse.setSimpleMessage(oneBotMessage.getData().getText());
            oneBotMessageParse.setType(oneBotMessage.getType());
        } else
            //两个的支持解析
            if (messageArray.size() == 2) {
                OneBotMessage oneBotMessage1 = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
                OneBotMessage oneBotMessage2 = JSONUtil.toBean(messageArray.get(1).toString(), OneBotMessage.class);
                if (oneBotMessage1.getType().equals(OneBotMessageTypeEnum.AT.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.TEXT.getType())) {
                    oneBotMessageParse.setAtUser(oneBotMessage1.getData().getQq());
                    oneBotMessageParse.setAtAfterText(oneBotMessage2.getData().getText());
                    oneBotMessageParse.setType(OneBotMessageTypeEnum.AT_TEXT.getType());
                } else if (oneBotMessage1.getType().equals(OneBotMessageTypeEnum.TEXT.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.AT.getType())) {
                    oneBotMessageParse.setAtBeforeText(oneBotMessage1.getData().getText());
                    oneBotMessageParse.setAtUser(oneBotMessage2.getData().getQq());
                    oneBotMessageParse.setType(OneBotMessageTypeEnum.TEXT_AT.getType());
                } else if (oneBotMessage1.getType().equals(OneBotMessageTypeEnum.AT.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.FACE.getType())) {
                    oneBotMessageParse.setAtUser(oneBotMessage1.getData().getQq());
                    oneBotMessageParse.setAtAfterText(oneBotMessage2.getData().getId());
                    oneBotMessageParse.setType(OneBotMessageTypeEnum.AT_FACE.getType());
                } else if (oneBotMessage1.getType().equals(OneBotMessageTypeEnum.FACE.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.AT.getType())) {
                    oneBotMessageParse.setAtBeforeText(oneBotMessage1.getData().getId());
                    oneBotMessageParse.setAtUser(oneBotMessage2.getData().getQq());
                    oneBotMessageParse.setType(OneBotMessageTypeEnum.FACE_AT.getType());
                } else if (oneBotMessage1.getType().equals(OneBotMessageTypeEnum.TEXT.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.IMAGE.getType())) {
                    oneBotMessageParse.setType(OneBotMessageTypeEnum.TEXT_IMAGE.getType());
                } else if (oneBotMessage1.getType().equals(OneBotMessageTypeEnum.IMAGE.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.TEXT.getType())) {
                    oneBotMessageParse.setType(OneBotMessageTypeEnum.IMAGE_TEXT.getType());
                } else {
                    oneBotMessageParse.setType(OneBotMessageTypeEnum.OTHER.getType());
                }
            } else
                //三个的也支持解析
                if (messageArray.size() == 3) {
                    OneBotMessage oneBotMessage1 = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
                    OneBotMessage oneBotMessage2 = JSONUtil.toBean(messageArray.get(1).toString(), OneBotMessage.class);
                    OneBotMessage oneBotMessage3 = JSONUtil.toBean(messageArray.get(2).toString(), OneBotMessage.class);
                    if (oneBotMessage1.getType().equals(OneBotMessageTypeEnum.TEXT.getType())
                            && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.AT.getType())
                            && oneBotMessage3.getType().equals(OneBotMessageTypeEnum.TEXT.getType())) {
                        oneBotMessageParse.setType(OneBotMessageTypeEnum.TEXT_AT_TEXT.getType());
                        oneBotMessageParse.setAtUser(oneBotMessage2.getData().getQq());
                        oneBotMessageParse.setAtBeforeText(oneBotMessage1.getData().getText());
                        oneBotMessageParse.setAtAfterText(oneBotMessage3.getData().getText());
                    } else {
                        oneBotMessageParse.setType(OneBotMessageTypeEnum.OTHER.getType());
                    }
                } else {
                    //其他的
                    oneBotMessageParse.setType(OneBotMessageTypeEnum.OTHER.getType());
                }
        return oneBotMessageParse;
    }

    public static JSONArray buildMessageJson(String message) {
        JSONArray arr = JSONUtil.createArray();
        JSONObject obj = JSONUtil.createObj();
        obj.set("type", "text");
        JSONObject obj1 = JSONUtil.createObj();
        obj1.set("text", message);
        obj.set("data", obj1);
        arr.add(obj);
        return arr;
    }
}
