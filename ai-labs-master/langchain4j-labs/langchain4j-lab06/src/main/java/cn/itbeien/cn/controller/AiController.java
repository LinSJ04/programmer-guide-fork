package cn.itbeien.cn.controller;

import cn.itbeien.cn.service.AiAssistant;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
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
     * low-level component 实现角色扮演和场景设置
     * @param question
     * AiMessage ai返回的信息
     * UserMessage 用户信息，用户提问
     * SystemMessage 系统信息，用于角色,场景设置
     * @return
     */
    @RequestMapping("/chat")
    public String aiChat(@RequestParam("question") String question) {
        return chatModel.chat(List.of(SystemMessage.from("假如你是埃隆·里夫·马斯克，请用他的思维方式和用户对话"), UserMessage.from(question)))
                .aiMessage().text();
    }

    /**
     * high-level component 实现角色扮演和场景设置
     * 通过@SystemMessage("假如你是埃隆·里夫·马斯克，请用他的思维方式和用户对话")实现角色场景设置
     * @param question
     * @return
     */
    @RequestMapping("/highChat")
    public String highChat(@RequestParam("question") String question) {
        return aiAssistant.highChat(question);
    }


}
