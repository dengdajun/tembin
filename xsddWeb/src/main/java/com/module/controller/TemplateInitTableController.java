package com.module.controller;

import com.base.database.trading.model.TradingTemplateInitTable;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.TemplateInitTableQuery;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.imageManage.service.ImageService;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingTemplateInitTable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/５.
 */
@Controller
public class TemplateInitTableController extends BaseAction{

    @Autowired
    private ITradingTemplateInitTable iTradingTemplateInitTable;
    @Autowired
    private ImageService imageService;
    @Autowired
    private SystemUserManagerService systemUserManagerService;

    /**
     * 模板模块
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/TemplateInitTableList.do")
    public ModelAndView TemplateInitTableList(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("initSomeParmMap")ModelMap modelMap){
        modelMap.put("imageUrlPrefix",imageService.getImageUrlPrefix());
        return forword("module/templateinittable/templateinittableList",modelMap);
    }

    /**查询模板分类*/
    @RequestMapping("/ajax/queryTemplateType.do")
    @ResponseBody
    public  void queryTemplateType(){
        Map map=new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerUserExtend> orgUsers=systemUserManagerService.queryAllUsersByOrgID("yes");
        if(orgUsers!=null&&orgUsers.size()>0){
            map.put("users",orgUsers);
        }else{
            orgUsers=new ArrayList<>();
            UsercontrollerUserExtend user=new UsercontrollerUserExtend();
            user.setUserId(Integer.valueOf(c.getId()+""));
            orgUsers.add(user);
            map.put("users",orgUsers);
        }
        List<TemplateInitTableQuery> l=iTradingTemplateInitTable.selectTemplateType(map);
        AjaxSupport.sendSuccessText("", l);
    }


    /**
     * 选择模板
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/selectTemplate.do")
    public ModelAndView selectTemplate(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        modelMap.put("imageUrlPrefix", imageService.getImageUrlPrefix());
        return forword("module/templateinittable/selectTemplate",modelMap);
    }

    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadTemplateInitTableList.do")
    @ResponseBody
    public void loadTemplateInitTableList(CommonParmVO commonParmVO,String status){
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerUserExtend> orgUsers=systemUserManagerService.queryAllUsersByOrgID("yes");
        if(orgUsers!=null&&orgUsers.size()>0){
            m.put("users",orgUsers);
        }else{
            orgUsers=new ArrayList<>();
            UsercontrollerUserExtend user=new UsercontrollerUserExtend();
            user.setUserId(Integer.valueOf(c.getId()+""));
            orgUsers.add(user);
            m.put("users",orgUsers);
        }
       /* m.put("userid",c.getId());*/

        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();

        List<TemplateInitTableQuery> TemplateInitTable=new ArrayList<TemplateInitTableQuery>();
        if(StringUtils.isNotEmpty(commonParmVO.getStrV1()) && !"all".equalsIgnoreCase(commonParmVO.getStrV1()) ){
            m.put("templateTypeId",commonParmVO.getStrV1());
            if (StringUtils.isNotBlank(status)){
                m.put("status",status);
            }
            TemplateInitTable = this.iTradingTemplateInitTable.selectByTemplateInitTableList(m,page);
        }else {
            TemplateInitTable = this.iTradingTemplateInitTable.selectByTemplateInitTableList(m,page);
        }

        jsonBean.setList(TemplateInitTable);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 模板添加
     * @param request
     * @param response
     * @param modelMap
     * @return
     */

