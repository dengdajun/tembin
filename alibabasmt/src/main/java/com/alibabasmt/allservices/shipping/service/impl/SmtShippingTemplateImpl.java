package com.alibabasmt.allservices.shipping.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibabasmt.database.smtshipping.mapper.SmtShippingTemplateMapper;
import com.alibabasmt.database.smtshipping.model.SmtShippingTemplate;
import com.alibabasmt.database.smtshipping.model.SmtShippingTemplateExample;
import com.alibabasmt.utils.signature.api.APIStaticParm;
import com.alibabasmt.utils.signature.api.ApiCallService;
import com.alibabasmt.utils.signature.vo.APICallVO;
import com.alibabasmt.utils.signature.vo.SignatureVO;
import com.base.utils.xmlutils.SamplePaseXml;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrtor on 2015/3/30.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtShippingTemplateImpl implements com.alibabasmt.allservices.shipping.service.ISmtShippingTemplate {

    static Logger logger = Logger.getLogger(SmtShippingTemplateImpl.class);
    @Autowired
    private SmtShippingTemplateMapper smtShippingTemplateMapper;

    /**
     * 获得运输模板列表信息
     * @param smtAccountId
     * @return
     */
    @Override
    public List<Map> queryShippingTemplateList(long smtAccountId){
        APICallVO apiCallVO=new APICallVO();
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        apiCallVO.setSmtAccountID(smtAccountId);
        apiCallVO.setUrlPath(APIStaticParm.listFreightTemplate);
        Map<String,String> m=new HashMap<String, String>();
        m.put("access_token", "");//access_token就设置为空，可以添加其他参数
        apiCallVO.setParam(m);
        apiCallVO.setSignatureVO(signatureVO);
        String result = ApiCallService.callApi(apiCallVO);
        Map jsons = JSON.parseObject(result, HashMap.class);
        List<Map> lim = (List<Map>)jsons.get("aeopFreightTemplateDTOList");
        return lim;
    }

    /**
     * 获得运输模板详细信息
     * @param smtAccountId
     * @return
     */
    @Override
    public Map queryShippingTemplateDetailMain(long smtAccountId,long templateId){
        APICallVO apiCallVO=new APICallVO();
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        apiCallVO.setSmtAccountID(smtAccountId);
        apiCallVO.setUrlPath(APIStaticParm.getFreightSettingByTemplateQuery);
        Map<String,String> m=new HashMap<String, String>();
        m.put("access_token", "");//access_token就设置为空，可以添加其他参数
        m.put("templateId", templateId+"");
        apiCallVO.setParam(m);
        apiCallVO.setSignatureVO(signatureVO);
        String result = ApiCallService.callApi(apiCallVO);
        Map jsons = JSON.parseObject(result, HashMap.class);
        if(jsons==null){
            return jsons;
        }
        if ("true".equals(jsons.get("success")+"")){
            return jsons;
        }else{
            logger.error("获取对应数据报错！");
            return null;
        }
    }

    @Override
    public void saveSmtShippingTemplateList(List<SmtShippingTemplate> li){
        for(SmtShippingTemplate sst:li){
            this.saveSmtShippingTemplate(sst);
        }
    }

    @Override
    public void saveSmtShippingTemplate(SmtShippingTemplate sst){
        if(sst.getId()!=null){
            this.smtShippingTemplateMapper.updateByPrimaryKeySelective(sst);
        }else{
            this.smtShippingTemplateMapper.insertSelective(sst);
        }
    }

    @Override
    public SmtShippingTemplate selectById(long templateId){
        SmtShippingTemplateExample sste = new SmtShippingTemplateExample();
        sste.createCriteria().andTemplateidEqualTo(templateId);
        List<SmtShippingTemplate> li = this.smtShippingTemplateMapper.selectByExample(sste);
        if(li!=null&&li.size()>0){
            return li.get(0);
        }else{
            return null;
        }
    }

    /**
     * 定时任务调用，每天调用一次，用于更新用户自已设置的运输选项
     * @param smtAccount
     */
    @Override
    public void saveShippingTemplateTask(long smtAccount){
        List<Map> lim = this.queryShippingTemplateList(smtAccount);
        List<SmtShippingTemplate> list = new ArrayList<SmtShippingTemplate>();
        for(Map m :lim){
            SmtShippingTemplate sst=this.selectById(Long.parseLong(m.get("templateId")+""));
            if(sst!=null){
                sst.setUpdateTime(new Date());
                list.add(sst);
            }else{
                sst = new SmtShippingTemplate();
                sst.setCreateTime(new Date());
                sst.setCreateUser(smtAccount);
                sst.setTemplateid(Long.parseLong(m.get("templateId")+""));
                sst.setTemplatename((String)m.get("templateName"));
                sst.setIsDefault("true".equals(m.get("default")+"")?"0":"1");
                sst.setTaskFlag("0");
                list.add(sst);
            }
        }
        if(list!=null&&list.size()>0){
            this.saveSmtShippingTemplateList(list);
        }
    }

}
