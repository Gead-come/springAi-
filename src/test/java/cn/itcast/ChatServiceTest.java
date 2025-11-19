package cn.itcast;

import cn.itcast.ChatService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatServiceTest {

    @Resource
    ChatService chatService;

    @Test
    void chat() {
        this.chatService.chat("讲一个笑话");
        this.chatService.chat("java是什么");
    }
}