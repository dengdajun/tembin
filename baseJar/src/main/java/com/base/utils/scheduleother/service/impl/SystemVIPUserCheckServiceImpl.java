package com.base.utils.scheduleother.service.impl;

import com.base.database.customtrading.mapper.TemBinListingDataReportMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserBillMapper;
import com.base.database.userinfo.mapper.UsercontrollerVipUserMapper;
import com.base.database.userinfo.model.SystemLog;
import com.base.database.userinfo.model.UsercontrollerVipUser;
import com.base.database.userinfo.model.UsercontrollerVipUserExample;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.userinfo.mapper.SystemUserManagerServiceMapper;
import com.base.userinfo.service.CountService;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.service.SystemVIPUserCheckService;
import com.base.utils.scheduleother.service.SystemVipUserCostFeeService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/2.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemVIPUserCheckServiceImpl implements SystemVIPUserCheckService{
    static Logger logger = Logger.getLogger(SystemVIPUserCheckServiceImpl.class);

    @Autowired
    private CountService countService;
    @Autowired
    private SystemUserManagerServiceMapper systemUserManagerServiceMapper;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private UsercontrollerVipUserMapper vipUserMapper;
    @Autowired
    private SystemVipUserCostFeeService costFeeService;


    @Override
    /**查询需要扣费的列表*/
    public List<UsercontrollerVipUser> queryVipList(){
        UsercontrollerVipUserExample example=new UsercontrollerVipUserExample();
        example.createCriteria().andStatusEqualTo("0");
        List<UsercontrollerVipUser> vipUsers = vipUserMapper.selectByExample(example);
        return vipUsers;
    }


    @Override
    public void checkVip(){
        //先查出所有的一级帐户
        Page page=Page.newAOnePage();
        List<UsercontrollerUserExtend> uue=countService.queryAllUser(new HashMap(), page);

        //查询当前帐户有多少子账户
        for (UsercontrollerUserExtend users:uue){
            Date regDate=users.getCreateTime();//注册日期
            if (regDate==null){continue;}
            int days= DateUtils.daysBetween(regDate,new Date());//注册了多少天了
            if (days<30){continue;}

            if (users.getNewLoinTime()!=null){
                int logdays= DateUtils.daysBetween(users.getNewLoinTime(),new Date());//多少天没有登陆了
                //logger.error("=========="+users.getUserLoginId()+">>>"+logdays+">>>"+users.getNewLoinTime());
                if (logdays>30){//如果大于30天了，那么就停止所有账户以及绑定的ebay账户
                    SystemLog systemLog=new SystemLog();
                    systemLog.setEventname("stopUserAndEbay");
                    systemLog.setOperuser("system");
                    systemLog.setEventdesc("账户:" + users.getUserLoginId() + " 很久没有登陆，被停用！");
                    try {
                        SystemLogUtils.saveLog(systemLog);
                        systemUserManagerService.stopAllUserByOrg(users.getUserOrgId().toString());
                    } catch (Exception e) {
                        logger.error(e);
                    }
                    continue;
                }

            }


            UsercontrollerVipUserExample example=new UsercontrollerVipUserExample();
            //example.setOrderByClause("updateTime desc");
            example.createCriteria().andUserIdEqualTo(users.getUserId().toString());
            List<UsercontrollerVipUser> vipUsers=vipUserMapper.selectByExample(example);

            int subUsers=0;//子账号数量
            int onlineListing=0;//在线刊登数量
            int ebayCount=0;//ebay帐号


            Map map=new HashMap();
            map.put("orgID",users.getUserOrgId());
            map.put("isShowStopOnly", "no");
            List<UsercontrollerUserExtend> x= systemUserManagerServiceMapper.queryAllUsersByOrgID(map);
            if (!ObjectUtils.isLogicalNull(x)){
                subUsers=x.size();
                if (subUsers==0){subUsers=1;}
            }

            onlineListing=countService.getAllListingDataCount(users.getUserId().toString());

            ebayCount=countService.getEbayCount(users.getUserId().toString());




            if (onlineListing>99 || ebayCount>1 || subUsers>1){
                if (ObjectUtils.isLogicalNull(vipUsers)){
                    //新增的时候，计费日期为30天后
                    Date after30= DateUtils.nowDateAddDay(StaticParam.costDay);//下次计费日期
                    UsercontrollerVipUser vipUser=new UsercontrollerVipUser();
                    vipUser.setStatus("0");
                    vipUser.setUpdateTime(new Date());
                    vipUser.setCreateTime(new Date());
                    vipUser.setEbayCount(ebayCount);
                    vipUser.setOllistingCount(onlineListing);
                    vipUser.setSubCount(subUsers);
                    vipUser.setUserId(users.getUserId().toString());
                    vipUser.setLastLoginTime(users.getNewLoinTime());
                    vipUser.setNextCostTime(after30);
                    vipUserMapper.insert(vipUser);
                    costFeeService.sfCost(vipUser);
                }else {
                    UsercontrollerVipUser vipUser=vipUsers.get(0);
                    if (DateUtils.comparTwoDate(new Date(),vipUser.getNextCostTime())==-1){
                        continue;
                    }


                    Date lastCost = vipUser.getNextCostTime();
                    if (lastCost==null){
                      lastCost=new Date();
                    }
                    Date after30 = DateUtils.dateAddDay(lastCost,StaticParam.costDay);//下次计费日期
                    vipUser.setNextCostTime(after30);
                    vipUser.setStatus("0");
                    vipUser.setUpdateTime(new Date());
                    vipUser.setEbayCount(ebayCount);
                    vipUser.setOllistingCount(onlineListing);
                    vipUser.setSubCount(subUsers);
                    vipUser.setLastLoginTime(users.getNewLoinTime());
                    vipUserMapper.updateByPrimaryKeySelective(vipUser);
                    costFeeService.sfCost(vipUser);
                }
            }else {
                if (!ObjectUtils.isLogicalNull(vipUsers)){
                    UsercontrollerVipUser vipUser=vipUsers.get(0);
                    vipUser.setStatus("1");
                    vipUser.setUpdateTime(new Date());
                    vipUser.setEbayCount(ebayCount);
                    vipUser.setOllistingCount(onlineListing);
                    vipUser.setSubCount(subUsers);
                    vipUser.setLastLoginTime(users.getNewLoinTime());
                    vipUserMapper.updateByPrimaryKeySelective(vipUser);
                }

            }

        }


    }

    @Override
    public UsercontrollerVipUser selectByUserId(String userId){
        UsercontrollerVipUserExample example=new UsercontrollerVipUserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UsercontrollerVipUser> vipUsers=vipUserMapper.selectByExample(example);
        if(vipUsers==null||vipUsers.size()==0){
            return null;
        }else{
            return vipUsers.get(0);
        }
    }
}
