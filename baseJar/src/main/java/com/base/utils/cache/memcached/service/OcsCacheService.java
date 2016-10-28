package com.base.utils.cache.memcached.service;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.CommAutowiredClass;
import com.google.common.collect.Lists;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.spring.MemcachedClientFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/8/27.
 */
@Component
public class OcsCacheService  {

    private  MemcachedClient cache;

   public MemcachedClient getMemcachedClient() throws Exception{
       if (cache==null){
           /*ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();
           connectionFactoryBuilder.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);*/
           CommAutowiredClass autowiredClass= ApplicationContextUtil.getBean(CommAutowiredClass.class);
           cache = new MemcachedClient(new BinaryConnectionFactory(), AddrUtil.getAddresses(autowiredClass.cache_ip + ":" + autowiredClass.cache_port));
       }
       return cache;
   }

    public <T> void put(String key,T o,int time) throws Exception{
        MemcachedClient client=getMemcachedClient();
        OperationFuture future = (OperationFuture) client.set("_ocs_"+key, time*60, o);
    }

    public <T> T get(String key) throws Exception{
        MemcachedClient client=getMemcachedClient();
        return (T)client.get("_ocs_"+key);
    }



}
