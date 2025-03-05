package cn.itbeien.resilience4j.client.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
@RequestMapping("/client")
@Slf4j
public class ClientController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getData")
    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallback")
    public String getData(@RequestParam("id") Integer id) {
        // 记录日志，输出id
        log.info("[getData][准备调用 Resilience4j-server 获取客户端传递的({})详情]", id);
        // 使用RestTemplate发送HTTP GET请求，获取服务器返回的数据
        return restTemplate.getForEntity("http://127.0.0.1:9090/api/get?id=" + id, String.class).getBody();
    }

    @GetMapping("/getList")
    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallback")
    public String getList(@RequestParam("ids") List<Integer> ids) {
        // 记录日志，输出id
        log.info("[getList][准备调用 Resilience4j-server 获取客户端传递的({})详情]", ids);
        //使用RestTemplate发送HTTP GET请求，获取服务器返回的数据
        return restTemplate.getForEntity("http://127.0.0.1:9090/api/getlist?ids=" + ids, String.class).getBody();
    }

    public String fallback(Integer id, Throwable throwable) {
        // 记录日志，id和异常信息
        log.info("[fallback][id({}) exception({})]", id, throwable.getMessage());
        log.info("fallback mock server:{}",id);
        // 返回fallback mock server加上id的字符串
        return "fallback mock server:" + id;
    }
}