    @RequestMapping("/addTemplateInitTable.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        modelMap.put("imageUrlPrefix", imageService.getImageUrlPrefix());
       // modelMap.put("currUserLoginID",imageService.getImageUserDir());
        return forword("module/templateinittable/addTemplateInitTable",modelMap);
    }
    /**
     * 模板编辑
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/editTemplateInitTable.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        Map m = new HashMap();
        m.put("id", request.getParameter("id"));
        List<TemplateInitTableQuery> TemplateInitTableli = this.iTradingTemplateInitTable.selectByTemplateInitTableList(m);
        modelMap.put("TemplateInitTable", TemplateInitTableli.get(0));
        modelMap.put("imageUrlPrefix", imageService.getImageUrlPrefix());
        return forword("module/templateinittable/addTemplateInitTable",modelMap);
    }

    /**启用或者停用模板*/
    @RequestMapping("/ajax/onOrStop.do")
    @ResponseBody
    public void onOrStop(Long id,String status){
        Asserts.assertTrue(id != null, "id不能为空！");
        TradingTemplateInitTable templateInitTable=new TradingTemplateInitTable();
        templateInitTable.setId(id);
        iTradingTemplateInitTable.onOrStopTemplate(templateInitTable);
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    @RequestMapping("/viewTemplateInitTable.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        Map m = new HashMap();
        m.put("id",request.getParameter("id"));
        List<TemplateInitTableQuery> TemplateInitTableli = this.iTradingTemplateInitTable.selectByTemplateInitTableList(m);
        String htmlText=TemplateInitTableli.get(0).getTemplateHtml();
        //htmlText= StringEscapeUtils.escapeHtml(htmlText);
        modelMap.put("TemplateInitTable",htmlText);
        return forword("module/templateinittable/viewTemplateInitTable",modelMap);
    }
    /**
     * 保存模板数据
     * @param request
     * @throws Exception
     */
    @RequestMapping("/ajax/saveTemplateInitTable.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveTemplateInitTable(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String id=request.getParameter("id");
        String templatehtml=request.getParameter("templateHtml");
        String tLevel=request.getParameter("level");
        String templateName=request.getParameter("templateName");
        String templateViewUrl=request.getParameter("templateViewUrl");
        TradingTemplateInitTable tm=new TradingTemplateInitTable();
        if(!ObjectUtils.isLogicalNull(id)){
            tm.setId(Long.parseLong(id));
        }
        if(templatehtml.contains("如果需要重新编辑保存,请先下载模板后编辑,再将编辑后的html复制到编辑框中")||StringUtils.isBlank(templatehtml)||templatehtml.length()<50){
            tm.setTLevel(tLevel);
            tm.setTemplateName(templateName);
            tm.setTemplateViewUrl(templateViewUrl);
            if(StringUtils.isNotBlank(tLevel)&&"1".equals(tLevel)){
                tm.setTemplateTypeId(655L);
            }
            iTradingTemplateInitTable.saveTemplateInitTable(tm);
            AjaxSupport.sendSuccessText("message", "操作成功!编辑框中的内容保存失败,请先将预先设置好的html复制到编辑框中!");
        }else{
            tm.setTemplateHtml(templatehtml);
            tm.setTLevel(tLevel);
            tm.setTemplateName(templateName);
            tm.setTemplateViewUrl(templateViewUrl);
            if(StringUtils.isNotBlank(tLevel)&&"1".equals(tLevel)){
                tm.setTemplateTypeId(655L);
            }
            iTradingTemplateInitTable.saveTemplateInitTable(tm);
            AjaxSupport.sendSuccessText("message", "操作成功！");
        }

    }
    /*
 *下载模板downloadOrders
 */
    @RequestMapping("/downloadTemplateInitTable.do")
    public void  downloadTemplateInitTable(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String id=request.getParameter("id");
        if(StringUtils.isBlank(id)){
            return;
        }
        TradingTemplateInitTable template=iTradingTemplateInitTable.selectById(Long.valueOf(id));
        response.setContentType("text/plain");// 一下两行关键的设置
        response.addHeader("Content-Disposition",
                "attachment;filename=template.txt");// filename指定默认的名字
        BufferedOutputStream buff = null;
        StringBuffer write = new StringBuffer();
        ServletOutputStream outSTr = null;
        try {
            outSTr = response.getOutputStream();// 建立
            buff = new BufferedOutputStream(outSTr);
            write.append(template.getTemplateHtml());
            buff.write(write.toString().getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buff.close();
                outSTr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
