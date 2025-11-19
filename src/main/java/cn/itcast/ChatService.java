package cn.itcast;

import reactor.core.publisher.Flux;

public interface ChatService {

    /**
     * 普通聊天
     *
     * @param question 用户提问
     * @return 大模型的回答
     */
    String chat(String question);
    /**
     * 流式聊天
     *
     * @param question 用户提问
     * @return 大模型的回答
     */
    Flux<String> chatStream(String question);
}