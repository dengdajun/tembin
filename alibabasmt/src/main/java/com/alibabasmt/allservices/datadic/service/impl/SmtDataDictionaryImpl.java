package com.alibabasmt.allservices.datadic.service.impl;

import com.alibabasmt.database.datadic.mapper.SmtDataDictionaryMapper;
import com.alibabasmt.database.datadic.model.SmtDataDictionary;
import com.alibabasmt.database.datadic.model.SmtDataDictionaryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrtor on 2015/3/21.
 */
@Service
public class SmtDataDictionaryImpl implements com.alibabasmt.allservices.datadic.service.ISmtDataDictionary {
    @Autowired
    private SmtDataDictionaryMapper smtDataDictionaryMapper;

    /**
     * 按类型查询
     * @param type
     * @return
     */
    @Override
    public List<SmtDataDictionary> queryByType(String type){
        SmtDataDictionaryExample sdde = new SmtDataDictionaryExample();
        sdde.createCriteria().andTypeEqualTo(type);
        return this.smtDataDictionaryMapper.selectByExample(sdde);
    }

    @Override
    public List<SmtDataDictionary> queryByTypeAndCountryId(String type, Long countryId) {
        SmtDataDictionaryExample sdde = new SmtDataDictionaryExample();
        sdde.createCriteria().andTypeEqualTo(type).andParentIdEqualTo(countryId);
        return this.smtDataDictionaryMapper.selectByExample(sdde);
    }

    @Override
    public SmtDataDictionary queryByTypeAndName(String type, String name) {
        SmtDataDictionaryExample sdde = new SmtDataDictionaryExample();
        sdde.createCriteria().andTypeEqualTo(type).andNameEqualTo(name);
        List<SmtDataDictionary> list=this.smtDataDictionaryMapper.selectByExample(sdde);
        return list!=null&&list.size()>0?list.get(0):null;
    }

}
