package com.base.database.publicd.mapper;

import com.base.domains.querypojos.ShippingCoustomQueryVO;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/7.
 */
public interface ShipingQueryMapper {
    /**用于判断运输方式是否已经存在的查询列表*/
    List<ShippingCoustomQueryVO> queryShipingType(Map map);
}
