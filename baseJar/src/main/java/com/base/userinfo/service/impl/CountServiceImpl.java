package com.base.userinfo.service.impl;

import com.base.database.customtrading.mapper.TemBinListingDataReportMapper;
import com.base.database.trading.model.*;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.database.userinfo.model.UsercontrollerUserExample;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.userinfo.service.CountService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.DateUtils;
import com.base.utils.mailUtil.MailUtils;
import com.base.utils.mailUtil.SMSUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/3/5.
 */

@Service
public class CountServiceImpl implements CountService {
    static Logger logger = Logger.getLogger(CountServiceImpl.class);
    @Autowired
    private UsercontrollerUserMapper userMapper;
    @Autowired
    private TemBinListingDataReportMapper temBinListingDataReportMapper;
    @Autowired
    private UsercontrollerEbayAccountMapper ebayAccountMapper;

    @Override
    /**统计用户数量*/
    public int[] countUser(){
        int[] us=new int[3];
        //启用的账户总数
        UsercontrollerUserExample userExample=new UsercontrollerUserExample();
        userExample.createCriteria().andStatusEqualTo("1");
        us[0] = userMapper.countByExample(userExample);//启用账户总数

        userExample.clear();
        userExample.createCriteria().andStatusEqualTo("1").andUserParentIdIsNull();
        us[1] = userMapper.countByExample(userExample);//启用主账户总数

        userExample.clear();
        userExample.createCriteria().andStatusEqualTo("0");
        us[2] = userMapper.countByExample(userExample);//停用账户数

        //本系统所
        return us;

    }

    @Override
    /**查询启用的所有一级账户*/
    public List<UsercontrollerUserExtend> queryAllUser(Map map,Page page){
        return temBinListingDataReportMapper.queryAllUser(map,page);
    }

    @Override
    /**查询启用的所有账户*/
    public List<UsercontrollerUserExtend> queryAllUserS(Map map){
        return temBinListingDataReportMapper.queryAllUserS(map);
    }

    @Override
    /**查询绑定的ebay帐号个数**********************************************************************/
    public int getEbayCount(String userID){
        UsercontrollerEbayAccountExample ebayAccountExample=new UsercontrollerEbayAccountExample();
        ebayAccountExample.createCriteria().andUserIdEqualTo(Long.valueOf(userID) ).andEbayStatusEqualTo("1");
        return ebayAccountMapper.selectByExample(ebayAccountExample).size();
    }

    @Override
    /**查询绑定的ebay帐号List**********************************************************************/
    public List<UsercontrollerEbayAccount> getEbayList(String userID){
        UsercontrollerEbayAccountExample ebayAccountExample=new UsercontrollerEbayAccountExample();
        ebayAccountExample.createCriteria().andUserIdEqualTo(Long.valueOf(userID) ).andEbayStatusEqualTo("1");
        return ebayAccountMapper.selectByExample(ebayAccountExample);
    }

    /**
     * 本系统当天刊登数量********************************************************
     * @param userid
     * @return
     */
    @Override
    public int getDayListingDataCount(String userid){
        Map map  = new HashMap();
        map.put("flag","1");
        map.put("datestr", DateUtils.formatDate(new Date()));
        map.put("userid", userid);
        List<TradingListingData> lidaydata = this.temBinListingDataReportMapper.selectTemBinListingReportList(map);
        return lidaydata==null?0:lidaydata.size();
    }

    /**
     * 本系统总共刊登数量
     * @param userid
     * @return
     */
    @Override
    public int getTemBinAllListingDataCount(String userid){
        Map map  = new HashMap();
        map.put("flag","0");
        map.put("userid",userid);
        List<TradingListingData> lidaydata = this.temBinListingDataReportMapper.selectTemBinListingReportList(map);
        return lidaydata==null?0:lidaydata.size();
    }

    /**
     * 所有在线数量
     * @param userid
     * @return
     */
    @Override
    public int getAllListingDataCount(String userid){
        Map map  = new HashMap();
        map.put("userid",userid);
        List<TradingListingData> lidaydata = this.temBinListingDataReportMapper.selectAllListingDataReportList(map);
        return lidaydata==null?0:lidaydata.size();
    }

    /**
     * 主账号所有SKU
     * @param userid
     * @return
     */
    @Override
    public int querySkuByUser(String userid){
        Map map  = new HashMap();
        map.put("userid",userid);
        List<TradingItem> lidata = this.temBinListingDataReportMapper.querySkuByUser(map);
        return lidata==null?0:lidata.size();
    }

    @Override
    /**激活账号*/
    public void activeNewUser(String userId) throws Exception{

        UsercontrollerUser u=userMapper.selectByPrimaryKey(Integer.valueOf(userId));
        if (u==null){logger.error("没有id为"+userId+"的帐号需要激活");return;}

        UsercontrollerUser user=new UsercontrollerUser();
        user.setStatus("1");

        UsercontrollerUserExample example=new UsercontrollerUserExample();
        example.createCriteria().andUserIdEqualTo(Integer.valueOf(userId))
                .andStatusEqualTo("2");
        userMapper.updateByExampleSelective(user, example);

        String email=u.getUserEmail();
        if (StringUtils.isNotBlank(email)){
            Email emaill = new HtmlEmail();
            emaill.addTo(email);
            emaill.setSubject("TemBin审核通知");
            emaill.setMsg("您好！您注册的TemBin账号已经通过审核！现在开始 <a href=http://www.tembin.com/xsddWeb/>登陆</a> 使用吧！<br/>" +
                    "流程为：绑定paypal–绑定ebay–为ebay指定默认paypal–一键搬家–刊登等其他操作<br/>" +
                    "如需帮助请加群 244373066  <br/>");
            MailUtils mailUtils= ApplicationContextUtil.getBean(MailUtils.class);
            mailUtils.sendMail(emaill);
        }
        if (StringUtils.isNotBlank(u.getTelPhone())){
            if (u.getTelPhone().length()!=11){return;}
            SMSUtils.sendREGSMS(u.getTelPhone(),"您申请注册的TemBin账号已经通过审核！欢迎登陆使用！谢谢支持！帮助请加群244373066");
        }


    }

}
