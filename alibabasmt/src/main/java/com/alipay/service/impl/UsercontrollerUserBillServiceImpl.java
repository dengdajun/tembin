package com.alipay.service.impl;

import com.alipay.database.customsmt.mapper.UsercontrollerUserBillQueryMapper;
import com.alipay.domains.querypojos.UsercontrollerUserBillQuery;
import com.base.database.userinfo.model.UsercontrollerUserBill;
import com.base.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/5/31.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UsercontrollerUserBillServiceImpl implements com.alipay.service.IUsercontrollerUserBillService {
    @Autowired
    private UsercontrollerUserBillQueryMapper usercontrollerUserBillQueryMapper;

    @Override
    public List<UsercontrollerUserBillQuery> selectBillList(Map map, Page page){
        return this.usercontrollerUserBillQueryMapper.selectUserBillQuery(map,page);
    }

    @Override
    public UsercontrollerUserBillQuery selectBillFee(Map map){
        return this.usercontrollerUserBillQueryMapper.selectUserBillFee(map);
    }


    @Override
    public List<UsercontrollerUserBillQuery> selectBillType(Map map){
        return this.usercontrollerUserBillQueryMapper.selectUserBillTypeQuery(map);
    }
}
