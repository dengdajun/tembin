package com.alibabasmt.allservices.order.service;


import com.alibabasmt.database.smtorder.model.SmtOrderTable;
import com.alibabasmt.domains.querypojos.smtorder.OrderTableQuery;
import com.base.mybatis.page.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/20.
 */

public interface ISmtOrderTable {
    public void saveSmtOrderTable(SmtOrderTable smtOrderTable) throws Exception;
    SmtOrderTable selectSmtOrderTableByOrderId(String orderId);
    List<SmtOrderTable> parseSMTOrderAndSaveOrder(String result,Long smtAcountId) throws Exception;
    List<OrderTableQuery> selectSmtOrderTableList(Map map,Page page);
    SmtOrderTable selectSmtOrderTableById(Long id);
    List<SmtOrderTable> selectSmtOrderTableByStatusOrFolder(String status,String folderId, List<Long> ebays);
    void downloadOrders(List<SmtOrderTable> list,String outputFile,HttpServletResponse response) throws Exception;
}
