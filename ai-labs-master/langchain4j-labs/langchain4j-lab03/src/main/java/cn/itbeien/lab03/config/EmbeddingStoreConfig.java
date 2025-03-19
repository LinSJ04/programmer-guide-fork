package cn.itbeien.lab03.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
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
public class EmbeddingStoreConfig {

    final PgVectorConfig pgVectorConfig;
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
       return PgVectorEmbeddingStore.builder().table(pgVectorConfig.getTableName())
               .dropTableFirst(true)
               .createTable(true)
               .host(pgVectorConfig.getHost())
               .port(pgVectorConfig.getPort())
               .user(pgVectorConfig.getUserName())
               .password(pgVectorConfig.getPassword())
               .database(pgVectorConfig.getDatabase())
               .dimension(384)
               .build();
    }
}
