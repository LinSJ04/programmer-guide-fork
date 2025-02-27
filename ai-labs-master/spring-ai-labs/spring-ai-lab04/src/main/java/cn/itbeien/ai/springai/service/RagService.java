package cn.itbeien.ai.springai.service;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Service
public class RagService {
    private final static String SYSTEM_PROMPT = """
        你需要使用我提供的文档内容对用户提出的问题进行回答，
        当用户提出的问题无法根据文档内容进行回复或者你也不知道时，回答不知道即可。
        文档内容如下:
        {documents}
        """;

    @Autowired
    private ZhiPuAiChatModel chatModel;
    @Autowired
    private VectorStore vectorStore;

    public String ragByVectorStore(String message) {
        // 根据输入的消息，在向量库中进行相似度搜索，获取相似文档列表
        List<Document> listOfSimilarDocuments = vectorStore.similaritySearch(message);
        // 断言相似文档列表不为空
        assert listOfSimilarDocuments != null;
        // 将相似文档列表中的文本内容拼接成一个字符串
        String documents = listOfSimilarDocuments.stream().map(Document::getText).collect(Collectors.joining());
        // 创建系统消息，将拼接的文本内容作为参数传入
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage(Map.of("documents", documents));
        // 创建用户消息，将输入的消息作为参数传入
        UserMessage userMessage = new UserMessage(message);
        // 调用聊天模型，将系统消息和用户消息作为参数传入
        ChatResponse rsp = chatModel.call(new Prompt(List.of(systemMessage, userMessage)));
        // 返回聊天模型的输出结果
        return rsp.getResult().getOutput().getText();
    }
}
