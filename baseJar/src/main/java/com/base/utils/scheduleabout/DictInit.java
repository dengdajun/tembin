package com.base.utils.scheduleabout;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.memcached.OCSCacheSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.MyCollectionsUtil;
import com.base.utils.common.ObjectUtils;
import com.trading.service.ITradingDataDictionary;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrtor on 2014/9/3.
 * 数据字典初始化
 */
//@Component
public class DictInit implements Initable {
    static Logger logger = Logger.getLogger(DictInit.class);
    /*@Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----y----------xxxxxxxxxxxxxxxxxxxxxx------------------");
    }*/
    @Override
    public void init() {
        try {
            ITradingDataDictionary o=ApplicationContextUtil.getBean(ITradingDataDictionary.class);
            if(o==null){MainTask.isDongInitMethod="no";logger.error("ApplicationContext还没有准备好！");return;}
            CommAutowiredClass autowiredClass=ApplicationContextUtil.getBean(CommAutowiredClass.class);
            if (autowiredClass!=null&&"2".equalsIgnoreCase(autowiredClass.dictCacheGetType)){
                List<TradingDataDictionary> tradingDataDictionaryList= null;
                try {
                    tradingDataDictionaryList = OCSCacheSupport.get("tradingDataDict");
                } catch (Exception e) {
                    logger.error("连接外部缓存报错了",e);
                }
                if (ObjectUtils.isLogicalNull(tradingDataDictionaryList)){
                    List<TradingDataDictionary> tradingDataDictionaryList1 = o.queryDictAll();
                    try {
                        OCSCacheSupport.put("tradingDataDict",tradingDataDictionaryList1,0);
                    } catch (Exception e) {
                        logger.error("连接外部缓存报错了!", e);
                    }
                }else {
                    logger.error(">>trading字典从缓存中获取");
                    DataDictionarySupport.put(tradingDataDictionaryList);
                }

                //===================================================================================
                List<PublicDataDict> publicDataDictList=new ArrayList<>();
                try {
                    int bbss=0;
                    for (int i=1;i<100;i++){
                        if (bbss>3){break;}
                        List<PublicDataDict>  publicDataDictListTT =OCSCacheSupport.get("publicDataDict"+i);
                        if (ObjectUtils.isLogicalNull(publicDataDictListTT)){
                            bbss++;
                        }else {
                            publicDataDictList.addAll(publicDataDictListTT);
                        }
                    }
                } catch (Exception e) {
                    publicDataDictList=null;
                    logger.error("连接外部缓存报错了!!",e);
                }
                if (ObjectUtils.isLogicalNull(publicDataDictList)){
                        publicDataDictList = o.queryPublicDictAll();
                    try {
                        int ii=0;
                        List<List<PublicDataDict>> tx= MyCollectionsUtil.splitList(publicDataDictList,10000);
                        for (List<PublicDataDict> pdds:tx){
                            ii++;
                            OCSCacheSupport.put("publicDataDict" + ii, pdds, 0);
                        }


                    } catch (Exception e) {
                        logger.error("连接外部缓存报错了!!!", e);
                    }
                }else {
                    logger.error(">>public字典从缓存中获取");
                    DataDictionarySupport.put(publicDataDictList);
                }
            }else {
                o.queryDictAll();
                o.queryPublicDictAll();
            }

        } catch (Exception e) {
            MainTask.isDongInitMethod="no";
            logger.error("加载数据字典缓存失败！",e);
        }
        logger.info("加载数据字典成功!");

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /*@Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("-----y----------before------------------"+s);
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("-----y----------after------------------"+s);
        return o;
    }*/
}
