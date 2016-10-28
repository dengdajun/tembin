package com.indexPage.controller;

import com.base.database.trading.model.TradingGetUserCases;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.CommonParmVO2;
import com.base.domains.querypojos.MessageGetmymessageQuery;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.domains.querypojos.hicharts.HchartsIndexVO;
import com.base.domains.querypojos.hicharts.PieVo;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.CountService;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.MyNumberUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.google.common.base.Joiner;
import com.trading.service.ITradingGetUserCases;
import com.trading.service.ITradingMessageGetmymessage;
import com.trading.service.ITradingOrderAddMemberMessageAAQToPartner;
import com.trading.service.ITradingOrderGetOrders;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Administrator on 2014/11/5.
 * 系统主页
 */
@Controller
public class IndexMainController extends BaseAction{
    private static final Log logger1 = LogFactory.getLog(IndexMainController.class);

    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @Autowired
    private ITradingGetUserCases iTradingGetUserCases;
    @Autowired
    private ITradingMessageGetmymessage iTradingMessageGetmymessage;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private CountService countService;
    /**打开首页*/
    @RequestMapping("indexInit.do")
    public ModelAndView indexInit(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays= systemUserManagerService.queryCurrAllEbay(map);
        List<UsercontrollerUserExtend> userExtends=systemUserManagerService.queryAllUsersByOrgID("yes");
        List<UsercontrollerUserExtend> orgUsers=new ArrayList<>();
        if(userExtends!=null&&userExtends.size()>0){
            orgUsers.addAll(userExtends);
        }else{
            UsercontrollerUserExtend extend=new UsercontrollerUserExtend();
            extend.setUserId((int) sessionVO.getId());
            orgUsers.add(extend);
        }
        List<MessageGetmymessageQuery> ebayMessages=iTradingMessageGetmymessage.selectByMessageGetmymessageNoReadCount();
        if(ebayMessages==null){
            ebayMessages=new ArrayList<>();
        }
        List<TradingGetUserCases> caseMessages=iTradingGetUserCases.selectGetUserCasesByHandled(userExtends);
        List<TradingOrderGetOrders> orderMessages=iTradingOrderGetOrders.selectOrderGetOrdersByPaidAndNotShipped(userExtends);
        Map m1 = new HashMap();
        m1.put("status", "Complete");
        if(ebays!=null&&ebays.size()>0){
            m1.put("ebays",ebays);
        }
        Page page = new Page();
        page.setPageSize(1000);
        page.setCurrentPage(1);
        List<OrderGetOrdersQuery> lists = this.iTradingOrderGetOrders.selectOrderGetOrdersByGroupList(m1,page);
        modelMap.put("ebayMessages",ebayMessages.size());
        modelMap.put("caseMessages", caseMessages.size());
        modelMap.put("orderMessages", lists.size());
        modelMap.put("ebays", ebays);
        Integer ebayNumSize=0;
        if (!ObjectUtils.isLogicalNull(ebays)){
            ebayNumSize=ebays.size();
        }
        modelMap.put("ebaysCount", ebayNumSize);
        return forword("/indexpage/indexPage",modelMap);
    }

