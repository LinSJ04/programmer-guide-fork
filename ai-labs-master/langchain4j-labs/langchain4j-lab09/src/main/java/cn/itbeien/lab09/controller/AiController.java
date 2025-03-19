package cn.itbeien.lab09.controller;

import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
@Slf4j
public class AiController {

    final QwenStreamingChatModel qwenStreamChatModel;

    @RequestMapping(value = "/streamChat",produces = "text/stream-event;charset=utf-8")
    public Flux<String> streamChat(@RequestParam("message") String message) {
        Flux<String> flux = Flux.create(fluxSink -> {
            qwenStreamChatModel.chat(message, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String partialResponse) {
                    log.info(partialResponse);
                    fluxSink.next(partialResponse);
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse) {
                    log.info(completeResponse.toString());
                    fluxSink.complete();
                }

                @Override
                public void onError(Throwable error) {
                    log.info(error.getMessage());
                    fluxSink.error(error);
                }
            });
        });
        return flux;
    }

}
