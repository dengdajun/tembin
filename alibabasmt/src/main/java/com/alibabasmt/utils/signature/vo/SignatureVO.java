package com.alibabasmt.utils.signature.vo;

import com.alibabasmt.utils.other.AutoValueBean;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import org.springframework.context.ApplicationContext;

/**
 * Created by Administrator on 2015/3/19.
 * 获取授权时候索用到的参数VO
 */
public class SignatureVO {
    private String host;
    private String appKey;
    private String appSecret;
    private String redirect_uri;//授权成功后转向的地址
    private String site="aliexpress";//"china";国际交易请用"aliexpress"
    private String state;//自定义参数

    private String refreshToken;
    private String accessToken;
    private String code;
    private Long smtAccountID;

    public static SignatureVO forTest(){
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.setAppKey("8516599");
        signatureVO.setHost("gw.api.alibaba.com");
        signatureVO.setAppSecret("DRXPnMh5re6S");
        signatureVO.setState("test");
        signatureVO.setRedirect_uri("http://www.baidu.com/");
        return signatureVO;
    }
    public void initVO(){
        AutoValueBean valueBean= ApplicationContextUtil.getBean(AutoValueBean.class);
        this.setAppKey(valueBean.appKey);
        this.setAppSecret(valueBean.appSecret);
        this.setHost(valueBean.sinWhenPostHostUrl);
        this.setRedirect_uri(valueBean.sinAfterUrl);
    }


    public Long getSmtAccountID() {
        return smtAccountID;
    }

    public void setSmtAccountID(Long smtAccountID) {
        this.smtAccountID = smtAccountID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
