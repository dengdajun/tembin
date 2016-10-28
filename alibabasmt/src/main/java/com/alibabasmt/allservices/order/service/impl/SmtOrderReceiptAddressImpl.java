package com.alibabasmt.allservices.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibabasmt.allservices.order.service.ISmtOrderReceiptAddress;
import com.alibabasmt.database.smtorder.mapper.SmtOrderReceiptAddressMapper;
import com.alibabasmt.database.smtorder.model.SmtOrderReceiptAddress;
import com.alibabasmt.database.smtorder.model.SmtOrderReceiptAddressExample;
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
public class SmtOrderReceiptAddressImpl implements ISmtOrderReceiptAddress {
    @Autowired
    private SmtOrderReceiptAddressMapper smtOrderReceiptAddressMapper;
    @Override
    public void saveSmtOrderReceiptAddress(SmtOrderReceiptAddress smtOrderReceiptAddress) throws Exception {
        if(smtOrderReceiptAddress.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderReceiptAddress);
            smtOrderReceiptAddressMapper.insert(smtOrderReceiptAddress);
        }else{
            SmtOrderReceiptAddress t=smtOrderReceiptAddressMapper.selectByPrimaryKey(smtOrderReceiptAddress.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(), SmtOrderReceiptAddressMapper.class, smtOrderReceiptAddress.getId(), "Synchronize");
            smtOrderReceiptAddressMapper.updateByPrimaryKeySelective(smtOrderReceiptAddress);
        }
    }

    @Override
    public SmtOrderReceiptAddress selectSmtOrderReceiptAddressByOrderId(String orderId) {
        SmtOrderReceiptAddressExample example=new SmtOrderReceiptAddressExample();
        SmtOrderReceiptAddressExample.Criteria cr=example.createCriteria();
        cr.andOrderidEqualTo(orderId);
        List<SmtOrderReceiptAddress> list=smtOrderReceiptAddressMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public SmtOrderReceiptAddress paseSmtOrderBuyerInfoAndSave(Map jsons,String orderId) throws Exception {
        SmtOrderReceiptAddress smtOrderReceiptAddress=new SmtOrderReceiptAddress();
        JSONObject addressObject= (JSONObject) jsons.get("receiptAddress");
        if(addressObject!=null){
            smtOrderReceiptAddress.setOrderid(orderId);
            smtOrderReceiptAddress.setPhonearea(addressObject.getString("phoneArea"));
            smtOrderReceiptAddress.setPhonecountry(addressObject.getString("phoneCountry"));
            smtOrderReceiptAddress.setProvince(addressObject.getString("province"));
            smtOrderReceiptAddress.setZip(addressObject.getString("zip"));
            smtOrderReceiptAddress.setPhonenumber(addressObject.getString("phoneNumber"));
            smtOrderReceiptAddress.setContactperson(addressObject.getString("contactPerson"));
            smtOrderReceiptAddress.setDetailaddress(addressObject.getString("detailAddress"));
            smtOrderReceiptAddress.setCity(addressObject.getString("city"));
            smtOrderReceiptAddress.setAddress2(addressObject.getString("address2"));
            smtOrderReceiptAddress.setCountry(addressObject.getString("country"));
            smtOrderReceiptAddress.setMobileno(addressObject.getString("mobileNo"));
            SmtOrderReceiptAddress address=selectSmtOrderReceiptAddressByOrderId(orderId);
            if(address!=null){
                smtOrderReceiptAddress.setId(address.getId());
            }
            smtOrderReceiptAddress.setUpdateTime(new Date());
            saveSmtOrderReceiptAddress(smtOrderReceiptAddress);
        }
        return smtOrderReceiptAddress;
    }
}
