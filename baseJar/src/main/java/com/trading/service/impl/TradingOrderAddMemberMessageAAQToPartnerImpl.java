package com.trading.service.impl;

import com.base.database.customtrading.mapper.AddMemberMessageAAQToPartnerMapper;
import com.base.database.trading.mapper.TradingOrderAddMemberMessageAAQToPartnerMapper;
import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;
import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartnerExample;
import com.base.domains.querypojos.TradingOrderAddMemberMessageAAQToPartnerQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.DateUtils;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderAddMemberMessageAAQToPartnerImpl implements com.trading.service.ITradingOrderAddMemberMessageAAQToPartner {

    static Logger logger1 = Logger.getLogger(TradingOrderAddMemberMessageAAQToPartnerImpl.class);

    @Autowired
    private TradingOrderAddMemberMessageAAQToPartnerMapper tradingOrderAddMemberMessageAAQToPartnerMapper;
    @Autowired
    private AddMemberMessageAAQToPartnerMapper addMemberMessageAAQToPartnerMapper;
    @Override
    public void saveOrderAddMemberMessageAAQToPartner(TradingOrderAddMemberMessageAAQToPartner OrderAddMemberMessageAAQToPartner) throws Exception {
        if(OrderAddMemberMessageAAQToPartner.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderAddMemberMessageAAQToPartner);
            tradingOrderAddMemberMessageAAQToPartnerMapper.insert(OrderAddMemberMessageAAQToPartner);
        }else{
            TradingOrderAddMemberMessageAAQToPartner t=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByPrimaryKey(OrderAddMemberMessageAAQToPartner.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderAddMemberMessageAAQToPartnerMapper.class,OrderAddMemberMessageAAQToPartner.getId(),"Synchronize");
            tradingOrderAddMemberMessageAAQToPartnerMapper.updateByPrimaryKeySelective(OrderAddMemberMessageAAQToPartner);
        }
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(String TransactionId,String itemid,Integer type,Long ebayId,
                                                                                                                        String replied,Integer...messageflag) {
        if (ObjectUtils.isLogicalNull(TransactionId)){
            //logger1.error("消息同步交易号为空!!!===ebayId:"+ebayId);
            //throw new RuntimeException("交易号不能为空啊！3");
            return null;
        }
        if (StringUtils.isBlank(itemid)||"0".equalsIgnoreCase(itemid)|| MyStringUtil.specEmptyString(itemid)){
            Integer.parseInt("errorItemid");
        }

        TradingOrderAddMemberMessageAAQToPartnerExample example=new TradingOrderAddMemberMessageAAQToPartnerExample();
        TradingOrderAddMemberMessageAAQToPartnerExample.Criteria cr=example.createCriteria();
        cr.andTransactionidEqualTo(TransactionId);
        cr.andMessagetypeEqualTo(type);
        cr.andItemidEqualTo(itemid);
        if(ebayId!=null){
            cr.andEbayIdEqualTo(ebayId);
        }
        if(messageflag!=null&&messageflag.length>0){
            cr.andMessageflagEqualTo(messageflag[0]);
        }
        cr.andRepliedEqualTo(replied);
        List<TradingOrderAddMemberMessageAAQToPartner> list=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartnerQuery> selectTradingOrderAddMemberMessageAAQToPartner(Map map, Page page) {
        return addMemberMessageAAQToPartnerMapper.selectByAddMemberMessageAAQToPartnerList(map,page);
    }

    @Override
    public void deleteTradingOrderAddMemberMessageAAQToPartner(TradingOrderAddMemberMessageAAQToPartner partner) {
        if(partner!=null&&partner.getId()!=null){
            tradingOrderAddMemberMessageAAQToPartnerMapper.deleteByPrimaryKey(partner.getId());
        }
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByItemIdAndSender(String itemid, Integer type, String sender,String recipient,String replied) {
        TradingOrderAddMemberMessageAAQToPartnerExample example=new TradingOrderAddMemberMessageAAQToPartnerExample();
        TradingOrderAddMemberMessageAAQToPartnerExample.Criteria cr=example.createCriteria();
        cr.andItemidEqualTo(itemid);
        cr.andMessagetypeEqualTo(type);
        cr.andSenderEqualTo(sender);
        cr.andRecipientidEqualTo(recipient);
        cr.andRepliedEqualTo(replied);
        List<TradingOrderAddMemberMessageAAQToPartner> list=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByTypeAndReplied(Integer type, String replied,Long Userid) {
        TradingOrderAddMemberMessageAAQToPartnerExample example=new TradingOrderAddMemberMessageAAQToPartnerExample();
        TradingOrderAddMemberMessageAAQToPartnerExample.Criteria cr=example.createCriteria();
        cr.andMessagetypeEqualTo(type);
        cr.andRepliedEqualTo(replied);
        cr.andCreateUserEqualTo(Userid);
        List<TradingOrderAddMemberMessageAAQToPartner> list=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByMessageID(String messageID, Integer type,String replied) {
        TradingOrderAddMemberMessageAAQToPartnerExample example=new TradingOrderAddMemberMessageAAQToPartnerExample();
        TradingOrderAddMemberMessageAAQToPartnerExample.Criteria cr=example.createCriteria();
        cr.andMessageidEqualTo(messageID);
        cr.andMessagetypeEqualTo(type);
        if(StringUtils.isNotBlank(replied)){
            cr.andRepliedEqualTo(replied);
        }
        List<TradingOrderAddMemberMessageAAQToPartner> list=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByAutoMessageIdAndOrderId(Long autoMessageId, Long orderId,String replied) {
        TradingOrderAddMemberMessageAAQToPartnerExample example=new TradingOrderAddMemberMessageAAQToPartnerExample();
        TradingOrderAddMemberMessageAAQToPartnerExample.Criteria cr=example.createCriteria();
        cr.andAutomessageIdEqualTo(autoMessageId);
        cr.andOrderIdEqualTo(orderId);
        if(replied!=null){
            cr.andRepliedEqualTo(replied);
        }
        List<TradingOrderAddMemberMessageAAQToPartner> list=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByAutoMessageIdAndBuyerAndItemid(Long autoMessageId, String buyer, String itemid, String replied) {
        TradingOrderAddMemberMessageAAQToPartnerExample example=new TradingOrderAddMemberMessageAAQToPartnerExample();
        TradingOrderAddMemberMessageAAQToPartnerExample.Criteria cr=example.createCriteria();
        cr.andAutomessageIdEqualTo(autoMessageId);
        cr.andRecipientidEqualTo(buyer);
        cr.andItemidEqualTo(itemid);
        Date date=new Date();
        Date start= DateUtils.turnToDateStart(date);
        Date end=DateUtils.turnToDateEnd(date);
        cr.andCreateTimeBetween(start,end);
        if(replied!=null){
            cr.andRepliedEqualTo(replied);
        }
        List<TradingOrderAddMemberMessageAAQToPartner> list=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderAddMemberMessageAAQToPartner> selectTradingOrderAddMemberMessageAAQToPartnerByOrderIdAndMessageTypeAndMessageFlag(Long orderId, Integer type, Integer messageFlag,String flag) {
        TradingOrderAddMemberMessageAAQToPartnerExample example=new TradingOrderAddMemberMessageAAQToPartnerExample();
        TradingOrderAddMemberMessageAAQToPartnerExample.Criteria cr=example.createCriteria();
        cr.andOrderIdEqualTo(orderId);
        cr.andMessagetypeEqualTo(type);
        cr.andMessageflagEqualTo(messageFlag);
        cr.andRepliedEqualTo(flag);
        List<TradingOrderAddMemberMessageAAQToPartner> list=tradingOrderAddMemberMessageAAQToPartnerMapper.selectByExample(example);
        return list;
    }
}
