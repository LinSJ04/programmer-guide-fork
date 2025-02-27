package cn.itbeien.ai.springai.controller;

import cn.itbeien.ai.springai.service.FileService;
import cn.itbeien.ai.springai.service.RagService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@RestController
@RequestMapping("/rag")
public class RagController {

    @Resource
    private FileService fileService;
    @Resource
    private RagService ragService;

    @RequestMapping("/upload/file")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.saveResourceFile(file);
    }

    @RequestMapping("/chat")
    public String chat(@RequestParam("message") String message) throws IOException {
       return ragService.ragByVectorStore(message);
    }
}
