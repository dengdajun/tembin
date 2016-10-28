package com.alipay.controller;

import com.alipay.service.AlipayOrderService;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.alipay.util.MyAlipayUtils;
import com.alipay.util.UtilDate;
import com.base.database.userinfo.model.AlipayOrders;
import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxResponse;
import com.common.base.web.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/28.
 */
@Controller
@RequestMapping("alipay")
public class AlipayOrderController extends BaseAction{
    static Logger logger = Logger.getLogger(AlipayOrderController.class);

    @Autowired
    private AlipayOrderService alipayOrderService;

    /**根据参数生成订单并且提交*/
    @RequestMapping("createOrder.do")
    @ResponseBody
    public void createOrder(HttpServletRequest request,HttpServletResponse response,String totalFee, ModelMap modelMap){
        Asserts.assertTrue(StringUtils.isNotBlank(totalFee), "请输入金额");
        Asserts.assertTrue(NumberUtils.isNumber(totalFee),"只能输入数字");

        //金额
        String total_fee = totalFee;//
        //支付类型
        String payment_type = "1";
        //必填，不能修改
        //服务器异步通知页面路径
        String notify_url = "http://task.tembin.com:8080/xsddWeb/alipay/async_notice.do";
        //需http://格式的完整路径，不能加?id=123这类自定义参数
        //页面跳转同步通知页面路径
        String return_url = "http://task.tembin.com:8080/xsddWeb/alipay/return_url.do";
        //需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

        //生成一个订单号
        String out_trade_no= UtilDate.getOrderNum() + MyStringUtil.getRandomStringAndNum(6);
        //订单名称
        String subject="TemBin坐席订单";

        //订单描述
        String body="TemBin坐席的订单";
        //商品展示地址
        String show_url="http://www.tembin.com";
        //防钓鱼时间戳
        String anti_phishing_key = "";
        //若要使用请调用类文件submit中的query_timestamp函数
        //客户端的IP地址
        String exter_invoke_ip = "";
        //非局域网的外网IP地址，如：221.0.0.1

        Map<String,String> sParaTemp= MyAlipayUtils.returnParmMap();
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
       /* String sHtmlText = "error";
        try {
            sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
        } catch (Exception e) {
            logger.error("==alipay报错",e);
        }
        logger.error(sHtmlText);*/
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        if (sessionVO==null){
            if (request!=null){
                if (request.getSession()!=null){
                    String ip="";
                    try {
                        ip= SystemLogUtils.getIpAddr(request);
                    } catch (Exception e) {
                        logger.error("=",e);
                    }
                    logger.error("sessionVO为空了!"+request.getSession().getAttribute(SessionCacheSupport.USERLOGINID)+":"+totalFee+">>ip:"+ip);
                }else {
                    logger.error("==充值时session为空！");
                }
            }

        }
        Asserts.assertTrue(sessionVO!=null,"不能取得登录信息！请到客服QQ群244373066反映该问题！");
        AlipayOrders orders = new AlipayOrders();
        orders.setMyTradeNo(out_trade_no);
        try {
            orders.setTembinBuyerId(((Long) sessionVO.getId()).intValue());
        } catch (Exception e) {
            orders.setTembinBuyerId(-10);
            logger.error(e);
        }
        orders.setUpdateTime(new Date());
        alipayOrderService.addOrUpdate(orders);
        AjaxResponse.sendText(response,sHtmlText);
    }

    /**同步通知*/
    @RequestMapping("return_url.do")
    public ModelAndView return_url(HttpServletRequest request,HttpServletResponse response,
                                   @ModelAttribute( "initSomeParmMap" )ModelMap modelMap)throws Exception{
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)
        //计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(params);
        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            //System.out.println(out_trade_no+">>>>"+verify_result);

            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                modelMap.put("orderResult", "支付成功!请稍后到支付记录页面查看");
                AlipayOrders orders=new AlipayOrders();
                orders.setAlipayTradeNo(trade_no);
                orders.setMyTradeNo(out_trade_no);
                orders.setBuyerEmail(buyer_email);
                //orders.setTembinBuyerId(((Long) sessionVO.getId()).intValue());
                orders.setTotalFee(total_fee);
                orders.setUpdateTime(new Date());
                orders.setTradeStatus(trade_status);
                alipayOrderService.addOrUpdate(orders);
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
            }else {
                modelMap.put("orderResult", "支付失败!支付状态为:"+trade_status);
            }
            //该页面可做页面美工编辑

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
            //////////////////////////////////////////////////////////////////////////////////////////
        }else{
            //该页面可做页面美工编辑
            modelMap.put("orderResult","验证失败!");
        }

        return forword("/alipay/return_url",modelMap);
    }
    /**异步通知*/
    @RequestMapping("async_notice.do")
    @ResponseBody
    public void async_notice(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
        if(AlipayNotify.verify(params)){
            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                AlipayOrders orders=new AlipayOrders();
                orders.setAlipayTradeNo(trade_no);
                orders.setMyTradeNo(out_trade_no);
                orders.setUpdateTime(new Date());
                orders.setTradeStatus(trade_status);
                alipayOrderService.endOrder(orders);
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
                AlipayOrders orders=new AlipayOrders();
                orders.setAlipayTradeNo(trade_no);
                orders.setMyTradeNo(out_trade_no);
                orders.setBuyerEmail(buyer_email);
                //orders.setTembinBuyerId(((Long) sessionVO.getId()).intValue());
                orders.setTotalFee(total_fee);
                orders.setUpdateTime(new Date());
                orders.setTradeStatus(trade_status);
                alipayOrderService.addOrUpdate(orders);
            }
            AjaxResponse.sendText(response,"success");
        }else {
            AjaxResponse.sendText(response,"fail");
        }

        //logger.error("sync>>>>>>"+out_trade_no+">>"+trade_status);

        return;
    }
}
