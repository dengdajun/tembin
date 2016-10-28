package com.weixin.controller;

import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.threadpool.TaskPool;
import com.common.base.utils.ajax.AjaxResponse;
import com.common.base.web.BaseAction;
import com.weixin.service.MsgTextService;
import com.weixin.util.WeiXinStringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/5/21.
 */
@Controller
@RequestMapping("weixin")
public class WeiXinController extends BaseAction {
    static Logger logger = Logger.getLogger(WeiXinController.class);
    @Autowired
    private MsgTextService msgTextService;

    /**提供一个消息转向功能10.170.21.95*/
    @RequestMapping("weiXinIn.do")
    @ResponseBody
    public void weiXinIn(HttpServletResponse response,@RequestBody(required = false) String requestBody,
                         WeiXinYZVO weiXinYZVO)throws Exception{
        logger.error(weiXinYZVO.getEchostr()+"===微信..");
        if (StringUtils.isNotBlank(weiXinYZVO.getEchostr())){AjaxResponse.sendText(response,weiXinYZVO.getEchostr());}
        if (StringUtils.isBlank(requestBody)){AjaxResponse.sendText(response,"");}
        final String url="http://10.170.21.95:8080/xsddWeb/weixin/chuLiWeiXin.do";
        final String b=requestBody;
        String r="";
        Callable c=new Callable() {
            @Override
            public Object call() throws Exception {
                HttpClient h=HttpClientUtil.getHttpClient();
                String r= HttpClientUtil.post(h, url, b);
                return r;
            }
        };
        ListenableFutureTask<String> task = new ListenableFutureTask<String>(c);
        TaskPool.threadPoolTaskExecutor.submit(task);
        String res="";
        try {
            res=task.get(4, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("超时了！返回空",e);
        }
        AjaxResponse.sendText(response,res);
    }

    /**处理信息*/
    @RequestMapping("chuLiWeiXin.do")
    @ResponseBody
    public void chuLiWeiXin(HttpServletResponse response,@RequestBody String requestBody){
        String msgType= WeiXinStringUtil.getMsgType(requestBody);
        if (StringUtils.isBlank(msgType)){
            logger.error(requestBody);
            AjaxResponse.sendText(response, "");
            return;
        }
        String replayM="";
        switch (msgType){
            case "event":{
                String event=WeiXinStringUtil.getEvent(requestBody);
                if("subscribe".equalsIgnoreCase(event)){
                    replayM=msgTextService.newUser(requestBody);
                }
                break;
            }
            case "text":{
                replayM=msgTextService.replayMessage(requestBody);
                break;
            }

            default:{
                replayM="";
                break;
            }
        }
        logger.error(requestBody + "==body");
        AjaxResponse.sendText(response, replayM);
    }
}
