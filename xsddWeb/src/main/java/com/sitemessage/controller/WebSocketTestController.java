package com.sitemessage.controller;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/5/19.
 */
@Controller
public class WebSocketTestController {
    static Logger logger = Logger.getLogger(WebSocketTestController.class);

    @Bean
    public SystemWebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }

    @RequestMapping("/auditing")
    @ResponseBody
    public void auditing(HttpServletRequest request){
        //无关代码都省略了
        //int unReadNewsCount = adminService.getUnReadNews(username);
        systemWebSocketHandler().sendMessageToUsers(new TextMessage("--xc" + ""));
    }
}
