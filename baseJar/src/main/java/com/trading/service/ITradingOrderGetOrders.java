package com.trading.service;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderGetOrders {

    void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders) throws Exception;

    List<OrderGetOrdersQuery> selectOrderGetOrdersByGroupList(Map map, Page page);

    List<TradingOrderGetOrders> selectOrderGetOrdersByOrderId(String orderId);

    List<TradingOrderGetOrders> selectOrderGetOrdersByTransactionId(String TransactionId,Long ebayId,String itemId);

    List<TradingOrderGetOrders> selectOrderGetOrdersByPaypalStatus(String status,List<Long> ebays);

    List<TradingOrderGetOrders> selectOrderGetOrdersByFolder(String folderId,List<Long> ebays);

    List<TradingOrderGetOrders> selectOrderGetOrdersByBuyerAndItemid(String itemid,String buyer);

    void downloadOrders(List<OrderGetOrdersQuery> list,String outputFile,HttpServletResponse response) throws Exception;

    List<OrderGetOrdersQuery> selectOrderGetOrdersBySendPaidMessage();

    List<OrderGetOrdersQuery> selectOrderGetOrdersBySendShipMessage();

    List<OrderGetOrdersQuery> selectOrderGetOrdersByeBayAccountAndTime1(Long ebay,Date start,Date end);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByeBayAccountAndTime2(Long userid, Date start, Date end);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByeBayAccountAndTime(Long ebay,Date start,Date end);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByeBayAccounts(Long ebay, Date start, Date end);

    TradingOrderGetOrders selectOrderGetOrdersById(Long id);

    void deleteOrderGetOrders(Long id) throws Exception;

    List<OrderGetOrdersQuery> selectOrderGetOrdersByItemFlag();

    List<OrderGetOrdersQuery> selectOrderGetOrdersByAccountFlag();

    List<TradingOrderGetOrders> selectOrderGetOrdersBySellerTrasactionFlag();

    List<TradingOrderGetOrders> selectOrderGetOrdersByTrackNumber();

    List<TradingOrderGetOrders> selectOrderGetOrdersByCreatedDateAndEbayAcount(Date date,String ebayName);

    List<TradingOrderGetOrders> selectOrderGetOrdersByAutoMessageId();

    List<OrderGetOrdersQuery> selectOrderGetOrdersByBuyerPaypalAcountIsNull();

    List<TradingOrderGetOrders> selectOrderGetOrdersByeBayAccountsCheck(Long ebay, Date start, Date end, String queryType);

    List<TradingOrderGetOrders> selectOrderGetOrdersByPaidAndNotShipped(List<UsercontrollerUserExtend> orgUsers);
}
