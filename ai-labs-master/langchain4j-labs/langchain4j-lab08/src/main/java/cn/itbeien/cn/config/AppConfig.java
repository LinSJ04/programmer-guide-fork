package cn.itbeien.cn.config;

import cn.itbeien.cn.service.AIAssistant;
import cn.itbeien.cn.service.PersistenceChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
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

    final ChatModel chatModel;
    final PersistenceChatMemoryStore persistenceChatMemoryStore;

    /**
     * 根据不同的会话id 创建不同的会话对象
     * @return
     */
    @Bean
    public AIAssistant createAiAssistant() {
        ChatMemoryProvider chatMemoryProvider = memoryId-> MessageWindowChatMemory.builder().maxMessages(10)
                .id(memoryId)
                .chatMemoryStore(persistenceChatMemoryStore)
                .build();
        return AiServices.builder(AIAssistant.class)
                .chatMemoryProvider(chatMemoryProvider)
                .chatModel(chatModel).build();
    }

}