    @RequestMapping("getTrenchData.do")
    @ResponseBody
    /**渠道分布数据(饼图)*/
    public void getTrenchData(HttpServletRequest request){
        String day=request.getParameter("day");
        if(!StringUtils.isNotBlank(day)){
            day="15";
        }
        Date end=new Date();
        end=com.base.utils.common.DateUtils.turnToDateEnd(end);
        Date start=null;
        if("1".equals(day)){
            start=com.base.utils.common.DateUtils.turnToDateStart(DateUtils.addYears(end, -1));
        }else if("30".equals(day)){
            start= com.base.utils.common.DateUtils.turnToDateStart(DateUtils.addDays(end,-29));
        }else{
            start=com.base.utils.common.DateUtils.turnToDateStart(DateUtils.addDays(end, -14));
        }
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays= systemUserManagerService.queryCurrAllEbay(map);
        List<PieVo> pieVos=new ArrayList<PieVo>();
        for(UsercontrollerEbayAccountExtend ebay:ebays){
            List<OrderGetOrdersQuery> liorder =iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccounts(ebay.getId(),start,end);
            int count = 0;
            if(liorder!=null&&liorder.size()>0&&liorder.get(0)!=null){
                count = Integer.parseInt(liorder.get(0).getTotalAmount());
            }
            PieVo pieVo=new PieVo();
            pieVo.setName(ebay.getEbayNameCode());
            pieVo.setY(MyNumberUtils.str2DoubleAndSCALE2(count+""));
            pieVos.add(pieVo);
        }
      /*  List<PieVo> pieVos=new ArrayList<PieVo>();
        PieVo pieVo=new PieVo();
        pieVo.setName("new york");
        pieVo.setY(MyNumberUtils.str2DoubleAndSCALE2("26.8"));
        pieVos.add(pieVo);

        PieVo pieVo1=new PieVo();
        pieVo1.setName("new york1");
        pieVo1.setY(MyNumberUtils.str2DoubleAndSCALE2("26.8"));
        pieVos.add(pieVo1);

        PieVo pieVo2=new PieVo();
        pieVo2.setName("new york2");
        pieVo2.setY(MyNumberUtils.str2DoubleAndSCALE2("26.8"));
        pieVos.add(pieVo2);

        PieVo pieVo3=new PieVo();
        pieVo3.setName("new york3");
        pieVo3.setY(MyNumberUtils.str2DoubleAndSCALE2("80"));
        pieVos.add(pieVo3);*/
        List<HchartsIndexVO> list=new ArrayList<HchartsIndexVO>();
        HchartsIndexVO hchartsIndexVO=new HchartsIndexVO();
        hchartsIndexVO.setName("qudao");
        hchartsIndexVO.setType("pie");
        hchartsIndexVO.setData(pieVos);
        list.add(hchartsIndexVO);
        AjaxSupport.sendSuccessText("", list);

    }

