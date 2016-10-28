package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.mapper.UsercontrollerEbayAccountInfoMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.database.trading.model.UsercontrollerEbayAccountInfo;
import com.base.database.trading.model.UsercontrollerEbayAccountInfoExample;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.ObjectUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 检查ebay帐号信息，定时任务
 */
public class CheckEbayAccountInfoRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(CheckEbayAccountInfoRun.class);
    @Override
    public void run() {
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria().andEbayStatusEqualTo("1");
        List<UsercontrollerEbayAccount> liue = ueam.selectByExampleWithBLOBs(ueame);
        if (ObjectUtils.isLogicalNull(liue)){
            return;
        }
        for (UsercontrollerEbayAccount ebayAccount : liue){
            String xml= BindAccountAPI.getEbayUserInfo(ebayAccount.getEbayToken());
            AddApiTask addApiTask = new AddApiTask();
            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
            d.setApiSiteid("0");
            d.setApiCallName(APINameStatic.GetUser);
            try {
                Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                String r11=resMap.get("stat");
                String res1=resMap.get("message");
                if ("fail".equalsIgnoreCase(r11)||StringUtils.isBlank(res1)){
                    continue;
                }
                String ack1 = SamplePaseXml.getVFromXmlString(res1,"Ack");
                if("Success".equalsIgnoreCase(ack1)){
                    String userID= SamplePaseXml.getSpecifyElementTextAllInOne(res1,"User","UserID");
                    String eAccount=ebayAccount.getEbayAccount();
                    if (StringUtils.isBlank(eAccount)){
                        eAccount=ebayAccount.getEbayName();
                    }
                    if (StringUtils.isNotBlank(userID) && !eAccount.equalsIgnoreCase(userID)){
                        SystemLog systemLog=new SystemLog();
                        systemLog.setEventdesc("账户名变动!["+ebayAccount.getEbayAccount()+"]变为:["+userID+"]");
                        systemLog.setOperuser(ebayAccount.getId().toString());
                        systemLog.setEventname(SystemLogUtils.EBAY_ACCOUNT_CHANGE_LOG);
                        systemLog.setCreatedate(new Date());
                        SystemLogUtils.saveLog(systemLog);

                        UsercontrollerEbayAccount account=new UsercontrollerEbayAccount();
                        account.setId(ebayAccount.getId());
                        account.setEbayName(userID);
                        account.setEbayAccount(userID);
                        ueam.updateByPrimaryKeySelective(account);
                    }
                    UsercontrollerEbayAccountInfoMapper uinfo = (UsercontrollerEbayAccountInfoMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountInfoMapper.class);
                    UsercontrollerEbayAccountInfoExample example=new UsercontrollerEbayAccountInfoExample();
                    example.createCriteria().andEbayIdEqualTo(ebayAccount.getId());
                    List<UsercontrollerEbayAccountInfo> uinfoList = uinfo.selectByExample(example);
                    String FeedbackScore= SamplePaseXml.getSpecifyElementTextAllInOne(res1,"User","FeedbackScore");
                    String PositiveFeedbackPercent= SamplePaseXml.getSpecifyElementTextAllInOne(res1,"User","PositiveFeedbackPercent");
                    String UserIDLastChanged= SamplePaseXml.getSpecifyElementTextAllInOne(res1,"User","UserIDLastChanged");
                    String Status= SamplePaseXml.getSpecifyElementTextAllInOne(res1,"User","SellerInfo","Status");
                    if (ObjectUtils.isLogicalNull(uinfoList)){
                        UsercontrollerEbayAccountInfo ebayAccountInfo=new UsercontrollerEbayAccountInfo();
                        ebayAccountInfo.setEbayId(ebayAccount.getId());
                        ebayAccountInfo.setFeedbackscore(FeedbackScore);
                        ebayAccountInfo.setPositivefeedbackpercent(PositiveFeedbackPercent);
                        ebayAccountInfo.setStatus(Status);
                        ebayAccountInfo.setUseridlastchanged(UserIDLastChanged);
                        ebayAccountInfo.setUpdateTime(new Date());
                        uinfo.insertSelective(ebayAccountInfo);
                    }else {
                        UsercontrollerEbayAccountInfo xx= uinfoList.get(0);
                        xx.setFeedbackscore(FeedbackScore);
                        xx.setPositivefeedbackpercent(PositiveFeedbackPercent);
                        xx.setStatus(Status);
                        xx.setUseridlastchanged(UserIDLastChanged);
                        xx.setUpdateTime(new Date());
                        uinfo.updateByPrimaryKeySelective(xx);
                    }
                }
                else {
                    String errors=SamplePaseXml.getVFromXmlString(res1,"Errors");
                    logger.error("获取账户信息失败！"+ebayAccount.getEbayName()+"::"+res1);
                }


            } catch (Exception e) {
                logger.error("获取ebay帐号信息报错！"+ebayAccount.getEbayName(),e);
                continue;
            }
        }
    }






    public CheckEbayAccountInfoRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.CHECK_EBAY_ACCOUNT_DESC;
    }

    @Override
    public Integer crTimeMinu() {
        return MainTaskStaticParam.SOME_CRTIMEMINU.get(MainTask.CHECK_EBAY_ACCOUNT_DESC);
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
