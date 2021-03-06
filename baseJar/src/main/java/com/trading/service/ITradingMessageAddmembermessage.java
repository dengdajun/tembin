package com.trading.service;

import com.base.database.trading.model.TradingMessageAddmembermessage;
import com.base.domains.querypojos.MessageAddmymessageQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/05.
 */
public interface ITradingMessageAddmembermessage {
    void saveMessageAddmembermessage(TradingMessageAddmembermessage MessageAddmembermessage) throws Exception;
    List<TradingMessageAddmembermessage> selectMessageGetmymessageByMessageId(String messageId,String replied);
    List<MessageAddmymessageQuery> selectMessageGetmymessageByGroupList(Map map,Page page);
    /*TradingMessageAddmembermessage toDAOPojo(String payInfo, String shippingInfo, String contactInfo, String guaranteeInfo, String feedbackInfo) throws  Exception;*/

   /* List<MessageGetmymessageQuery> selectByMessageGetmymessageList(Map map);

    List<MessageGetmymessageQuery> selectByMessageGetmymessageList(Map map, Page page);

    List<MessageGetmymessageQuery> selectMessageGetmymessageByGroupList(Map map, Page page);

    List<MessageGetmymessageQuery> selectMessageGetmymessageBySender(Map map);*/
}
