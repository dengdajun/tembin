package com.base.utils.scheduleother.service;

import com.base.database.userinfo.model.UsercontrollerVipUser;

import java.util.List;

/**
 * Created by Administrator on 2015/6/2.
 */
public interface SystemVIPUserCheckService {
    /**查询需要扣费的列表*/
    List<UsercontrollerVipUser> queryVipList();

    void checkVip();

    UsercontrollerVipUser selectByUserId(String userId);
}
