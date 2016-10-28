package com.alibabasmt.allservices.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibabasmt.allservices.order.service.ISmtOrderChildOrderDetails;
import com.alibabasmt.allservices.order.service.ISmtOrderProductAttributes;
import com.alibabasmt.database.smtorder.mapper.SmtOrderChildOrderDetailsMapper;
import com.alibabasmt.database.smtorder.model.SmtOrderChildOrderDetails;
import com.alibabasmt.database.smtorder.model.SmtOrderChildOrderDetailsExample;
import com.alibabasmt.database.smtorder.model.SmtOrderProductAttributes;
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
 * Created by Administrtor on 2015/3/30.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtOrderChildOrderDetailsImpl implements ISmtOrderChildOrderDetails {
    @Autowired
    private SmtOrderChildOrderDetailsMapper smtOrderChildOrderDetailsMapper;
    @Autowired
    private ISmtOrderProductAttributes iSmtOrderProductAttributes;
    @Override
    public void saveSmtOrderChildOrderDetails(SmtOrderChildOrderDetails smtOrderChildOrderDetails) throws Exception {
        if(smtOrderChildOrderDetails.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderChildOrderDetails);
            smtOrderChildOrderDetailsMapper.insert(smtOrderChildOrderDetails);
        }else{
            SmtOrderChildOrderDetails t=smtOrderChildOrderDetailsMapper.selectByPrimaryKey(smtOrderChildOrderDetails.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(), SmtOrderChildOrderDetailsMapper.class, smtOrderChildOrderDetails.getId(), "Synchronize");
            smtOrderChildOrderDetailsMapper.updateByPrimaryKeySelective(smtOrderChildOrderDetails);
        }
    }

    @Override
    public SmtOrderChildOrderDetails selectSmtOrderChildOrderDetailsByChildOrderId(String childId) {
        SmtOrderChildOrderDetailsExample example=new SmtOrderChildOrderDetailsExample();
        SmtOrderChildOrderDetailsExample.Criteria cr=example.createCriteria();
        cr.andChildidEqualTo(childId);
        List<SmtOrderChildOrderDetails> list=smtOrderChildOrderDetailsMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public void parseSmtOrderChildOrderDetailsAndSave(Map jsons) throws Exception {
        JSONArray childOrderList= (JSONArray) jsons.get("childOrderList");
        for(int i=0;i<childOrderList.size();i++){
            JSONObject childObj=childOrderList.getJSONObject(i);
            SmtOrderChildOrderDetails smtOrderChildOrderDetails=new SmtOrderChildOrderDetails();
            smtOrderChildOrderDetails.setFundstatus(childObj.getString("fundStatus"));
            smtOrderChildOrderDetails.setOrderstatus(childObj.getString("orderStatus"));
            smtOrderChildOrderDetails.setProductid(childObj.getLong("productId"));
            smtOrderChildOrderDetails.setProductunit(childObj.getString("productUnit"));
            smtOrderChildOrderDetails.setChildid(String.valueOf(childObj.getLong("id")));
            smtOrderChildOrderDetails.setLotnum(childObj.getInteger("lotNum"));
            smtOrderChildOrderDetails.setFrozenstatus(childObj.getString("frozenStatus"));
            smtOrderChildOrderDetails.setSkucode(childObj.getString("skuCode"));
            smtOrderChildOrderDetails.setIssuestatus(childObj.getString("issueStatus"));
            smtOrderChildOrderDetails.setProductname(childObj.getString("productName"));
            JSONObject initOrderAmt=childObj.getJSONObject("initOrderAmt");
            if(initOrderAmt!=null){
                smtOrderChildOrderDetails.setInitorderamt(String.valueOf(initOrderAmt.getBigDecimal("amount")));
                smtOrderChildOrderDetails.setInitorderamtcurrency(initOrderAmt.getString("currencyCode"));
            }
            JSONObject productPrice=childObj.getJSONObject("productPrice");
            if(productPrice!=null){
                smtOrderChildOrderDetails.setProductprice(String.valueOf(productPrice.getBigDecimal("amount")));
                smtOrderChildOrderDetails.setProductpricecurrency(productPrice.getString("currencyCode"));
            }
            String productAttributes=childObj.getString("productAttributes");
            //String productAttributes="{\"sku\":[{\"order\":1,\"pId\":14,\"pName\":\"Color\",\"pValue\":\"Red\",\"pValueId\":10,\"selfDefineValue\":\"\",\"showType\":\"colour_atla\",\"skuImg\":\"\"}]}";
            Map attrJson = JSON.parseObject(productAttributes, HashMap.class);
            JSONArray attrArray= (JSONArray) attrJson.get("sku");
            for(int j=0;j<attrArray.size();j++){
                JSONObject attrObj=attrArray.getJSONObject(j);
                SmtOrderProductAttributes smtOrderProductAttributes=new SmtOrderProductAttributes();
                smtOrderProductAttributes.setSkuimg(attrObj.getString("skuImg"));
                smtOrderProductAttributes.setShowtype(attrObj.getString("showType"));
                smtOrderProductAttributes.setSelfdefinevalue(attrObj.getString("selfDefineValue"));
                smtOrderProductAttributes.setOrderd(attrObj.getInteger("order"));
                smtOrderProductAttributes.setPid(attrObj.getInteger("pId"));
                smtOrderProductAttributes.setPname(attrObj.getString("pName"));
                smtOrderProductAttributes.setProductid(childObj.getLong("productId"));
                smtOrderProductAttributes.setPvalue(attrObj.getString("pValue"));
                smtOrderProductAttributes.setPvalueid(String.valueOf(attrObj.getBigInteger("pValueId")));
                SmtOrderProductAttributes attributes=iSmtOrderProductAttributes.selectSmtOrderProductAttributesByProductId(smtOrderProductAttributes.getProductid());
                if(attributes!=null){
                    smtOrderProductAttributes.setId(attributes.getId());
                }
                smtOrderProductAttributes.setUpdateTime(new Date());
                iSmtOrderProductAttributes.saveSmtOrderProductAttributes(smtOrderProductAttributes);
            }
            SmtOrderChildOrderDetails details=selectSmtOrderChildOrderDetailsByChildOrderId(smtOrderChildOrderDetails.getChildid());
            if(details!=null){
                smtOrderChildOrderDetails.setId(details.getId());
            }
            smtOrderChildOrderDetails.setUpdateTime(new Date());
            saveSmtOrderChildOrderDetails(smtOrderChildOrderDetails);
        }
    }
}
