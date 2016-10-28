package com.task.service.impl;

import com.base.database.task.mapper.TaskGetMessagesMapper;
import com.base.database.task.model.TaskGetMessages;
import com.base.database.task.model.TaskGetMessagesExample;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskGetMessagesImpl implements com.task.service.ITaskGetMessages {
    static Logger logger = Logger.getLogger(TaskGetMessagesImpl.class);

    @Autowired
    private TaskGetMessagesMapper taskGetMessagesMapper;

    @Override
    public void saveListTaskGetMessages(TaskGetMessages taskGetMessages){
        if(taskGetMessages.getId()==null){
            this.taskGetMessagesMapper.insertSelective(taskGetMessages);
        }else{
            this.taskGetMessagesMapper.updateByPrimaryKeySelective(taskGetMessages);
        }
    }

    @Override
    public List<TaskGetMessages> selectTaskGetMessagesByflagAndSaveTime(Integer flag, Date saveTime) {
        TaskGetMessagesExample tde = new TaskGetMessagesExample();
        TaskGetMessagesExample.Criteria c = tde.createCriteria();
        c.andTokenflagEqualTo(flag);
        c.andSavetimeLessThan(saveTime);
        return this.taskGetMessagesMapper.selectByExampleWithBLOBs(tde);
    }

    @Override
    public List<TaskGetMessages> selectTaskGetMessagesByFlagIsFalseOrderBysaveTime() {
        TaskGetMessagesExample tde = new TaskGetMessagesExample();
        TaskGetMessagesExample.Criteria c = tde.createCriteria();
        /*Date date=new Date();
        String date3=date.toString();
        int year=Integer.valueOf(date3.substring(24));
        int month=date.getMonth();
        int day=Integer.valueOf(date3.substring(8, 10));
        Date date1= DateUtils.buildDateTime(year, month, day, 3, 0, 0);
        Date date2= org.apache.commons.lang.time.DateUtils.addDays(date1,-1);
        if(date.before(date1)){
            c.andSavetimeBetween(date2,date1);
        }else if(date.after(date1)||date.equals(date1)){
            Date date4= org.apache.commons.lang.time.DateUtils.addDays(date1,1);
            c.andSavetimeBetween(date1,date4);
        }*/
        Date d1=new Date();
        Date date1 = DateUtils.turnToDateStart(d1);
        Date date2 = DateUtils.turnToDateEnd(d1);
        c.andSavetimeBetween(date1,date2);
        tde.setOrderByClause("tokenFlag");
        List<TaskGetMessages> taskGetMessages=taskGetMessagesMapper.selectByExampleWithBLOBs(tde);
        if (ObjectUtils.isLogicalNull(taskGetMessages)) {
            tde.clear();
            c.andLastsyctimeBetween(date1, date2);
            tde.setOrderByClause("tokenFlag");
            taskGetMessages=taskGetMessagesMapper.selectByExampleWithBLOBs(tde);
            if (!ObjectUtils.isLogicalNull(taskGetMessages)&&taskGetMessages.size()>6){
                taskGetMessages=taskGetMessages.subList(0,5);
            }
        }

        if (ObjectUtils.isLogicalNull(taskGetMessages)){
            logger.error(">>>>>获取消息定时任务出现异常！没有生成任务列表！>>>>>>>>>>>>>>>>>>>>");
        }

        date1=null;
        date2=null;
        return taskGetMessages;
    }

    @Override
    public List<TaskGetMessages> selectTaskGetMessagesByFlagIsFalseOrderByLastSycTimeAndEbayName(String ebayName) {
        TaskGetMessagesExample example=new TaskGetMessagesExample();
        TaskGetMessagesExample.Criteria cr=example.createCriteria();
        cr.andEbaynameEqualTo(ebayName);
        cr.andLastsyctimeIsNotNull();
        example.setOrderByClause("lastsyctime");
        return this.taskGetMessagesMapper.selectByExample(example);
    }
}
