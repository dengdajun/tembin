package com.alibabasmt.allservices.productlisting.service;



import com.alibabasmt.database.smtproduct.model.SmtProductListing;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/31.
 */
public interface ISmtProductListing {
    void saveSmtProductListing(SmtProductListing smtProductListing);

    void saveSmtProductListingList(List<SmtProductListing> list);

    Map queryProductListJson(long smtAccount, int pageSize, int currentPage, String type);

    void taskProductList(long smtAccountId, String type);

    SmtProductListing selectByProductId(long productId);

    List<SmtProductListing> selectByProductQueryList(Map map,Page page);
}
