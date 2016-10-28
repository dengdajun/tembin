package com.thirdpart.mapper;

import com.base.domains.SessionVO;
import com.thirdpart.vo.AccessKeyVO;

import java.util.Map;

/**
 * Created by Administrator on 2015/4/24.
 */
public interface ThirdPartMapper {
    SessionVO findSessionVO4ThirdPart(Map map);
    AccessKeyVO findUserByKey(Map map);
}
