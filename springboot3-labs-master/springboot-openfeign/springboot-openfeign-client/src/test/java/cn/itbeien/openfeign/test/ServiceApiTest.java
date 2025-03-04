package cn.itbeien.openfeign.test;

import cn.itbeien.openfeign.feign.ServiceFeignClient;
import cn.itbeien.openfeign.feign.vo.UserResponse;
import cn.itbeien.openfeign.feign.vo.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
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
@SpringBootTest
@Slf4j
public class ServiceApiTest {
    @Autowired
    private ServiceFeignClient serviceFeignClient;

    @Test
    public void  get() {
        UserResponse response = serviceFeignClient.get(3);
        log.info("response: {}", response);
    }

    @Test
    public void list() {
        List<UserResponse> responseList =  serviceFeignClient.list("itbeien", 1);
        log.info("responseList: {}", responseList);
    }

    @Test
    public void  test() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", "itbeien");
        List<UserResponse> responspList =  serviceFeignClient.list(queryMap);
        log.info("responspList: {}", responspList);
    }

    @Test
    public void add() {
        UserRequest request =new UserRequest();
        request.setName("itbeien");
        request.setGender(2);
        Integer id = serviceFeignClient.add(request);
        log.info("id: {}", id);
    }
}
