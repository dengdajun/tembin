package com.trading.service;

import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;
import com.base.domains.querypojos.TradingOrderAddMemberMessageAAQToPartnerQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderAddMemberMessageAAQToPartner {

    void saveOrderAddMemberMessageAAQToPartner(TradingOrderAddMemberMessageAAQToPartner OrderAddMemberMessageAAQToPartner) throws Exception;
    List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(String TransactionId,String itemid,Integer type,Long ebayId,String replied,Integer...messageflag);
    List<TradingOrderAddMemberMessageAAQToPartnerQuery> selectTradingOrderAddMemberMessageAAQToPartner(Map map, Page page);
    void deleteTradingOrderAddMemberMessageAAQToPartner(TradingOrderAddMemberMessageAAQToPartner partner);
    List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByItemIdAndSender(String itemid,Integer type,String sender,String recipient,String replied);
    List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByTypeAndReplied(Integer type,String replied,Long Userid);
    List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByMessageID(String messageID,Integer type,String replied);
    List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByAutoMessageIdAndOrderId(Long autoMessageId,Long orderId,String replied);
    List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByAutoMessageIdAndBuyerAndItemid(Long autoMessageId,String buyer,String itemid,String replied);
    List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByOrderIdAndMessageTypeAndMessageFlag(Long orderId,Integer type,Integer messageFlag,String flag);
}
