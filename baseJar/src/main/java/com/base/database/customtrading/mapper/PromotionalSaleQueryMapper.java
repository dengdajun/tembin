package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.PaypalQuery;
import com.base.domains.querypojos.PromotionalSaleQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface PromotionalSaleQueryMapper {

    /**
     *
     * @param map
     * @return
     */
    List<PromotionalSaleQuery> selectByList(Map map, Page page);
}