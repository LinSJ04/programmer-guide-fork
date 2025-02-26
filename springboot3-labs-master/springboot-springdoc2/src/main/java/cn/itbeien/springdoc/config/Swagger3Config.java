package cn.itbeien.springdoc.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Configuration
public class Swagger3Config {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("贝恩聊架构 Swagger3 详解")
                        .description("Swagger3 Spring Boot 3.4.3 application")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://www.itbeien.cn")))
                .externalDocs(new ExternalDocumentation()
                        .description("api接口文档")
                        .url("https://www.itbeien.cn"));
    }
}
