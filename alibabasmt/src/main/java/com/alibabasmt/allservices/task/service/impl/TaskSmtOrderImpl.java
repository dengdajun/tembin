package com.alibabasmt.allservices.task.service.impl;

import com.alibabasmt.allservices.task.service.ITaskSmtOrder;
import com.alibabasmt.database.smttask.mapper.TaskSmtOrderMapper;
import com.alibabasmt.database.smttask.model.TaskSmtOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrtor on 2015/4/10.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskSmtOrderImpl implements ITaskSmtOrder {
    @Autowired
    private TaskSmtOrderMapper taskSmtOrderMapper;
    @Override
    public void saveTaskSmtOrder(TaskSmtOrder taskSmtOrder) {
        if(taskSmtOrder.getId()==null){
            this.taskSmtOrderMapper.insertSelective(taskSmtOrder);
        }else{
            this.taskSmtOrderMapper.updateByPrimaryKeySelective(taskSmtOrder);
        }
    }
}
