package plus.easydo.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import plus.easydo.bot.sandbox.SandboxWebsocketHandler;
import plus.easydo.bot.websocket.OneBotWebSocketHandler;

/**
 * @author laoyu
 * @version 1.0
 * @description WebSocketConfig
 * @date 2023/12/2
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //配置handler,拦截器和跨域
        registry.addHandler(oneBotWebSocketHandler(), "/ws/oneBot").setAllowedOrigins("*");
        registry.addHandler(sandBoxWebsocketHandler(), "/ws/sandbox").setAllowedOrigins("*");
    }


    @Bean
    public WebSocketHandler oneBotWebSocketHandler() {
        return new OneBotWebSocketHandler();
    }
    @Bean
    public WebSocketHandler sandBoxWebsocketHandler() {
        return new SandboxWebsocketHandler();
    }

}
