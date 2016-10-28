package com.alibabasmt.allservices.datadic.service;

import com.alibabasmt.database.datadic.model.SmtDataDictionary;

import java.util.List;

/**
 * Created by Administrtor on 2015/3/21.
 */
public interface ISmtDataDictionary {
    List<SmtDataDictionary> queryByType(String type);
    List<SmtDataDictionary> queryByTypeAndCountryId(String type,Long countryId);
    SmtDataDictionary queryByTypeAndName(String type,String name);
}
