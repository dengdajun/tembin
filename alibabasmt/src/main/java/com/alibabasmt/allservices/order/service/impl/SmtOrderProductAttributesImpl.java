package com.alibabasmt.allservices.order.service.impl;

import com.alibabasmt.allservices.order.service.ISmtOrderProductAttributes;
import com.alibabasmt.database.smtorder.mapper.SmtOrderProductAttributesMapper;
import com.alibabasmt.database.smtorder.model.SmtOrderProductAttributes;
import com.alibabasmt.database.smtorder.model.SmtOrderProductAttributesExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2015/3/30.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtOrderProductAttributesImpl implements ISmtOrderProductAttributes {
    @Autowired
    private SmtOrderProductAttributesMapper smtOrderProductAttributesMapper;
    @Override
    public void saveSmtOrderProductAttributes(SmtOrderProductAttributes smtOrderProductAttributes) throws Exception {
        if(smtOrderProductAttributes.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderProductAttributes);
            smtOrderProductAttributesMapper.insert(smtOrderProductAttributes);
        }else{
            SmtOrderProductAttributes t=smtOrderProductAttributesMapper.selectByPrimaryKey(smtOrderProductAttributes.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(), SmtOrderProductAttributesMapper.class, smtOrderProductAttributes.getId(), "Synchronize");
            smtOrderProductAttributesMapper.updateByPrimaryKeySelective(smtOrderProductAttributes);
        }
    }

    @Override
    public SmtOrderProductAttributes selectSmtOrderProductAttributesByProductId(Long productId) {
        SmtOrderProductAttributesExample example=new SmtOrderProductAttributesExample();
        SmtOrderProductAttributesExample.Criteria cr=example.createCriteria();
        cr.andProductidEqualTo(productId);
        List<SmtOrderProductAttributes> list=smtOrderProductAttributesMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }
}
