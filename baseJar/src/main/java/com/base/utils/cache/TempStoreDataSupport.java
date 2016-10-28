package com.base.utils.cache;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.ObjectUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/10/29.
 * 临时数据缓存工具类，该缓存只占用很短的时间
 */
public class TempStoreDataSupport extends CacheBaseSupport {
    public static final String TEMP_DATA_CACHE="tempStoreData"; //临时数据所占用的缓存名

    /**放入数据*/
    public static <T> void pushData(String key,T t){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        Element element=new Element(key+"_session",t);
        cache.put(element);
    }

    /**取出数据*/
    public static <T> T pullData(String key){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        Element element=cache.get(key+"_session");
        if(ObjectUtils.isLogicalNull(element)){
            return null;
        }
        return (T) element.getObjectValue();
    }

    /**移除数据*/
    public static void removeData(String key){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        cache.remove(key+"_session");
    }

    /**放入数据,并设置强制过期时间,单位是分*/
    public static <T> void pushDataByTime(String key,T t,int liveTime){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        Element element=new Element(key+"_session",t);
        element.setTimeToLive(liveTime*60);
        cache.put(element);
    }
    /**放入数据,并设置未调用过期时间,单位是分*/
    public static <T> void pushDataByIdelTime(String key,T t,int liveTime){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        Element element=new Element(key+"_session",t);
        element.setTimeToIdle(liveTime*60);
        cache.put(element);
    }

    /**获取指定字符开头的缓存*/
    public static List<String> getKeyWithStart(String keyStart){
        //CacheManager cacheManager= ApplicationContextUtil.getBean(CacheManager.class);
       // String[] ss = cacheManager.getCacheNames();//查询有哪些缓存库
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        List<String> ll = cache.getKeys();

        if (ObjectUtils.isLogicalNull(ll)){return null;}
        List<String> r=new ArrayList<>();
        for (String k:ll){
            if (k.startsWith(keyStart)){
                r.add(k);
            }
        }

        return r;

    }
}
