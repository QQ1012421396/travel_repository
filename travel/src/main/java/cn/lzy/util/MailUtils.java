package cn.lzy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtils {

    private static Properties properties;
    //加载一次配置文件
    static{
        InputStream in = MailUtils.class.getClassLoader().getResourceAsStream("email.properties");
        try {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送邮件
     * @param email	发送地址
     * @param content  发送内容
     */
    public static void sendEmail(String email, String content) {
        try {
            //创建包含邮件服务器的网络连接信息的Session会话对象
            Session session = Session.getDefaultInstance(properties);
            //代表一封邮件
            MimeMessage message = new MimeMessage(session);
            //设置发件人
            message.setSender(new InternetAddress(properties.getProperty("sender")));
            //设置收件人
            message.setRecipients(RecipientType.TO, email);
            //设置邮件主题
            message.setSubject("飞吧旅游网注册激活");
            //设置自定义发件人昵称
            String nick=javax.mail.internet.MimeUtility.encodeText("飞吧旅游网官方");//对中文进行编码
            message.setFrom(new InternetAddress(nick+"<" + properties.getProperty("sender")+">"));//后面的发送人地址zyliu1993@163.com必须加 ，还必须对

            //设置邮件的正文内容
            message.setContent(content, "text/html;charset=utf-8");
            message.saveChanges();

            //发送邮件
            Transport ts = session.getTransport();
            ts.connect(properties.getProperty("userName"), properties.getProperty("pwd"));//连接，注意这里的密码是163邮箱的授权码
            ts.sendMessage(message, message.getAllRecipients());
            //关闭
            ts.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
