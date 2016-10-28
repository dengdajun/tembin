package com.common.base.web;

import com.base.domains.SessionVO;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.imageManage.service.ImageService;
import com.common.base.utils.DateEditor;
import com.common.base.utils.LongEditor;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by wula on 2014/6/22.
 */
public class BaseAction {
    private static final Log logger1 = LogFactory.getLog(BaseAction.class);

    @ModelAttribute( "initSomeParmMap" )
    public ModelMap initSomeParm(){
        ModelMap map=new ModelMap();
        map.put("nowDateTime",new Date());
        map.put("jscacheVersion","37");
        map.put("jsfileVersion","?vv=42");
        try {
            ImageService imageService= (ImageService) ApplicationContextUtil.getBean(ImageService.class);
            CommAutowiredClass commAutowiredClass= (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);
            map.put("itemListIconUrl",imageService.getItemListIconUrl());
            map.put("serviceItemUrl",commAutowiredClass.serviceItemUrl);
            SessionVO sessionVO = SessionCacheSupport.getSessionVO();
            if (sessionVO!=null){
                map.put("isMainAccount",sessionVO.getParentId()==0?"0":"1");
            }
        } catch (Exception e) {
            logger1.error("BaseAction初始化报错..."+e);
        }
        return map;
    }

    /**初始化时设置一些参数的转换*/
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        /*binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));*/
        binder.registerCustomEditor(Date.class, new DateEditor());

        binder.registerCustomEditor(int.class,new LongEditor());
    }

    /**所有的forword action都由这里转发*/
    public ModelAndView forword(String viewName,Map context){
        /*ModelAndView mv = new ModelAndView();
        //添加模型数据 可以是任意的POJO对象
        mv.addObject("message", "Hello World!");
        //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
        mv.setViewName("hello");*/
        return new ModelAndView(viewName,context);
    }

    public ModelAndView redirect(String url){
        return new ModelAndView("redirect:"+url);
        /*如果需要addFlashAttribute传参数的话
        @RequestMapping(value="addcustomer", method=RequestMethod.POST)
        public String addCustomer(@ModelAttribute("customer") Customer customer,
        final RedirectAttributes redirectAttributes) {
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectAttributes.addFlashAttribute("message","Added successfully.");
            return "redirect:showcustomer.html";
        }*/
    }
}
