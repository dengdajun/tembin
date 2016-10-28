package com.weixin.util;

import com.base.utils.common.MyStringUtil;
import com.weixin.vo.MsgVO;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2015/5/22.
 * 解析微信的xml字符串
 */
public class WeiXinStringUtil {
    /**获取发送人*/
    public static String getFromUserName(String target){
        return getCustomString(target,"FromUserName");
    }
    /**获取createTime*/
    public static String getCreateTime(String target){
        return getCustomString(target,"CreateTime");
    }
    /**获取MsgType*/
    public static String getMsgType(String target){
        return getCustomString(target,"MsgType");
    }
    /**获取Event*/
    public static String getEvent(String target){
        return getCustomString(target,"Event");
    }
    /**获取EventKey*/
    public static String getEventKey(String target){
        return getCustomString(target,"EventKey");
    }

    /**消息事件获取消息*/
    public static MsgVO getMsg(String target){
        MsgVO m=new MsgVO();
        m.setContent(getCustomString(target, "Content"));
        m.setMsgId(getCustomString(target, "MsgId"));
        m.setCreateTime(getCreateTime(target));
        m.setFromUserName(getFromUserName(target));
        m.setMsgType(getMsgType(target));
        m.setToUserName(getCustomString(target,"ToUserName"));
        return m;
    }


    /**得到指定的结点字符串*/
    public static String getCustomString(String target,String s){
        String open1="<"+s+"><![CDATA[";
        String open2="<"+s+">";
        String close1="]]></"+s+">";
        String close2="</"+s+">";
        String res= MyStringUtil.subStringBetween(target,open1,close1);
        if (StringUtils.isBlank(res)){
            res=MyStringUtil.subStringBetween(target,open2,close2);
        }
        if ("<![CDATA[]]>".equalsIgnoreCase(res)){
            res="";
        }
        return res;
    }
}
