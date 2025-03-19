package cn.itbeien.lab02.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@AiService
public interface MultiTurnChatService {
    @SystemMessage("你是一个支持多轮对话的客服助理")
    String chat(String userMessage);
}
