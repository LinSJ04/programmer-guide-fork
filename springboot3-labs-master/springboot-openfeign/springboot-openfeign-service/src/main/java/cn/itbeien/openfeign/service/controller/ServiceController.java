package cn.itbeien.openfeign.service.controller;


import cn.itbeien.openfeign.service.vo.UserRequest;
import cn.itbeien.openfeign.service.vo.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
@Slf4j
public class ServiceController {
    @GetMapping("/get")
    public UserResponse get(@RequestParam("id") Integer id) {
        return new UserResponse().setId(id)
                .setName("itbeien：" + id).setGender(id % 2 == 0 ? 1 : 2);
    }

    @GetMapping("/list") // 获得匹配的用户列表
    public List<UserResponse> list(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "gender", required = false) Integer gender) {
        List<UserResponse> users = new ArrayList<>();
        for (int id = 1; id <= 2; id++) {
            users.add(new UserResponse().setId(id)
                    .setName(name + "_" + id).setGender(gender));
        }
        return users;
    }

    @PostMapping("/add")
    public Integer add(@RequestBody UserRequest request) {
        log.info("昵称：{}" ,request.getName());
        log.info("性别：{}" ,request.getGender());
        return 1;
    }

}
