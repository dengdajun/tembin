package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface OrderGetOrdersMapper {

    List<OrderGetOrdersQuery> selectOrderGetOrdersByGroupList(Map map, Page page);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByAccountFlag(Map map);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByItemFlag(Map map);

    List<OrderGetOrdersQuery> selectOrderGetOrdersBySendPaidMessage(Map map,Page page);

    List<OrderGetOrdersQuery> selectOrderGetOrdersBySendShipMessage(Map map,Page page);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByeBayAccountAndTime(Map map,Page page);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByeBayAccounts(Map map,Page page);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByBuyerPaypalAcountIsNull(Map map);

    List<TradingOrderGetOrders> selectOrderGetOrdersByeBayAccountsCheck(Map map);

    List<OrderGetOrdersQuery> selectOrderGetOrdersByeBayAccountAndTime1(Map map,Page page);
}