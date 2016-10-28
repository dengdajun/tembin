package com.base.utils.common;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.mailUtil.MailUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

/**
 * Created by Administrtor on 2015/9/29.
 * 邮件发送静态工具类
 */
public class SendEMailUtil {
    static Logger logger = Logger.getLogger(SendEMailUtil.class);

    //发送异常信息给管理员
    public static void sendEmail2Admin(String content, String title){
        try {
            MailUtils mailUtils= (MailUtils) ApplicationContextUtil.getBean("mailUtils", MailUtils.class);
            Email email=new HtmlEmail();
            email.addTo("410705498@qq.com");
            email.addCc("892129701@qq.com");
            email.setSubject(title);
            email.setMsg(content);
            mailUtils.sendMail(email);
        } catch (Exception e) {
            logger.error(e);
        }
    }
    /*发送
    Email emaill = new HtmlEmail();
     emaill.addTo(email);
     emaill.setSubject("TemBin审核通知");
     emaill.setMsg("");
     */
    public static void sendEmail(Email email){
        try {
            MailUtils mailUtils= (MailUtils) ApplicationContextUtil.getBean("mailUtils", MailUtils.class);
            mailUtils.sendMail(email);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
