package com.alibabasmt.allservices.product.service.impl;

import com.alibabasmt.database.smtproduct.mapper.SmtAeopaeProductSkuMapper;
import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductSku;
import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductSkuExample;
import com.alibabasmt.domains.querypojos.smtproduct.SmtAeopaeProductSkuQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2015/4/18.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtAeopaeProductSkuImpl implements com.alibabasmt.allservices.product.service.ISmtAeopaeProductSku {
    @Autowired
    private SmtAeopaeProductSkuMapper smtAeopaeProductSkuMapper;

    @Override
    public List<SmtAeopaeProductSku> selectByParentId(long parentId){
        SmtAeopaeProductSkuExample sap = new SmtAeopaeProductSkuExample();
        sap.createCriteria().andParentIdEqualTo(parentId);
        return this.smtAeopaeProductSkuMapper.selectByExample(sap);
    }
}
