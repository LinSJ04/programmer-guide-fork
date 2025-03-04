package cn.itbeien.openfeign.feign;

import cn.itbeien.openfeign.feign.vo.UserRequest;
import cn.itbeien.openfeign.feign.vo.UserResponse;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
public interface ServiceFeignClient {
    // 获得用户详情
    @RequestLine("GET /api/get?id={id}")
    UserResponse get(@Param("id") Integer id);

    @RequestLine("GET /api/list?name={name}&gender={gender}")
    List<UserResponse> list(@Param("name") String name,
                            @Param("gender") Integer gender);

    @RequestLine("GET /api/list")
    List<UserResponse> list(@QueryMap Map<String, Object> queryMap);

    @RequestLine("POST /api/add")
    @Headers("Content-Type: application/json")
    Integer add(UserRequest request);
}
