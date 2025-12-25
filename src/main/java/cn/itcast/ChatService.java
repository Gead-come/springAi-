package cn.itcast;

import reactor.core.publisher.Flux;

public interface ChatService {

    /**
     * 普通聊天
     *
     * @param question 用户提问
     * @return 大模型的回答
     */
  
    /**
     * 流式聊天
     *
     * @param question 用户提问
     * @return 大模型的回答
     */


    String chat(String question, String sessionId);

    Flux<String> chatStream(String question, String sessionId);
}