    /**单量走势，曲线图*/
    @RequestMapping("getOrderCountData.do")
    @ResponseBody
    public void getOrderCountData(HttpServletRequest request){
        String time=request.getParameter("time");
        String amount=request.getParameter("amount");
        if(!StringUtils.isNotBlank(amount)){
            amount="0";
        }
        if(!StringUtils.isNotBlank(time)){
            time="15";
        }
        /*String xAxis="['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']";*/
        String xAxis="";
        //单量
        List<HchartsIndexVO> hchartsIndexVOs=new ArrayList<HchartsIndexVO>();
        HchartsIndexVO h1=new HchartsIndexVO();
        //金额
        HchartsIndexVO hchartsIndexVO=new HchartsIndexVO();
        List<Double> l12 = new ArrayList<Double>();
        h1.setName(" ");
        List<Double> l1 = new ArrayList<Double>();
        Date date1=new Date();
        if("1".equals(time)){
            Date date=DateUtils.addYears(date1,-1);
            int yeat= com.base.utils.common.DateUtils.getFullYear(date);
            int month= com.base.utils.common.DateUtils.getMonth(date);
            date=com.base.utils.common.DateUtils.buildDate(yeat, date.getMonth(), 1);
            date= com.base.utils.common.DateUtils.turnToDateStart(date);
            Map<Integer,String> map=new HashMap<Integer, String>();
            map.put(1,"Jan");
            map.put(2,"Feb");
            map.put(3,"Mar");
            map.put(4,"Apr");
            map.put(5,"May");
            map.put(6,"Jun");
            map.put(7,"Jul");
            map.put(8,"Aug");
            map.put(9,"Sep");
            map.put(10,"Oct");
            map.put(11,"Nov");
            map.put(12,"Dec");
            for(int i=1;i<13;i++){
                Date date3=DateUtils.addMonths(date,i+1);
                Date start=DateUtils.addMonths(date, i);
                Date end=com.base.utils.common.DateUtils.turnToDateEnd(DateUtils.addDays(date3, -1));
                List<OrderGetOrdersQuery> list1=iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccountAndTime(Long.valueOf(amount), start, end);
                List<OrderGetOrdersQuery> list=iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccountAndTime1(Long.valueOf(amount), start, end);
                int count=0;
                if(list!=null&&list.size()>0){
                    if(list.get(0)!=null){
                        count=Integer.valueOf(list.get(0).getCountNum()+"");
                    }
                }
                Double money=0.0;
                if(list1!=null&&list1.size()>0){
                    OrderGetOrdersQuery query=list1.get(0);
                    if(query!=null){
                        String money11=query.getTotalAmount();
                        if(StringUtils.isNotBlank(money11)){
                            money=Double.valueOf(money11);
                        }
                    }
                }
                DecimalFormat formater = new DecimalFormat();
                formater.setMaximumFractionDigits(2);
                formater.setGroupingSize(0);
                formater.setRoundingMode(RoundingMode.FLOOR);
                money= Double.valueOf(formater.format(money));
                l1.add(Double.parseDouble(count+""));
                l12.add(money);
                if(i==12){
                    xAxis+="'"+map.get(start.getMonth()+1)+"']";
                }else if(i==1){
                    xAxis+="['"+map.get(start.getMonth()+1)+"',";
                }else{
                    xAxis+="'"+map.get(start.getMonth()+1)+"',";
                }
            }
            /*xAxis="['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']";*/

        }else if("15".equals(time)){
            Date date=DateUtils.addDays(date1,-14);
            date1=com.base.utils.common.DateUtils.turnToDateEnd(date);
            date= com.base.utils.common.DateUtils.turnToDateStart(date);
            for(int i=0;i<15;i++){
                Date start=DateUtils.addDays(date,i);
                Date end=DateUtils.addDays(date1,i);
                List<OrderGetOrdersQuery> list1=iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccountAndTime(Long.valueOf(amount), start, end);
                List<OrderGetOrdersQuery> list=iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccountAndTime1(Long.valueOf(amount), start, end);
                int count=0;
                if(list!=null&&list.size()>0){
                    if(list.get(0)!=null){
                        count=Integer.valueOf(list.get(0).getCountNum()+"");
                    }
                }
                Double money=0.0;
                if(list1!=null&&list1.size()>0){
                    OrderGetOrdersQuery query=list1.get(0);
                    if(query!=null){
                        String money11=query.getTotalAmount();
                        if(StringUtils.isNotBlank(money11)){
                            money=Double.valueOf(money11);
                        }
                    }
                }
                DecimalFormat formater = new DecimalFormat();
                formater.setMaximumFractionDigits(2);
                formater.setGroupingSize(0);
                formater.setRoundingMode(RoundingMode.FLOOR);
                money= Double.valueOf(formater.format(money));
                l12.add(money);
                l1.add(Double.parseDouble(count+""));
                int month=start.getMonth()+1;
                int day= com.base.utils.common.DateUtils.getDayOfMonth(start);
                if(i==14){
                    xAxis+="'"+(month+"月"+day+"号")+"']";
                }else if(i==0){
                    xAxis+="['"+(month+"月"+day+"号")+"',";
                }else{
                    xAxis+="'"+(month+"月"+day+"号")+"',";
                }
            }
        }else if("30".equals(time)){
            Date date=DateUtils.addDays(date1,-29);
            date1=com.base.utils.common.DateUtils.turnToDateEnd(date);
            date= com.base.utils.common.DateUtils.turnToDateStart(date);
            for(int i=0;i<30;i++){
                Date start=DateUtils.addDays(date,i);
                Date end=DateUtils.addDays(date1,i);
                List<OrderGetOrdersQuery> list1=iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccountAndTime(Long.valueOf(amount), start, end);
                List<OrderGetOrdersQuery> list=iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccountAndTime1(Long.valueOf(amount), start, end);
                int count=0;
                if(list!=null&&list.size()>0){
                    if(list.get(0)!=null){
                        count=Integer.valueOf(list.get(0).getCountNum()+"");
                    }
                }
                Double money=0.0;
                if(list1!=null&&list1.size()>0){
                    OrderGetOrdersQuery query=list1.get(0);
                    if(query!=null){
                        String money11=query.getTotalAmount();
                        if(StringUtils.isNotBlank(money11)){
                            money=Double.valueOf(money11);
                        }
                    }
                }
                DecimalFormat formater = new DecimalFormat();
                formater.setMaximumFractionDigits(2);
                formater.setGroupingSize(0);
                formater.setRoundingMode(RoundingMode.FLOOR);
                money= Double.valueOf(formater.format(money));
                l12.add(money);
                l1.add(Double.parseDouble(count+""));
                int month=start.getMonth()+1;
                int day= com.base.utils.common.DateUtils.getDayOfMonth(start);
                if(i==29){
                    xAxis+="'"+(month+"-"+day)+"']";
                }else if(i==0){
                    xAxis+="['"+(month+"-"+day)+"',";
                }else{
                    xAxis+="'"+(month+"-"+day)+"',";
                }
            }
        }
        h1.setData(l1);
        h1.setName("单量");
        hchartsIndexVOs.add(h1);
        hchartsIndexVO.setName("金额");
        hchartsIndexVO.setData(l12);
        hchartsIndexVOs.add(hchartsIndexVO);



        AjaxSupport.sendSuccessText(xAxis,hchartsIndexVOs);
    }


