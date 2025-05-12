package cn.itbeien.springai.test;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI/支付系统/SAAS多租户基础技术平台学习社群
 * Copyright© 2025 itbeien
 */
@SpringBootTest
public class ChatClientTest {
    @Autowired
    private  ChatModel chatModel;
    @Test
    public void test() {
        //编程方式创建ChatClient
        //ChatClient.Builder builder = ChatClient.builder(this.chatModel);
        ChatClient chatClient = ChatClient.create(this.chatModel);
        System.out.println(chatClient.prompt("请你介绍下spring生态中的Fluent API").call().content());
    }

}
