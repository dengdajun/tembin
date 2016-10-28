package com.alibabasmt.allservices.authorize.service.impl;

import com.alibabasmt.allservices.authorize.service.SmtAccountManService;
import com.alibabasmt.database.smtaccount.mapper.SmtUserAccountManagerMapper;
import com.alibabasmt.database.smtaccount.mapper.SmtUserAccountMapper;
import com.alibabasmt.database.smtaccount.model.SmtUserAccount;
import com.alibabasmt.database.smtaccount.model.SmtUserAccountExample;
import com.alibabasmt.database.smtaccount.model.SmtUserAccountManager;
import com.alibabasmt.database.smtaccount.model.SmtUserAccountManagerExample;
import com.alibabasmt.domains.querypojos.smtaccount.SmtUserAccountExt;
import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/26.
 * 关于速卖通帐号管理的service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtAccountManServiceImpl implements SmtAccountManService {
    @Autowired
    private SmtUserAccountMapper smtUserAccountMapper;
    @Autowired
    private SmtUserAccountManagerMapper smtUserAccountManagerMapper;
    /**获取当前登录人所在公司所有的smt帐号*/
    @Override
    public List<SmtUserAccountExt> querySMTAccByOrg(){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        List<SmtUserAccountExt> aexs=new ArrayList<>();
        SmtUserAccountExample example=new SmtUserAccountExample();
        example.createCriteria().andOrgIdEqualTo(((Long)sessionVO.getOrgId()).intValue());
        List<SmtUserAccount> as=smtUserAccountMapper.selectByExample(example);
        if (!ObjectUtils.isLogicalNull(as)){
            for (SmtUserAccount a:as){
                SmtUserAccountExt e=new SmtUserAccountExt();
                ConvertPOJOUtil.coverWithSpring(a,e);
                e.setSmtFreshtoken("");
                aexs.add(e);
            }
        }
        return aexs;
    }

    /**获取当前登录人管理的速卖通帐号*/
    @Override
    public List<SmtUserAccountExt> querySMTAccByCurrId(){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        List<SmtUserAccountExt> aexs=new ArrayList<>();
        SmtUserAccountManagerExample example=new SmtUserAccountManagerExample();
        example.createCriteria().andSysIdEqualTo(sessionVO.getId());
        List<SmtUserAccountManager> managers=smtUserAccountManagerMapper.selectByExample(example);
        List<Long> lid=new ArrayList<>();
        if (!ObjectUtils.isLogicalNull(managers)){
            for (SmtUserAccountManager manager:managers){
                if (manager.getSmtId()!=null && manager.getSmtId()!=0){
                    lid.add(manager.getSmtId());
                }

            }
        }else {
            return aexs;
        }



        SmtUserAccountExample example2=new SmtUserAccountExample();
        example2.createCriteria().andIdIn(lid);
        List<SmtUserAccount> as=smtUserAccountMapper.selectByExample(example2);
        if (!ObjectUtils.isLogicalNull(as)){
            for (SmtUserAccount a:as){
                SmtUserAccountExt e=new SmtUserAccountExt();
                ConvertPOJOUtil.coverWithSpring(a,e);
                e.setSmtFreshtoken("");
                aexs.add(e);
            }
        }
        return aexs;
    }



}
