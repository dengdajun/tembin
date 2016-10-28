package com.alibabasmt.allservices.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibabasmt.allservices.order.service.ISmtOrderLog;
import com.alibabasmt.database.smtorder.mapper.SmtOrderLogMapper;
import com.alibabasmt.database.smtorder.model.SmtOrderLog;
import com.alibabasmt.database.smtorder.model.SmtOrderLogExample;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/27.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtOrderLogImpl implements ISmtOrderLog {
    @Autowired
    private SmtOrderLogMapper smtOrderLogMapper;
    @Override
    public void saveSmtOrderLog(SmtOrderLog smtOrderLog) throws Exception {
        if(smtOrderLog.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderLog);
            smtOrderLogMapper.insert(smtOrderLog);
        }else{
            SmtOrderLog t=smtOrderLogMapper.selectByPrimaryKey(smtOrderLog.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(), SmtOrderLogMapper.class, smtOrderLog.getId(), "Synchronize");
            smtOrderLogMapper.updateByPrimaryKeySelective(smtOrderLog);
        }
    }

    @Override
    public void parseSmtOrderLogAndSave(Map jsons) throws Exception {
        JSONArray logArray= (JSONArray) jsons.get("oprLogDtoList");
        for(int i=0;i<logArray.size();i++){
            JSONObject log=logArray.getJSONObject(i);
            SmtOrderLog smtOrderLog=new SmtOrderLog();
            smtOrderLog.setLogid(log.getLong("id"));
            smtOrderLog.setOperator(log.getString("operator"));
            smtOrderLog.setOrderid(String.valueOf(log.getLong("orderId")));
            smtOrderLog.setActiontype(log.getString("actionType"));
            String gmtCreate=log.getString("gmtCreate");
            smtOrderLog.setGmtcreate(DateUtils.NumStringToDate(gmtCreate));
            String gmtModified=log.getString("gmtModified");
            smtOrderLog.setGmtmodified(DateUtils.NumStringToDate(gmtModified));
            smtOrderLog.setChildorderid(String.valueOf(log.getLong("childOrderId")));
            SmtOrderLog orderLog=selectSmtOrderLogByLogId(smtOrderLog.getLogid());
            if(orderLog!=null){
                smtOrderLog.setId(orderLog.getId());
            }
            saveSmtOrderLog(smtOrderLog);
        }
    }

    @Override
    public SmtOrderLog selectSmtOrderLogByLogId(Long logId) {
        SmtOrderLogExample example=new SmtOrderLogExample();
        SmtOrderLogExample.Criteria cr=example.createCriteria();
        cr.andLogidEqualTo(logId);
        List<SmtOrderLog> list=smtOrderLogMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }
}
