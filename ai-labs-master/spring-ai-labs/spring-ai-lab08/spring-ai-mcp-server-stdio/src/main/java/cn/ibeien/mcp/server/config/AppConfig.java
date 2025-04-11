package cn.ibeien.mcp.server.config;

import cn.ibeien.mcp.server.service.McpService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI/支付系统/SAAS多租户基础技术平台学习社群
 * Copyright© 2025 itbeien
 */
@Configuration
public class AppConfig {
    @Bean
    public ToolCallbackProvider weatherTools(McpService mcpService) {
        return  MethodToolCallbackProvider.builder().toolObjects(mcpService).build();
    }

}
