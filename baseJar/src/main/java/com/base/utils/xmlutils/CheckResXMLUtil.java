package com.base.utils.xmlutils;

import com.base.utils.common.MyStringUtil;
import com.base.utils.threadpool.EbayErrorCodeStatic;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2014/12/4.
 * 定义一些指定的err xml
 */
public class CheckResXMLUtil {
    static Logger logger = Logger.getLogger(CheckResXMLUtil.class);

    /**api次数用完的警告*/
    public static boolean checkApiLimit(String res){
        if(StringUtils.isNotEmpty(res) &&
                res.indexOf("<ErrorCode>518</ErrorCode>")>-1 &&
                res.indexOf("<Ack>Failure</Ack>")>-1 &&
                res.indexOf("limit")>-1
                ){
            logger.error("api次数用完！");
            return true;
        }
        return false;
    }

    /**请求是否出现了验证码*/
    public static boolean checkBotBlock(String res){
        if (MyStringUtil.containsSpecStr(res,"<BotBlock>")){
            String bb=MyStringUtil.getStringBetween2char(res,"<BotBlock>","</BotBlock>");
            logger.error("api出现验证码！"+bb);
            return true;
        }
        return false;
    }
    /**请求出现了失败的情况*/
    public static String checkAckFail(String res){
        if (MyStringUtil.containsSpecStr(res,"<Ack>Failure</Ack>")){
            String bb=MyStringUtil.getStringBetween2char(res,"<LongMessage>","</LongMessage>");
            String errCode=MyStringUtil.getStringBetween2char(res,"<ErrorCode>","</ErrorCode>");
            if (EbayErrorCodeStatic.request_timed_out.equalsIgnoreCase(errCode)){//如果是超时的错误，不暂停
                return "false";
            }
            if (EbayErrorCodeStatic.request_is_suspended841.equalsIgnoreCase(errCode)){//如果是帐号锁定的错误
                return errCode;
            }
            logger.error("调用失败！"+bb+",errCode:"+errCode);
            if (StringUtils.isNotBlank(errCode)){return errCode;}
            return "true";
        }
        return "false";
    }
}
