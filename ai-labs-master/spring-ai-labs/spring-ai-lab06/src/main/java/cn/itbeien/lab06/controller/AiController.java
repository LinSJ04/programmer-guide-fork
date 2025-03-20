package cn.itbeien.lab06.controller;

import cn.itbeien.lab06.service.FunctionCallingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    // 自定义人设，来与用户进行角色扮演 提示词
    private final static String systemPrompt = "你是智能天气助手，请回答与天气相关的问题，用户在询问天气情况时必须要求给出哪个城市和哪一天";

    // 历史消息列表
    static List<Message> historyMessage = new ArrayList<>(List.of(new SystemMessage(systemPrompt)));

    private final ChatModel chatModel;
    private final FunctionCallingService functionCallingService;

    // 历史消息列表的最大长度
    static int maxLen = 10;

    @GetMapping("/chat")
    String generation(@RequestParam("prompt") String userInput) {
        return this.chatModel.call(userInput);
    }


    @GetMapping("/fcChat")
    String fcChat(@RequestParam("prompt") String userInput) {
        // 用户输入的文本是UserMessage
        historyMessage.add(new UserMessage(userInput));
        // 发给AI前对历史消息对列的长度进行检查
        if(historyMessage.size() > maxLen){
            historyMessage = historyMessage.subList(historyMessage.size()-maxLen-1,historyMessage.size());
            // 确保第一个是SystemMessage
            historyMessage.add(0,new SystemMessage(systemPrompt));
        }

        Prompt prompt = new Prompt(historyMessage);

        return ChatClient.create(chatModel)
                .prompt(prompt)
                .tools(functionCallingService)
                .call()
                .content();
    }
}
