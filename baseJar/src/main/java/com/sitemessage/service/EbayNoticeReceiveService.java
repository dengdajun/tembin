package com.sitemessage.service;

/**
 * Created by Administrator on 2015/2/5.
 */
public interface EbayNoticeReceiveService {
    /**处理MyMessagesM2MMessage消息接收*/
    void processMyMessagesM2MMessage(String xml) throws Exception;

    void processFixedPriceTransaction(String xml) throws Exception;

    void processItemSold(String xml) throws Exception;

}
