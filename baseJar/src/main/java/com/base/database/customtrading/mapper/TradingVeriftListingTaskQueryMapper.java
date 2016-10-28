package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingVeriftListingTask;
import com.base.domains.querypojos.ItemAddressQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface TradingVeriftListingTaskQueryMapper {

    /**
     *
     * @param map
     * @return
     */
    List<TradingVeriftListingTask> selectByVeriftListingTask(Map map, Page page);
}