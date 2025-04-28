package cn.itbeien.springframework;

import cn.itbeien.springframework.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI/支付系统/SAAS多租户基础技术平台学习社群
 * Copyright© 2025 itbeien
 */
public class CoreBoot {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.getBean("");
    }
}
