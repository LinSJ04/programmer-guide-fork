package cn.itbeien.lab02.service;

import dev.langchain4j.model.chat.ChatModel;
import org.springframework.stereotype.Service;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Service
public class QAService {

    ChatModel chatModel;

    public QAService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String askQuestion(String userMessage) {
        return chatModel.chat(userMessage);
    }
}