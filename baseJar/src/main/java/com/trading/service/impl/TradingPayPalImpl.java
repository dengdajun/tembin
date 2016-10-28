package com.trading.service.impl;

import com.base.database.customtrading.mapper.PaypalMapper;
import com.base.database.trading.mapper.TradingPaypalMapper;
import com.base.database.trading.model.TradingPaypal;
import com.base.database.trading.model.TradingPaypalExample;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.PaypalQuery;
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
 * 付款模块
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingPayPalImpl implements com.trading.service.ITradingPayPal {
    @Autowired
    private TradingPaypalMapper tradingPaypalMapper;

    @Autowired
    private PaypalMapper paypalMapper;


    @Override
    public void savePaypal(TradingPaypal tradingPaypal) throws Exception {
        if(tradingPaypal.getId()==null){
            ObjectUtils.toInitPojoForInsert(tradingPaypal);
            this.tradingPaypalMapper.insertSelective(tradingPaypal);
        }else{
            TradingPaypal tp = this.selectById(tradingPaypal.getId());
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            SystemUserManagerService managerService= ApplicationContextUtil.getBean(SystemUserManagerService.class);
            String norgid=((Long) sessionVO.getOrgId()).toString();
            String corgid = managerService.gerOrgIdByuserId(tp.getCreateUser().intValue());
            Asserts.assertTrue(norgid.equalsIgnoreCase(corgid),"您没有权限修改!");
            SystemLog systemLog = new SystemLog();
            systemLog.setEventdesc(sessionVO.getLoginId()+"修改了"+tradingPaypal.getId()+"的付款选项!");
            systemLog.setEventname("ModuleModifyEvent");
            systemLog.setOperuser(sessionVO.getLoginId());
            SystemLogUtils.saveLog(systemLog);
            TradingPaypal t=tradingPaypalMapper.selectByPrimaryKey(tradingPaypal.getId());
            if(tradingPaypal.getCheckFlag()==null) {
                tradingPaypal.setCheckFlag(t.getCheckFlag());
            }
            this.tradingPaypalMapper.updateByPrimaryKeySelective(tradingPaypal);
        }

    }
    @Override
    public TradingPaypal toDAOPojo(String payName, String site, String paypal, String paymentinstructions) throws Exception {
        TradingPaypal pojo = new TradingPaypal();
        ObjectUtils.toInitPojoForInsert(pojo);
        pojo.setPayName(payName);
        pojo.setSite(site);
        pojo.setPaypal(paypal);
        pojo.setPaymentinstructions(paymentinstructions);
        return pojo;
    }

    @Override
    public List<PaypalQuery> selectByPayPalList(Map map,Page page){
        return this.paypalMapper.selectByPayPalList(map,page);
    }

    @Override
    public PaypalQuery selectByPayPal(Map map){
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(100);
        return this.paypalMapper.selectByPayPalList(map,page).get(0);
    }

    @Override
    public TradingPaypal selectById(Long id){
        return this.tradingPaypalMapper.selectByPrimaryKey(id);
    }

    @Override
    public TradingPaypal selectBySitePaypalId(String site,String paypalId){
        TradingPaypalExample tpe = new TradingPaypalExample();
        tpe.createCriteria().andSiteEqualTo(site).andPaypalEqualTo(paypalId);
        List<TradingPaypal> litp = this.tradingPaypalMapper.selectByExample(tpe);
        if(litp!=null&&litp.size()>0){
            return litp.get(0);
        }else{
            return null;
        }
    }

}
