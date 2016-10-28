
package com.alibabasmt.utils.signature.api;


/**
 * Created by Administrator on 2015/3/18.
 */
import java.io.IOException;
import java.util.Map;

import com.alibabasmt.allservices.authorize.service.AboutAuthorizeService;
import com.alibabasmt.database.smtaccount.mapper.SmtUserAccountMapper;
import com.alibabasmt.database.smtaccount.model.SmtUserAccount;
import com.alibabasmt.utils.other.SMT7HStoreDataSupport;
import com.alibabasmt.utils.signature.util.ApacheHttpClientUtils;
import com.alibabasmt.utils.signature.vo.APICallVO;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibabasmt.utils.signature.util.CommonUtil;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * api调用的服务类
 */

public class ApiCallService {
    static Logger logger = Logger.getLogger(ApiCallService.class);



     public static String callApi(APICallVO apiCallVO){
         String urlPath = apiCallVO.getUrlPath() +"/"+ apiCallVO.getSignatureVO().getAppKey();
         String urlHead="";
         if (StringUtils.isBlank(apiCallVO.getUrlHead())){
             urlHead="http://"+apiCallVO.getSignatureVO().getHost()+ "/openapi/";
         }else {
             urlHead = apiCallVO.getUrlHead() + "/openapi/";
         }
         String accessToken="";
         if (apiCallVO.getParam()!=null && !apiCallVO.getParam().isEmpty()){
             accessToken=apiCallVO.getParam().get("access_token");
             if (StringUtils.isBlank(accessToken)){
                 Long smtAccount=apiCallVO.getSmtAccountID();
                 if (smtAccount==null || smtAccount==0){logger.error("速卖通帐号id不能为空！");return null;}
                 accessToken= SMT7HStoreDataSupport.pullData("access_Token_cache"+smtAccount);
                 if(StringUtils.isBlank(accessToken)){
                     //String refresh = SMT7HStoreDataSupport.pullData("refresh_Token_cache"+smtAccount);
                     //if (StringUtils.isBlank(refresh)){
                         //SmtUserAccountMapper accountMapper= ApplicationContextUtil.getBean(SmtUserAccountMapper.class);
                         //SmtUserAccount userAccount = accountMapper.selectByPrimaryKey(smtAccount);
                         //if (userAccount==null){logger.error("id为"+smtAccount+"没有查询到记录"); return null;}
                         //refresh=userAccount.getSmtFreshtoken();

                     //}
                     AboutAuthorizeService aboutAuthorizeService= ApplicationContextUtil.getBean(AboutAuthorizeService.class);
                     accessToken=aboutAuthorizeService.fetchAccessTokenByAccountID(smtAccount);
                     SMT7HStoreDataSupport.pushData("access_Token_cache"+smtAccount,accessToken);
                 }

             }

         }
         apiCallVO.getParam().put("access_token",accessToken);
         String result = ApiCallService.callApiTest(urlHead, urlPath,
                 apiCallVO.getSignatureVO().getAppSecret(), apiCallVO.getParam());
         return result;
     }



    /**
     * 调用api测试
     * @param urlHead 请求的url到openapi的部分，如http://gw.open.1688.com/openapi/
     * @param urlPath protocol/version/namespace/name/appKey
     * @param appSecretKey 测试的app密钥，如果为空表示不需要签名
     * @param params api请求参数map。如果api需要用户授权访问，那么必须完成授权流程，params中必须包含access_token参数
     * @return json格式的调用结果
     * String urlPath = "param2/2/system/currentTime/" + client_id;
    String urlHead = "http://" + host + "/openapi/";
    Map<String, String> param = new HashMap<String, String>();
    param.put("access_token", (String)jsonObject1.get("access_token"));
    String result = ApiCallService.callApiTest(urlHead, urlPath, appSecret, param);
     */
     public static String callApiTest(String urlHead, String urlPath, String appSecretKey, Map<String, String> params){

        HttpClient httpClient = null;
        String siteUrl = StringUtils.lowerCase(urlHead);
        if (siteUrl.startsWith("https")){
            httpClient= ApacheHttpClientUtils.getNewHttpsClient();
        }else {
            httpClient=ApacheHttpClientUtils.getNewHttpClient();
        }
        final PostMethod method = new PostMethod(urlHead + urlPath);
        method.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

        if(params != null){
            for (Map.Entry<String, String> entry : params.entrySet()) {
                method.setParameter(entry.getKey(), entry.getValue());
            }
        }
        if(appSecretKey != null){
            method.setParameter("_aop_signature", CommonUtil.signatureWithParamsAndUrlPath(urlPath, params, appSecretKey));
        }
        String response = "";
        try{
            int status = httpClient.executeMethod(method);
            if(status >= 300 || status < 200){
                throw new RuntimeException("invoke api failed, urlPath:" + urlPath
                        + " status:" + status + " response:" + method.getResponseBodyAsString());
            }
            response = CommonUtil.parserResponse(method);
        } catch (HttpException e) {
            logger.error("smt:",e);
        } catch (IOException e) {
            logger.error("smt:",e);
        }finally{
            method.releaseConnection();
        }
        return response;
    }

}

