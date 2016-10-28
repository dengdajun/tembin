package com.weixin.util;

/**
 * Created by Administrator on 2015/5/22.
 * 关于微信中一些用到的静态变量
 */
public class WeiXinStaticPairm {
    /**事件*/
    public static final String msgType="event";
    /**用户之前未关注现在新关注的事件*/
    public static final String subscribe="subscribe";
    /**用户已关注的事件---不能理解*/
    public static final String SCAN="SCAN";
    /**取消关注事件*/
    public static final String unsubscribe="unsubscribe";
    /**点击自定义菜单的事件*/
    public static final String CLICK="CLICK";
    /**点击自定义菜单跳转的事件*/
    public static final String VIEW="VIEW";

    /**接收普通消息的事件*/
    public static final String text="text";

    /**根据messagetype来确定使用哪个接口的方法*/
}
