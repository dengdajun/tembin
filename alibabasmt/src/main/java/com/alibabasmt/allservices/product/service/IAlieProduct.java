package com.alibabasmt.allservices.product.service;

import com.alibabasmt.database.smtproduct.model.SmtProduct;
import com.alibabasmt.database.smtproduct.model.SmtProductListing;
import com.alibabasmt.domains.querypojos.smtproduct.SmtProductQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/19.
 */
public interface IAlieProduct {
    List<Map> queryCategoryByIdList(String cateid,long smtAccountId);

    List<Map> queryCategoryAttrByIdList(String cateid,long smtAccountId);

    Map queryByProductId(long productId, long smtAccountId);

    void saveSynchronizeProduct(long productId, long smtAccountId,SmtProductListing smtProductListing) throws Exception;

    void saveSmtProduct(SmtProduct smtProduct);

    List<SmtProductQuery> selectSmtProductQueryList(Map map, Page page);

    SmtProduct selectById(long id);
}
