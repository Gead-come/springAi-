package cn.itcast;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringAiConfig {
    /**
     * 创建并返回一个ChatClient的Spring Bean实例。
     *
     * @param builder 用于构建ChatClient实例的构建者对象
     * @return 构建好的ChatClient实例
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder,
                                 Advisor simpleLoggerAdvisor, // 添加一个Advisor，用于记录日志
                                 Advisor messageChatMemoryAdvisor,//添加一个Advisor，用于消息聊天记忆
                                 Advisor promptChatMemoryAdvisor,//提示级记忆拦截器
                                 Advisor safeGuardAdvisor, // 添加一个Advisor，用于防止敏感词
                                 WeatherTools weatherTools // 添加一个Tool，用于获取天气信息
    ) {
        return builder
                .defaultSystem(Constant.SYSTEM_ROLE) // 设置默认的系统角色
                .defaultAdvisors(simpleLoggerAdvisor, promptChatMemoryAdvisor,  messageChatMemoryAdvisor, safeGuardAdvisor) // 设置默认的Advisor
                .defaultTools(weatherTools) // 设置默认的Tool ，用于获取天气信息
                .build();
    }
//    @Bean
//    public ChatClient chatClient(ChatClient.Builder builder,
//                                 Advisor simpleLoggerAdvisor, // 添加一个Advisor，用于记录日志
//                                 Advisor messageChatMemoryAdvisor,//    添加一个Advisor，用于消息聊天记忆
//                                 Advisor promptChatMemoryAdvisor,//
//                                 Advisor safeGuardAdvisor // 添加一个Advisor，用于防止敏感词
//    ) {
//        return builder
//                .defaultSystem(Constant.SYSTEM_ROLE) // 设置默认的系统角色
//                .defaultAdvisors(simpleLoggerAdvisor, messageChatMemoryAdvisor,
//                               promptChatMemoryAdvisor, safeGuardAdvisor) // 设置默认的Advisor
//                .build();
//    }
    
    @Bean
    public Advisor simpleLoggerAdvisor(){
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
    public Advisor messageChatMemoryAdvisor(ChatMemory chatMemory){
        return new MessageChatMemoryAdvisor(chatMemory);
    }
    /**
 * 配置PromptChatMemoryAdvisor Bean，用于在应用程序中提供提示聊天记忆功能
 * 此方法创建一个PromptChatMemoryAdvisor实例，将ChatMemory接口与chatMemory()方法返回的InMemoryChatMemory实例关联
 *
 * @param chatMemory ChatMemory类型的参数，用于提供聊天记忆功能
 * @return Advisor 返回一个PromptChatMemoryAdvisor实例，作为Spring容器
     * **/
    @Bean
    public Advisor promptChatMemoryAdvisor(ChatMemory chatMemory){
        return new PromptChatMemoryAdvisor(chatMemory);
    }
    /**
 * 配置SafeGuardAdvisor Bean，用于在应用程序中提供安全防护功能
 * 此方法创建一个SafeGuardAdvisor实例，将敏感词列表、违规提示语和 advisor处理优先级作为参数传入
 *
 * @return Advisor 返回一个SafeGuardAdvisor实例，作为Spring容器
     **/

    @Bean
    public Advisor safeGuardAdvisor() {
        // 敏感词列表（示例数据，建议实际使用时从配置文件或数据库读取）
        List<String> sensitiveWords = List.of("日你妈", "敏感词2");
        // 创建安全防护Advisor，参数依次为：敏感词库、违规提示语、advisor处理优先级，数字越小越优先
        return new SafeGuardAdvisor(
                sensitiveWords,
                "敏感词提示：请勿输入敏感词！",
                Advisor.DEFAULT_CHAT_MEMORY_PRECEDENCE_ORDER
        );
    }
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }
}