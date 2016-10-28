package com.alibabasmt.database.customsmt.mapper;

import com.alibabasmt.database.smtproduct.model.SmtProductListing;
import com.alibabasmt.domains.querypojos.smtaccount.SmtUserAccountExt;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface SmtProductListingQueryMapper {

    /**
     *
     * @param map
     * @return
     */
    List<SmtProductListing> selectSmtProductListingQueryList(Map map,Page page);

}