package cn.itbeien.ai.springai.service;

import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Service
public class FileService {

    @jakarta.annotation.Resource
    private VectorStore vectorStore;

    private TokenTextSplitter tokenTextSplitter;

    @Bean
    public TokenTextSplitter tokenTextSplitter() {
        tokenTextSplitter = new TokenTextSplitter();
        return tokenTextSplitter;
    }

    public void saveResourceFile(MultipartFile file) throws IOException {
        // 获取上传文件的原始文件名
        String fileName= file.getOriginalFilename();
        // 创建临时文件
        Path tempFile = Files.createTempFile("rag-temp-", fileName);
        // 将上传文件写入临时文件
        Files.write(tempFile, file.getBytes());
        // 将临时文件转换为Resource对象
        Resource fileResource = new FileSystemResource(tempFile.toFile());
        // 创建MarkdownDocumentReaderConfig对象
        MarkdownDocumentReaderConfig loadConfig = MarkdownDocumentReaderConfig.builder().build();
        // 创建MarkdownDocumentReader对象
        MarkdownDocumentReader markdownDocumentReader=new MarkdownDocumentReader(fileResource, loadConfig);
        // 将MarkdownDocumentReader对象中的内容分割成token，并传入vectorStore
        vectorStore.accept(tokenTextSplitter.apply(markdownDocumentReader.get()));
    }
}
