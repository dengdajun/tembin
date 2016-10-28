package com.trading.service.impl;

import com.base.database.customtrading.mapper.ItemAddressMapper;
import com.base.database.trading.mapper.TradingItemAddressMapper;
import com.base.database.trading.model.TradingItemAddress;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemAddressQuery;
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
 * 物品所在地
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingItemAddressImpl implements com.trading.service.ITradingItemAddress {
    @Autowired
    private TradingItemAddressMapper tradingItemAddressMapper;
    @Autowired
    private ItemAddressMapper itemAddressMapper;

    @Override
    public void saveItemAddress(TradingItemAddress tradingItemAddress) throws Exception {
        if(tradingItemAddress.getId()==null){
            ObjectUtils.toInitPojoForInsert(tradingItemAddress);
            this.tradingItemAddressMapper.insertSelective(tradingItemAddress);
        }else{
            TradingItemAddress t=tradingItemAddressMapper.selectByPrimaryKey(tradingItemAddress.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            SystemUserManagerService managerService= ApplicationContextUtil.getBean(SystemUserManagerService.class);
            String norgid=((Long) sessionVO.getOrgId()).toString();
            String corgid = managerService.gerOrgIdByuserId(t.getCreateUser().intValue());
            Asserts.assertTrue(norgid.equalsIgnoreCase(corgid),"您没有权限修改!");
            SystemLog systemLog = new SystemLog();
            systemLog.setEventdesc(sessionVO.getLoginId()+"修改了"+tradingItemAddress.getId()+"的物品所在地!");
            systemLog.setEventname("ModuleModifyEvent");
            systemLog.setOperuser(sessionVO.getLoginId());
            SystemLogUtils.saveLog(systemLog);
            if(tradingItemAddress.getCheckFlag()==null) {
                tradingItemAddress.setCheckFlag(t.getCheckFlag());
            }
            this.tradingItemAddressMapper.updateByPrimaryKeySelective(tradingItemAddress);
        }
    }

    @Override
    public TradingItemAddress toDAOPojo(String name, String address, Long countryId, String postalcode) throws Exception {
        TradingItemAddress pojo = new TradingItemAddress();
        pojo.setName(name);
        pojo.setAddress(address);
        pojo.setCountryId(countryId);
        pojo.setPostalcode(postalcode);
        return pojo;
    }

    @Override
    public List<ItemAddressQuery> selectByItemAddressQuery(Map map,Page page){
        return this.itemAddressMapper.selectByItemAddressList(map,page);
    }
    @Override
    public List<ItemAddressQuery> selectByItemAddressQuery(Map map){
        Page page=new Page();
        page.setPageSize(100);
        page.setCurrentPage(1);
        return this.itemAddressMapper.selectByItemAddressList(map,page);
    }

    @Override
    public TradingItemAddress selectById(Long id){
        return this.tradingItemAddressMapper.selectByPrimaryKey(id);
    }
}
