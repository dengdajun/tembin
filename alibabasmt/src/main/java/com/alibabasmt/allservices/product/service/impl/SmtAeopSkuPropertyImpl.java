package com.alibabasmt.allservices.product.service.impl;

import com.alibabasmt.database.smtproduct.mapper.SmtAeopSkuPropertyMapper;
import com.alibabasmt.database.smtproduct.model.SmtAeopSkuProperty;
import com.alibabasmt.database.smtproduct.model.SmtAeopSkuPropertyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2015/4/21.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtAeopSkuPropertyImpl implements com.alibabasmt.allservices.product.service.ISmtAeopSkuProperty {
    @Autowired
    private SmtAeopSkuPropertyMapper smtAeopSkuPropertyMapper;

    @Override
    public List<SmtAeopSkuProperty> selectByParentId(long parentid){
        SmtAeopSkuPropertyExample sa = new SmtAeopSkuPropertyExample();
        sa.createCriteria().andParentIdEqualTo(parentid);
        return this.smtAeopSkuPropertyMapper.selectByExample(sa);
    }
}
