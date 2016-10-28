package com.alibabasmt.database.customsmt.mapper;

import com.alibabasmt.domains.querypojos.smtorder.OrderTableQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface MessageMapper {

    /**
     *
     * @param map
     * @return
     */
    List<OrderTableQuery> selectSmtMessageList(Map map, Page page);

}