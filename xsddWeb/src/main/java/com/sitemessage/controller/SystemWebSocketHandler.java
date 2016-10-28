package com.sitemessage.controller;

import com.base.utils.cache.SessionCacheSupport;
import org.apache.log4j.Logger;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/5/19.
 */
public class SystemWebSocketHandler implements WebSocketHandler {
    private static final ArrayList<WebSocketSession> users;
    static Logger logger = Logger.getLogger(SystemWebSocketHandler.class);
    static {
        users = new ArrayList<>();
    }



        @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            logger.debug("connect to the websocket success......");
            users.add(session);
            String userName = (String) session.getAttributes().get(SessionCacheSupport.USERLOGINID);
            if(userName!= null){
                //查询未读消息
                //int count = webSocketService.getUnReadNews((String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME));

                session.sendMessage(new TextMessage("ssx" + ""));
            }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
//sendMessageToUsers();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket connection closed......");
        users.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        users.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
           if (user.isOpen()) {
               user.sendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(SessionCacheSupport.USERLOGINID).equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
