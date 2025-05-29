package cn.itbeien.lab05.controller;

import cn.itbeien.lab05.service.AiAssistant;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
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
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {
    /**
     * 注入ChatLanguageModel
     * low-level component
     */
    final ChatModel chatModel;
    final AiAssistant aiAssistant;

    /**
     * low-level component 对话服务
     * @param question
     * AiMessage ai返回的信息
     * UserMessage 用户信息，用户提问
     * SystemMessage 系统信息，用于角色,场景设置
     * @return
     */
    @RequestMapping("/chat")
    public String aiChat(@RequestParam("question") String question) {
        return chatModel.chat(UserMessage.from(question)).aiMessage().text();
    }

    /**
     * high-level component 对话服务
     * @param question
     * @return
     */
    @RequestMapping("/highChat")
    public String highChat(@RequestParam("question") String question) {
        return aiAssistant.highChat(question);
    }


}
