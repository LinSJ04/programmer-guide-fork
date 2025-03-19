package cn.itbeien.lab03.controller;

import cn.itbeien.lab03.service.Assistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatAiAPI {

    final ChatLanguageModel chatLanguageModel;

    final Assistant assistant;

    /**
     * 低级别对话API
     * @param question
     * @return
     */
    @RequestMapping("/chat")
    public String chat(@RequestParam("question") String question) {
        return chatLanguageModel.chat(UserMessage.from(question)).aiMessage().text();
    }

    /**
     * 高级别API对话
     * @param question
     * @return
     */
    @RequestMapping("/assistant")
    public String assistant(@RequestParam("question")String question) {
        return assistant.chat(question);
    }

    final EmbeddingStore<TextSegment> embeddingStore;

    /**
     * 加载企业/个人知识库文档
     * @return
     */
    @RequestMapping("/load")
    public String load() {
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("E:\\gitee\\knowledge\\programmer-guide\\ai-labs-master\\langchain4j-labs\\langchain4j-lab03\\document");
        EmbeddingStoreIngestor.ingest(documents,embeddingStore);
        return "知识库加载到内存/pgvector成功";
    }
}
