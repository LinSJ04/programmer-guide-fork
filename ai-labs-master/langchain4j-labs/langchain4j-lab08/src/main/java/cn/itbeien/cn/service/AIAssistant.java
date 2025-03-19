package cn.itbeien.cn.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
public interface AIAssistant {
    @SystemMessage("你是一个程序员，你只能回答技术问题，请记住不能回答非技术问题")
    public String chat(String question);
    public String chat(@MemoryId String memoryId, @UserMessage String question);
}
