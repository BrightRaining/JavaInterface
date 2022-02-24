package com.pumpink.demo.utils.file;

import com.pumpink.demo.bean.pojo.CheckResponseResult;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

@Controller
public class SendEmail {

//    public static void main(String[] args) throws Exception{
//
//        SendEmail sendEmail = new SendEmail();
//        //sendEmail.send("陆二帅","哈哈","测试","jiangnanqiang@vcinema.cn",null);
//    }

    private static String account = "luershuai@vcinema.cn";// 登录账户
    private static String password = "pKXvjWgs9TF5pFMF";// 登录密码
    private static String host = "smtp.exmail.qq.com";// 服务器地址
    private static String port = "465";// 端口
    private static String protocol = "smtp";// 协议
    //初始化参数
    public static Session initProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        // 使用smtp身份验证
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // 使用SSL,企业邮箱必需 start
        // 开启安全协议
        MailSSLSocketFactory mailSSLSocketFactory = null;
        try {
            mailSSLSocketFactory = new MailSSLSocketFactory();
            mailSSLSocketFactory.setTrustAllHosts(true);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "true");
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.debug","true");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, password);
            }
        });
        // 使用SSL,企业邮箱必需 end
        // TODO 显示debug信息 正式环境注释掉
        session.setDebug(true);
        return session;
    }

    // @param sender 发件人别名
    // @param subject 邮件主题
    //@param content 邮件内容
    //@param receiverList 接收者列表,多个接收者之间用","隔开
    //@param fileSrc 附件地址
    public void send(CheckResponseResult checkResponseResult, String fileSrc) {
       String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
       String sender = "luershuai";
       String content = checkResponseResult.getTotalCase() +"<br><br>" + "由于请求链接在邮件预览中无法使用若要查看完整样式请下载附件查看<br>";
       String receiverList = "zhuangxulin@vcinema.cn,wangyinmei@vcinema.cn,liangxuequan@vcinema.cn,baiyupeng@vcinema.cn,cuichengcheng@vcinema.cn,wangliying@vcinema.cn,hejinhai@vcinema.cn,zhouliangliang@vcinema.cn,luershuai@vcinema.cn,luershuai@vcinema.cn";
       // String receiverList = "zhuangxulin@vcinema.cn,wangyinmei@vcinema.cn,baiyupeng@vcinema.cn,luershuai@vcinema.cn";

        String  subject = "接口测试报告"+"-"+time;
        try {
            Session session = initProperties();
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(account, sender));// 发件人,可以设置发件人的别名
            // 收件人,多人接收
            InternetAddress[] internetAddressTo = new InternetAddress().parse(receiverList);
            mimeMessage.setRecipients(Message.RecipientType.TO, internetAddressTo);
            // 主题
            mimeMessage.setSubject(subject);
            // 时间
            mimeMessage.setSentDate(new Date());
            // 容器类 附件
            MimeMultipart mimeMultipart = new MimeMultipart();
            // 可以包装文本,图片,附件
            MimeBodyPart bodyPart = new MimeBodyPart();
            // 设置内容
            bodyPart.setContent(content, "text/html; charset=UTF-8");
            mimeMultipart.addBodyPart(bodyPart);
            // 添加图片&附件
            File file  = new File(fileSrc);
            bodyPart = new MimeBodyPart();
            bodyPart.attachFile(file);
            mimeMultipart.addBodyPart(bodyPart);
            mimeMessage.setContent(mimeMultipart);
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
