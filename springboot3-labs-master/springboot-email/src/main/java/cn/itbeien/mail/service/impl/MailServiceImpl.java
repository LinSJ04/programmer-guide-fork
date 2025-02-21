package cn.itbeien.mail.service.impl;

import cn.itbeien.mail.service.MailService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Component
@Slf4j
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender mailSender;

    @Value("${from.mail.address}")
    private String from;

    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        // 创建一个SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置发件人
        message.setFrom(from);
        // 设置收件人
        message.setTo(to);
        // 设置邮件主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setText(content);

        try {
            // 发送邮件
            mailSender.send(message);
            // 记录日志
            log.info("文本邮件已经发送");
        } catch (Exception e) {
            // 记录异常日志
            log.error("happen sendSimpleMail error！", e);
        }

    }

    /**
     * 发送html邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //创建一个MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //设置发件人
            helper.setFrom(from);
            //设置收件人
            helper.setTo(to);
            //设置邮件主题
            helper.setSubject(subject);
            //设置邮件内容，true表示需要创建一个multipart message
            helper.setText(content, true);

            //发送邮件
            mailSender.send(message);
            //记录日志
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            //记录异常日志
            log.error("happen sendHtmlMail error！", e);
        }
    }


    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath){
        // 创建MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();

        try {
            // 创建MimeMessageHelper对象，用于设置邮件内容
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 设置发件人
            helper.setFrom(from);
            // 设置收件人
            helper.setTo(to);
            // 设置邮件主题
            helper.setSubject(subject);
            // 设置邮件内容，true表示支持html格式
            helper.setText(content, true);

            // 创建FileSystemResource对象，用于添加附件
            FileSystemResource file = new FileSystemResource(new File(filePath));
            // 获取文件名
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            // 添加附件
            helper.addAttachment(fileName, file);
            //helper.addAttachment("test"+fileName, file);

            // 发送邮件
            mailSender.send(message);
            // 记录日志
            log.info("带附件的邮件已经发送");
        } catch (MessagingException e) {
            // 记录异常日志
            log.error("happen sendAttachmentsMail error！", e);
        }
    }


    /**
     * 发送正文中有静态资源（图片）的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
        // 创建MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();

        try {
            // 创建MimeMessageHelper对象，用于设置邮件内容
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 设置发件人
            helper.setFrom(from);
            // 设置收件人
            helper.setTo(to);
            // 设置邮件主题
            helper.setSubject(subject);
            // 设置邮件正文，true表示正文是HTML格式
            helper.setText(content, true);

            // 创建FileSystemResource对象，用于读取静态资源文件
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            // 将静态资源文件嵌入到邮件正文中
            helper.addInline(rscId, res);

            // 发送邮件
            mailSender.send(message);
            // 记录日志
            log.info("嵌入静态图片的邮件已经发送");
        } catch (MessagingException e) {
            // 记录异常日志
            log.error("happen sendInlineResourceMail error！", e);
        }
    }
}
