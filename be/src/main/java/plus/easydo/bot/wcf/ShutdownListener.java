package plus.easydo.bot.wcf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import plus.easydo.bot.manager.CacheManager;

/**
 * @author yuzhanfeng
 * @Date 2024-03-28
 * @Description 停机监听
 */
@Slf4j
@Component
public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // 在这里处理应用程序停止的逻辑
        log.info("Application is closing...");
        CacheManager.CLIENT_CACHE.forEach(client -> {
            client.diableRecvMsg();
        });
    }
}
