package cn.itbeien.langchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI/支付系统/SAAS多租户基础技术平台学习社群
 * langchain4j-core模块，定义核心抽象（如ChatModel和EmbeddingStore）及其api
 * Copyright© 2025 itbeien
 */
public class DeepSeekExamples {
    static class Simple_Prompt{
        public static void main(String[] args) {
            OpenAiChatModel chatModel = OpenAiChatModel.builder()
                   .baseUrl(ApiContants.DEEPSEEK_API_URL)
                   .apiKey(ApiContants.DEEPSEEK_API_KEY)
                   .modelName(ApiContants.DEEPSEEK_API_MODEL)
                   .build();
            //String joke = chatModel.chat("你是谁");

            //System.out.println(joke);
        }
    }
}
