package com.alibabasmt.allservices.authorize.service;

import com.alibabasmt.utils.signature.vo.SignatureVO;

/**
 * Created by Administrator on 2015/3/24.
 */
public interface AboutAuthorizeService {
    /**绑定速卖通时记录用户的两个token*/
    void addToken(SignatureVO signatureVO);

    /**通过refreshtoken获取accesstoken，直接返回，不写数据库*/
    String fetchAccessTokenByAccountID(Long accountId);

    /**通过freshtoken更新accesstoken*/
    void refreshAccessToken(Long accountId);
}
