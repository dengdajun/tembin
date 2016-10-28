package com.base.utils.threadpool;

import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.cxfclient.CXFPostClient;
import com.base.utils.httpclient.ApiHeader;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.xmlutils.CheckResXMLUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by wula on 2014/8/6.
 */
public class ApiCallable implements Callable {
    static Logger logger = Logger.getLogger(ApiCallable.class);

    private static final String enCode="utf-8";//使用utf8编码
    private UsercontrollerDevAccountExtend devAccountExtend;
    private String xml;
    private String url;

    @Override
    public Object call() throws Exception {
        if(xml==null || url==null || devAccountExtend ==null){return null;}

        String black= MainTaskStaticParam.blackBill.get(devAccountExtend.getApiCallName() + devAccountExtend.getRepetitionMark());
        if (StringUtils.isNotEmpty(black)){
            logger.error("已经进入黑名单!"+devAccountExtend.getApiCallName()+":"+devAccountExtend.getRepetitionMark());
            return "black";
        }

        String isLimit=SessionCacheSupport.getOther("devLimit");
        if(StringUtils.isNotEmpty(isLimit) && "limit".equalsIgnoreCase(isLimit)){
            return "apiLimit";
        }
        Date botBlock=TempStoreDataSupport.pullData("BotBlock_"+devAccountExtend.getApiCallName());
        if (!ObjectUtils.isLogicalNull(botBlock)){
            return null;
        }
        Date apiFail=TempStoreDataSupport.pullData("ApiFail_"+devAccountExtend.getApiCallName());
        if (!ObjectUtils.isLogicalNull(apiFail)){
            return "apiFail";
        }
        Date apiFail2Date=TempStoreDataSupport.pullData("ApiFail2_" + devAccountExtend.getApiCallName() + "_" + devAccountExtend.getRepetitionMark());
        if (!ObjectUtils.isLogicalNull(apiFail2Date)){
            logger.error("已经进入长时间限制的名单>>>>>"+devAccountExtend.getApiCallName()+":"+devAccountExtend.getRepetitionMark());
            return null;
        }


        String key="";
        if(StringUtils.isNotEmpty(devAccountExtend.getRepetitionMark())
                && "task".equalsIgnoreCase(devAccountExtend.getSourceMark())){
            key=devAccountExtend.getApiCallName()+devAccountExtend.getRepetitionMark();
            Integer isReapt= TempStoreDataSupport.pullData(key);
            isReapt=(isReapt==null)?0:isReapt;
            isReapt=isReapt+1;
            TempStoreDataSupport.pushData(key,isReapt);
            if(isReapt!=null&&isReapt!=0){
                if (isReapt>=4){
                    logger.error("不能提交重复的xml"+devAccountExtend.getRepetitionMark());
                    if (isReapt>=5){
                        logger.error("加入黑名单!"+devAccountExtend.getRepetitionMark());
                        MainTaskStaticParam.blackBill.put(key,"重复太多！");
                    }
                    return "repeti";}
            }
        }

        /**调用方法的主体===============================================================*/

        List<BasicHeader> headers=null;
        if(ApiHeader.DISPUTE_API_HEADER.equalsIgnoreCase(devAccountExtend.getHeaderType())){
            headers=ApiHeader.getDisputeApiHeader(devAccountExtend);
        }else {
            headers = ApiHeader.getApiHeader(devAccountExtend);
        }

        CXFPostClient cxfPostClient = (CXFPostClient) ApplicationContextUtil.getBean(CXFPostClient.class);

        String res = cxfPostClient.sendPostAction(headers,url,xml);

        /**关于错误的一些验证================================================*/
        if (StringUtils.isBlank(res)){return null;}
        boolean b= CheckResXMLUtil.checkApiLimit(res);
        if(b){
            SessionCacheSupport.putOther("devLimit","limit");//放入次数限制的参数
            return "apiLimit";
        }
        if (StringUtils.isNotBlank(devAccountExtend.getApiCallName())
                &&"task".equalsIgnoreCase(devAccountExtend.getSourceMark())){
            /**检查是否出现了验证码*/
            boolean bb= CheckResXMLUtil.checkBotBlock(res);
            if (bb){
                logger.error(devAccountExtend.getApiCallName()+"任务出现验证码即将暂停！===========");
                TempStoreDataSupport.pushDataByTime("BotBlock_"+devAccountExtend.getApiCallName(),new Date(),300);
                return null;
            }
            /**检查是否调用api失败*/
            String bbbs= CheckResXMLUtil.checkAckFail(res);
            boolean bbb=false;
            Integer time=1;

            if ("false".equalsIgnoreCase(bbbs)){
                bbb=false;
            }else if("true".equalsIgnoreCase(bbbs)){
                    time=5;
                    bbb=true;
                Integer t = EbayErrorCodeStatic.small_err.get(bbbs);
                if (t!=null){
                    time=t;
                }
            }else {
                bbb=false;
                Integer t=EbayErrorCodeStatic.small_err.get(bbbs);
                if (t==null){t=10;}
                if (StringUtils.isBlank(devAccountExtend.getRepetitionMark())){
                    logger.error(">>出现不能处理的errcode:"+bbbs);
                }
                else {
                    TempStoreDataSupport.pushDataByTime("ApiFail2_"+devAccountExtend.getApiCallName()+"_"+devAccountExtend.getRepetitionMark(),new Date(),t);
                }

            }
            if (bbb){
                logger.error(devAccountExtend.getApiCallName()+"任务出现失败情况即将暂停！"+time+"分钟===========");
                TempStoreDataSupport.pushDataByTime("ApiFail_"+devAccountExtend.getApiCallName(),new Date(),time);
                return res;
            }
        }




        return res;
    }

    public ApiCallable(UsercontrollerDevAccountExtend dev,String xml,String url){
        this.devAccountExtend=dev;
        this.url=url;
        this.xml=xml;

    }



}
