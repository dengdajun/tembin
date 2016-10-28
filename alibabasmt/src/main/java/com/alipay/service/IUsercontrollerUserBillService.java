package com.alipay.service;

import com.alipay.domains.querypojos.UsercontrollerUserBillQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/5/31.
 */
public interface IUsercontrollerUserBillService {
    List<UsercontrollerUserBillQuery> selectBillList(Map map, Page page);

    UsercontrollerUserBillQuery selectBillFee(Map map);

    List<UsercontrollerUserBillQuery> selectBillType(Map map);
}
