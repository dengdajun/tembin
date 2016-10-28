package com.alibabasmt.utils.other;

import com.base.utils.cache.CacheBaseSupport;
import com.base.utils.common.ObjectUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * Created by Administrator on 2014/10/29.
 * 速卖通用于存放较长时限数据的缓存
 */
public class SMT7HStoreDataSupport extends CacheBaseSupport {
    public static final String TEMP_DATA_CACHE="smt_7h_Cache"; //临时数据所占用的缓存名

    /**放入数据*/
    public static <T> void pushData(String key,T t){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        Element element=new Element(key+"_smt",t);
        cache.put(element);
    }

    /**取出数据*/
    public static <T> T pullData(String key){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        Element element=cache.get(key+"_smt");
        if(ObjectUtils.isLogicalNull(element)){
            return null;
        }
        return (T) element.getObjectValue();
    }

    /**移除数据*/
    public static void removeData(String key){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        cache.remove(key+"_smt");
    }
}
