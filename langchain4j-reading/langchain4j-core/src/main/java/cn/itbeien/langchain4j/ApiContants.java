package cn.itbeien.langchain4j;

import static dev.langchain4j.internal.Utils.getOrDefault;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI/支付系统/SAAS多租户基础技术平台学习社群
 * Copyright© 2025 itbeien
 */
public class ApiContants {
    public static final String DEEPSEEK_API_KEY = System.getenv("DEEPSEEK_KEY");
    public static final String DEEPSEEK_API_URL = "https://api.deepseek.com";
    public static final String DEEPSEEK_API_MODEL = "deepseek-chat";
}
