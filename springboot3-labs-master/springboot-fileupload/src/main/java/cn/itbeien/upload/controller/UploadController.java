package cn.itbeien.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Controller
@Slf4j
public class UploadController {

    // 定义上传文件路径
    private static String UPLOADED_FOLDER = "d://temp//";

    // 返回上传页面
    @GetMapping("/")
    public String index() {
        return "upload";
    }

    // 处理文件上传
    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
        RedirectAttributes redirectAttributes) {
        // 判断文件是否为空
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "请选择需要上传的文件");
            return "redirect:uploadStatus";
        }

        try {
            // 获取文件字节数组
            byte[] bytes = file.getBytes();
            // 获取文件路径
            Path dir = Paths.get(UPLOADED_FOLDER);
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            // 判断路径是否存在，不存在则创建
            if(!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            // 将文件写入路径
            Files.write(path, bytes);
            // 添加上传成功信息
            redirectAttributes.addFlashAttribute("message",
                "你成功上传 '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            // 添加上传失败信息
            redirectAttributes.addFlashAttribute("message", "系统异常");
            log.error(e.getMessage());
        }
        // 重定向到上传状态页面
        return "redirect:/uploadStatus";
    }

    // 返回上传状态页面
    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}