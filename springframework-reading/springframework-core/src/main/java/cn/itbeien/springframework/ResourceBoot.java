package cn.itbeien.springframework;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.InputStream;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI/支付系统/SAAS多租户基础技术平台学习社群
 * Copyright© 2025 itbeien
 */
public class ResourceBoot {
    public static void main(String[] args) throws Exception{
        urlResourceTest();
        classPathResourceTest();
        fileSystemResourceTest();
    }

    public static void classPathResourceTest() throws Exception{
        //ClassPathResource是 Spring 框架中的一个组件，用于访问类路径上的资源。
        // 在此示例中，path 变量存储了类路径资源的路径。这里，文件位于 src/main/resources/application.properties 路径目录。
        String path = "application.properties";
        Resource resource = new ClassPathResource(path);
        System.out.println(resource.contentLength());
        System.out.println(resource.getFilename());
        try (InputStream is = resource.getInputStream()) {
            // 读取和处理资源内容
            System.out.println(new String(is.readAllBytes()));
        }
    }

    public static void fileSystemResourceTest() throws Exception{
        //读取文件系统资源 请替换为自己的目录
        String path = "E:\\gitee\\programmer-guide\\springframework-reading\\springframework-core\\README.md";
        Resource resource = new FileSystemResource(path);
        try (InputStream is = resource.getInputStream()) {
            // 读取和处理资源内容
            System.out.println(new String(is.readAllBytes()));
        }
    }

    public static void urlResourceTest() throws Exception{
        //读取网络资源
        Resource resource = new UrlResource("https://baike.baidu.com/item/Java/85979");
        try (InputStream is = resource.getInputStream()) {
            // 读取和处理资源内容
            System.out.println(new String(is.readAllBytes()));
        }
    }
}
