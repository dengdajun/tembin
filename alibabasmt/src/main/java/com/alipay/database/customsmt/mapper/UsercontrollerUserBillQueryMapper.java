package com.alipay.database.customsmt.mapper;

import com.alipay.domains.querypojos.UsercontrollerUserBillQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/5/31.
 */
public interface UsercontrollerUserBillQueryMapper {
    /**
     * 交费记录log
     * @param map
     * @param page
     * @return
     */
    List<UsercontrollerUserBillQuery> selectUserBillQuery(Map map,Page page);

    /**
     * 查询余额
     * @param map
     * @return
     */
    UsercontrollerUserBillQuery selectUserBillFee(Map map);

    /**
     * 查询最近交费，扣费信息
     * @param map
     * @return
     */
    List<UsercontrollerUserBillQuery> selectUserBillTypeQuery(Map map);
}
