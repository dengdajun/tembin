package com.base.utils.scheduleother.dorun;

import com.base.database.userinfo.mapper.UsercontrollerVipUserMapper;
import com.base.database.userinfo.model.UsercontrollerVipUser;
import com.base.database.userinfo.model.UsercontrollerVipUserExample;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.service.SystemVIPUserCheckService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/6/2.
 * 检查更新需要付费的用户
 */
public class SystemVIPUserCheckRun implements ScheduleOtherBase {
    static Logger logger = Logger.getLogger(SystemVIPUserCheckRun.class);

    @Override
    public String stype() {
        return StaticParam.SYSTEM_VIP_USER_CHECK;
    }

    @Override
    public Integer cyclesTime() {
        return 600;
    }

    @Override
    public void run() {
        //Date lastDayThisMonth= DateUtils.turnToMonthEnd(new Date());

        //开启只在本月最后一天执行
        /*if (!DateUtils.isSameDay(lastDayThisMonth,new Date())){
            return;
        }*/

        /*UsercontrollerVipUserMapper mapper=ApplicationContextUtil.getBean(UsercontrollerVipUserMapper.class);
        UsercontrollerVipUserExample example=new UsercontrollerVipUserExample();
        example.setOrderByClause("update_Time desc");
        example.createCriteria().andStatusEqualTo("0");
        List<UsercontrollerVipUser> vipUsers=mapper.selectByExample(example);
        if (!ObjectUtils.isLogicalNull(vipUsers)){
            Date updateTime=vipUsers.get(0).getUpdateTime();            //本月的最后一天

            if (DateUtils.isSameDay(updateTime,new Date())){
                return;
            }
            if (!DateUtils.isSameDay(lastDayThisMonth, new Date())) {
                //上个月最后一天
                Date lastDayPreMonth= DateUtils.turnToMonthEnd(new DateTime(new Date()).minusMonths(1).toDate());
                int lmark=DateUtils.comparTwoDate(updateTime, lastDayPreMonth);//应该为0或1
                int nmark=DateUtils.comparTwoDate(updateTime,lastDayThisMonth);//应该为-1

                if ((lmark==0||lmark==1)&&nmark==-1){
                    return;
                }

            }
        }*/

        SystemVIPUserCheckService vipUserCheckService= ApplicationContextUtil.getBean(SystemVIPUserCheckService.class);
        vipUserCheckService.checkVip();
        if (ObjectUtils.isLogicalNull(StaticParam.xietiao)){
            StaticParam.xietiao.add("yes");
        }

    }
}
