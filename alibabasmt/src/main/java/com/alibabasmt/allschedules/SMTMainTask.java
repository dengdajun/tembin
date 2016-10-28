package com.alibabasmt.allschedules;

import com.alibabasmt.utils.other.AutoValueBean;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.AppcenterClassFinder;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.common.MyClassUtil;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Administrator on 2015/3/24.
 */
@Component
public class SMTMainTask {
    static Logger logger = Logger.getLogger(SMTMainTask.class);

    private AutoValueBean valueBean;

    public static Map<String, Date> taskRunTime = new HashMap<String, Date>();//每增加一组任务。该值加1
    /**主入口,2分钟执行一次的任务*/
    @Scheduled(cron="0 0/2 *  * * ?")
    public void mainMethod(){

        //定义一组该类型任务需要执行的任务类型
        List<String> doList= SMTTaskMainParm.doList;
        if (valueBean==null) {
            valueBean = ApplicationContextUtil.getBean(AutoValueBean.class);
        }
        if(valueBean==null){return;}

        List<String> taskList=new ArrayList<String>();
        if("false".equalsIgnoreCase(valueBean.smtTaskList)){
            return;
        }else {
            List<String> taskListTe=Arrays.asList(StringUtils.split(valueBean.smtTaskList,","));
            if(taskListTe==null || taskListTe.isEmpty()){return;}
            for (String t :taskListTe){
                if(doList.contains(t)){
                    taskList.add(t);
                }else {
                    continue;
                }
            }
        }
        if(taskList.isEmpty()){return;}

        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>40){//如果队列大于30，那么不放入任何任务
            logger.info("队列中还有任务太多，等待下一次执行...........");
            return;
        }
        List<Class<? extends Scheduledable>> classList = AppcenterClassFinder.getInstance("com.alibabasmt.allschedules.task")
                .findSubClass(Scheduledable.class);
        List<? extends Scheduledable> scheduledableList = MyClassUtil.newInstance(classList);

            for (Scheduledable s : scheduledableList){
                if(taskList !=null && taskList.contains(s.getScheduledType())){
                    Integer ii=s.crTimeMinu();
                    if(ii==null||ii==-1||ii==0){ii=2;}
                    if(ii!=null && ii !=-1){
                        Date lastTime=taskRunTime.get(s.getScheduledType());//上一次执行的时间
                        if(lastTime!=null){
                            int c= DateUtils.minuteBetween(lastTime, new Date());//现在时间与上次时间相差多少分钟
                            if(c<ii){
                                continue;
                            }
                        }
                    }

                    Integer ci=SMTTaskMainParm.SOME_MULIT_TASK.get(s.getScheduledType());
                    if (ci!=null && ci>1){
                        for (int iii=0;iii<ci;iii++){
                            try {
                                Scheduledable ss1=s.getClass().newInstance();
                                ss1.setMark(String.valueOf(iii));
                                TaskPool.scheduledThreadPoolTaskExecutor.execute(ss1);
                            } catch (Exception e) {
                                logger.error(s.getScheduledType() + "新建实例失败!", e);
                            }
                            //try {Thread.sleep(50L);} catch (Exception e) {logger.error(e);}
                        }
                    }else {
                        s.setMark("0");
                        TaskPool.scheduledThreadPoolTaskExecutor.execute(s);
                    }
                    taskRunTime.put(s.getScheduledType(),new Date());
                }
            }
        }


    /**每天凌晨执行一次的任务*/
    @Scheduled(cron="0 31 2 * * ?")
    //@Scheduled(cron="0/10 * *  * * ?")
    private void doItEveryDay() throws Exception{
        List<String> doList= SMTTaskMainParm.doListEveryDay;
        if (valueBean==null) {
            valueBean = ApplicationContextUtil.getBean(AutoValueBean.class);
        }
        if(valueBean==null){return;}

        List<String> taskList=new ArrayList<String>();
        if("false".equalsIgnoreCase(valueBean.smtTaskList)){
            return;
        }else {
            List<String> taskListTe=Arrays.asList(StringUtils.split(valueBean.smtTaskList,","));
            for (String t :taskListTe){
                if(doList.contains(t)){
                    taskList.add(t);
                }else {
                    continue;
                }
            }
        }
        if(taskList.isEmpty()){return;}

        List<Class<? extends Scheduledable>> classList = AppcenterClassFinder.getInstance("com.alibabasmt.allschedules.task")
                .findSubClass(Scheduledable.class);
        List<? extends Scheduledable> scheduledableList = MyClassUtil.newInstance(classList);

        for (Scheduledable s : scheduledableList){
            if(taskList !=null && taskList.contains(s.getScheduledType())){
                TaskPool.scheduledThreadPoolTaskExecutor.execute(s);
            }
        }
    }


}
