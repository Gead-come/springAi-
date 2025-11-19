package cn.itcast;

import cn.hutool.core.date.DateUtil;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@lombok.extern.slf4j.Slf4j
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    /**
     * 与聊天客户端进行交互，发送用户问题并获取响应内容。
     *
     * @param question 用户输入的问题内容
     * @return 聊天客户端返回的响应内容
     */
    @Override
    public String chat(String question) {
        // 检查question是否为空
        if (question == null || question.trim().isEmpty()) {
            return "问题不能为空";
        }
        
        // 调用聊天客户端处理用户问题并获取响应内容
        var content = this.chatClient.prompt()
                        .system(s -> s.text(Constant.SYSTEM_ROLE).param("now", DateUtil.now()))
                        .user(question)
                        .call()
                        .content();
        log.info("question: {}, content: {}", question, content);
        return content;
    }
    
    /**
     * 处理用户问题并返回流式响应内容
     * @param question 用户输入的问题内容
     * @return 包含逐条响应内容和结束标记的响应流，每个元素为字符串格式
     */
    @Override
    public Flux<String> chatStream(String question) {
        // 检查question是否为空
        if (question == null || question.trim().isEmpty()) {
            return Flux.just("问题不能为空");
        }
        
        // 调用聊天客户端生成流式响应内容
        return this.chatClient.prompt()
                .system(s -> s.text(Constant.SYSTEM_ROLE).param("now", DateUtil.now()))
                .user(question)
                .stream()
                .content()
                // 记录每次接收到的响应内容
                .doOnNext(content -> log.info("question: {}, content: {}", question, content))
                // 在流结束时添加结束标记
                .concatWith(Flux.just("[END]"));
    }

}