package plus.easydo.bot.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.enums.onebot.OneBotMessageTypeEnum;
import plus.easydo.bot.websocket.model.OneBotMessage;
import plus.easydo.bot.websocket.model.OneBotMessageParse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 消息解析工具类
 * @date 2024/4/5
 */

public class MessageParseUtil {


    private static final String CQ_PRE = "CQ:";
    /**
     * &=&amp;
     */
    private static final String AND = "&";
    private static final String AND_ESCAPE = "&amp;";
    /**
     * [=&#91;
     */
    private static final String L_BRACKET = "[";
    private static final String L_BRACKET_ESCAPE = "&#91;";
    /**
     * ]=&#93
     */
    private static final String R_BRACKET = "]";
    private static final String R_BRACKET_ESCAPE = "&#93;";
    /**
     * ,=&#44
     */
    private static final String COMMA = ",";
    private static final String COMMA_ESCAPE = "&#44;";
    private static final String UNDERLINE = "_";

    private MessageParseUtil() {
    }


    public static void parseMessage(JSONObject postData) {
        String message = postData.getStr(OneBotConstants.MESSAGE);
        if (Objects.nonNull(message)) {
            try {
                OneBotMessageParse messageParse = parseMessage(message);
                postData.set(OneBotConstants.MESSAGE_PARSE, messageParse);
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    public static OneBotMessageParse parseMessage(String message) {
        try {
            JSONArray messageArray = JSONUtil.parseArray(message);
            return parseArrayMessage(messageArray);
        } catch (Exception e) {
            //尝试解析CQ码
            return parseCqMessage(message);
        }
    }

    /**
     * 解析CQ码消息结构
     *
     * @param message message
     * @return plus.easydo.bot.websocket.model.OneBotMessageParse
     * @author laoyu
     * @date 2024/4/5
     */
    private static OneBotMessageParse parseCqMessage(String message) {
        JSONArray array = cqMessageToArrayMessage(message);
        return parseArrayMessage(array);
    }

    public static JSONArray cqMessageToArrayMessage(String cqMessage) {
        JSONArray result = JSONUtil.createArray();
        //解码转义
        String currentMessage = unescape(cqMessage);
        //判断是否存在CQ码
        if (currentMessage.contains(L_BRACKET) && currentMessage.contains(R_BRACKET)) {
            do {
                //判断是否CQ码开头
                if (currentMessage.startsWith(L_BRACKET) && currentMessage.contains(R_BRACKET)) {
                    //截取当前消息段
                    String messageSegments = CharSequenceUtil.subBefore(currentMessage, R_BRACKET, false);
                    //取出中间的内容
                    String centerText = CharSequenceUtil.subAfter(messageSegments, L_BRACKET, false);
                    //切割出所有参数
                    List<String> centerTexts = CharSequenceUtil.split(centerText, COMMA);
                    //至少要有两个参数才是一个合法的CQ消息
                    if (centerTexts.size() > 1) {
                        String cqType = CharSequenceUtil.subAfter(centerTexts.get(0), CQ_PRE, false);
                        JSONObject oneBotMessage = JSONUtil.createObj();
                        oneBotMessage.set(OneBotConstants.TYPE, cqType);
                        JSONObject data = JSONUtil.createObj();
                        for (int i = 1; i < centerTexts.size(); i++) {
                            String itext = centerTexts.get(i);
                            String key = CharSequenceUtil.subBefore(itext, "=", false);
                            String value = CharSequenceUtil.subAfter(itext, "=", false);
                            data.set(key, value);
                        }
                        oneBotMessage.set(OneBotConstants.DATA, data);
                        result.add(oneBotMessage);
                        currentMessage = CharSequenceUtil.replace(currentMessage, messageSegments + R_BRACKET, CharSequenceUtil.EMPTY);
                    } else {
                        //因为解析CQ发现不合法,直接返回整体是个文本消息
                        //不存在CQ码，直接整个返回
                        result.add(buildTextMessage(cqMessage));
                        return result;
                    }
                } else {
                    //不是CQ码开头则认为当前有一段文字,截取并解析文字
                    String messageSegments = CharSequenceUtil.subBefore(currentMessage, L_BRACKET, false);
                    JSONObject oneBotMessage = JSONUtil.createObj();
                    oneBotMessage.set(OneBotConstants.TYPE, OneBotMessageTypeEnum.TEXT.getType());
                    JSONObject data = JSONUtil.createObj();
                    data.set(OneBotMessageTypeEnum.TEXT.getType(), escape(messageSegments));
                    oneBotMessage.set(OneBotConstants.DATA, data);
                    result.add(oneBotMessage);
                    //截取完当前文字段后，将当前消息段替换掉
                    currentMessage = CharSequenceUtil.replace(currentMessage, messageSegments, CharSequenceUtil.EMPTY);
                }
            } while (CharSequenceUtil.isNotBlank(currentMessage));
        } else {
            //不存在CQ码，直接整个返回
            result.add(buildTextMessage(cqMessage));
            return result;
        }
        return result;
    }

    private static JSONObject buildTextMessage(String cqMessage) {
        JSONObject oneBotMessage = JSONUtil.createObj();
        oneBotMessage.set(OneBotConstants.TYPE, OneBotMessageTypeEnum.TEXT.getType());
        JSONObject data = JSONUtil.createObj();
        data.set(OneBotMessageTypeEnum.TEXT.getType(), cqMessage);
        oneBotMessage.set(OneBotConstants.DATA, data);
        return oneBotMessage;
    }

    /**
     * 解码转义
     *
     * @param message message
     * @return java.lang.String
     * @author laoyu
     * @date 2024/4/5
     */
    public static String unescape(String message) {
        message = CharSequenceUtil.replace(message, L_BRACKET_ESCAPE, L_BRACKET);
        message = CharSequenceUtil.replace(message, R_BRACKET_ESCAPE, R_BRACKET);
        message = CharSequenceUtil.replace(message, COMMA_ESCAPE, COMMA);
        message = CharSequenceUtil.replace(message, AND, AND_ESCAPE);
        return message;
    }

    /**
     * 转义
     *
     * @param message message
     * @return java.lang.String
     * @author laoyu
     * @date 2024/4/5
     */
    public static String escape(String message) {
        message = CharSequenceUtil.replace(message, L_BRACKET, L_BRACKET_ESCAPE);
        message = CharSequenceUtil.replace(message, R_BRACKET, R_BRACKET_ESCAPE);
        message = CharSequenceUtil.replace(message, COMMA, COMMA_ESCAPE);
        message = CharSequenceUtil.replace(message, AND_ESCAPE, AND);
        return message;
    }


    /**
     * 解析数组类型的消息
     *
     * @param messageArray messageArray
     * @return plus.easydo.bot.websocket.model.OneBotMessageParse
     * @author laoyu
     * @date 2024/4/5
     */
    public static OneBotMessageParse parseArrayMessage(JSONArray messageArray) {
        OneBotMessageParse oneBotMessageParse = new OneBotMessageParse();
        oneBotMessageParse.setSegmentSize(messageArray.size());
        //是个空数组的情况
        if (messageArray.isEmpty()) {
            oneBotMessageParse.setType(OneBotMessageTypeEnum.EMPTY.getType());
            return oneBotMessageParse;
        }
        StringBuilder parseType = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < messageArray.size(); i++) {
            Object messageObj = messageArray.get(i);
            JSONObject oneBotMessage = JSONUtil.parseObj(messageObj);
            String type = oneBotMessage.getStr(OneBotConstants.TYPE);
            parseType.append(i > 0 ? UNDERLINE : "").append(type);
            JSONObject data = oneBotMessage.getJSONObject(OneBotConstants.DATA);
            stringBuilder.append(L_BRACKET).append(CQ_PRE).append(type).append(COMMA);
            HashMap<String, Object> map = data.toBean(HashMap.class);
            int count = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                if (count > 0) {
                    stringBuilder.append(COMMA);
                }
                count++;
            }
            stringBuilder.append(R_BRACKET);
        }
        oneBotMessageParse.setParseMessage(stringBuilder.toString());
        oneBotMessageParse.setType(parseType.toString());
        oneBotMessageParse.setSegmentSize(messageArray.size());
        //就一个消息段
        if (messageArray.size() == 1) {
            if (CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.TEXT.getType())) {
                OneBotMessage oneBotMessage = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
                oneBotMessageParse.setSimpleMessage(oneBotMessage.getData().getText());
            } else if (CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.IMAGE.getType())) {
                OneBotMessage oneBotMessage = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
                oneBotMessageParse.setFile(oneBotMessage.getData().getUrl());
            }
        } else
            //两个的支持解析
            if (messageArray.size() == 2) {
                OneBotMessage oneBotMessage1 = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
                OneBotMessage oneBotMessage2 = JSONUtil.toBean(messageArray.get(1).toString(), OneBotMessage.class);
                if (CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.AT_TEXT.getType())) {
                    oneBotMessageParse.setAtUser(oneBotMessage1.getData().getQq());
                    oneBotMessageParse.setAfterText(oneBotMessage2.getData().getText());
                } else if (CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.TEXT_AT.getType())) {
                    oneBotMessageParse.setBeforeText(oneBotMessage1.getData().getText());
                    oneBotMessageParse.setAtUser(oneBotMessage2.getData().getQq());
                } else if (CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.AT_FACE.getType())) {
                    oneBotMessageParse.setAtUser(oneBotMessage1.getData().getQq());
                    oneBotMessageParse.setAfterText(oneBotMessage2.getData().getId());
                } else if (CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.FACE_AT.getType())) {
                    oneBotMessageParse.setBeforeText(oneBotMessage1.getData().getId());
                    oneBotMessageParse.setAtUser(oneBotMessage2.getData().getQq());
                } else if (CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.TEXT_IMAGE.getType())) {
                    oneBotMessageParse.setBeforeText(oneBotMessage1.getData().getText());
                    oneBotMessageParse.setFile(oneBotMessage2.getData().getUrl());
                } else if (CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.IMAGE_TEXT.getType())) {
                    oneBotMessageParse.setFile(oneBotMessage1.getData().getUrl());
                    oneBotMessageParse.setAfterText(oneBotMessage2.getData().getText());
                }
            } else
                //三个解析
                if (messageArray.size() == 3) {
                    OneBotMessage oneBotMessage1 = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
                    OneBotMessage oneBotMessage2 = JSONUtil.toBean(messageArray.get(1).toString(), OneBotMessage.class);
                    OneBotMessage oneBotMessage3 = JSONUtil.toBean(messageArray.get(2).toString(), OneBotMessage.class);
                    if (CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.TEXT_AT_TEXT.getType())) {
                        oneBotMessageParse.setAtUser(oneBotMessage2.getData().getQq());
                        oneBotMessageParse.setBeforeText(oneBotMessage1.getData().getText());
                        oneBotMessageParse.setAfterText(oneBotMessage3.getData().getText());
                    }
                }
        return oneBotMessageParse;
    }

}
