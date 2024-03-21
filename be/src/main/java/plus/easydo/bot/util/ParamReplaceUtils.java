package plus.easydo.bot.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.json.JSONObject;

import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-03-08 12:30
 * @Description 参数替换工具类
 */
public class ParamReplaceUtils {

    private ParamReplaceUtils() {
    }

    private static final String PARAM_RE =  "\\$\\{(.*?)\\}";
    public static String replaceParam(String content,JSONObject paramJson){
        //带符号的
        List<String> group0 = ReUtil.findAllGroup0(PARAM_RE, content);
        //不带符号的
        List<String> group1 = ReUtil.findAllGroup1(PARAM_RE, content);
        for (int i = 0; i < group0.size(); i++) {
            Object value = paramJson.getByPath(group1.get(i));
            if(Objects.nonNull(value)){
                content = CharSequenceUtil.replace(content,group0.get(i),String.valueOf(value));
            }
        }
        return content;
    }

}
