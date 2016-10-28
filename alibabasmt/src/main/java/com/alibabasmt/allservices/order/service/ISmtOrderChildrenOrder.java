package com.alibabasmt.allservices.order.service;


import com.alibabasmt.database.smtorder.model.SmtOrderChildrenOrder;

import java.util.List;

/**
 * Created by Administrtor on 2015/3/20.
 */
public interface ISmtOrderChildrenOrder {
    public void saveSmtOrderChildrenOrder(SmtOrderChildrenOrder smtOrderChildrenOrder) throws Exception;
    SmtOrderChildrenOrder selectSmtOrderChildrenOrderByChildId(String orderId);
    List<SmtOrderChildrenOrder> selectSmtOrderChildrenOrderByParentId(String ParentId);
}
