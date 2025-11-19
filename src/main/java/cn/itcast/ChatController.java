package cn.itcast;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final ChatClient chatClient;

    @PostMapping
    public String chat(@RequestBody String question) {
        // 检查question是否为空
        if (question == null || question.trim().isEmpty()) {
            return "问题不能为空";
        }
        
        // 调用聊天客户端处理用户问题并获取响应内容
        var content = this.chatClient.prompt()
                .system(s -> s.text(Constant.SYSTEM_ROLE).param("now", java.time.LocalDateTime.now()))
                .user(question)
                .call()
                .content();
        log.info("question: {}, content: {}", question, content);
        return content;
    }
    
    @PostMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestBody String question) {
        // 检查question是否为空
        if (question == null || question.trim().isEmpty()) {
            return Flux.just("问题不能为空");
        }
        
        return chatService.chatStream(question);
    }

}