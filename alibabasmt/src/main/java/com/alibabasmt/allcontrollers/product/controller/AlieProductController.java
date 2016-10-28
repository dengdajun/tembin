package com.alibabasmt.allcontrollers.product.controller;

import com.alibaba.fastjson.JSON;
import com.alibabasmt.allservices.authorize.service.SmtAccountManService;
import com.alibabasmt.allservices.datadic.service.ISmtDataDictionary;
import com.alibabasmt.allservices.product.service.IAlieProduct;
import com.alibabasmt.allservices.product.service.ISmtAeopSkuProperty;
import com.alibabasmt.allservices.product.service.ISmtAeopaeProductProperty;
import com.alibabasmt.allservices.product.service.ISmtAeopaeProductSku;
import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductProperty;
import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductSku;
import com.alibabasmt.database.smtproduct.model.SmtProduct;
import com.alibabasmt.domains.querypojos.smtaccount.SmtUserAccountExt;
import com.alibabasmt.domains.querypojos.smtproduct.SmtAeopaeProductSkuQuery;
import com.alibabasmt.domains.querypojos.smtproduct.SmtProductQuery;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.ListingDataQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.ObjectUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/19.
 */
@Controller
@RequestMapping("alieproduct")
public class AlieProductController extends BaseAction {
    static Logger logger = Logger.getLogger(AlieProductController.class);
    @Autowired
    private IAlieProduct iAlieProduct;
    @Autowired
    private ISmtDataDictionary iSmtDataDictionary;
    @Autowired
    private SmtAccountManService smtAccountManService;
    @Autowired
    private ISmtAeopaeProductProperty iSmtAeopaeProductProperty;
    @Autowired
    private ISmtAeopaeProductSku iSmtAeopaeProductSku;
    @Autowired
    private ISmtAeopSkuProperty iSmtAeopSkuProperty;
    /**新增产品界面*/
    @RequestMapping("addalieproduct.do")
    public ModelAndView addAlieProduct(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        MyStringUtil.generateRandomFilename();
        modelMap.put("unitList",this.iSmtDataDictionary.queryByType("productUnit"));
        return forword("/alibabasmt/product/addalieproduct",modelMap);
    }
    /**编辑产品界面*/
    @RequestMapping("eidtSmtpProduct.do")
    public ModelAndView eidtSmtpProduct(String productId,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        MyStringUtil.generateRandomFilename();
        SmtProduct sp = this.iAlieProduct.selectById(Long.parseLong(productId));
        modelMap.put("sp",sp);
        modelMap.put("unitList",this.iSmtDataDictionary.queryByType("productUnit"));
        List<SmtAeopaeProductProperty> lisap = this.iSmtAeopaeProductProperty.selectByParentId(sp.getId());
        if(lisap!=null&&lisap.size()>0){
            modelMap.put("property", JSON.toJSON(lisap));
        }
        List<SmtAeopaeProductSku> lisku = this.iSmtAeopaeProductSku.selectByParentId(sp.getId());
        List<SmtAeopaeProductSkuQuery> lisq = new ArrayList<>();
        for(SmtAeopaeProductSku saps:lisku){
            SmtAeopaeProductSkuQuery sq =new SmtAeopaeProductSkuQuery();
            try {
                ConvertPOJOUtil.coverWithSpring(saps,sq);
            } catch (Exception e) {
                logger.error("转换数据报错：",e);
                e.printStackTrace();
            }
            sq.setListSmtskuPro(this.iSmtAeopSkuProperty.selectByParentId(sq.getId()));
            lisq.add(sq);
        }
        if(lisku!=null&&lisku.size()>0){
            modelMap.put("lisku",JSON.toJSON(lisq));
        }
        return forword("/alibabasmt/product/addalieproduct",modelMap);
    }
    @RequestMapping("smtproductlist.do")
    public ModelAndView smtProductList(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        MyStringUtil.generateRandomFilename();
        modelMap.put("status",this.iSmtDataDictionary.queryByType("productStatus"));
        return forword("/alibabasmt/product/smtproductlist",modelMap);
    }

    @RequestMapping("/ajax/getAlieCategoryList.do")
    @ResponseBody
    public void getAlieCategoryList(HttpServletRequest request,ModelMap modelMap) {
        String cateid = request.getParameter("id");
        String level = request.getParameter("level");
        List<SmtUserAccountExt> lis = this.smtAccountManService.querySMTAccByCurrId();
        long smtAccountId = 1L;
        if(lis!=null&&lis.size()>0){
            smtAccountId = lis.get(0).getId();
        }
        List<Map> lim = this.iAlieProduct.queryCategoryByIdList(cateid,smtAccountId);
        for(Map m:lim){
            m.put("level",(Integer.parseInt(level)+1));
        }
        modelMap.put("lim",lim);
        AjaxSupport.sendSuccessText("", modelMap);
    }

    @RequestMapping("/ajax/getAlieCategoryAttr.do")
    @ResponseBody
    public void getAlieCategoryAttr(HttpServletRequest request,ModelMap modelMap) {
        String cateid = request.getParameter("cateid");
        List<SmtUserAccountExt> lis = this.smtAccountManService.querySMTAccByCurrId();
        long smtAccountId = 1L;
        if(lis!=null&&lis.size()>0){
            smtAccountId = lis.get(0).getId();
        }
        List<Map> lim = this.iAlieProduct.queryCategoryAttrByIdList(cateid,smtAccountId);
        modelMap.put("lim",lim);
        AjaxSupport.sendSuccessText("", modelMap);
    }

    @RequestMapping("/ajax/getSmtProductList.do")
    @ResponseBody
    public void getSmtProductList(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) {
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map = new HashMap();
        List<SmtProductQuery> lispq = this.iAlieProduct.selectSmtProductQueryList(map,page);
        for(SmtProductQuery spq : lispq){
            if(spq.getImageurls()!=null&&spq.getImageurls().split(";").length>0){
                spq.setImgSrc(spq.getImageurls().split(";")[0]);
            }
        }
        jsonBean.setList(lispq);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("/ajax/saveProductData.do")
    @ResponseBody
    public void saveProductData(HttpServletRequest request,ModelMap modelMap,SmtProductFrom smtProductFrom) {
        String [] propertyvaluedefinitionnames = request.getParameterValues("propertyvaluedefinitionname");
        String [] skuimages = request.getParameterValues("skuimage");
        List<SmtAeopaeProductSkuFrom> lisku = smtProductFrom.getSmtAeopaeProductSkuFroms();
        List<SmtAeopaeProductProperty> lipro = smtProductFrom.getSmtAeopaeProductProperties();

        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");

        AjaxSupport.sendSuccessText("", "message.....");

    }

}
