package com.alibabasmt.allservices.product.service.impl;

import com.alibabasmt.database.smtproduct.mapper.SmtAeopaeProductPropertyMapper;
import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductProperty;
import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductPropertyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2015/4/13.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtAeopaeProductPropertyImpl implements com.alibabasmt.allservices.product.service.ISmtAeopaeProductProperty {

    @Autowired
    private SmtAeopaeProductPropertyMapper smtAeopaeProductPropertyMapper;

    @Override
    public List<SmtAeopaeProductProperty> selectByParentId(long parentId){
        SmtAeopaeProductPropertyExample sap = new SmtAeopaeProductPropertyExample();
        sap.createCriteria().andParentIdEqualTo(parentId);
        return this.smtAeopaeProductPropertyMapper.selectByExample(sap);
    }
}
