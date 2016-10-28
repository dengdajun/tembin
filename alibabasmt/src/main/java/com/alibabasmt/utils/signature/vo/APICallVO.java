package com.alibabasmt.utils.signature.vo;

import java.util.Map;

/**
 * Created by Administrator on 2015/3/25.
 */
public class APICallVO {
    private String urlHead;
    private String urlPath;
    private Map<String, String> param;
    private SignatureVO signatureVO;
    private Long smtAccountID;


    public Long getSmtAccountID() {
        return smtAccountID;
    }

    public void setSmtAccountID(Long smtAccountID) {
        this.smtAccountID = smtAccountID;
    }

    public SignatureVO getSignatureVO() {
        return signatureVO;
    }

    public void setSignatureVO(SignatureVO signatureVO) {
        this.signatureVO = signatureVO;
    }

    public String getUrlHead() {
        return urlHead;
    }

    public void setUrlHead(String urlHead) {
        this.urlHead = urlHead;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }
}
