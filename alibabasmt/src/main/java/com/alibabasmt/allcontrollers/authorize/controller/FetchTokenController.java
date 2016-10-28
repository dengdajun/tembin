package com.alibabasmt.allcontrollers.authorize.controller;

import com.alibabasmt.allservices.authorize.service.AboutAuthorizeService;
import com.alibabasmt.utils.other.AutoValueBean;
import com.alibabasmt.utils.signature.api.APIStaticParm;
import com.alibabasmt.utils.signature.api.ApiCallService;
import com.alibabasmt.utils.signature.auth.AuthService;
import com.alibabasmt.utils.signature.auth.ClientAuthService;
import com.alibabasmt.utils.signature.vo.APICallVO;
import com.alibabasmt.utils.signature.vo.SignatureVO;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.common.MyStringUtil;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/3/17.
 * 用于与速卖通账户建立授权关系的页面
 */
@Controller
@RequestMapping("authorize")
public class FetchTokenController extends BaseAction
{
    static Logger logger = Logger.getLogger(FetchTokenController.class);
    @Autowired
    private AboutAuthorizeService authorizeService;

    /**1打开授权页面主页*/
    @RequestMapping("bindSMTAccountPage.do")
    public ModelAndView bindSMTAccountPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        MyStringUtil.generateRandomFilename();
        return forword("/alibabasmt/authorize/bindSMTAccountPage",modelMap);
    }

    /**2授权操作在此转向*/
    @RequestMapping("redirectA.do")
    public ModelAndView redirectA(String state){
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        if(StringUtils.isNotBlank(state)){
            signatureVO.setState(state);
        }
        String url= ClientAuthService.urlForCode(signatureVO);
        return redirect(url);
    }

    /**3授权成功后获取code的转向的页面,根据code获取两个token*/
    @RequestMapping("redirectAfterAuthorize.do")
    public ModelAndView redirectAfterAuthorize(String state,String code,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        signatureVO.setCode(code);
        if(StringUtils.isNotBlank(state)){
            signatureVO.setState(state);
        }
        AuthService.fetchTokenByCode(signatureVO);//获取token
        authorizeService.addToken(signatureVO);

        logger.info(state+":"+code);
        return forword("/alibabasmt/authorize/bindSMTAccountPage",modelMap);
    }


/**测试调用api===*/
@RequestMapping("apicalltest.do")
@ResponseBody
    public void apicalltest(){
        APICallVO apiCallVO=new APICallVO();
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
    /**====================普通情况下，只需要两个参数=============================*/
        apiCallVO.setSmtAccountID(1L);
     //   apiCallVO.setUrlHead("http://"+signatureVO.getHost());//默认
        apiCallVO.setUrlPath(APIStaticParm.queryAeAnouncement);
    /**=================================================*/

    Map<String,String> m=new HashMap<String, String>();
    m.put("access_token", "");//access_token就设置为空，可以添加其他参数
    apiCallVO.setParam(m);
        apiCallVO.setSignatureVO(signatureVO);



        String a = ApiCallService.callApi(apiCallVO);
    AjaxSupport.sendSuccessText("",a);
    }

}
