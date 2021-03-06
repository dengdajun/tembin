package com.trading.service;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.domains.DictDataFilterParmVO;
import com.base.domains.DictQuery.DataDicShipingQueryParamVO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingDataDictionary {
    void saveDataDictionary(TradingDataDictionary tradingDataDictionary);

    TradingDataDictionary toDAOPojo(String value, String name, String type, String value1, String name1) throws Exception;

    List<TradingDataDictionary> selectDictionaryByType(String type, String name1, String name, String value);

    /**根据类型查询TradingDataDictionary*/
    List<TradingDataDictionary> selectDictionaryByType(String type);

    List<TradingDataDictionary> selectDictionaryByMap(Map<String, String> m);

    /**根据类型查询PublicDataDict*/
    List<PublicDataDict> selectPublicDataDictByType(String type);

    /**根据类型和用户id查询userDataDict*/
    List<PublicUserConfig> selectUserConfigDataDictByType(String type, Long userId);

    /**根据ID查询TradingDataDictionary*/
    TradingDataDictionary selectDictionaryByID(Long id);

    /**根据map查询publicDataDictionary*/
    List<PublicDataDict> selectPublicDictionaryByMap(Map<String, String> map);

    /**根据ID查询publicDataDictionary*/
    List<PublicDataDict> selectPublicDictionaryByID(Long id);

    List<PublicDataDict> selectPublicDictionaryByItemIDs(String itemid,String type);

    /**根据itemID查询publicDataDictionary*/
    List<PublicDataDict> selectPublicDictionaryByItemID(Long id);

    /**根据parentID查询publicDataDictionary*/
    List<PublicDataDict> selectPublicDictionaryByParentID(DictDataFilterParmVO vo);


    /**根据parentID和itemlevel查询publicDataDictionary*/
    List<PublicDataDict> selectPublicDictionaryByItemLevel(DictDataFilterParmVO v);

    /**根据ID查询userConfig*/
    PublicUserConfig selectUserConfigByID(Long id);

    /**添加类别属性信息*/
    List<PublicDataDict> addPublicData(String xml,String siteID) throws Exception;

    //@Cacheable(value ="dataDictionaryCache")
    /**查询所有的TradingDataDictionary数据字典数据*/
    List<TradingDataDictionary> queryDictAll();

    /**查询所有的publicDataDictionary数据字典数据*/
    List<PublicDataDict> queryPublicDictAll();

    List<PublicUserConfig> selectUserConfigDictionaryByMap(Map<String, String> m);

    List<PublicDataDict> selectByDicExample(String categoryId, String siteId);

    PublicDataDict selectByParentDicExample(String categoryId, String siteId);

    /**判断运输方式是否有重复*/
    List<String> checkShipingIsExit(DataDicShipingQueryParamVO vo);
}
