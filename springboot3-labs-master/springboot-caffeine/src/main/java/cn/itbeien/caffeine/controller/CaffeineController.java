package cn.itbeien.caffeine.controller;

import cn.itbeien.caffeine.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@RestController
@Slf4j
public class CaffeineController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping("/test")
    public String test() {
        log.info("进入controller....");
        return cacheService.getUserById(1L).toString();
    }

    @RequestMapping("/delete")
    public String delete() {
        cacheService.deleteUserById(1L);
        return "success";
    }
}
