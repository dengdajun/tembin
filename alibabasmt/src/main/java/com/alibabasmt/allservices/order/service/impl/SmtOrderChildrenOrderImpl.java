package com.alibabasmt.allservices.order.service.impl;


import com.alibabasmt.allservices.order.service.ISmtOrderChildrenOrder;
import com.alibabasmt.database.smtorder.mapper.SmtOrderChildrenOrderMapper;
import com.alibabasmt.database.smtorder.model.SmtOrderChildrenOrder;
import com.alibabasmt.database.smtorder.model.SmtOrderChildrenOrderExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2015/3/20.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtOrderChildrenOrderImpl implements ISmtOrderChildrenOrder {

    @Autowired
    private SmtOrderChildrenOrderMapper smtOrderChildrenOrderMapper;
    @Override
    public void saveSmtOrderChildrenOrder(SmtOrderChildrenOrder smtOrderChildrenOrder) throws Exception {
        if(smtOrderChildrenOrder.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderChildrenOrder);
            smtOrderChildrenOrderMapper.insert(smtOrderChildrenOrder);
        }else{
            SmtOrderChildrenOrder t=smtOrderChildrenOrderMapper.selectByPrimaryKey(smtOrderChildrenOrder.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),SmtOrderChildrenOrderMapper.class,smtOrderChildrenOrder.getId(),"Synchronize");
            smtOrderChildrenOrderMapper.updateByPrimaryKeySelective(smtOrderChildrenOrder);
        }
    }

    @Override
    public SmtOrderChildrenOrder selectSmtOrderChildrenOrderByChildId(String orderId) {
        SmtOrderChildrenOrderExample example=new SmtOrderChildrenOrderExample();
        SmtOrderChildrenOrderExample.Criteria cr=example.createCriteria();
        cr.andChildidEqualTo(orderId);
        List<SmtOrderChildrenOrder> list=smtOrderChildrenOrderMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public List<SmtOrderChildrenOrder> selectSmtOrderChildrenOrderByParentId(String ParentId) {
        SmtOrderChildrenOrderExample example=new SmtOrderChildrenOrderExample();
        SmtOrderChildrenOrderExample.Criteria cr=example.createCriteria();
        cr.andParentorderidEqualTo(ParentId);
        return smtOrderChildrenOrderMapper.selectByExample(example);
    }
}
