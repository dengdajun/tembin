package com.alibabasmt.allservices.shipping.service.impl;

import com.alibabasmt.allservices.shipping.service.ISmtShippingTemplate;
import com.alibabasmt.database.smtshipping.mapper.SmtShippingSelfdefineMapper;
import com.alibabasmt.database.smtshipping.mapper.SmtShippingSelfstandardMapper;
import com.alibabasmt.database.smtshipping.mapper.SmtShippingTemplateDetailMapper;
import com.alibabasmt.database.smtshipping.mapper.SmtShippingTemplateMapper;
import com.alibabasmt.database.smtshipping.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/30.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtShippingTemplateDetailImpl implements com.alibabasmt.allservices.shipping.service.ISmtShippingTemplateDetail {

    @Autowired
    private SmtShippingTemplateMapper smtShippingTemplateMapper;
    @Autowired
    private SmtShippingTemplateDetailMapper smtShippingTemplateDetailMapper;
    @Autowired
    private ISmtShippingTemplate iSmtShippingTemplate;
    @Autowired
    private SmtShippingSelfdefineMapper smtShippingSelfdefineMapper;
    @Autowired
    private SmtShippingSelfstandardMapper smtShippingSelfstandardMapper;


    @Override
    public void saveShippingTemplateDetail(SmtShippingTemplateDetailWithBLOBs smtShippingTemplateDetailWithBLOBs){
        if(smtShippingTemplateDetailWithBLOBs.getId()!=null){
            this.smtShippingTemplateDetailMapper.updateByPrimaryKeyWithBLOBs(smtShippingTemplateDetailWithBLOBs);
        }else{
            this.smtShippingTemplateDetailMapper.insertSelective(smtShippingTemplateDetailWithBLOBs);
        }
    }

    @Override
    public void taskShippingTemplate(){
        SmtShippingTemplateExample sste = new SmtShippingTemplateExample();
        sste.createCriteria().andTaskFlagEqualTo("0");
        List<SmtShippingTemplate> li = this.smtShippingTemplateMapper.selectByExample(sste);
        for(SmtShippingTemplate sst:li){
            Map map = this.iSmtShippingTemplate.queryShippingTemplateDetailMain(sst.getCreateUser(),sst.getTemplateid());
            if(map==null){
                continue;
            }
            if ("true".equals(map.get("success")+"")){
                List<Map> litem = (List<Map>) map.get("freightSettingList");
                for(Map mtem:litem){
                    SmtShippingTemplateDetailExample sstd = new SmtShippingTemplateDetailExample();
                    sstd.createCriteria().andParentIdEqualTo(sst.getId());
                    List<SmtShippingTemplateDetail> lissd = this.smtShippingTemplateDetailMapper.selectByExample(sstd);
                    if(lissd!=null&&lissd.size()>0){

                    }else{
                        SmtShippingTemplateDetailWithBLOBs sstdd = new SmtShippingTemplateDetailWithBLOBs();
                        Map ms = (Map) mtem.get("template");
                        sstdd.setParentId(sst.getId());
                        sstdd.setLogisticscompany( ms.get("logisticsCompany")+"");
                        if(ms.get("allStandardShipping")!=null) {
                            sstdd.setAllstandardshipping( ms.get("allStandardShipping")+"");
                        }
                        if(ms.get("allStandardDiscount")!=null) {
                            sstdd.setAllstandarddiscount(Double.parseDouble(ms.get("allStandardDiscount")+""));
                        }
                        if(ms.get("allFreeShipping")!=null){
                            sstdd.setAllfreeshipping(ms.get("allFreeShipping")+"");
                        }
                        if(ms.get("freeShippingCountry")!=null){
                            sstdd.setFreeshippingcountry((String) ms.get("freeShippingCountry"));
                        }

                        if(ms.get("standardShippingCountry")!=null){
                            sstdd.setStandardshippingcountry(ms.get("standardShippingCountry")+"");
                        }
                        if(ms.get("standardShippingDiscount")!=null){
                            sstdd.setStandardshippingdiscount(Double.parseDouble(ms.get("standardShippingDiscount")+""));
                        }
                        sstdd.setCreateTime(new Date());
                        sstdd.setCreateUser(sst.getCreateUser());
                        if(ms.get("selfdefine")!=null&&((List<Map>)ms.get("selfdefine")).size()>0){
                            sstdd.setIsSelf("1");
                        }else{
                            sstdd.setIsSelf("0");
                        }
                        this.saveShippingTemplateDetail(sstdd);
                        if(sstdd.getIsSelf().equals("1")){
                            SmtShippingSelfdefineExample ssse = new SmtShippingSelfdefineExample();
                            ssse.createCriteria().andParentIdEqualTo(sstdd.getId());
                            List<SmtShippingSelfdefine> lisss = this.smtShippingSelfdefineMapper.selectByExample(ssse);
                            Map mfine = (Map) ms.get("selfdefine");
                            if(mfine!=null){
                                if(lisss!=null&&lisss.size()>0){

                                }else{
                                    SmtShippingSelfdefine sss = new SmtShippingSelfdefine();
                                    sss.setParentId(sstdd.getId());
                                    sss.setStartordernum(Integer.parseInt(mfine.get("startOrderNum")+""));
                                    sss.setEndordernum(Integer.parseInt(mfine.get("endOrderNum")+""));
                                    sss.setMinfreight(Double.parseDouble(mfine.get("minFreight")+""));
                                    sss.setPeraddnum(Integer.parseInt( mfine.get("perAddNum")+""));
                                    sss.setAddfreight(Double.parseDouble(mfine.get("addFreight")+""));
                                    sss.setShippingcountry(mfine.get("shippingCountry")+"");
                                    this.saveSmtShippingSelfdefine(sss);
                                }
                            }
                            Map mstan = (Map)ms.get("selfstandard");
                            if(mstan!=null){
                                SmtShippingSelfstandardExample ssts = new SmtShippingSelfstandardExample();
                                ssts.createCriteria().andParentIdEqualTo(sstdd.getId());
                                List<SmtShippingSelfstandard> lisst = this.smtShippingSelfstandardMapper.selectByExample(ssts);
                                if(lisst!=null&&lisst.size()>0){

                                }else {
                                    SmtShippingSelfstandard sssts = new SmtShippingSelfstandard();
                                    sssts.setParentId(sstdd.getId());
                                    sssts.setSelfstandardcountry(mstan.get("selfStandardCountry")+"");
                                    sssts.setSelfstandarddiscount(Integer.parseInt(mstan.get("selfStandardDiscount")+""));
                                    this.saveSmtShippingSelfstandard(sssts);
                                }
                            }
                        }
                    }
                }
            }
            sst.setTaskFlag("1");
            this.iSmtShippingTemplate.saveSmtShippingTemplate(sst);
        }
    }


    @Override
    public void saveSmtShippingSelfdefine(SmtShippingSelfdefine smtShippingSelfdefine){
        if(smtShippingSelfdefine.getId()!=null){
            this.smtShippingSelfdefineMapper.insertSelective(smtShippingSelfdefine);
        }else{
            this.smtShippingSelfdefineMapper.updateByPrimaryKeyWithBLOBs(smtShippingSelfdefine);
        }
    }

    @Override
    public void saveSmtShippingSelfstandard(SmtShippingSelfstandard smtShippingSelfstandard){
        if(smtShippingSelfstandard.getId()!=null){
            this.smtShippingSelfstandardMapper.insertSelective(smtShippingSelfstandard);
        }else{
            this.smtShippingSelfstandardMapper.updateByPrimaryKeyWithBLOBs(smtShippingSelfstandard);
        }
    }
}


