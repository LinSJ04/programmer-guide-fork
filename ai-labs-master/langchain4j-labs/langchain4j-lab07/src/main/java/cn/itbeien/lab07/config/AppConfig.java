package cn.itbeien.lab07.config;

import cn.itbeien.lab07.service.AIAssistant;
import cn.itbeien.lab07.tool.FunctionCall;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
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

    final ChatLanguageModel chatLanguageModel;

    /**
     * 根据不同的会话id 创建不同的会话对象
     * @return
     */
    @Bean
    public AIAssistant createAiAssistant(FunctionCall functionCall) {
        return AiServices.builder(AIAssistant.class)
                .chatMemoryProvider(memoryId-> MessageWindowChatMemory.withMaxMessages(10))
                .chatLanguageModel(chatLanguageModel)
                .tools(functionCall)
                .build();
    }

}
