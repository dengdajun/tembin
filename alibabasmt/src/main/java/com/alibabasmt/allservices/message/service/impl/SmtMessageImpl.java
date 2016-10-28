package com.alibabasmt.allservices.message.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibabasmt.allservices.message.service.ISmtMessage;
import com.alibabasmt.database.smtmessage.mapper.SmtMessageMapper;
import com.alibabasmt.database.smtmessage.model.SmtMessage;
import com.alibabasmt.database.smtmessage.model.SmtMessageExample;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/4/13.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtMessageImpl implements ISmtMessage {
    @Autowired
    private SmtMessageMapper smtMessageMapper;

    @Override
    public void saveSmtMessage(SmtMessage smtMessage) throws Exception {
        if(smtMessage.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtMessage);
            smtMessageMapper.insert(smtMessage);
        }else{
            SmtMessage t=smtMessageMapper.selectByPrimaryKey(smtMessage.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),SmtMessageMapper.class,smtMessage.getId(),"Synchronize");
            smtMessageMapper.updateByPrimaryKeySelective(smtMessage);
        }
    }

    @Override
    public void parseSmtMessageAndSave(Map jsons,Long smtAcountId) throws Exception {
        JSONArray msgList= (JSONArray) jsons.get("msgList");
        for(int i=0;i<msgList.size();i++){
            JSONObject msg=msgList.getJSONObject(i);
            SmtMessage smtMessage=new SmtMessage();
            smtMessage.setTypeid(msg.getLong("typeId"));
            smtMessage.setMessageId(msg.getLong("id"));
            smtMessage.setProductname(msg.getString("productName"));
            smtMessage.setReceivername(msg.getString("receiverName"));
            smtMessage.setSendername(msg.getString("senderName"));
            smtMessage.setFileurl(msg.getString("fileUrl"));
            smtMessage.setContent(msg.getString("content"));
            Integer relationId=msg.getInteger("relationId");
            if(relationId!=null){
                smtMessage.setRelationid(Long.valueOf(relationId));
            }
            Boolean haveFile=msg.getBoolean("haveFile");
            if(haveFile){
                smtMessage.setHavefile(1);
            }else{
                smtMessage.setHavefile(0);
            }
            String gmtCreate=msg.getString("gmtCreate");
            if(StringUtils.isNotBlank(gmtCreate)){
                smtMessage.setGmtcreate(DateUtils.NumStringToDate(gmtCreate));
            }
            smtMessage.setReceiverloginid(msg.getString("receiverLoginId"));
            Boolean isRead=msg.getBoolean("read");
            if(isRead){
                smtMessage.setIsread(1);
            }else{
                smtMessage.setIsread(0);
            }
            smtMessage.setSenderloginid(msg.getString("senderLoginId"));
            smtMessage.setOrderid(msg.getLong("orderId"));
            smtMessage.setMessagetype(msg.getString("messageType"));
            smtMessage.setProducturl(msg.getString("productUrl"));
            smtMessage.setOrderurl(msg.getString("orderUrl"));
            smtMessage.setProductid(msg.getLong("productId"));
            SmtMessage message=selectSmtMessageByMessageId(smtMessage.getMessageId());
            if(message!=null){
                smtMessage.setId(message.getId());
            }
            smtMessage.setUpdateTime(new Date());
            smtMessage.setSmtAcountId(smtAcountId);
            saveSmtMessage(smtMessage);
        }
    }

    @Override
    public SmtMessage selectSmtMessageByMessageId(Long messageId) {
        SmtMessageExample example=new SmtMessageExample();
        SmtMessageExample.Criteria cr=example.createCriteria();
        cr.andMessageIdEqualTo(messageId);
        List<SmtMessage> list=smtMessageMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }
}
