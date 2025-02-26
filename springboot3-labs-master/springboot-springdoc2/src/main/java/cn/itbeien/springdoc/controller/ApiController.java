package cn.itbeien.springdoc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * 使用@Operation、@ApiResponses、@ApiResponse等注解来详细描述接口的功能、响应状态码和响应内容等信息
 * Copyright© 2025 itbeien
 */
@RestController
public class ApiController {

    @Operation(summary = "测试接口", description = "用于返回测试信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "返回信息成功",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class))})
    })
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot 3 with SpringDoc 2";
    }
}
