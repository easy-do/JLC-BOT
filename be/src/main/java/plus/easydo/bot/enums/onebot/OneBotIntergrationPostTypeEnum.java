package plus.easydo.bot.enums.onebot;


import lombok.Getter;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot对接上报类型
 * @date 2024/4/4
 */
@Getter
public enum OneBotIntergrationPostTypeEnum {

    HTTP_POST("http_post", "HTTP上报"),
    WEBSOCKET("websocket", "正向websocket"),
    WEBSOCKET_REVERSE("websocket_reverse", "反向websocket"),
    WCF_HTTP("wcf_http", "wcf http上报"),
    WCF_CLIENT("wcf_client", "wcf 客户端通信");

    private final String type;
    private final String desc;

    OneBotIntergrationPostTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String mode) {
        for (OneBotIntergrationPostTypeEnum oneBotPostTypeEnum : values()) {
            if (Objects.equals(oneBotPostTypeEnum.type, mode)) {
                return oneBotPostTypeEnum.desc;
            }
        }
        return "";
    }
}
