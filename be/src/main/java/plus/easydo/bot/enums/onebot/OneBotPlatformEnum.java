package plus.easydo.bot.enums.onebot;


import lombok.Getter;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 平台类型
 * @date 2024/3/29
 */
@Getter
public enum OneBotPlatformEnum {

    QQ("qq", "腾讯qq"),
    WX("wx", "微信"),
    OTHER("other", "其他");

    private final String type;
    private final String desc;

    OneBotPlatformEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String mode) {
        for (OneBotPlatformEnum oneBotPostTypeEnum : values()) {
            if (Objects.equals(oneBotPostTypeEnum.type, mode)) {
                return oneBotPostTypeEnum.desc;
            }
        }
        return "";
    }
}
