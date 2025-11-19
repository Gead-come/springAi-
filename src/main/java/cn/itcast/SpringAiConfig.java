package cn.itcast;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder,
                                 Advisor SimpleLoggerAdvisor,
                                 Advisor MessageChatMemoryAdvisor
                                 ){
        return builder
                .defaultSystem(Constant.SYSTEM_ROLE)
                .defaultAdvisors(SimpleLoggerAdvisor)
                .defaultAdvisors(MessageChatMemoryAdvisor)
                .build();
    }
    @Bean
    public Advisor SimpleLoggerAdvisor(){
        return new SimpleLoggerAdvisor();
    }
/**
 * 配置ChatMemory Bean，用于在应用程序中提供聊天记忆功能
 * 此方法创建一个基于内存的聊天记忆实现，将ChatMemory接口与InMemoryChatMemory实现类关联
 *
 * @return ChatMemory 返回一个InMemoryChatMemory实例，作为Spring容器中的Bean
 */
    @Bean    // 将此方法返回的对象声明为Spring容器中的一个Bean
    public ChatMemory chatMemory(){    // 定义一个名为chatMemory的方法，返回ChatMemory类型
        return new InMemoryChatMemory();    // 创建并返回一个InMemoryChatMemory实例
    }
    /**
 * 配置MessageChatMemoryAdvisor Bean，用于在应用程序中提供消息聊天记忆功能
 * 此方法创建一个MessageChatMemoryAdvisor实例，将ChatMemory接口与chatMemory()方法返回的InMemoryChatMemory实例关联
 *
 * @param chatMemory ChatMemory类型的参数，用于提供聊天记忆功能
 * @return Advisor 返回一个MessageChatMemoryAdvisor实例，作为Spring容器
     **/
    @Bean
    public Advisor MessageChatMemoryAdvisor(ChatMemory chatMemory){
        return new MessageChatMemoryAdvisor(chatMemory);
    }
}