    /********************************************************************************************************************/
    @RequestMapping("/countSome.do")
    /**统计分析帐号页面*/
    public ModelAndView countSome(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) {
        if (!canAccressUser()) {
            String r="";
            try {
                r= URLEncoder.encode("没有权限访问！!", "utf-8");
            } catch (UnsupportedEncodingException e1) {}
            return redirect("/errorPage.jsp?c="+r);
        }

        int[] iss = countService.countUser();//统计用户
        modelMap.put("allUser", iss[0]);
        modelMap.put("mainUser", iss[1]);
        modelMap.put("stopUser", iss[2]);
        return forword("/indexpage/countSome", modelMap);
    }

    @RequestMapping("/queryOnlineUser.do")
    @ResponseBody
    public void queryOnlineUser(){
        List<SessionVO> sessionVOList=SessionCacheSupport.getOnlineUser();
        List<String> strings=new ArrayList<>();
        for (SessionVO sessionVO:sessionVOList){
            String x="";
            x+=sessionVO.getLoginId()+">>>"+ com.base.utils.common.DateUtils.formatDateTime(sessionVO.getLoginTime()) +" |  ";
            strings.add(x);
        }
        AjaxSupport.sendSuccessText("", strings);
    }


    /**查询刊登商品的情况*/
    @RequestMapping("/queryPageListingCount.do")
    @ResponseBody
    public void queryListingCount(String userID){
        Asserts.assertTrue(canAccressUser(), "没有权限");
        String string="";
        if (StringUtils.isNotBlank(userID)){
            userID=userID.trim();
            int currListcount = countService.getDayListingDataCount(userID);//当天刊登
            int allListCount = countService.getTemBinAllListingDataCount(userID);//总共刊登
            int onLineCount = countService.getAllListingDataCount(userID);
            int ebayNum = countService.getEbayCount(userID);
            int skuNum = countService.querySkuByUser(userID);

            Date start= com.base.utils.common.DateUtils.turnToDateStart(new Date());
            Date end= com.base.utils.common.DateUtils.turnToDateEnd(new Date());
            List<OrderGetOrdersQuery> list1=iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccountAndTime2(Long.valueOf(userID), start, end);
            int ordercount=0;
            if (!ObjectUtils.isLogicalNull(list1)){
                OrderGetOrdersQuery q=list1.get(0);
                if (q!=null){
                    Long onn=q.getCountNum();
                    ordercount=(onn==null)?0:onn.intValue();
                }

           }

            string+="今天刊登:"+currListcount+",总共刊登:"+allListCount+",在线:"+onLineCount;
            string+=",sku数量:"+skuNum+",ebay帐号数量:"+ebayNum+",今日订单:"+ordercount;
        }

        AjaxSupport.sendSuccessText("", string);
        return;
    }

