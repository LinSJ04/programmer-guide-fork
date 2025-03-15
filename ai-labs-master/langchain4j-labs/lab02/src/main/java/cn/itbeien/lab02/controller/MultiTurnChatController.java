package cn.itbeien.lab02.controller;


import cn.itbeien.lab02.service.MultiTurnChatService;
import org.springframework.web.bind.annotation.*;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@RestController
@RequestMapping("/api/multi")
public class MultiTurnChatController {

    private final MultiTurnChatService chatService;

    public MultiTurnChatController(MultiTurnChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public String handleChat(@RequestBody String question) {
        return chatService.chat(question);
    }
}
