package cn.itcast;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final VectorStore vectorStore;

/**
 * 处理聊天请求的POST接口方法
 * 接收前端发送的聊天数据，并调用服务层方法处理
 *
 * @param chatDTO 包含聊天信息的DTO对象，包含问题内容和会话ID
 * @return 返回聊天服务层处理后的响应结果字符串
 */
    @PostMapping
    public String chat(@RequestBody ChatDTO chatDTO) {
    // 调用chatService的chat方法，传入问题内容和会话ID，并返回处理结果
        return chatService.chat(chatDTO.getQuestion(), chatDTO.getSessionId());
    }


/**
 * 处理聊天流式请求的接口方法
 * 该接口使用POST方式，返回类型为服务器发送事件(SSE)流
 *
 * @param chatDTO 聊天数据传输对象，包含问题和会话ID
 * @return 返回一个Flux<String>类型的响应流，用于流式返回聊天回复
 */
    @PostMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestBody ChatDTO chatDTO) {
    // 调用chatService的chatStream方法，传入问题内容和会话ID，返回流式数据
        return chatService.chatStream(chatDTO.getQuestion(), chatDTO.getSessionId());
    }
/**
 * 处理向量搜索的接口方法
 * 接收用户输入的查询字符串，调用向量存储进行搜索，返回搜索结果列表
 *
 * @param query 用户输入的查询字符串
 * @return 搜索结果列表
 */
    @PostMapping("/search")
    public List<Document> search(@RequestParam("query") String query) {
        return this.vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .topK(3)
                .build());

    }


}