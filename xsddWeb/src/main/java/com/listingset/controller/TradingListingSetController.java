package com.listingset.controller;

import com.alibaba.fastjson.JSON;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ListingDataQuery;
import com.base.domains.querypojos.PromotionalSaleQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.promotionalsale.service.ITradingItemPromotionalSaleRule;
import com.promotionalsale.service.ITradingItemPromotionalSaleSet;
import com.trading.service.ITradingListingData;
import com.trading.service.ITradingListingSet;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrtor on 2015/6/18.
 */
@Controller
public class TradingListingSetController extends BaseAction {
    static Logger logger = Logger.getLogger(TradingListingSetController.class);

    @Autowired
    private ITradingListingSet iTradingListingSet;

    @RequestMapping("/listingset.do")
    public ModelAndView listingset(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        modelMap.put("orgId",c.getOrgId());
        return forword("listingset/listingsetPage",modelMap);
    }

    @RequestMapping("/ajax/saveListingSet.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public void saveApiData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO,TradingListingSet tradingListingSet) {
        SessionVO c= SessionCacheSupport.getSessionVO();
        if(tradingListingSet.getId()!=null){
            tradingListingSet.setUpdateUser(c.getId());
            tradingListingSet.setUpdateTime(new Date());
        }else{
            tradingListingSet.setCreateUser(c.getId());
            tradingListingSet.setOrgId(c.getOrgId());
            tradingListingSet.setCreateTime(new Date());
        }
        if(tradingListingSet.getAutoListing()==null){
            tradingListingSet.setAutoListing("");
            tradingListingSet.setCheckFlag("1");
        }
        this.iTradingListingSet.saveTradingListingSet(tradingListingSet);
        AjaxSupport.sendSuccessText("","操作成功！");
    }

    @RequestMapping("/ajax/loadListingSet.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public void loadListingSet(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO,String orgId) {
        Asserts.assertTrue(orgId!=null,"获取当前所属公司报错！");
        TradingListingSet tls = this.iTradingListingSet.selectByOrgId(Long.parseLong(orgId));
        AjaxSupport.sendSuccessText("", JSON.toJSON(tls));
    }
}
