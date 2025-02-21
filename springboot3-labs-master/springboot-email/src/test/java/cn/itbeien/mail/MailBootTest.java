package cn.itbeien.mail;

import cn.itbeien.mail.service.MailService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailBootTest {
    // 注入邮件服务
    @Resource
    private MailService mailServiceImpl;

    // 注入模板引擎
    @Autowired
    private TemplateEngine templateEngine;

    // 测试发送简单文本邮件
    @Test
    public void testSimpleMail() throws Exception {
        mailServiceImpl.sendSimpleMail("xxx@163.com","测试简单的文本邮件","这时一封简单的文本邮件");
    }

    // 测试发送html邮件
    @Test
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h2>hello itbeien ! 这是一封html邮件!</h2>\n" +
                "</body>\n" +
                "</html>";
        mailServiceImpl.sendHtmlMail("xxx@163.com","这是html邮件",content);
    }

    // 测试发送带附件的邮件
    @Test
    public void sendAttachmentsMail() {
        String filePath="C:\\Users\\Administrator\\Downloads\\spring-copy.png";
        mailServiceImpl.sendAttachmentsMail("xxx@163.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }


    // 测试发送带图片的邮件
    @Test
    public void sendInlineResourceMail() {
        String rscId = "itbeien";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "C:\\Users\\Administrator\\Downloads\\spring-copy.png";

        mailServiceImpl.sendInlineResourceMail("xxx@163.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }


    // 测试发送模板邮件
    @Test
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("content", "tech/SpringBoot3/17SpringBoot3.4.2基于Mybatis和MySQL8多数据源使用示例.html");
        String emailContent = templateEngine.process("emailTemplate", context);

        mailServiceImpl.sendHtmlMail("xxx@163.com","主题：这是模板邮件",emailContent);
    }
}
