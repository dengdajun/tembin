package com.module.controller;

import com.base.database.trading.model.TradingDiscountpriceinfo;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.DiscountpriceinfoQuery;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingDiscountPriceInfo;
import com.trading.service.IUsercontrollerEbayAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/29.
 */
@Controller
public class DiscountPriceInfoController extends BaseAction {

    @Autowired
    private ITradingDiscountPriceInfo iTradingDiscountPriceInfo;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    /**
     * 查询列表页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/discountPriceInfoList.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView discountPriceInfoList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
       /* SessionVO c= SessionCacheSupport.getSessionVO();
        Map m = new HashMap();
        m.put("userid",c.getId());
        List<DiscountpriceinfoQuery> disli = this.iTradingDiscountPriceInfo.selectByDiscountpriceinfo(m);
        modelMap.put("disli",disli);*/
        return forword("module/discountpriceinfo/discountpriceinfoList",modelMap);
    }

    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadDiscountPriceInfoList.do")
    @ResponseBody
    public void loadDiscountPriceInfoList(HttpServletRequest request,CommonParmVO commonParmVO){
        Map m = new HashMap();
        String checkFlag = request.getParameter("checkFlag");
        m.put("checkFlag",checkFlag);
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");
        if(systemUserManagerService.isMainUserAcount()){
            List<String> liue = new ArrayList<String>();
            for(UsercontrollerUserExtend uue:liuue){
                liue.add(uue.getUserId()+"");
            }
            liue.add(c.getId()+"");
            m.put("liue",liue);
        }else{
            for(UsercontrollerUserExtend ue:liuue){
                if(ue.getUserParentId()==null){
                    m.put("userid1",ue.getUserId());
                }
            }
            m.put("userid",c.getId());
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<DiscountpriceinfoQuery> Discountpriceinfo = this.iTradingDiscountPriceInfo.selectByDiscountpriceinfo(m,page);
        for(DiscountpriceinfoQuery l:Discountpriceinfo){
            if(systemUserManagerService.isMainUserAcount()){
                l.setIsMainAcount(true);
            }else{
                l.setIsMainAcount(false);
            }
            l.setCurAcountUserId(c.getId());
        }
        jsonBean.setList(Discountpriceinfo);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 新增界面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/addDiscountPriceInfo.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addDiscountPriceInfo(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("userli",ebayList);
        return forword("module/discountpriceinfo/adddiscountpriceinfo",modelMap);
    }

    /**
     * 编辑界面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/editDiscountPriceInfo.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editDiscountPriceInfo(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String id = request.getParameter("id");

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        Map m = new HashMap();
        if(systemUserManagerService.isMainUserAcount()){
            List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");
            List<String> liue = new ArrayList<String>();
            for(UsercontrollerUserExtend uue:liuue){
                liue.add(uue.getUserId()+"");
            }
            liue.add(c.getId()+"");
            m.put("liue",liue);
        }else{
            m.put("userid",c.getId());
        }
        modelMap.put("userli",ebayList);
        //m.put("userid",c.getId());
        m.put("id",id);
        List<DiscountpriceinfoQuery> disli = this.iTradingDiscountPriceInfo.selectByDiscountpriceinfo(m);
        if(disli!=null&&disli.size()>0){
            modelMap.put("dis",disli.get(0));
        }
        String type = request.getParameter("type");
        if(type!=null&&!"".equals(type)){
            modelMap.put("type",type);
        }
        return forword("module/discountpriceinfo/adddiscountpriceinfo",modelMap);
    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/saveDiscountPriceInfo.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveDiscountPriceInfo( HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String selectflag  = request.getParameter("selectflag");
        String name = request.getParameter("name");
        String ebayAccount = request.getParameter("ebayAccount");
        String disStarttime = request.getParameter("disStarttime");
        String disEndtime = request.getParameter("disEndtime");
        String MadeForOutletComparisonPrice = request.getParameter("MadeForOutletComparisonPrice");
        String MinimumAdvertisedPrice = request.getParameter("MinimumAdvertisedPrice");
        String isShippingFee = request.getParameter("isShippingFee");
        String flag = request.getParameter("a1");
        String id = request.getParameter("id");
        if(flag!=null){
            if("a1".equals(flag)){
                MinimumAdvertisedPrice="0";
            }else if("a2".equals(flag)){
                MadeForOutletComparisonPrice="0";
            }
        }
        if(selectflag==null){
            MinimumAdvertisedPrice="0";
            MadeForOutletComparisonPrice="0";
        }

        TradingDiscountpriceinfo tdpi = new TradingDiscountpriceinfo();
        ObjectUtils.toInitPojoForInsert(tdpi);
        if(!ObjectUtils.isLogicalNull(id)){
            tdpi.setId(Long.parseLong(id));
        }
        tdpi.setName(name);
        tdpi.setEbayAccount(ebayAccount);
        tdpi.setDisStarttime(DateUtils.parseDateTime(disStarttime + ":00"));
        tdpi.setDisEndtime(DateUtils.parseDateTime(disEndtime + ":00"));
        tdpi.setIsShippingfee(isShippingFee);
        if(!ObjectUtils.isLogicalNull(MadeForOutletComparisonPrice)){
            tdpi.setMadeforoutletcomparisonprice(Double.parseDouble(MadeForOutletComparisonPrice));
        }
        if(!ObjectUtils.isLogicalNull(MinimumAdvertisedPrice)){
            tdpi.setMinimumadvertisedprice(Double.parseDouble(MinimumAdvertisedPrice));
        }
        tdpi.setMinimumadvertisedpriceexposure("DuringCheckout");
        if(tdpi.getIsShippingfee()==null){
            tdpi.setIsShippingfee("0");
        }
        this.iTradingDiscountPriceInfo.saveDiscountpriceinfo(tdpi);
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delDiscountprice.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void delDiscountprice(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");

        TradingDiscountpriceinfo tp= this.iTradingDiscountPriceInfo.selectById(Long.parseLong(id));
        if("1".equals(tp.getCheckFlag())){
            tp.setCheckFlag("0");
        }else{
            tp.setCheckFlag("1");
        }
        this.iTradingDiscountPriceInfo.saveDiscountpriceinfo(tp);
        AjaxSupport.sendSuccessText("","操作成功!");
    }
}
