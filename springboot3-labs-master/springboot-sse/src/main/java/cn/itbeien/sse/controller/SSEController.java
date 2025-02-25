package cn.itbeien.sse.controller;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@RestController
public class SSEController {
    // 定义一个AtomicInteger类型的计数器
    private final AtomicInteger counter = new AtomicInteger(0);

    // 定义一个GET请求，返回ServerSentEvent类型的Flux
    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Integer>> streamEvents() {
        // 每隔1秒返回一个ServerSentEvent，数据为计数器的值
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> ServerSentEvent.<Integer>builder()
                        .data(counter.incrementAndGet())
                        .build());
    }
}
