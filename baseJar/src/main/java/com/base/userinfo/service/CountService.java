package com.base.userinfo.service;

import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/3/5.
 */
public interface CountService {
    /**统计用户数量*/
    int[] countUser();

    List<UsercontrollerUserExtend> queryAllUser(Map map, Page page);

    /**查询启用的所有一级账户*/
    List<UsercontrollerUserExtend> queryAllUserS(Map map);

    /**查询绑定的ebay帐号个数**********************************************************************/
    int getEbayCount(String userID);

    /**查询绑定的ebay帐号List**********************************************************************/
    List<UsercontrollerEbayAccount> getEbayList(String userID);

    int getDayListingDataCount(String userid);

    int getTemBinAllListingDataCount(String userid);

    int getAllListingDataCount(String userid);

    int querySkuByUser(String userid);

    /**激活账号*/
    void activeNewUser(String userId) throws Exception;
}
