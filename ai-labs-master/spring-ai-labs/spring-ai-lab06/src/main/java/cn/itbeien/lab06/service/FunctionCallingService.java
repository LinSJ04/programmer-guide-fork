package cn.itbeien.lab06.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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
public class FunctionCallingService {

    /**
     * @author itbeien
     * 项目网站：https://www.itbeien.cn
     * 公众号：贝恩聊架构
     * 全网同名，欢迎小伙伴们关注
     * Java/AI学习社群
     * 基于function calling实现智能天气查询助手
     *  @Tool声明该方法需要进行function calling
     *  @ToolParam需要大模型传入的参数
     * Copyright© 2025 itbeien
     */
    @Tool(description = "查询天气")
    public String functionCalling(@ToolParam(description = "哪一天",required = true) String day,@ToolParam(description = "哪个城市",required = true) String city){
        /**
         * @author itbeien
         * 项目网站：https://www.itbeien.cn
         * 比如实现智能天气助手就可以调佣查询天气的API接口进行天气查询,查询后返回结果
         * 或者在这里调用业务系统功能，然后返回信息让AI大模型给你重新组织语言给到终端用户
         */
        log.info("function calling:调用天气查询接口/你的业务系统功能{},{}",day,city);
        return "今天的天气艳阳高照";
    }

}
