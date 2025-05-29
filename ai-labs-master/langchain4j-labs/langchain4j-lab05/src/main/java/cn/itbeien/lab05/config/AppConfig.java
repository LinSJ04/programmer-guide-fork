package cn.itbeien.lab05.config;


import cn.itbeien.lab05.service.AiAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注入high-level component
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final ChatModel chatModel;

    @Bean
    public AiAssistant aiAssistant() {
        return AiServices.builder(AiAssistant.class)
                .chatModel(chatModel)
                .build();
    }
}
