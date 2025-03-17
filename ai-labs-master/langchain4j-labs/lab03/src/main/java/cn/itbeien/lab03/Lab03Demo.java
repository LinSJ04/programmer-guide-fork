package cn.itbeien.lab03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@SpringBootApplication
public class Lab03Demo {

  /*  @Value("${langchain4j.community.zhihu.api-key}")
    private String apiKey;
    @Bean
    public ChatLanguageModel chatLanguageModel() {
        ChatLanguageModel chatLanguageModel = ZhipuAiChatModel.builder()
                .apiKey(apiKey)
                .callTimeout(Duration.ofSeconds(60))
                .connectTimeout(Duration.ofSeconds(60))
                .writeTimeout(Duration.ofSeconds(60))
                .readTimeout(Duration.ofSeconds(60))
                .build();
        return chatLanguageModel;
    }*/
    public static void main(String[] args) {
        SpringApplication.run(Lab03Demo.class, args);
    }
}
