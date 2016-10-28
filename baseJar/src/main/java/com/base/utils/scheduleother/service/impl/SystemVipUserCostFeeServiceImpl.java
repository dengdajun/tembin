package com.base.utils.scheduleother.service.impl;

import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.userinfo.mapper.UsercontrollerUserBillMapper;
import com.base.database.userinfo.mapper.UsercontrollerVipUserMapper;
import com.base.database.userinfo.model.UsercontrollerUserBill;
import com.base.database.userinfo.model.UsercontrollerUserBillExample;
import com.base.database.userinfo.model.UsercontrollerVipUser;
import com.base.database.userinfo.model.UsercontrollerVipUserExample;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.userinfo.service.CountService;
import com.base.utils.common.DateUtils;
import com.base.utils.common.MyNumberUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.service.SystemVIPUserCheckService;
import com.base.utils.scheduleother.service.SystemVipUserCostFeeService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Administrator on 2015/6/3.
 * 关于扣费
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemVipUserCostFeeServiceImpl implements SystemVipUserCostFeeService{
    static Logger logger = Logger.getLogger(SystemVipUserCostFeeServiceImpl.class);

    @Autowired
    private UsercontrollerUserBillMapper billMapper;
    @Autowired
    private CountService countService;
    @Autowired
    private UsercontrollerVipUserMapper vipUserMapper;




    @Override
    /**根据条件判断是不是该扣费*/
    public void sfCost(UsercontrollerVipUser vipUser){
        /*String cosDate="";
        Date lastDayThisMonth= DateUtils.turnToMonthEnd(new Date());
        if (DateUtils.isSameDay(lastDayThisMonth, new Date())){
            Date nextMonth=new DateTime(new Date()).plusMonths(1).toDate();
            cosDate=DateUtils.getFullYear(nextMonth)+"-"+DateUtils.getMonth(nextMonth);
        }else {
            cosDate=DateUtils.getFullYear(new Date())+"-"+DateUtils.getMonth(new Date());
        }*/

        String cosDate="";
        cosDate=DateUtils.getFullYear(vipUser.getNextCostTime())+"-"+DateUtils.getMonth(vipUser.getNextCostTime());


        /**先将原来的数据删掉*/
        List<String> s=new ArrayList<>();
        s.add("ebayAccount");
        s.add("subUser");
        UsercontrollerUserBillExample exampledel =new UsercontrollerUserBillExample();
        exampledel.createCriteria().andStatusEqualTo("1").andBillSouceEqualTo("system").andBillTypeEqualTo("deduct")
                .andCostsTypeIn(s).andUserIdEqualTo(Integer.valueOf(vipUser.getUserId()))
                .andCostsDateEqualTo(cosDate);
        billMapper.deleteByExample(exampledel);

        //查询当前用户的ebay帐户
        List<UsercontrollerEbayAccount> ebayAccounts=countService.getEbayList(vipUser.getUserId());
        if (!ObjectUtils.isLogicalNull(ebayAccounts)){
            for (UsercontrollerEbayAccount account:ebayAccounts){
                UsercontrollerUserBill bill=new UsercontrollerUserBill();
                bill.setBillSouce("system");
                bill.setBillType("deduct");
                bill.setCostsType("ebayAccount");
                bill.setCostsTarget(account.getId().toString());
                bill.setUserId(Integer.valueOf(vipUser.getUserId()));
                bill.setAmount(new BigDecimal(("-") + StaticParam.ebayFeeOne));
                bill.setStatus("1");
                bill.setCostsDate(cosDate);
                costVIPMonth(bill);
            }

        }

        //查询子账号
        Map map=new HashMap();
        map.put("parentID",vipUser.getUserId());
        List<UsercontrollerUserExtend> x= countService.queryAllUserS(map);
        UsercontrollerUserExtend d=new UsercontrollerUserExtend();
        d.setUserId(Integer.valueOf(vipUser.getUserId()));
        if (ObjectUtils.isLogicalNull(x)){
            x=new ArrayList<>();
        }
        x.add(d);

        if (!ObjectUtils.isLogicalNull(x)){
            for (UsercontrollerUserExtend e:x){
                UsercontrollerUserBill bill=new UsercontrollerUserBill();
                bill.setBillSouce("system");
                bill.setBillType("deduct");
                bill.setCostsType("subUser");
                bill.setCostsTarget(e.getUserId().toString());
                bill.setUserId(Integer.valueOf(vipUser.getUserId()));
                bill.setAmount(new BigDecimal(("-") + StaticParam.subUserFeeOne));
                bill.setStatus("1");
                bill.setCostsDate(cosDate);
                costVIPMonth(bill);
            }
        }

        if (isEnough(vipUser.getUserId())){
            UsercontrollerUserBillExample exampleb =new UsercontrollerUserBillExample();
            exampleb.createCriteria().andUserIdEqualTo(Integer.valueOf(vipUser.getUserId())).andStatusEqualTo("1");
            UsercontrollerUserBill uu=new UsercontrollerUserBill();
            uu.setUpdateTime(new Date());
            uu.setStatus("0");
            int kf = billMapper.updateByExampleSelective(uu, exampleb);
            if (kf==0){
                logger.error("没有符合条件的扣费记录>>>>>>>>>>>>>>>>>>>>>>");
                return;
            }
            UsercontrollerVipUserExample eee=new UsercontrollerVipUserExample();
            eee.createCriteria().andUserIdEqualTo(vipUser.getUserId());
            UsercontrollerVipUser ug=new UsercontrollerVipUser();
            ug.setLastCostTime(new Date());
            ug.setUpdateTime(new Date());
            Date after30 = DateUtils.dateAddDay(ug.getLastCostTime(),StaticParam.costDay);//下次计费日期
            ug.setNextCostTime(after30);
            vipUserMapper.updateByExampleSelective(ug,eee);
        }

    }

    /**用户的余额是否为负数*/
    private boolean isEnough(String userId){

        return !(queryBanlace(userId).compareTo(BigDecimal.ZERO)==-1);

    }
    @Override
    /**查询当前用户的余额*/
    public BigDecimal queryBanlace(String userId){
        //查询当前用户的余额
        List<String> s=new ArrayList<>();
        s.add("0");
        s.add("1");
        UsercontrollerUserBillExample example =new UsercontrollerUserBillExample();
        example.setOrderByClause("create_time desc");
        example.createCriteria().andStatusIn(s).andUserIdEqualTo(Integer.valueOf(userId));
        List<UsercontrollerUserBill> userBills=billMapper.selectByExample(example);

        BigDecimal bd=new BigDecimal("0");
        UsercontrollerUserBill lastCost=null;  //找到最近的一条扣费记录
        for (UsercontrollerUserBill b:userBills){
            bd=MyNumberUtils.add(bd,b.getAmount());
            if (lastCost==null && "deduct".equalsIgnoreCase(b.getBillType())){
                lastCost=b;
            }
        }
        return bd;
    }



    @Override
    /**扣费 月费    *
     * @param bill
     *bill type:  deduct  recharge
     * costtype: ebayAccount  subUser
     * costtarget:ebayid userid
     * amount  金额
     * user_id  扣费的主账号
     * status  0已扣  1预扣 2作废
     * costs_date 扣费的月份
     * @return
     */
    public String costVIPMonth(UsercontrollerUserBill bill){
        bill.setBillSouce("system");
        /**首先检查该笔费用这个月扣过没有*/
        UsercontrollerUserBillExample example =new UsercontrollerUserBillExample();
        example.createCriteria().andBillSouceEqualTo("system")
                .andCostsDateEqualTo(bill.getCostsDate()).andBillTypeEqualTo("deduct")
                .andCostsTypeEqualTo(bill.getCostsType())
                .andCostsTargetEqualTo(bill.getCostsTarget()).andUserIdEqualTo(bill.getUserId());
        List<UsercontrollerUserBill> billList=billMapper.selectByExample(example);
        if (ObjectUtils.isLogicalNull(billList)){
            bill.setCreateTime(new Date());
            bill.setUpdateTime(new Date());
            billMapper.insertSelective(bill);
        }else {
            if (billList.size()>1){
                StringBuffer sb=new StringBuffer();
                for (UsercontrollerUserBill u:billList){
                    sb.append(u.getId()).append(",");
                }
                logger.error(">>>出现异常！重复扣费！"+sb.toString());
                return "err";
            }
            UsercontrollerUserBill uu=billList.get(0);
            if (uu.getStatus().equalsIgnoreCase(bill.getStatus())){
                return "n";
            }
            if ("0".equalsIgnoreCase(uu.getStatus())){
                uu.setUpdateTime(new Date());
                billMapper.updateByPrimaryKeySelective(uu);
                return "errkf";
            }
            uu.setUpdateTime(new Date());
            uu.setStatus(bill.getStatus());
            billMapper.updateByPrimaryKeySelective(uu);
        }
      return "ok";
    }

    @Override
    /**直接写入一笔费用比如充值等情况*/
    public void addBill(UsercontrollerUserBill bill){
        if (!"0".equalsIgnoreCase(bill.getStatus())){
            logger.error("===通过该方法写入的账单必须是已完成状态==");
            return;
        }
        if (StringUtils.isBlank(bill.getBillType())){
            logger.error("费用清单类型为必填!");
            return;
        }
        if ("deduct".equalsIgnoreCase(bill.getBillType())){
            if (bill.getAmount().compareTo(BigDecimal.ZERO)!=-1){
                logger.error("扣费清单不能为正数!"+bill.getCostsType()+">>>"+bill.getAmount());
                return;
            }
        }


        if (bill.getAmount().compareTo(BigDecimal.ZERO)==-1){//如果费用是负数，即是扣费账单那么就判断余额是否足够
            BigDecimal b= MyNumberUtils.add(bill.getAmount(), queryBanlace(bill.getUserId().toString()));
            if (b.compareTo(BigDecimal.ZERO)==-1){
                logger.error("余额不够啊....." + bill.getBillSouce() + ">>" + bill.getAmount() + ">>"+bill.getUserId());
                Asserts.assertTrue(false,"code:-111,余额不够！请充值!");
                return;
            }
        }

        bill.setUpdateTime(new Date());
        billMapper.insertSelective(bill);
    }

    @Override
    /**作废一笔账单*/
    public void cancelBill(Integer billId){
        /*UsercontrollerUserBillExample example =new UsercontrollerUserBillExample();
        example.createCriteria().andIdEqualTo(billId);*/
        UsercontrollerUserBill b=new UsercontrollerUserBill();
        b.setId(billId);
        b.setStatus("2");
        billMapper.updateByPrimaryKeySelective(b);
    }

    @Override
    public void middleCost(UsercontrollerUserBill userBill){
        UsercontrollerVipUserExample example=new UsercontrollerVipUserExample();
        example.createCriteria().andUserIdEqualTo(userBill.getUserId().toString()).andStatusEqualTo("0");
        List<UsercontrollerVipUser> vs= vipUserMapper.selectByExample(example);
        if (!ObjectUtils.isLogicalNull(vs)){
            UsercontrollerVipUser v=vs.get(0);
            String cosDate=DateUtils.getFullYear(v.getNextCostTime())+"-"+DateUtils.getMonth(v.getNextCostTime());

            //判断一下本次计费周期已经交过费没有
            UsercontrollerUserBillExample example1bill=new UsercontrollerUserBillExample();
            example1bill.createCriteria().andCostsTargetEqualTo(userBill.getCostsTarget()).andCostsDateEqualTo(cosDate)
            .andCostsTypeEqualTo(userBill.getCostsType());
            List<UsercontrollerUserBill> bills = billMapper.selectByExample(example1bill);
            if (!ObjectUtils.isLogicalNull(bills)){
                Asserts.assertTrue(!"1".equalsIgnoreCase(bills.get(0).getStatus()),"还没扣费啊！");
                if ("0".equalsIgnoreCase(bills.get(0).getStatus())){
                    return;
                }
            }


            Integer xiangcha=DateUtils.daysBetween( new Date(),v.getNextCostTime());//距离计费日期还有多少天
            Asserts.assertTrue(xiangcha>0,"上个周期没有计费，请计费后再添加!");
            BigDecimal feyong=new BigDecimal("0");
            if ("subUser".equalsIgnoreCase(userBill.getCostsType())){
                feyong=new BigDecimal(StaticParam.subUserFeeOne);
            }else if ("ebayAccount".equalsIgnoreCase(userBill.getCostsType())){
                feyong=new BigDecimal(StaticParam.ebayFeeOne);
            }
            BigDecimal flEveryDay=MyNumberUtils.divide(feyong,StaticParam.costDay);//每天多少钱
            //应该交费多少
            BigDecimal shouldF=MyNumberUtils.multiple(flEveryDay,xiangcha).setScale(2, RoundingMode.HALF_UP);
            userBill.setAmount(shouldF.negate());
            userBill.setCostsDate(cosDate);
            userBill.setUpdateTime(new Date());
            userBill.setCreateTime(new Date());
            addBill(userBill);
        }
    }

}
