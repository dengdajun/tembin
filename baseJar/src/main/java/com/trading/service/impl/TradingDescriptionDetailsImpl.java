package com.trading.service.impl;

import com.base.database.customtrading.mapper.DescriptionDetailsMapper;
import com.base.database.trading.mapper.TradingDescriptionDetailsMapper;
import com.base.database.trading.model.TradingDescriptionDetailsWithBLOBs;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.DescriptionDetailsWithBLOBsQuery;
import com.base.mybatis.page.Page;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * 商品描述模块
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingDescriptionDetailsImpl implements com.trading.service.ITradingDescriptionDetails {
    @Autowired
    private TradingDescriptionDetailsMapper tradingDescriptionDetailsMapper;

    @Autowired
    private DescriptionDetailsMapper DescriptionDetailsMapper;

    @Override
    public void saveDescriptionDetails(TradingDescriptionDetailsWithBLOBs pojo) throws Exception {
        if(pojo.getId()==null){
            ObjectUtils.toInitPojoForInsert(pojo);
            this.tradingDescriptionDetailsMapper.insertSelective(pojo);
        }else{
            TradingDescriptionDetailsWithBLOBs t=tradingDescriptionDetailsMapper.selectByPrimaryKey(pojo.getId());
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            SystemUserManagerService managerService= ApplicationContextUtil.getBean(SystemUserManagerService.class);
            String norgid=((Long) sessionVO.getOrgId()).toString();
            String corgid = managerService.gerOrgIdByuserId(t.getCreateUser().intValue());
            Asserts.assertTrue(norgid.equalsIgnoreCase(corgid),"您没有权限修改!");
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            SystemLog systemLog = new SystemLog();
            systemLog.setEventdesc(sessionVO.getLoginId()+"修改了"+pojo.getId()+"的卖家描述!");
            systemLog.setEventname("ModuleModifyEvent");
            systemLog.setOperuser(sessionVO.getLoginId());
            SystemLogUtils.saveLog(systemLog);
            if(pojo.getCheckFlag()==null) {
                pojo.setCheckFlag(t.getCheckFlag());
            }
            this.tradingDescriptionDetailsMapper.updateByPrimaryKeySelective(pojo);
        }
    }
    @Override
    public TradingDescriptionDetailsWithBLOBs toDAOPojo(String payInfo, String shippingInfo, String contactInfo, String guaranteeInfo, String feedbackInfo) throws Exception {
        TradingDescriptionDetailsWithBLOBs pojo = new TradingDescriptionDetailsWithBLOBs();
        pojo.setPayInfo(payInfo);
        pojo.setShippingInfo(shippingInfo);
        pojo.setContactInfo(contactInfo);
        pojo.setGuaranteeInfo(guaranteeInfo);
        pojo.setFeedbackInfo(feedbackInfo);
        ObjectUtils.toInitPojoForInsert(pojo);
        return pojo;
    }
    @Override
    public TradingDescriptionDetailsWithBLOBs selectById(Long id){
        return this.tradingDescriptionDetailsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DescriptionDetailsWithBLOBsQuery> selectByDescriptionDetailsList(Map map,Page page) {
        return this.DescriptionDetailsMapper.selectByDescriptionDetailsList(map,page);
    }

    @Override
    public List<DescriptionDetailsWithBLOBsQuery> selectByDescriptionDetailsList(Map map) {
        Page page=new Page();
        page.setPageSize(10);
        page.setCurrentPage(1);
        return this.DescriptionDetailsMapper.selectByDescriptionDetailsList(map,page);
    }
}