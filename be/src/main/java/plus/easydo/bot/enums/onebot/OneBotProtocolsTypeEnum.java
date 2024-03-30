package plus.easydo.bot.enums.onebot;


import lombok.Getter;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description bot通信协议类型美剧
 * @date 2024/3/29
 */
@Getter
public enum OneBotProtocolsTypeEnum {

    WEBSOCKET("websocket", "正向websocket"),
    WEBSOCKET_REVERSE("websocket_reverse", "反向websocket"),
    HTTP("http", "http"),
    HTTP_POST("http_post", "http上报"),
    WCF_HTTP("wcf_http", "wcf http 通信"),
    WCF_CLIENT("wcf_client", "wcf 客户端通信");

    private final String type;
    private final String desc;

    OneBotProtocolsTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String mode) {
        for (OneBotProtocolsTypeEnum oneBotPostTypeEnum : values()) {
            if (Objects.equals(oneBotPostTypeEnum.type, mode)) {
                return oneBotPostTypeEnum.desc;
            }
        }
        return "";
    }
}
