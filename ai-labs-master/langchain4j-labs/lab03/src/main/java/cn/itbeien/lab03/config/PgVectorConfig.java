package cn.itbeien.lab03.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties(prefix = "pg.vector")
@Data
public class PgVectorConfig {
    private String host;
    private int port;
    private String database;
    private String userName;
    private String password;
    private String tableName;
}
