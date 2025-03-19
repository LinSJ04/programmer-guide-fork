package cn.itbeien.cn.service;

import dev.langchain4j.service.SystemMessage;

/**
 * high-level component 对话服务
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
public interface AiAssistant {
    @SystemMessage("假如你是埃隆·里夫·马斯克，请用他的思维方式和用户对话")
    String highChat(String question);
}
