package com.alibabasmt.allservices.authorize.service.impl;

import com.alibabasmt.allservices.authorize.service.AboutAuthorizeService;
import com.alibabasmt.database.smtaccount.mapper.SmtAccessTokenMapper;
import com.alibabasmt.database.smtaccount.mapper.SmtUserAccountMapper;
import com.alibabasmt.database.smtaccount.model.SmtAccessToken;
import com.alibabasmt.database.smtaccount.model.SmtAccessTokenExample;
import com.alibabasmt.database.smtaccount.model.SmtUserAccount;
import com.alibabasmt.database.smtaccount.model.SmtUserAccountExample;
import com.alibabasmt.utils.signature.auth.AuthService;
import com.alibabasmt.utils.signature.vo.SignatureVO;
import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/3/24.
 * 授权操作用到的
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AboutAuthorizeServiceImpl implements AboutAuthorizeService{
    static Logger logger = Logger.getLogger(AboutAuthorizeServiceImpl.class);
    @Autowired
    private SmtUserAccountMapper smtUserAccountMapper;
    @Autowired
    private SmtAccessTokenMapper smtAccessTokenMapper;

    @Override
    /**绑定速卖通时记录用户的两个token*/
    public void addToken(SignatureVO signatureVO){

        SmtUserAccountExample accountExample=new SmtUserAccountExample();
        accountExample.createCriteria().andSmtFreshtokenEqualTo(signatureVO.getRefreshToken());
        List<SmtUserAccount> accounts = smtUserAccountMapper.selectByExample(accountExample);
        Asserts.assertTrue(ObjectUtils.isLogicalNull(accounts),"token已经存在！");

        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        SmtUserAccount userAccount=new SmtUserAccount();
        userAccount.setCreateTime(new Date());
        userAccount.setCreateUser(((Long) sessionVO.getId()).intValue());
        userAccount.setOrgId(((Long)sessionVO.getOrgId()).intValue());
        userAccount.setSmtAccountName(signatureVO.getState());
        userAccount.setSmtFreshtoken(signatureVO.getRefreshToken());
        userAccount.setUpdateTime(new Date());
        smtUserAccountMapper.insertSelective(userAccount);

        SmtAccessToken smtAccessToken=new SmtAccessToken();
        smtAccessToken.setUpdateTime(new Date());
        smtAccessToken.setAccessToken(signatureVO.getAccessToken());
        smtAccessToken.setSmtId(userAccount.getOrgId());
        smtAccessTokenMapper.insert(smtAccessToken);
    }


    @Override
    /**通过refreshtoken获取accesstoken，直接返回，不写数据库*/
    public String fetchAccessTokenByAccountID(Long accountId){
        SmtUserAccount userAccount = smtUserAccountMapper.selectByPrimaryKey(accountId);
        if (userAccount==null){logger.error("id为"+accountId+"没有查询到记录"); return null;}
        if (StringUtils.isBlank(userAccount.getSmtFreshtoken())){logger.error("id为"+accountId+"没有refreshtoken"); return null;}
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        signatureVO.setRefreshToken(userAccount.getSmtFreshtoken());
        AuthService.getAccesstokenByrefresh(signatureVO);
        return signatureVO.getAccessToken();
    }

    @Override
    /**通过freshtoken更新accesstoken  计入数据库*/
    public void refreshAccessToken(Long accountId){
        SmtUserAccount userAccount = smtUserAccountMapper.selectByPrimaryKey(accountId);
        if (userAccount==null){logger.error("id为"+accountId+"没有查询到记录"); return;}
        if (StringUtils.isBlank(userAccount.getSmtFreshtoken())){logger.error("id为"+accountId+"没有refreshtoken"); return;}
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        signatureVO.setRefreshToken(userAccount.getSmtFreshtoken());
        AuthService.getAccesstokenByrefresh(signatureVO);
        if (StringUtils.isBlank(signatureVO.getAccessToken())){logger.error("id为"+accountId+"没有获取到accessToken"); return;}

        SmtAccessTokenExample tokenExample=new SmtAccessTokenExample();
        tokenExample.createCriteria().andSmtIdEqualTo(accountId.intValue());
        List<SmtAccessToken> tokens = smtAccessTokenMapper.selectByExample(tokenExample);
        if (ObjectUtils.isLogicalNull(tokens)){
            SmtAccessToken smtAccessToken=new SmtAccessToken();
            smtAccessToken.setUpdateTime(new Date());
            smtAccessToken.setAccessToken(signatureVO.getAccessToken());
            smtAccessToken.setSmtId(userAccount.getOrgId());
            smtAccessTokenMapper.insert(smtAccessToken);
        }else {
            SmtAccessToken smtAccessToken=tokens.get(0);
            smtAccessToken.setUpdateTime(new Date());
            smtAccessToken.setAccessToken(signatureVO.getAccessToken());
            smtAccessTokenMapper.updateByPrimaryKey(smtAccessToken);
        }
    }
}
