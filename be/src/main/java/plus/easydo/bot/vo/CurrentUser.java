package plus.easydo.bot.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description CurrentUser
 * @date 2023/11/16
 */

@NoArgsConstructor
@Data
public class CurrentUser {

    private String userName;
    private List<Object> menu;
    private List<Object> resource;
    private String mode;
}
