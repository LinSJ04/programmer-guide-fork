package cn.itbeien.knife4j.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@RestController
@Tag(name = "测试控制类")
@Slf4j
public class TestController {
    @Operation(summary = "普通请求")
    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody String json){
        return ResponseEntity.ok(json);
    }

    @Operation(summary = "普通请求+Param+Header+Path")
    @Parameters({
            @Parameter(name = "id",description = "id",in = ParameterIn.PATH),
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
            @Parameter(name = "name",description = "文件名称",required = true,in=ParameterIn.QUERY)
    })
    @PostMapping("/test/{id}")
    public ResponseEntity<String> test(@PathVariable("id") String id, @RequestHeader("token") String token, @RequestParam("name")String name, @RequestBody String json){
        log.info("name:"+name+",token:"+token+",id:"+id);
        return ResponseEntity.ok(json);
    }
}
