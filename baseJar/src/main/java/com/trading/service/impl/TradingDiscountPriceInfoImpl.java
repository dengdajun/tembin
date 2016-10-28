package com.trading.service.impl;

import com.base.database.customtrading.mapper.DiscountpriceinfoMapper;
import com.base.database.trading.mapper.TradingDiscountpriceinfoMapper;
import com.base.database.trading.model.TradingDiscountpriceinfo;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.DiscountpriceinfoQuery;
import com.base.mybatis.page.Page;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.exception.Asserts;
import com.base.xmlpojo.trading.addproduct.DiscountPriceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingDiscountPriceInfoImpl implements com.trading.service.ITradingDiscountPriceInfo {
    @Autowired
    private TradingDiscountpriceinfoMapper tradingDiscountpriceinfoMapper;
    @Autowired
    private DiscountpriceinfoMapper discountpriceinfoMapper;

    @Override
    public void saveDiscountpriceinfo(TradingDiscountpriceinfo pojo) throws Exception {
        if(ObjectUtils.isLogicalNull(pojo.getId())) {
            ObjectUtils.toInitPojoForInsert(pojo);
            this.tradingDiscountpriceinfoMapper.insertSelective(pojo);
        }else{

            TradingDiscountpriceinfo t=tradingDiscountpriceinfoMapper.selectByPrimaryKey(pojo.getId());
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            SystemUserManagerService managerService= ApplicationContextUtil.getBean(SystemUserManagerService.class);
            String norgid=((Long) sessionVO.getOrgId()).toString();
            String corgid = managerService.gerOrgIdByuserId(t.getCreateUser().intValue());
            Asserts.assertTrue(norgid.equalsIgnoreCase(corgid),"您没有权限修改!");
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            SystemLog systemLog = new SystemLog();
            systemLog.setEventdesc(sessionVO.getLoginId()+"修改了"+pojo.getId()+"的折扣信息!");
            systemLog.setEventname("ModuleModifyEvent");
            systemLog.setOperuser(sessionVO.getLoginId());
            SystemLogUtils.saveLog(systemLog);
            if(pojo.getCheckFlag()==null) {
                pojo.setCheckFlag(t.getCheckFlag());
            }
            this.tradingDiscountpriceinfoMapper.updateByPrimaryKeySelective(pojo);
        }
    }

    @Override
    public TradingDiscountpriceinfo toDAOPojo(DiscountPriceInfo discountPriceInfo) throws Exception {
        TradingDiscountpriceinfo pojo = new TradingDiscountpriceinfo();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,discountPriceInfo);
        return pojo;
    }

    @Override
    public List<DiscountpriceinfoQuery> selectByDiscountpriceinfo(Map map,Page page){
        return this.discountpriceinfoMapper.selectByDiscountpriceinfoList(map,page);
    }

    @Override
    public List<DiscountpriceinfoQuery> selectByDiscountpriceinfo(Map map){
        Page page=new Page();
        page.setPageSize(10);
        page.setPageSize(1);
        return this.discountpriceinfoMapper.selectByDiscountpriceinfoList(map,page);
    }

    @Override
    public TradingDiscountpriceinfo selectById(Long id){
        return this.tradingDiscountpriceinfoMapper.selectByPrimaryKey(id);
    }
}
