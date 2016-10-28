package com.base.utils.mailUtil;


import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.threadpool.TaskPool;
import org.apache.commons.mail.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Created by Administrator on 2014/10/13.
 * 邮件发送工具类,只需填入发送人和发送内容
 *  Email emaill = new HtmlEmail();
 emaill.addTo(email);
 emaill.setSubject("TemBin审核通知");
 emaill.setMsg("");
 */
@Component("mailUtils")
public class MailUtils {
    static Logger logger = Logger.getLogger(MailUtils.class);

    /**关于mail发送的参数*/
    @Value("${MAIL.HOSTNAME}")
    public String hostName;//服务商url
    @Value("${MAIL.PORT}")
    public String port;//端口
    @Value("${MAIL.USERNAME}")
    public String userName;
    @Value("${MAIL.PASSWORD}")
    public String userPassword;
    @Value("${MAIL.FROM}")
    public String mailFrom;

    public void sendMail(Email email){
        String toMail=email.getToAddresses().get(0).toString();

        Integer cs=TempStoreDataSupport.pullData("sendMail_" + toMail);
        Date csd=TempStoreDataSupport.pullData("sendMailTime_"+toMail);
        if (csd!=null){
            logger.error(toMail+"邮件发送出现异常频率太高"+csd);
            return;
        }

        if (cs!=null&&cs>=4){
            logger.error(toMail+"邮件发送出现异常在五分钟内发送的邮件超过限制数量！"+cs);
            return;
        }
        if (cs==null){
            TempStoreDataSupport.pushDataByTime("sendMail_"+toMail,1,5);
        }else {
            TempStoreDataSupport.pushDataByTime("sendMail_"+toMail,cs++,5);
        }
        TempStoreDataSupport.pushDataByIdelTime("sendMailTime_" + toMail, new Date(), 1);//记录邮件发送时间,一分钟内只能给同一个人发送一封邮件
        TaskPool.otherScheduledThreadPoolTaskExecutor.execute(new MainRunable(email));
        /*Email email1 = new SimpleEmail();
        email1.setHostName("smtp.126.com");
        email1.setSmtpPort(Integer.valueOf("465"));
        email1.setAuthenticator(new DefaultAuthenticator("byuniversal99@126.com", "byby12345"));
        email1.setSSLOnConnect(true);
        email1.setCharset("UTF-8");
        email1.setFrom("byuniversal99@126.com");
        email1.addTo("892129701@qq.com");
        email1.setSubject("tembin密码修改验证码叫姐姐");
        email1.setMsg("您正在进行密码找回操作，本次操作验证码为:");
        email1.send();*/


    }

    /*public static void main(String[] args) throws Exception {
        MailUtils mailUtils = new MailUtils();

        mailUtils.sendMail2(null);
    }


    public void sendMail2(Email email) throws Exception {
        if(email==null){
            email = new HtmlEmail();

        }
        email.setHostName("smtp.exmail.qq.com");
        email.setSmtpPort(Integer.valueOf("465"));
        // email.setAuthenticator(null);
        email.setAuthenticator(new DefaultAuthenticator("info@tembin.com", "QWQW123"));
        email.setSSLOnConnect(true);
        email.setCharset("UTF-8");
        email.setFrom("info@tembin.com");
        email.addTo("892129701@qq.com");
        email.setSubject("ddd");
        email.setMsg("<a href=\"http://www.baidu.com\"><img src=\"http://www.baidu.com/img/baidu_jgylogo3.gif\"/></a>");
        email.send();
    }*/

class MainRunable implements Runnable{
    private Email email;

    public MainRunable(Email email){
        this.email=email;
    }
    @Override
    public void run() {
        try {
            email.setHostName(hostName);
            email.setSmtpPort(Integer.valueOf(port));
            email.setAuthenticator(new DefaultAuthenticator(userName, userPassword));
            email.setSSLOnConnect(true);
            email.setCharset("UTF-8");
            email.setFrom(mailFrom);
            email.send();
        } catch (Exception e) {
            logger.error("发送邮件出错了！>>"+mailFrom+">>"+email.getToAddresses(),e);
        }
    }
}
}

