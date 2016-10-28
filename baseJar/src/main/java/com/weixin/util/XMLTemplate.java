package com.weixin.util;

import com.weixin.vo.MsgVO;

import java.util.Date;

/**
 * Created by Administrator on 2015/5/22.
 * xml模板
 */
public class XMLTemplate {
    /**被动回复消息*/
    public static String bdReturnMessage(MsgVO msgVO){
        String xml="<xml>" +
                "<ToUserName><![CDATA["+msgVO.getToUserName()+"]]></ToUserName>" +
                "<FromUserName><![CDATA["+msgVO.getFromUserName()+"]]></FromUserName>" +
                "<CreateTime>"+new Date().getTime()+"</CreateTime>" +
                "<MsgType><![CDATA["+msgVO.getMsgType()+"]]></MsgType>" +
                "<Content><![CDATA["+msgVO.getContent()+"]]></Content>" +
                "</xml>";
        return xml;
    }
}
