package com.alibabasmt.database.customsmt.mapper;

import com.alibabasmt.database.smtproduct.model.SmtProduct;
import com.alibabasmt.database.smtproduct.model.SmtProductListing;
import com.alibabasmt.domains.querypojos.smtproduct.SmtProductQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface SmtProductQueryMapper {

    /**
     *
     * @param map
     * @return
     */
    List<SmtProductQuery> selectSmtProductQueryList(Map map, Page page);

}