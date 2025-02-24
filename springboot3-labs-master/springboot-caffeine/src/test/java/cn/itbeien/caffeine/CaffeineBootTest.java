package cn.itbeien.caffeine;

import cn.itbeien.caffeine.service.CacheService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@SpringBootTest
public class CaffeineBootTest {
    @Resource
    private CacheService cacheService;

    @Test
    public void test(){
        cacheService.getUserById(1L);
    }

    @Test
    public void delete(){
        cacheService.deleteUserById(1L);
    }
}
