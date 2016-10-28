package com.alibabasmt.database.customsmt.mapper;

import com.alibabasmt.domains.querypojos.smtorder.OrderTableQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface OrderTableMapper {

    /**
     *
     * @param map
     * @return
     */
    List<OrderTableQuery> selectSmtOrderTableList(Map map, Page page);

}