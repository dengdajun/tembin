package com.common.interceptor.randr;

import com.base.database.userinfo.mapper.UsercontrollerUserBillMapper;
import com.base.database.userinfo.mapper.UsercontrollerVipUserMapper;
import com.base.database.userinfo.model.UsercontrollerUserBill;
import com.base.database.userinfo.model.UsercontrollerUserBillExample;
import com.base.database.userinfo.model.UsercontrollerVipUser;
import com.base.database.userinfo.model.UsercontrollerVipUserExample;
import com.base.domains.PermissionVO;
import com.base.domains.SessionVO;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.thirdpart.service.ThirdPartUserService;
import com.thirdpart.utils.ThirdPartStaticParam;
import com.thirdpart.utils.ThirtPartUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/21.
 * session过滤器未登录的返回到登陆页面
 * ajax请求则返回错误信息
 */
public class SessionVOInterceptor extends HandlerInterceptorAdapter {
    private static final Log logger = LogFactory.getLog(SessionVOInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        String currURL=request.getRequestURI();
        /**判断是否是免登录页面*/
        if (isInWhiteBill(currURL)){
            return true;
        }
        /**判断是否有登录信息*/
        HttpSession session = request.getSession();
        if (session==null || ObjectUtils.isLogicalNull(session.getAttribute(SessionCacheSupport.USERLOGINID))){
            //如果没有登陆信息，那么就判断是否是第三方
            //先判断是否有第三方参数
            Map<String, String[]> map =request.getParameterMap();
            List<String> the3user = getParmByName(map, "thirdPartUser", null);
            if (!ObjectUtils.isLogicalNull(the3user)){
                ThirdPartUserService user3Service= ApplicationContextUtil.getBean(ThirdPartUserService.class);
                if (user3Service==null){return false;}
                user3Service.thirdPartLogin(the3user.get(0));
            }else {
                redirectLogin(request,response);
                return false;
            }

        }
        String loginID= (String) session.getAttribute(SessionCacheSupport.USERLOGINID);
        if (StringUtils.isEmpty(loginID)){//判断是否有登录成功标记
            redirectLogin(request,response);
            return false;
        }
        SessionVO sessionVO = SessionCacheSupport.get(loginID);
        //如果缓存里面没有登录信息或者sessionid不同
        if(sessionVO==null
                || StringUtils.isEmpty(sessionVO.getLoginId())
                ||StringUtils.isEmpty(sessionVO.getSessionID())
                || !sessionVO.getSessionID().equalsIgnoreCase(request.getSession().getId())){
            if(sessionVO!=null){
                logger.error(sessionVO.getLoginId()+":sessionVO中的ID"+sessionVO.getSessionID()+
                        "session中的id:"+request.getSession().getId()+
                        ":缓存中的id与当前登陆的id不一致！重新登陆！");
            }
            if(sessionVO==null){
                logger.error("由于缓存中已经没有sessionVO，重新登陆!");
            }

            session.removeAttribute(SessionCacheSupport.USERLOGINID);
            session.invalidate();
            redirectLogin(request, response);
            return false;
        }
        /**验证一下当前页面第三方用户能否使用*/
        if ("yes".equalsIgnoreCase(sessionVO.getThirdPartLogin())){
            for (String a: ThirtPartUtil.denyThirdPart()){
                if (currURL.endsWith(a)){
                    logger.error("第三方用户不能访问！"+currURL);
                    return false;
                }
            }

        }

        /**控制欠费用户*/
        Boolean b=TempStoreDataSupport.pullData("isAmountOver_Temp"+sessionVO.getLoginId());
        if (b==null){
            b=isAmountOver(sessionVO);
            TempStoreDataSupport.pushDataByTime("isAmountOver_Temp"+sessionVO.getLoginId(),b,120);
        }

        if (b && !accessWhenAmountOver(currURL) && !isInWhiteBill(currURL)){
            if (sessionVO.getParentId()==0){
                response.sendRedirect(request.getContextPath()+"/systemuser/sysUserManPage.do?t=qf&u=self");
            }else {
               /* String r="";
                try {
                    r= URLEncoder.encode("您已欠费!请联系管理员续费", "utf-8");
                } catch (UnsupportedEncodingException e1) {}
                response.sendRedirect(request.getContextPath()+"/errorPage.jsp?c="+r);*/
                response.sendRedirect(request.getContextPath()+"/systemuser/sysUserManPage.do?t=qf&u=subUser");
            }

            return false;
        }

        /*if (isAccessPageRole(currURL,request,response)){
            return true;
        }else {
            request.setAttribute("errmessage","没有权限访问");
            response.sendRedirect(request.getContextPath()+"/errorPage.jsp");
            return false;
        }*/
        return true;

    }


private void redirectLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
    Map<String, String[]> map =request.getParameterMap();
    List<String> AjaxMode = getParmByName(map, "AjaxMode", null);
    if(!ObjectUtils.isLogicalNull(AjaxMode)){
        AjaxSupport.sendFailText("sessionStatusFalse","未登录或者已过期");
        return;
    }
    response.sendRedirect(request.getContextPath()+"/login.jsp");
}

    /**在参数request的参数map内，查找指定条件的的参数*/
    private List<String> getParmByName(Map<String, String[]> map,String startWith,String endWith){
        List<String> stringList = new ArrayList<String>();
        if (map==null || map.isEmpty() || StringUtils.isEmpty(startWith)) {
            return stringList;
        }
        if (StringUtils.isNotEmpty(endWith)){
            for(Map.Entry _o : map.entrySet()){
                String s=_o.getKey().toString();
                if (s.startsWith(startWith) && s.endsWith(endWith)){
                    String _v =  ((String[])_o.getValue())[0];
                    if (_v!=null && !"".equals(_v)){
                        stringList.add(_v);
                    }

                }
            }
        }else {
            String[] strings = (String[])map.get(startWith);
            if (strings!=null && strings.length>0){
                String _v =  strings[0];
                if (_v!=null && !"".equals(_v)){
                    stringList.add((String)_v);
                }
            }

        }
        return stringList;
    }



    /**当月是否交钱*/
    public boolean isAmountOver(SessionVO sessionVO) throws Exception {

            UsercontrollerVipUserMapper vipUserMapper=ApplicationContextUtil.getBean(UsercontrollerVipUserMapper.class);
            if (vipUserMapper==null){return false;}
            UsercontrollerVipUserExample userExample=new UsercontrollerVipUserExample();
        if (sessionVO.getParentId()!=0) {
            userExample.createCriteria().andUserIdEqualTo(String.valueOf(sessionVO.getParentId())).andStatusEqualTo("0");
        }else {
            userExample.createCriteria().andUserIdEqualTo(String.valueOf(sessionVO.getId())).andStatusEqualTo("0");
        }
            List<UsercontrollerVipUser> vipUsers = vipUserMapper.selectByExample(userExample);
            if (ObjectUtils.isLogicalNull(vipUsers)){return false;}

        UsercontrollerVipUser v=vipUsers.get(0);

        int i= DateUtils.comparTwoDate(new Date(),v.getNextCostTime());
        if (i==1){
            return true;
        }

        String cosDate= DateUtils.getFullYear(v.getNextCostTime())+"-"+DateUtils.getMonth(v.getNextCostTime());
        Date nextMon=DateUtils.nowDateAddMonth(1);
        String cosDate2=DateUtils.getFullYear(nextMon)+"-"+DateUtils.getMonth(nextMon);
        if (cosDate2.equals(cosDate)){
            cosDate=DateUtils.getFullYear(new Date())+"-"+DateUtils.getMonth(new Date());
        }
        UsercontrollerUserBillMapper billMapper=ApplicationContextUtil.getBean(UsercontrollerUserBillMapper.class);
        if (billMapper==null){return false;}//如果没有取到bean那么就让他使用
        UsercontrollerUserBillExample example=new UsercontrollerUserBillExample();
        /*List<String> lll=new ArrayList<>();
        lll.add(cosDate);
        lll.add(cosDate2);*/
        example.createCriteria().andCostsDateEqualTo(cosDate).andStatusEqualTo("0")
                .andCostsTypeEqualTo("subUser").andCostsTargetEqualTo(String.valueOf(sessionVO.getId()));
        List<UsercontrollerUserBill> ls= billMapper.selectByExample(example);
        if (ObjectUtils.isLogicalNull(ls)){
            return true;
        }
        return false;

    }

    /**未交费的时候只能访问的页面*/
    public static boolean accessWhenAmountOver(String url){
        List<String> wurlList=new ArrayList<String>();
        wurlList.add("/systemuser/");//登陆操作
        wurlList.add("ajax/getToken.do");//获取token
        wurlList.add("role/queryRoleList.do");

        for (String s:wurlList){
            if (url.contains(s)){
                return true;
            }
        }

        return false;

    }


/**一些不需要验证登陆直接通行的请求*/
    public static boolean isInWhiteBill(String url){
        List<String> wurlList=new ArrayList<String>();
        wurlList.add("login.do");//登陆操作
        wurlList.add("getCountSize.do");//商品统计
        wurlList.add("receiveNoti.do");//接收ebay的notice
        wurlList.add("orderRefundCaptchaAction.do");//验证码
        //wurlList.add("generateAccessKey.do");//获取access
       // wurlList.add("syncThirdPartAccount.do");//同步第三方帐户
        wurlList.add("weiXinIn.do");//微信相关
        wurlList.add("chuLiWeiXin.do");//微信相关
        wurlList.add("async_notice.do");//支付宝
        wurlList.add("return_url.do");//支付宝
        //wurlList.add("createOrder.do");
        for (String s:wurlList){
            if (url.endsWith(s)){
                return true;
            }
        }

        return false;
    }

}
