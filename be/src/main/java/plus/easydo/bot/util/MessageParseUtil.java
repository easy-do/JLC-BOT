package plus.easydo.bot.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import plus.easydo.bot.constant.OneBotConstants;
import plus.easydo.bot.enums.onebot.OneBotMessageTypeEnum;
import plus.easydo.bot.websocket.model.OneBotMessage;
import plus.easydo.bot.websocket.model.OneBotMessageParse;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author laoyu
 * @version 1.0
 * @description 消息解析工具类
 * @date 2024/4/5
 */

public class MessageParseUtil {


    private static final String CENTER_TEXT = "\\[(.*?)\\]";
    //
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
//                log.warn("parseMessage失败:{}", ExceptionUtil.getMessage(e));
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
        OneBotMessageParse oneBotMessageParse = new OneBotMessageParse();
        int deep = 0;
        StringBuilder typeParse = new StringBuilder();
        String currentMessage = message;
        do {
            if (currentMessage.startsWith(L_BRACKET) && currentMessage.endsWith(R_BRACKET)) {
                //截取当前消息段
                String messageSegments = CharSequenceUtil.subBefore(currentMessage, R_BRACKET, false);
                //取出中间的内容
                List<String> group = ReUtil.findAllGroup0(CENTER_TEXT, messageSegments);
                String centerText = group.get(0);
                //切割出所有参数
                List<String> centerTexts = CharSequenceUtil.split(centerText, COMMA);
                //至少要有两个参数才是一个合法的CQ消息
                if (centerTexts.size() > 1) {
                    String cqText = CharSequenceUtil.subBefore(centerText, COMMA, false);
                    String cqType = CharSequenceUtil.subBefore(cqText, CQ_PRE, false);
                    typeParse.append(deep > 0 ? UNDERLINE : CharSequenceUtil.EMPTY).append(cqType);
                    currentMessage = CharSequenceUtil.replace(currentMessage, messageSegments, "");
                } else {
                    oneBotMessageParse.setSegmentSize(1);
                    oneBotMessageParse.setType(OneBotMessageTypeEnum.TEXT.getType());
                    oneBotMessageParse.setSimpleMessage(unescape(message));
                    oneBotMessageParse.setParseMessage(unescape(message));
                    return oneBotMessageParse;
                }
                deep++;
            } else {
                String messageSegments = CharSequenceUtil.subBefore(currentMessage, "[", false);
                typeParse.append(deep > 0 ? UNDERLINE : CharSequenceUtil.EMPTY).append(OneBotMessageTypeEnum.TEXT.getType());
                currentMessage = CharSequenceUtil.replace(currentMessage, messageSegments, "");
                deep++;
            }
        } while (CharSequenceUtil.isNotBlank(currentMessage));
        oneBotMessageParse.setSegmentSize(deep);
        oneBotMessageParse.setType(typeParse.toString());
        oneBotMessageParse.setSimpleMessage(deep > 1 ? unescape(message) : null);
        oneBotMessageParse.setParseMessage(deep > 1 ? unescape(message) : null);
        return oneBotMessageParse;
    }

    /**
     * 解码转义
     *
     * @param message message
     * @return java.lang.String
     * @author laoyu
     * @date 2024/4/5
     */
    private static String unescape(String message) {
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
    private static String escape(String message) {
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
            parseType.append(i > 0 ? UNDERLINE : type);
            JSONObject data = oneBotMessage.getJSONObject(OneBotConstants.DATA);
            stringBuilder.append(L_BRACKET).append(CQ_PRE).append(type).append(COMMA);
            AtomicInteger atomicInteger = new AtomicInteger();
            data.forEach((key, value) -> {
                int count = atomicInteger.getAndIncrement();
                stringBuilder.append(key).append(count > 0 ? "=" : "").append(value).append(COMMA);
            });
            stringBuilder.append(R_BRACKET);
        }
        oneBotMessageParse.setParseMessage(stringBuilder.toString());
        oneBotMessageParse.setType(parseType.toString());
        //就一个消息段
        if (messageArray.size() == 1 && CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.TEXT.getType())) {
            OneBotMessage oneBotMessage = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
            oneBotMessageParse.setSimpleMessage(oneBotMessage.getData().getText());
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
                    oneBotMessageParse.setFile(oneBotMessage2.getData().getFile());
                } else if (CharSequenceUtil.equals(oneBotMessageParse.getType(), OneBotMessageTypeEnum.IMAGE_TEXT.getType())) {
                    oneBotMessageParse.setFile(oneBotMessage1.getData().getFile());
                    oneBotMessageParse.setAfterText(oneBotMessage2.getData().getText());
                } else {
                    oneBotMessageParse.setType(OneBotMessageTypeEnum.OTHER.getType());
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
                    } else {
                        oneBotMessageParse.setType(OneBotMessageTypeEnum.OTHER.getType());
                    }
                }
        return oneBotMessageParse;
    }

}
