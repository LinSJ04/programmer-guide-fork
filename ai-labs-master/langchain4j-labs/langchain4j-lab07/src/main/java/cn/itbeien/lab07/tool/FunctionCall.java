package cn.itbeien.lab07.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Service
@Slf4j
public class FunctionCall {

    /**
     * 告诉大模型什么对话可以调用该函数
     * @param
     * @return
     */
    @Tool("退票")
    public String call(@P("订单号")String orderId){
        log.info("收到退票请求，订单号：{}",orderId);
        return "退票成功";
    }
}
