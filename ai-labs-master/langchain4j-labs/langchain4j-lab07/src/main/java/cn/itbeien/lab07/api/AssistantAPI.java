package cn.itbeien.lab07.api;

import cn.itbeien.lab07.service.AIAssistant;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class AssistantAPI {

    final ChatLanguageModel chatLanguageModel;

    final AIAssistant assistant;

    private final ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

    /**
     * 低级别对话API
     * @param question
     * @return
     */
    @RequestMapping("/chat")
    public String lowChat(@RequestParam("question") String question) {
        chatMemory.add(UserMessage.userMessage(question));
        ChatResponse chatResponse = chatLanguageModel.chat(chatMemory.messages());
        chatMemory.add(chatResponse.aiMessage());
        return chatResponse.aiMessage().text();
    }

    @RequestMapping("/systemMessage")
    public String systemMessage(@RequestParam("question")String question) {
        return assistant.chat(question);
    }

    /**
     * 高级别对话API
     * @param question
     * @return
     */
    @RequestMapping("/assistant")
    public String highChat(@RequestParam("memoryId")String memoryId,@RequestParam("question")String question) {
        return assistant.chat(memoryId,question);
    }


}
