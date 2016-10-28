package com.test.mapper2;

import com.base.database.publicd.model.PublicDataDict;

import java.util.List;
import java.util.Map;

/**
 * Created by chace.cai on 2014/7/9.
 */
public interface TestFTPMapper {
    List<Map> queryFtpAccount(Map map);
    void updateFtpPW(Map map);
    void addFtpAccount(Map map);



}
