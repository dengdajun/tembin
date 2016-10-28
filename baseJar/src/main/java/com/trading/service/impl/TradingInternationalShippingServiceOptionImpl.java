package com.trading.service.impl;

import com.base.database.trading.mapper.TradingInternationalshippingserviceoptionMapper;
import com.base.database.trading.model.*;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.InternationalShippingServiceOption;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceAdditionalCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceCost;
import com.trading.service.ITradingAttrMores;
import com.trading.service.ITradingDataDictionary;
import com.trading.service.ITradingInternationalShippingServiceOptionDoc;
import com.trading.service.ITradingItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingInternationalShippingServiceOptionImpl implements com.trading.service.ITradingInternationalShippingServiceOption {
    @Autowired
    private TradingInternationalshippingserviceoptionMapper tradingInternationalshippingserviceoptionMapper;
    @Autowired
    private ITradingAttrMores iTradingAttrMores;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;
    @Autowired
    private ITradingInternationalShippingServiceOptionDoc iTradingInternationalShippingServiceOptionDoc;
    @Autowired
    private ITradingItem iTradingItem;
    
    @Override
    public void saveInternationalShippingServiceOption(TradingInternationalshippingserviceoption pojo) throws Exception {
        if(pojo.getId()==null) {
            ObjectUtils.toInitPojoForInsert(pojo);
            this.tradingInternationalshippingserviceoptionMapper.insert(pojo);
        }else{
            this.tradingInternationalshippingserviceoptionMapper.updateByPrimaryKeySelective(pojo);
        }
    }

    @Override
    public TradingInternationalshippingserviceoption toDAOPojo(InternationalShippingServiceOption isso) throws Exception {
        TradingInternationalshippingserviceoption pojo = new TradingInternationalshippingserviceoption();
        ConvertPOJOUtil.convert(pojo,isso);
        pojo.setShippingservicecost(isso.getShippingServiceCost()==null?0d:isso.getShippingServiceCost().getValue());
        pojo.setShippingservicepriority(isso.getShippingServicePriority()==null?0L:isso.getShippingServicePriority().longValue());
        pojo.setShippingserviceadditionalcost(isso.getShippingServiceAdditionalCost()==null?0d:isso.getShippingServiceAdditionalCost().getValue());
        return pojo;
    }

    @Override
    public List<TradingInternationalshippingserviceoption> selectByParentid(Long parentid){
        TradingInternationalshippingserviceoptionExample tie = new TradingInternationalshippingserviceoptionExample();
        tie.createCriteria().andParentIdEqualTo(parentid);
        return this.tradingInternationalshippingserviceoptionMapper.selectByExample(tie);
    }
    /**
     * 通过父ID删除数据
     * @param id
     */
    @Override
    public void deleteByParentId(Long id){
        TradingInternationalshippingserviceoptionExample tie = new TradingInternationalshippingserviceoptionExample();
        tie.createCriteria().andParentIdEqualTo(id);
        this.tradingInternationalshippingserviceoptionMapper.deleteByExample(tie);
    }
    @Override
    public List<InternationalShippingServiceOption> toXmlPojo(Long id,TradingShippingdetails tradingShippingdetails,Long docId) throws Exception {
        List<InternationalShippingServiceOption> liisso = new ArrayList();
        List<TradingInternationalshippingserviceoptionDoc> lidoc = this.iTradingInternationalShippingServiceOptionDoc.selectByParentIdDocId(docId);
        TradingDataDictionary tdd = this.iTradingDataDictionary.selectDictionaryByID(Long.parseLong(tradingShippingdetails.getSite()));
        List<TradingInternationalshippingserviceoption> liInter = this.selectByParentid(id);
        if(liInter==null||(liInter!=null&&liInter.size()==0)){
            return null;
        }
        List<String> listr = new ArrayList();
        if(liInter!=null&&liInter.size()>0){
        	Map<String, String> mto = new HashMap<>();
        	for(TradingInternationalshippingserviceoption inters:liInter){
        		List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(inters.getId(), "ShipToLocation");
				if (litam != null && litam.size() > 0) {
					for (TradingAttrMores tam : litam) {
						mto.put(tam.getValue(), tam.getValue());
					}
				}
        	}
        	for (Map.Entry<String, String> entry : mto.entrySet()) {
                listr.add(entry.getValue());
            }
        }
        if(lidoc==null||lidoc.size()==0) {
            TradingInternationalshippingserviceoptionExample tie = new TradingInternationalshippingserviceoptionExample();
            tie.createCriteria().andParentIdEqualTo(id);
            List<TradingInternationalshippingserviceoption> litis = this.tradingInternationalshippingserviceoptionMapper.selectByExample(tie);
            if (litis == null || litis.size() == 0)
                return null;
            for (int i = 0; i < litis.size(); i++) {
                TradingInternationalshippingserviceoption ti = litis.get(i);
                InternationalShippingServiceOption isso = new InternationalShippingServiceOption();
                ConvertPOJOUtil.convert(isso, ti);
                isso.setShippingService(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(isso.getShippingService())).getValue());
                isso.setShippingServiceCost(new ShippingServiceCost(tdd.getValue1(), ti.getShippingservicecost()));
                isso.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost(tdd.getValue1(), ti.getShippingserviceadditionalcost()));
                isso.setShipToLocation(listr);
                liisso.add(isso);
            }
        }else{
            for(TradingInternationalshippingserviceoptionDoc tid : lidoc){
                InternationalShippingServiceOption isso = new InternationalShippingServiceOption();
                ConvertPOJOUtil.convert(isso, tid);
                isso.setShippingService(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(isso.getShippingService())).getValue());
                isso.setShippingServiceCost(new ShippingServiceCost(tdd.getValue1(), tid.getShippingservicecost()));
                isso.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost(tdd.getValue1(), tid.getShippingserviceadditionalcost()));
                isso.setShipToLocation(listr);
                liisso.add(isso);
            }
        }
        if(liisso!=null&&liisso.size()>0) {
            return liisso;
        }else{
            return null;
        }
    }
}
