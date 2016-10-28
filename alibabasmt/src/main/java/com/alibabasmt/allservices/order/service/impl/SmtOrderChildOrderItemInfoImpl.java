package com.alibabasmt.allservices.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibabasmt.allservices.order.service.ISmtOrderChildOrderItemInfo;
import com.alibabasmt.allservices.order.service.ISmtOrderItemSku;
import com.alibabasmt.database.smtorder.mapper.SmtOrderChildOrderItemInfoMapper;
import com.alibabasmt.database.smtorder.model.SmtOrderChildOrderItemInfo;
import com.alibabasmt.database.smtorder.model.SmtOrderChildOrderItemInfoExample;
import com.alibabasmt.database.smtorder.model.SmtOrderItemSku;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/26.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtOrderChildOrderItemInfoImpl implements ISmtOrderChildOrderItemInfo {
    @Autowired
    private SmtOrderChildOrderItemInfoMapper smtOrderChildOrderItemInfoMapper;
    @Autowired
    private ISmtOrderItemSku iSmtOrderItemSku;
    @Override
    public void saveSmtOrderChildOrderItemInfo(SmtOrderChildOrderItemInfo smtOrderChildOrderItemInfo) throws Exception {
        if(smtOrderChildOrderItemInfo.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderChildOrderItemInfo);
            smtOrderChildOrderItemInfoMapper.insert(smtOrderChildOrderItemInfo);
        }else{
            SmtOrderChildOrderItemInfo t=smtOrderChildOrderItemInfoMapper.selectByPrimaryKey(smtOrderChildOrderItemInfo.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),SmtOrderChildOrderItemInfoMapper.class,smtOrderChildOrderItemInfo.getId(),"Synchronize");
            smtOrderChildOrderItemInfoMapper.updateByPrimaryKeySelective(smtOrderChildOrderItemInfo);
        }
    }

    @Override
    public SmtOrderChildOrderItemInfo selectSmtOrderChildOrderItemInfoByOrderId(Long productId) {
        SmtOrderChildOrderItemInfoExample example=new SmtOrderChildOrderItemInfoExample();
        SmtOrderChildOrderItemInfoExample.Criteria cr=example.createCriteria();
        cr.andProductidEqualTo(productId);
        List<SmtOrderChildOrderItemInfo> list=smtOrderChildOrderItemInfoMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public void parseSmtOrderChildOrderItemInfoAndSave(Map jsons,String orderId) throws Exception {
        JSONArray childOrderExtInfoList= (JSONArray) jsons.get("childOrderExtInfoList");
        for(int i=0;i<childOrderExtInfoList.size();i++){
            JSONObject iteminfo=childOrderExtInfoList.getJSONObject(i);
            SmtOrderChildOrderItemInfo orderItemInfo=new SmtOrderChildOrderItemInfo();
            orderItemInfo.setOrderid(orderId);
            orderItemInfo.setUpdateTime(new Date());
            orderItemInfo.setProductid(iteminfo.getLong("productId"));
            orderItemInfo.setProductname(iteminfo.getString("productName"));
            orderItemInfo.setQuantity(iteminfo.getInteger("quantity"));
            JSONObject unitPrice=iteminfo.getJSONObject("unitPrice");
            if(unitPrice!=null){
                orderItemInfo.setAmount(String.valueOf(unitPrice.getBigDecimal("amount")));
                orderItemInfo.setCurrencycode(unitPrice.getString("currencyCode"));
            }
            SmtOrderChildOrderItemInfo infol=selectSmtOrderChildOrderItemInfoByOrderId(orderItemInfo.getProductid());
            if(infol!=null){
                orderItemInfo.setId(infol.getId());
            }
            saveSmtOrderChildOrderItemInfo(orderItemInfo);
            //保存商品sku
            String skuString=iteminfo.getString("sku");
            Map skuJson = JSON.parseObject(skuString, HashMap.class);
            JSONArray skuArray= (JSONArray) skuJson.get("sku");
            for(int j=0;j<skuArray.size();j++){
                JSONObject skuObj=skuArray.getJSONObject(i);
                SmtOrderItemSku smtOrderItemSku=new SmtOrderItemSku();
                smtOrderItemSku.setProductid(iteminfo.getLong("productId"));
                smtOrderItemSku.setUpdateTime(new Date());
                smtOrderItemSku.setOrderd(skuObj.getInteger("order"));
                smtOrderItemSku.setPid(skuObj.getInteger("pId"));
                smtOrderItemSku.setPname(skuObj.getString("pName"));
                smtOrderItemSku.setPvalue(skuObj.getString("pValue"));
                smtOrderItemSku.setPvalueid(String.valueOf(skuObj.getInteger("pValueId")));
                smtOrderItemSku.setSelfdefinevalue(skuObj.getString("selfDefineValue"));
                smtOrderItemSku.setShowtype(skuObj.getString("showType"));
                smtOrderItemSku.setSkuimg(skuObj.getString("skuImg"));
                SmtOrderItemSku skul=iSmtOrderItemSku.selectSmtOrderItemSkuByProductId(smtOrderItemSku.getProductid());
                if(skul!=null){
                    smtOrderItemSku.setId(skul.getId());
                }
                iSmtOrderItemSku.saveSmtOrderItemSku(smtOrderItemSku);
            }
        }
    }
}
