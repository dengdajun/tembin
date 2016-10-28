package com.base.utils.scheduleother.dorun;

import com.base.database.userinfo.model.UsercontrollerVipUser;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.service.SystemVIPUserCheckService;
import com.base.utils.scheduleother.service.SystemVipUserCostFeeService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/6/3.
 * 扣月费的任务
 */
public class SystemVIPUserCostFeeRun implements ScheduleOtherBase {
    static Logger logger = Logger.getLogger(SystemVIPUserCheckRun.class);

    @Override
    public String stype() {
        return StaticParam.SYSTEM_VIP_USER_COST_FEE;
    }

    @Override
    public Integer cyclesTime() {
        return 120;
    }

    @Override
    public void run() {
        if (ObjectUtils.isLogicalNull(StaticParam.xietiao)){
            logger.error("=不执行！=");
            return;
        }
        /*String tname=stype()+"_isruned";
        String x=TempStoreDataSupport.pullData(tname);
        if (StringUtils.isBlank(x)){
            TempStoreDataSupport.pushDataByTime(tname,"yes",3000);
            return;
        }*/
        logger.error(stype()+"=执行=======================");
        /*Date lastDayThisMonth= DateUtils.turnToMonthEnd(new Date());
        if (DateUtils.isSameDay(new Date(),lastDayThisMonth)){

        }else {
            return;
        }*/

        //查出需要计费的帐号列表
        SystemVIPUserCheckService checkService= ApplicationContextUtil.getBean(SystemVIPUserCheckService.class);
        List<UsercontrollerVipUser> vipUserList=checkService.queryVipList();
        if (ObjectUtils.isLogicalNull(vipUserList)){return;}

        SystemVipUserCostFeeService costFeeService=ApplicationContextUtil.getBean(SystemVipUserCostFeeService.class);

        for (UsercontrollerVipUser u:vipUserList){
            //if (DateUtils.isSameDay(u.getUpdateTime(),u.getLastCostTime())){continue;}
            //if (DateUtils.comparTwoDate(u.getLastCostTime(),u.getUpdateTime())==1){continue;}
            //if(DateUtils.minuteBetween(u.getLastCostTime(),u.getUpdateTime())<0){continue;}
            if (u.getLastCostTime()!=null){
                if (DateUtils.isSameDay(u.getUpdateTime(),u.getLastCostTime())){

                    int i= DateUtils.comparTwoDate(new Date(), u.getNextCostTime());
                    if (i==-1){
                        continue;
                    }

                }
            }
            int i=40;
            if (u.getLastCostTime()!=null){
                i=DateUtils.daysBetween(u.getLastCostTime(),u.getNextCostTime());
            }
            if (DateUtils.comparTwoDate(new Date(), u.getNextCostTime())==-1
                    &&u.getLastCostTime()!=null
                    &&i<30){
                continue;
            }

            costFeeService.sfCost(u);

        }
    }
}
