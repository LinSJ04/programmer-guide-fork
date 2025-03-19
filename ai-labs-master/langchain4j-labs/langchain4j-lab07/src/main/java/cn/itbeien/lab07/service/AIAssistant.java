package cn.itbeien.lab07.service;

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
    @SystemMessage("你是景区智能客服，当遇到景区相关问题时，你需要回答景区相关问题。当遇到退票问题时，需要客户提供订单号，然后你需要回答退票相关问题。")
    public String chat(String question);
    public String chat(@MemoryId String memoryId, @UserMessage String question);
}
