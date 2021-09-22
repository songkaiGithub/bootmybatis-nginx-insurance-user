package com.util;

import com.po.Insurance;
import com.util.vo.InsuranceUserVo;
import org.springframework.dao.DataAccessException;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Emailutil {
    private static String smsdk=null;

    public static  String emailreglist(InsuranceUserVo insuranceUserVo){
         String account = "s953784167@163.com";            //替换为发件人账号
         String password = "EKGAKITYSZPYHKMR";                    //替换为发件人允许第三方访问密码
         String receiveMailAccount =insuranceUserVo.getUserCode();
            //替换为收件人账号
            // 1. 使用Properties对象封装连接所需的信息
            Properties props = new Properties();                    // 参数配置
            props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议
            props.setProperty("mail.smtp.host", "smtp.163.com");    // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

            try {
                Date date = new Date();
                long datetime = date.getTime();
                String strdate = datetime + "";
                smsdk = strdate.substring(strdate.length() - 4, strdate.length());
                // 2. 获取Session对象
                Session session = Session.getDefaultInstance(props);
                // 3. 封装Message对象
                MimeMessage message = createMimeMessage(insuranceUserVo,session, account, receiveMailAccount);
                // 4. 使用Transport发送邮件
                Transport transport = session.getTransport();
                transport.connect(account, password);
                transport.sendMessage(message, message.getAllRecipients());
                // 5. 关闭连接
                transport.close();
                System.out.println("发送成功！");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return smsdk;
        }

        public static MimeMessage createMimeMessage (InsuranceUserVo insuranceUserVo,Session session, String sendMail, String receiveMail)
            throws Exception {
            // 1. 创建一封邮件
            MimeMessage message = new MimeMessage(session);
            // 2. From: 发件人
            message.setFrom(new InternetAddress(sendMail, "阳光彩票", "UTF-8"));
            // 3. To: 收件人
            message.setRecipient(MimeMessage.RecipientType.TO,
                    new InternetAddress(receiveMail, "XX用户", "UTF-8"));
            // 4. Subject: 邮件主题
            message.setSubject("邮件验证测试", "UTF-8");
            //获取系统当前时间

            message.setContent(  insuranceUserVo.getUserCode()+ "用户你好，你昨日购买的9566655彩票以中一等奖，" + "请输入" + smsdk, "text/html;charset=UTF-8");
            // 6. 设置发件时间
            message.setSentDate(new Date());
            // 7. 保存设置
            message.saveChanges();
            return message;
        }
    }

