package cn.itbeien.lab03.config;

import cn.itbeien.lab03.service.Assistant;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

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
public class InMemoryEmbeddingConfig {

    final ChatLanguageModel chatLanguageModel;


    //@Bean
    public EmbeddingStore<TextSegment> initEmbeddingStore() {
        return new InMemoryEmbeddingStore<TextSegment>();
    }

    @Bean
    public Assistant init(EmbeddingStore<TextSegment> embeddingStore) {
        return AiServices.builder(Assistant.class)
                .chatMemoryProvider(memoryId-> MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .chatLanguageModel(chatLanguageModel).build();
    }
}
