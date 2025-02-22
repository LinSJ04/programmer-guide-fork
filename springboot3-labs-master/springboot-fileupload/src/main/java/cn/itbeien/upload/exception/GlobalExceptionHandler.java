package cn.itbeien.upload.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 处理MultipartException异常
    @ExceptionHandler(MultipartException.class)
    public String handleError(MultipartException e, RedirectAttributes redirectAttributes) {
        // 记录异常信息
        log.error(e.getMessage());
        // 将异常信息添加到重定向属性中
        redirectAttributes.addFlashAttribute("message", e.getMessage());
        // 重定向到/uploadStatus页面
        return "redirect:/uploadStatus";
    }
}
