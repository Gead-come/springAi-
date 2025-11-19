package cn.itcast;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, Advisor SimpleLoggerAdvisor){
        return builder
                .defaultSystem(Constant.SYSTEM_ROLE)
                .defaultAdvisors(SimpleLoggerAdvisor)
                .build();
    }
    @Bean
    public Advisor SimpleLoggerAdvisor(){
        return new SimpleLoggerAdvisor();
    }
}
