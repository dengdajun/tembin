package com.alibabasmt.allservices.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibabasmt.allservices.order.service.ISmtOrderBuyerInfo;
import com.alibabasmt.database.smtorder.mapper.SmtOrderBuyerInfoMapper;
import com.alibabasmt.database.smtorder.model.SmtOrderBuyerInfo;
import com.alibabasmt.database.smtorder.model.SmtOrderBuyerInfoExample;
import com.alibabasmt.database.smtorder.model.SmtOrderBuyerInfo;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/25.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtOrderBuyerInfoImpl implements ISmtOrderBuyerInfo {
    @Autowired
    private SmtOrderBuyerInfoMapper smtOrderBuyerInfoMapper;
    @Override
    public void saveSmtOrderBuyerInfo(SmtOrderBuyerInfo smtOrderBuyerInfo) throws Exception {
        if(smtOrderBuyerInfo.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderBuyerInfo);
            smtOrderBuyerInfoMapper.insert(smtOrderBuyerInfo);
        }else{
            SmtOrderBuyerInfo t=smtOrderBuyerInfoMapper.selectByPrimaryKey(smtOrderBuyerInfo.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(), SmtOrderBuyerInfoMapper.class, smtOrderBuyerInfo.getId(), "Synchronize");
            smtOrderBuyerInfoMapper.updateByPrimaryKeySelective(smtOrderBuyerInfo);
        }
    }

    @Override
    public SmtOrderBuyerInfo selectSmtOrderBuyerInfoByLoginId(String loginId) {
        SmtOrderBuyerInfoExample example=new SmtOrderBuyerInfoExample();
        SmtOrderBuyerInfoExample.Criteria cr=example.createCriteria();
        cr.andLoginidEqualTo(loginId);
        List<SmtOrderBuyerInfo> list=smtOrderBuyerInfoMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public SmtOrderBuyerInfo paseSmtOrderBuyerInfoAndSave(Map jsons) throws Exception{
        JSONObject infoObject= (JSONObject) jsons.get("buyerInfo");
        SmtOrderBuyerInfo smtOrderBuyerInfo=new SmtOrderBuyerInfo();

        if(infoObject!=null){
            smtOrderBuyerInfo.setLastname(infoObject.getString("lastName"));
            smtOrderBuyerInfo.setCountry(infoObject.getString("country"));
            smtOrderBuyerInfo.setLoginid(infoObject.getString("loginId"));
            smtOrderBuyerInfo.setFirstname(infoObject.getString("firstName"));
            smtOrderBuyerInfo.setEmail(infoObject.getString("email"));
            SmtOrderBuyerInfo info=selectSmtOrderBuyerInfoByLoginId(smtOrderBuyerInfo.getLoginid());
            if(info!=null){
                smtOrderBuyerInfo.setId(info.getId());
            }
            smtOrderBuyerInfo.setUpdateTime(new Date());
            saveSmtOrderBuyerInfo(smtOrderBuyerInfo);
        }
        return smtOrderBuyerInfo;
    }
}
