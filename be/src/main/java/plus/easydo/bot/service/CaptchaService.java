package plus.easydo.bot.service;

import jakarta.servlet.http.HttpServletResponse;
import plus.easydo.bot.vo.CaptchaVo;

import java.io.IOException;

/**
 * @author yuzhanfeng
 * @Date 2024-01-05 16:43
 * @Description 验证码服务
 */
public interface CaptchaService {


    void generateCaptchaV1(String key, HttpServletResponse response) throws IOException;

    boolean verify(String key, String captchaCode);

    CaptchaVo generateCaptchaV2(String captchaKey);
}
