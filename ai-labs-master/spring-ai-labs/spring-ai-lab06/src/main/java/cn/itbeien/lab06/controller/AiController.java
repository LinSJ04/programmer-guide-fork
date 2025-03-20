package cn.itbeien.lab06.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final OpenAiChatModel chatModel;

    @GetMapping("/chat")
    String generation(@RequestParam("prompt") String userInput) {
        return this.chatModel.call(userInput);
    }
}
