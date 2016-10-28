package com.alibabasmt.allservices.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibabasmt.allservices.order.service.ISmtOrderMessage;
import com.alibabasmt.database.smtorder.mapper.SmtOrderMessageMapper;
import com.alibabasmt.database.smtorder.model.SmtOrderMessage;
import com.alibabasmt.database.smtorder.model.SmtOrderMessageExample;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/26.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtOrderMessageImpl implements ISmtOrderMessage {
    @Autowired
    private SmtOrderMessageMapper smtOrderMessageMapper;
    @Override
    public void saveSmtOrderMessage(SmtOrderMessage smtOrderMessage) throws Exception {
        if(smtOrderMessage.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderMessage);
            smtOrderMessageMapper.insert(smtOrderMessage);
        }else{
            SmtOrderMessage t=smtOrderMessageMapper.selectByPrimaryKey(smtOrderMessage.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),SmtOrderMessageMapper.class,smtOrderMessage.getId(),"Synchronize");
            smtOrderMessageMapper.updateByPrimaryKeySelective(smtOrderMessage);
        }
    }

    @Override
    public List<SmtOrderMessage> selectSmtOrderMessageByOrderId(String orderId) {
        SmtOrderMessageExample example=new SmtOrderMessageExample();
        SmtOrderMessageExample.Criteria cr=example.createCriteria();
        cr.andOrderidEqualTo(orderId);
        List<SmtOrderMessage> list=smtOrderMessageMapper.selectByExample(example);
        return list;
    }

    @Override
    public SmtOrderMessage selectSmtOrderMessageById(Long id) {
        return smtOrderMessageMapper.selectByPrimaryKey(id);
    }

    @Override
    public SmtOrderMessage selectSmtOrderMessageByOrderIdAndGmtCreate(String orderId, Date gmtCreate) {
        SmtOrderMessageExample example=new SmtOrderMessageExample();
        SmtOrderMessageExample.Criteria cr=example.createCriteria();
        cr.andOrderidEqualTo(orderId);
        cr.andGmtcreateEqualTo(gmtCreate);
        List<SmtOrderMessage> list=smtOrderMessageMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public void parseSmtOrderMessageAndSave(Map jsons) throws Exception {
        JSONArray orderMsgList= (JSONArray) jsons.get("orderMsgList");
        for(int i=0;i<orderMsgList.size();i++){
            JSONObject msgList=orderMsgList.getJSONObject(i);
            SmtOrderMessage message=new SmtOrderMessage();
            String orderid= String.valueOf(msgList.getLong("orderId"));
            String create=msgList.getString("gmtCreate");
            Date gmtCreate= DateUtils.NumStringToDate(create);
            message.setContent(msgList.getString("content"));
            Boolean havefile=msgList.getBoolean("haveFile");
            if(havefile){
                message.setHavefile(1);
            }else{
                message.setHavefile(0);
            }
            message.setSendername(msgList.getString("senderName"));
            message.setGmtcreate(gmtCreate);
            message.setSenderloginid(msgList.getString("senderLoginId"));
            message.setReceiverloginid(msgList.getString("receiverLoginId"));
            Boolean read=msgList.getBoolean("read");
            if(read){
                message.setReaded(1);
            }else{
                message.setReaded(0);
            }
            message.setReceivername(msgList.getString("receiverName"));
            message.setOrderid(orderid);
            message.setFileurl(msgList.getString("fileUrl"));
            SmtOrderMessage message1=selectSmtOrderMessageByOrderIdAndGmtCreate(orderid,gmtCreate);
            if(message1!=null){
                message.setId(message1.getId());
            }
            message.setUpdateTime(new Date());
            saveSmtOrderMessage(message);
        }
    }
}
