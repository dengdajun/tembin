package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.ListingDataQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface ListingDataMapper {

    List<ListingDataQuery> selectByExample(Map map,Page page);
    List<ListingDataQuery> selectListDateByExample(Map map,Page page);
    List<ListingDataQuery> selectListDateByTwentySku(Map map,Page page);
    List<ListingDataQuery> selectByEbayAccountSite(Map map,Page page);
    List<ListingDataQuery> selectByonSite(Map map,Page page);
}