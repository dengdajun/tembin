package com.weixin.service;

/**
 * Created by Administrator on 2015/5/22.
 */
public interface MsgTextService {
    /**新关注的用户处理*/
    String newUser(String reqBody);

    /**收到消息后被动回复*/
    String replayMessage(String reqBody);
}
