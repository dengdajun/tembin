package com.userinfo.controller;

import com.alipay.domains.querypojos.UsercontrollerUserBillQuery;
import com.alipay.service.IUsercontrollerUserBillService;
import com.base.database.userinfo.model.UsercontrollerVipUser;
import com.base.domains.RoleVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.CommonParmVO2;
import com.base.domains.userinfo.AddSubUserVO;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.MyNumberUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.service.SystemVIPUserCheckService;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/19.
 * 系统账户管理
 */
@Controller
@RequestMapping("systemuser")
public class SystemUserManagerController extends BaseAction {

    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    public IUsercontrollerUserBillService iUsercontrollerUserBillService;
    @Autowired
    public UserInfoService userInfoService;

    @Autowired
    public SystemVIPUserCheckService systemVIPUserCheckService;

    /**打开系统账户管理页面*/
    @RequestMapping("sysUserManPage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView sysUserManPage(
            String t,String u,
            @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        if (StringUtils.isNotBlank(t)){
            modelMap.put("t",t);
            modelMap.put("u",u);
        }
        return forword("/userinfo/systemUserManager",modelMap);
    }

    @RequestMapping("userConfPage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView userConfPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/userinfo/selfUserConfig",modelMap);
    }

    /**初始化增加子用户的页面*/
    @RequestMapping("addSubUserInit.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addSubUserInit(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/userinfo/pop/addSubSystenUser",modelMap);
    }
    /**初始化修改子用户的页面*/
    @RequestMapping("editSubUserInit.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editSubUserInit(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,String userID){
        if(StringUtils.isNotEmpty(userID)){
            modelMap.put("userID",userID);
        }
        return forword("/userinfo/pop/addSubSystenUser",modelMap);
    }

    /**获取帐号列表数据*/
    @RequestMapping("getSysManData.do")
    @ResponseBody
    public void getSysManData(CommonParmVO2 commonParmVO, @RequestParam(value = "isShowStop",required = false)String isShowStop){
        Map map=new HashMap();
        if(StringUtils.isNotEmpty(isShowStop)){
            map.put("isShowStop",isShowStop);
        }else {
            map.put("isShowStop","active");
        }
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UsercontrollerUserExtend> lists=  systemUserManagerService.queryAccountListByUserID(map,page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**查询交费记录列表信息*/
    @RequestMapping("queryPayLogList.do")
    @ResponseBody
    public void queryPayLogList(CommonParmVO2 commonParmVO, @RequestParam(value = "isShowStop",required = false)String isShowStop){
        Map map=new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        map.put("userId",c.getId());
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UsercontrollerUserBillQuery> lists =  this.iUsercontrollerUserBillService.selectBillList(map, page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**交费信息*/
    @RequestMapping("getAliPay.do")
    @ResponseBody
    public void getAliPay(CommonParmVO2 commonParmVO, @RequestParam(value = "isShowStop",required = false)String isShowStop){
        SessionVO c= SessionCacheSupport.getSessionVO();
        long userid=0;
        if(c.getParentId()!=0){
            userid = c.getParentId();
        }else{
            userid = c.getId();
        }
        Map map=new HashMap();
        DecimalFormat df = new DecimalFormat("###0.00");
       // if(systemUserManagerService.isMainUserAcount()) {//管理员账号
            //List<UsercontrollerUserExtend> liuser = this.systemUserManagerService.queryAllUsersByOrgID("yes");

            Map mfee = new HashMap();
            mfee.put("userId",userid);
            UsercontrollerUserBillQuery uubq = this.iUsercontrollerUserBillService.selectBillFee(mfee);
            if(uubq!=null){//余额
                map.put("balanceFee",df.format(uubq.getAmount()));
            }
            UsercontrollerVipUser uvip = this.systemVIPUserCheckService.selectByUserId(userid+"");
            if(uvip!=null) {
                map.put("accountNumber", uvip.getSubCount());
                map.put("ebayNumber", uvip.getEbayCount());
                map.put("payFee", df.format(MyNumberUtils.add(MyNumberUtils.multiple(new BigDecimal(StaticParam.subUserFeeOne),uvip.getSubCount()),MyNumberUtils.multiple(new BigDecimal(StaticParam.ebayFeeOne),uvip.getEbayCount()))));
            }else{
                map.put("accountNumber", "0");
                map.put("ebayNumber", "0");
                map.put("payFee", "0.00");
            }

            Map ms = new HashMap();//充值
            ms.put("userId",userid);
            ms.put("billType","recharge");
            List<UsercontrollerUserBillQuery> lists1 =  this.iUsercontrollerUserBillService.selectBillType(ms);
            if(lists1!=null&&lists1.size()>0){
                UsercontrollerUserBillQuery us1 = lists1.get(0);
                map.put("upPayFee",df.format(us1.getAmount()));
                map.put("startDate",us1.getCreateTime());
            }
            Map ms2 = new HashMap();//扣款
            ms2.put("userId",userid);
            ms2.put("billType","deduct");
            List<UsercontrollerUserBillQuery> lists2 =  this.iUsercontrollerUserBillService.selectBillType(ms2);
            if(lists2!=null&&lists2.size()>0){
                UsercontrollerUserBillQuery us2 = lists2.get(0);
            }
            //当月有没有扣费记录
            Map ms3 = new HashMap();//扣款
            ms3.put("userId",userid);
            ms3.put("billType","deduct");
            ms3.put("startDate", DateUtils.turnToMonthStart(new Date()));
            List<UsercontrollerUserBillQuery> lists3 =  this.iUsercontrollerUserBillService.selectBillType(ms3);
            if(lists3!=null&&lists3.size()>0){
                map.put("nowDateFlag","1");
            }else{
                map.put("nowDateFlag","0");
            }
            //到期时间，因为每一个月只扣一次款，所以到期时间为下个月一号

            map.put("endDate","2015-06-01");
       // }


        AjaxSupport.sendSuccessText("", map);
    }

    /**
     * 交费界面
     * @param modelMap
     * @return
     */
    @RequestMapping("tenFeePage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView tenFeePage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/userinfo/tenfee",modelMap);
    }

    /**对帐号进行操作*/
    @RequestMapping("operationAccount.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void operationAccount(String userid,String doaction){
        Asserts.assertTrue(StringUtils.isNotEmpty(userid)&&StringUtils.isNotEmpty(doaction),"帐号或者操作参数不能为空!");
        Map map =new HashMap();
        map.put("userid",userid);
        map.put("doaction",doaction);
        systemUserManagerService.operationAccount(map);
        AjaxSupport.sendSuccessText("","修改成功！");
    }

    @RequestMapping("queryMySpecAllRole.do")
    @ResponseBody
    /**获取当前登录用户定义的所有角色*/
    public void queryMySpecAllRole(Long userID){
        Map map =new HashMap();

        List<RoleVO> roleVOs = systemUserManagerService.queryAllCustomRole(map);
        AjaxSupport.sendSuccessText("",roleVOs);
    }

    @RequestMapping("queryMySpecAllEbay.do")
    @ResponseBody
    /**获取当前登录用户定义的所有ebay账户*/
    public void queryMySpecAllEbay(Long userID){
        Map map =new HashMap();
        map.put("AllEbay","");
        List<UsercontrollerEbayAccountExtend> roleVOs = systemUserManagerService.queryACurrAllEbay(map);
        AjaxSupport.sendSuccessText("",roleVOs);
    }

    /**编辑页面获取用户信息*/
    @RequestMapping("queryUserAccountInfo.do")
    @ResponseBody
    public void queryUserAccountInfo(Long userID){
        AddSubUserVO addSubUserVO=systemUserManagerService.queryAllUserAccountInfo(userID);
        AjaxSupport.sendSuccessText("",addSubUserVO);
    }

    /**修改子用户*/
    @RequestMapping("editSubUser.do")
    @ResponseBody
    @AvoidDuplicateSubmission(needRemoveToken = true)
    public void editSubUser(AddSubUserVO addSubUserVO){
        systemUserManagerService.editSubAccount(addSubUserVO);
        AjaxSupport.sendSuccessText("","修改成功!");
    }

    /**添加子用户*/
    @RequestMapping("addSubUser.do")
    @ResponseBody
    @AvoidDuplicateSubmission(needRemoveToken = true)
    public void addSubUser(AddSubUserVO addSubUserVO){
        systemUserManagerService.addSubAccount(addSubUserVO);
        AjaxSupport.sendSuccessText("","添加成功");
    }

    /**修改密码*/
    @RequestMapping("changePWD.do")
    @ResponseBody
    @AvoidDuplicateSubmission(needRemoveToken = true)
    public void changePWD(String oldPWD,String newPWD){
        Asserts.assertTrue(StringUtils.isNotEmpty(oldPWD) && StringUtils.isNotEmpty(newPWD),"原密码和新密码不能为空");
        systemUserManagerService.changePWD(oldPWD, newPWD);
        AjaxSupport.sendSuccessText("","修改成功！");
    }

    @RequestMapping("getSafeCodelogin.do")
    /**邮件获取验证码*/
    public void getSafeCode(HttpServletRequest request,HttpServletResponse response,String loginUserID,
                                 @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        Asserts.assertTrue(StringUtils.isNotEmpty(loginUserID),"帐号不能为空!");
        Map map=new HashMap();
        map.put("HttpServletRequest",request);
        map.put("loginUserID",loginUserID);//已经修改为邮箱帐号
        systemUserManagerService.sendSafeCode(map);
        AjaxSupport.sendSuccessText("","验证码已发送至邮箱！");
    }

    @RequestMapping("changePWDBySafeCodelogin.do")
    /**根据邮件的验证码来修改账户密码*/
    public void changePWDBySafeCode(HttpServletRequest request,HttpServletResponse response,
                                    @ModelAttribute( "initSomeParmMap" )ModelMap modelMap,
                                    String loginUserID,String safeCode,String newPWD,String sid){
        Asserts.assertTrue(StringUtils.isNotEmpty(loginUserID) && StringUtils.isNotEmpty(safeCode) && StringUtils.isNotEmpty(newPWD),"帐号、验证码、密码不能为空");
       Map map =new HashMap();
        map.put("HttpServletRequest",request);
        map.put("loginUserId",loginUserID);
        map.put("newPWD",newPWD);
        map.put("safeCode",safeCode);
        map.put("sid",sid);
        systemUserManagerService.doChangeForgetPassWord(map);
        AjaxSupport.sendSuccessText("success","修改成功！");
    }


}
