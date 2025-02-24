package cn.itbeien.caffeine.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Configuration
@EnableCaching
public class CacheConfig {
    // 配置缓存管理器
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    // 配置Caffeine缓存
    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100) // 初始化缓存容量
                .maximumSize(500) // 最大缓存容量,当缓存中的元素数量超过这个值时，会根据 LRU（最近最少使用）策略进行淘汰。
                .expireAfterAccess(10, TimeUnit.MINUTES) // 缓存过期时间,设置缓存项在最后一次访问后的 10 分钟后过期
                .recordStats(); // 记录缓存统计信息
    }
}
