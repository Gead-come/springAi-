package cn.itcast;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class a {

    private final ChatClient chatClient;
    private final ChatClient client;
    
    public a(ChatClient chatClient) {
        this.chatClient = chatClient;
        this.client = chatClient;
    }

    @GetMapping("/ask")
    public String ask(String q){
        return  chatClient
                .prompt()
                .user(q)
                .call()
                .content();
    }
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(String q) {

        return chatClient
                .prompt()
                .user(q)
                .stream()       // ⭐ 关键：打开流式模式
                .content();     // ⭐ 每段内容都会作为一个事件推送
    }
    @GetMapping("/person")
    public cn.itcast.Person createPerson(String desc) {

        return chatClient
                .prompt()
                .user("根据描述生成一个人物对象：" + desc)
                .call()
                .entity(cn.itcast.Person.class);   // ⭐ 结构化输出
    }
    @GetMapping("/teacher")
    public String teacherMode(String q) {

        return chatClient
                .prompt()
                .system("你是一个 Java 老师，回答必须清晰、分步骤、结合例子。")
                .user(q)
                .call()
                .content();
    }
    @GetMapping("/chat")
    public String chat(String q) {
        return client
                .prompt()
                .user(q)
                .call()
                .content();
    }
    @GetMapping("/assistant")
    public String assistant(String q) {
        return client
                .prompt()
                .system("你是一个 Java 老师，回答必须清晰、分步骤、结合例子。")
                .user(q)
                .call()
                .content();
    }

}