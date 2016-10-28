package com.thirdpart.service;

import com.thirdpart.vo.AccessKeyVO;
import com.thirdpart.vo.ThirdPartAccountVO;

import java.util.List;

/**
 * Created by Administrator on 2015/4/24.
 */
public interface ThirdPartUserService {
    /**同步帐户 一个主账号所有子账号为一个list*/
    List<AccessKeyVO> sync3PartAccount(List<ThirdPartAccountVO> thirdPartAccountVOs);

    /**根据用户的mark和第三方的mark，生成accessKey*/
    String generateAccessKey(String userMark, String thirdPartMark);

    /**模拟登陆，放入登陆信息*/
    void thirdPartLogin(String accessKey);
}
