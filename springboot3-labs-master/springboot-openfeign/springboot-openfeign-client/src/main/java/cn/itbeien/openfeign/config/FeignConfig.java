package cn.itbeien.openfeign.config;

import cn.itbeien.openfeign.feign.ServiceFeignClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.beans.factory.annotation.Value;
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
public class FeignConfig {

    @Value("${api.serviceUrl}")
    private String serviceUrl;

    @Bean
    public ServiceFeignClient serviceFeignClient() {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(ServiceFeignClient.class, serviceUrl); // 服务提供者地址
    }

}
