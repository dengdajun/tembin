package com.base.utils.applicationcontext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by wula on 2014/7/6.
 * 一个web项目类实现ApplicationContextAware这个接口，并在spring容器中注册这个实现类
 * 那么这个类就可以获得spring容器
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;
    //声明一个静态变量保存
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        this.context = contex;
    }
    public static ApplicationContext getContext() {
        if(context==null){
            return null;
        }
        return context;
    }
    public final static Object getBean(String beanName) {
        if(context==null){
            return null;
        }
        return context.getBean(beanName);
    }
    public final static<T> T getBean(String beanName, Class<?> requiredType) {
        if(context==null){
            return null;
        }
        return (T)context.getBean(beanName, requiredType);
    }
    public final static<T> T getBean(Class<?> c){
        if(context==null){
            return null;
        }
        return (T)context.getBean(c);
    }
}
