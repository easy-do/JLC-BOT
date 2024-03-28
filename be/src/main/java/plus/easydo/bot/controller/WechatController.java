package plus.easydo.bot.controller;

import cn.hutool.core.io.IoUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author yuzhanfeng
 * @Date 2024-03-28
 * @Description 微信消息对接接口
 */
@Slf4j
@RestController
@RequestMapping("/api/wx")
@RequiredArgsConstructor
public class WechatController {

    @PostMapping("/post")
    public void post(HttpServletRequest request, @RequestParam("token")String token) throws IOException {

        Enumeration<String> names = request.getHeaderNames();
        for (Iterator<String> it = names.asIterator(); it.hasNext(); ) {
            String name = it.next();
            log.info("wechat-hearder=>{},{}",name,request.getHeader(name));
        }
        log.info("wechat-body:{}",new String(getBodyByte(request)));
    }

    /**
     * 获取请问内容的字节数组
     *
     * @param request request
     * @return byte[]
     * @author laoyu
     * @date 2024-02-22
     */
    private static byte[] getBodyByte(HttpServletRequest request) throws IOException {
        ServletInputStream in = request.getInputStream();
        int length = request.getContentLength();
        ByteArrayOutputStream opt = new ByteArrayOutputStream(length);
        IoUtil.copy(in,opt);
        IoUtil.close(in);
        byte[] bodyByte = opt.toByteArray();
        IoUtil.close(opt);
        return bodyByte;
    }
}
