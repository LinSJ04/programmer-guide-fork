package cn.itbeien.resilience4j.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@RestController
@RequestMapping("/api")
public class ServerController {
    @GetMapping("/get")
    public String get(@RequestParam("id") Integer id) {
        return "server:" + id;
    }

    @GetMapping("/getlist")
    public List<String> getList(@RequestParam("ids") List<Integer> ids) {
        return ids.stream().map(id -> "server:" + id).collect(Collectors.toList());
    }
}