    @RequestMapping("/countPageQueryAlluserList.do")
    @ResponseBody
    /**查询账所有主账号号列表*/
    public void queryAlluserList(CommonParmVO2 commonParmVO){
        Asserts.assertTrue(canAccressUser(),"没有权限");
        PageJsonBean jsonBean = commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UsercontrollerUserExtend> uue=countService.queryAllUser(new HashMap(),page);
        jsonBean.setTotal((int)page.getTotalCount());
        jsonBean.setList(uue);
        AjaxSupport.sendSuccessText("",jsonBean);
        return;
    }

    @RequestMapping("/queryWaitActiveUserList.do")
    @ResponseBody
    /**查询账所有待激活列表*/
    public void queryWaitActiveUserList(CommonParmVO2 commonParmVO){
        Asserts.assertTrue(canAccressUser(),"没有权限");
        PageJsonBean jsonBean = commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        map.put("active", "2");
        List<UsercontrollerUserExtend> uue=countService.queryAllUser(map, page);
        jsonBean.setTotal((int) page.getTotalCount());
        jsonBean.setList(uue);
        AjaxSupport.sendSuccessText("", jsonBean);
        return;
    }

    @RequestMapping("/activeUser.do")
    @ResponseBody
    /**激活账号*/
    public void activeUser(String userID) throws Exception {
        Asserts.assertTrue(canAccressUser(), "没有权限");
        Asserts.assertTrue(StringUtils.isNotBlank(userID) && NumberUtils.isNumber(userID), "userId不能为空且不能为数字");
        countService.activeNewUser(userID);
        AjaxSupport.sendSuccessText("ok","ok");
        return;
    }


    /**查询日志日志列表*/
    @RequestMapping("/getLogList.do")
    @ResponseBody
    public void getLogList(){
        File file=new File("/var/tembinConfig/logs/");
        Collection<File> c= FileUtils.listFiles(file, new String[]{"log"}, false);
        //List<File> files=new ArrayList<>(c);
        List<String> x=new ArrayList<>();
        for (File f:c){
            x.add(f.getName());
        }
        String names= Joiner.on("|").skipNulls().join(x);
        AjaxSupport.sendSuccessText("",names);
        //FileUtils.listFiles()
        //MyFileUtils.findFileOrDirList()
    }

    /**下载日志*/
    @RequestMapping("/downLog.do")
    @ResponseBody
    public void downLog(HttpServletResponse res,String fileName){
        if (StringUtils.isBlank(fileName)){
            return;
        }
        File file=new File("/var/tembinConfig/logs/"+fileName);
        if (!file.exists()){
            return;
        }
        ServletOutputStream os=null;
        try {
            os=  res.getOutputStream();
            res.setHeader("Content-Disposition", "attachment; filename="+fileName+"");
            res.setContentType("application/octet-stream; charset=utf-8");

            os.write(FileUtils.readFileToByteArray(file));
            os.flush();
            os.close();
        } catch (Exception e) {
            logger1.error(e);
        }finally {
            if (os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }


    /**设置一些职能访问本页面的账户*/
    private boolean canAccressUser(){
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        String nowName=sessionVO.getLoginId();

        List<String> users=new ArrayList<>();
        users.add("caixu23@126.com");
        users.add("510871881@qq.com");
        users.add("410705498@qq.com");

        if (users.contains(nowName)){
            return true;
        }
            return false;
    }
}
