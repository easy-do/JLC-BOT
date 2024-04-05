package plus.easydo.bot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.bot.vo.DataResult;
import plus.easydo.bot.vo.R;


/**
 * @author laoyu
 * @version 1.0
 * @description ollama
 * @date 2024/4/6
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "ollama")
@RequestMapping("/api/ollama")
public class OllamaController {

    @SaCheckLogin
    @Operation(summary = "聊天")
    @GetMapping("/ai/chat")
    public R<String> chat(@RequestParam(value = "message") String message,
                          @RequestParam(value = "baseUrl") String baseUrl,
                          @RequestParam(value = "model") String model) {
        var ollamaApi = new OllamaApi(baseUrl);
        var chatClient = new OllamaChatClient(ollamaApi).withDefaultOptions(OllamaOptions.create().withModel(model));
        String res = chatClient.call(message);
        return DataResult.ok(res);
    }
}
