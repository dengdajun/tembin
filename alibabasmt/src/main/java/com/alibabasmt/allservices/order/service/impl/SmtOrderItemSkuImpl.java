package com.alibabasmt.allservices.order.service.impl;

import com.alibabasmt.allservices.order.service.ISmtOrderItemSku;
import com.alibabasmt.database.smtorder.mapper.SmtOrderItemSkuMapper;
import com.alibabasmt.database.smtorder.model.SmtOrderItemSku;
import com.alibabasmt.database.smtorder.model.SmtOrderItemSkuExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2015/3/26.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtOrderItemSkuImpl implements ISmtOrderItemSku {
    @Autowired
    private SmtOrderItemSkuMapper smtOrderItemSkuMapper;
    @Override
    public void saveSmtOrderItemSku(SmtOrderItemSku smtOrderItemSku) throws Exception {
        if(smtOrderItemSku.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderItemSku);
            smtOrderItemSkuMapper.insert(smtOrderItemSku);
        }else{
            SmtOrderItemSku t=smtOrderItemSkuMapper.selectByPrimaryKey(smtOrderItemSku.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),SmtOrderItemSkuMapper.class,smtOrderItemSku.getId(),"Synchronize");
            smtOrderItemSkuMapper.updateByPrimaryKeySelective(smtOrderItemSku);
        }
    }

    @Override
    public SmtOrderItemSku selectSmtOrderItemSkuByProductId(Long productId) {
        SmtOrderItemSkuExample example=new SmtOrderItemSkuExample();
        SmtOrderItemSkuExample.Criteria cr=example.createCriteria();
        cr.andProductidEqualTo(productId);
        List<SmtOrderItemSku> list=smtOrderItemSkuMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }
}
