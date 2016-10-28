package com.base.utils.cache.memcached;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.memcached.service.OcsCacheService;

import java.util.List;

/**
 * Created by Administrator on 2015/8/27.
 * ocs外部缓存支持
 */
public class OCSCacheSupport {
    public static <T> T get(String key) throws Exception{
        OcsCacheService ocsCacheService= ApplicationContextUtil.getBean(OcsCacheService.class);
        return ocsCacheService.get(key);
    }

    public static <T> void put(String key,T o,int time) throws Exception{
        OcsCacheService ocsCacheService= ApplicationContextUtil.getBean(OcsCacheService.class);
        ocsCacheService.put(key,o,time);
    }

    /*public static List<String> getKeysByNamePre()throws Exception{
        OcsCacheService ocsCacheService= ApplicationContextUtil.getBean(OcsCacheService.class);
        List<String> ks = ocsCacheService.getAllKeys();
        return ks;
    }*/
}
