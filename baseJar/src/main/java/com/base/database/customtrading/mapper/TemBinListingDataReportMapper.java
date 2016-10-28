package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingListingData;
import com.base.database.trading.model.TradingListingReport;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface TemBinListingDataReportMapper {

    /**
     *查询本平台刊登数量，当天1，总数0
     * @param map
     * @return
     */
    List<TradingListingData> selectTemBinListingReportList(Map map);

    List<TradingListingData> selectAllListingDataReportList(Map map);

    List<UsercontrollerUserExtend> queryAllUser(Map map,Page page);

    List<UsercontrollerUserExtend> queryAllUserS(Map map);

    List<TradingItem> querySkuByUser(Map map);

}