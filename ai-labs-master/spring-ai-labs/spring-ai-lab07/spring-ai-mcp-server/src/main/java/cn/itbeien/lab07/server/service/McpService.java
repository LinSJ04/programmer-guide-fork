package cn.itbeien.lab07.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI/支付系统/SAAS多租户基础技术平台学习社群
 * Copyright© 2025 itbeien
 */
@Service
@Slf4j
public class McpService {

    @Tool(description = "获取城市的天气信息")
    public String getWeatherByCity(@ToolParam(required=true,description = "城市名称") String city) {
        log.info("获取城市天气信息：{}",city);
        return city +"天气信息：晴空万里，气温25°C";
    }
}
