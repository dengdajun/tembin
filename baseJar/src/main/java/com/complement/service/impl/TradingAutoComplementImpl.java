package com.complement.service.impl;

import com.base.database.customtrading.mapper.AutoComplementMapper;
import com.base.database.customtrading.mapper.SystemLogQueryMapper;
import com.base.database.inventory.model.ItemInventory;
import com.base.database.inventory.model.ShihaiyouInventory;
import com.base.database.task.mapper.TaskComplementMapper;
import com.base.database.task.model.TaskComplement;
import com.base.database.task.model.TaskComplementExample;
import com.base.database.trading.mapper.TradingAutoComplementMapper;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.domains.querypojos.SystemLogQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.DateUtils;
import com.complement.service.ITradingAutoComplement;
import com.complement.service.ITradingInventoryComplement;
import com.complement.service.ITradingSetComplement;
import com.inventory.service.IItemInventory;
import com.task.service.ITaskComplement;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingOrderGetOrders;
import com.trading.service.ITradingVariation;
import com.trading.service.ITradingVariations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/12/11.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingAutoComplementImpl implements com.complement.service.ITradingAutoComplement {
    static Logger logger = Logger.getLogger(TradingAutoComplementImpl.class);
    @Autowired
    private TradingAutoComplementMapper tradingAutoComplementMapper;
    @Autowired
    private AutoComplementMapper autoComplementMapper;
    @Autowired
    private SystemLogQueryMapper systemLogQueryMapper;
    @Autowired
    private ITradingSetComplement iTradingSetComplement;
    @Autowired
    private TradingListingDataMapper tradingListingDataMapper;
    @Autowired
    private ITaskComplement iTaskComplement;
    @Autowired
    private ITradingInventoryComplement iTradingInventoryComplement;
    @Autowired
    private IItemInventory iItemInventory;
    @Autowired
    private UsercontrollerEbayAccountMapper usercontrollerEbayAccountMapper;
    @Autowired
    private TradingOrderGetOrdersMapper tradingOrderGetOrdersMapper;
    @Autowired
    private TaskComplementMapper taskComplementMapper;
    @Autowired
    private ITradingItem iTradingItem;
    @Autowired
    private ITradingVariations iTradingVariations;
    @Autowired
    private ITradingVariation iTradingVariation;

    @Override
    public List<TradingAutoComplement> selectByList(Map m, Page page){
        return this.autoComplementMapper.selectAutoComplementList(m,page);
    }

    @Override
    public void saveAutoComplement(TradingAutoComplement tradingAutoComplement){
        if(tradingAutoComplement.getId()!=null){
            this.tradingAutoComplementMapper.updateByPrimaryKeySelective(tradingAutoComplement);
        }else{
            this.tradingAutoComplementMapper.insertSelective(tradingAutoComplement);
        }
    }
    @Override
    public int delAutoComplement(long id){
        return this.tradingAutoComplementMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delByEbayId(Long ebayId){
        TradingAutoComplementExample tace = new TradingAutoComplementExample();
        tace.createCriteria().andEbayIdEqualTo(ebayId);
        this.tradingAutoComplementMapper.deleteByExample(tace);
    }

    @Override
    public TradingAutoComplement selectById(long id){
        return this.tradingAutoComplementMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemLogQuery> selectLogList(Map m, Page page){
        return this.systemLogQueryMapper.selectLogList(m,page);
    }

    @Override
    public List<TradingAutoComplement> selectByEbayAccount(long ebayAccountid){
        TradingAutoComplementExample tace = new TradingAutoComplementExample();
        tace.createCriteria().andEbayIdEqualTo(ebayAccountid).andCheckFlagEqualTo("0");
        return this.tradingAutoComplementMapper.selectByExample(tace);
    }

    @Override
    public void checkAutoComplementType(TradingListingData tradingListingData,String token,String siteid){
        String ebayAccount = tradingListingData.getEbayAccount();
        boolean isFlag = true;
        UsercontrollerEbayAccountExample usercontrollerEbayAccountExample = new UsercontrollerEbayAccountExample();
        usercontrollerEbayAccountExample.createCriteria().andEbayAccountEqualTo(ebayAccount);
        List<UsercontrollerEbayAccount> liua = this.usercontrollerEbayAccountMapper.selectByExampleWithBLOBs(usercontrollerEbayAccountExample);
        if(liua!=null&&liua.size()>0){
            UsercontrollerEbayAccount ue = liua.get(0);
            TradingSetComplement tsc = this.iTradingSetComplement.selectByEbayId(ue.getId());
            if(tsc!=null&&tradingListingData.getListingType().equals("FixedPriceItem")) {
                if (tsc.getComplementType().equals("1")) {//该ebay账户设置成自动补数
                    List<TradingAutoComplement> litac = this.selectByEbayAccount(ue.getId());
                    if(litac!=null&&litac.size()>0) {
                        for (TradingAutoComplement tac : litac) {//排除设置好的规则，如果有满足条件的商品就不再做库存补冲
                            if (tac != null) {
                                if (tac.getAutoType().equals("1") && tac.getSkuKey().equals(tradingListingData.getSku())) {//设置规则中，ＳＫＵ相等时
                                    isFlag = false;
                                } else if (tac.getAutoType().equals("2") && tradingListingData.getSku().indexOf(tac.getSkuKey()) > 0) {//设置规则中，ＳＫＵ开头相等时
                                    isFlag = false;
                                }
                            }
                        }
                    }
                    TradingListingDataExample tlde = new TradingListingDataExample();
                    tlde.createCriteria().andItemIdEqualTo(tradingListingData.getItemId());
                    List<TradingListingData> litl = tradingListingDataMapper.selectByExample(tlde);
                    if (litl != null && litl.size() > 0) {
                        TradingListingData oldtd = litl.get(0);
                        if (oldtd.getQuantity() > tradingListingData.getQuantity() && tradingListingData.getIsFlag().equals("0")) {

                            if(oldtd.getQuantity()==null || oldtd.getQuantity()==0 ||
                                    tradingListingData.getQuantity()==null || tradingListingData.getQuantity()==0){
                                return;
                            }
                            //logger.error("该数据的确需要补数！");
                            TaskComplement tc = new TaskComplement();
                            tc.setToken(token);
                            tc.setSite(siteid);
                            tc.setTaskFlag("0");
                            tc.setSku(tradingListingData.getSku());
                            tc.setRepValue(oldtd.getQuantity() + "");
                            tc.setCreateDate(new Date());
                            tc.setItemId(tradingListingData.getItemId());
                            tc.setOldValue(tradingListingData.getQuantity() + "");
                            tc.setEbayAccount(tradingListingData.getEbayAccount());
                            tc.setDataType("1");
                            iTaskComplement.saveTaskComplement(tc);
                        }
                    }
                } else if (tsc.getComplementType().equals("2")) {//该ebay账号设置成库存补数
                    TradingInventoryComplement tic = this.iTradingInventoryComplement.selectBySkuOrEbayAccount(tradingListingData.getSku(), ue.getId());
                    if (tic != null) {//如果配置的规则不为空，那么就按照库存中的数字来调整商品的数量
                        List<TradingInventoryComplementMore> liticm = this.iTradingInventoryComplement.slectByParentId(tic.getId());
                        int countNumber = 0;
                        if(liticm!=null&&liticm.size()>0) {
                            for (TradingInventoryComplementMore ticm : liticm) {
                                //先检查出口易，第四方有没有这样的ＳＫＵ
                                List<ItemInventory> liii = this.iItemInventory.selectBySku(ticm.getSkuValue());
                                if (liii != null && liii.size() > 0) {
                                    for (ItemInventory ii : liii) {
                                        countNumber += ii.getAvailStock();
                                    }
                                }
                                //检查四海邮ＳＫU
                                List<ShihaiyouInventory> lishy = this.iItemInventory.selectShiHaiYouByBySku(ticm.getSkuValue());
                                if (lishy != null && lishy.size() > 0) {
                                    for (ShihaiyouInventory sh : lishy) {
                                        countNumber += sh.getQuantity();
                                    }
                                }
                            }
                        }
                        if (countNumber != 0 && countNumber != tradingListingData.getQuantity()) {
                            TaskComplement tc = new TaskComplement();
                            tc.setToken(token);
                            tc.setSite(siteid);
                            tc.setTaskFlag("0");
                            tc.setRepValue(countNumber + "");
                            tc.setSku(tradingListingData.getSku());
                            tc.setCreateDate(new Date());
                            tc.setItemId(tradingListingData.getItemId());
                            tc.setOldValue(tradingListingData.getQuantity() + "");
                            tc.setEbayAccount(tradingListingData.getEbayAccount());
                            tc.setDataType("2");
                            iTaskComplement.saveTaskComplement(tc);
                        }
                    }
                }//如果ebay账号未设置补数方式，那么将不会补数，由用户手动调整在线数量
            }else if(tsc!=null&&tradingListingData.getListingType().equals("2")){//多属性自动补数
                try {
                    checkOrderByOrders(tradingListingData, siteid, token);
                }catch(Exception e){
                    logger.error("物品号为："+tradingListingData.getItemId()+"同步在线时，检查物品是多属性自动补数报错：",e);
                }
            }
        }
    }


    public void checkOrderByOrders(TradingListingData tradingListingData,String siteid,String token){
        TradingOrderGetOrdersExample tradingOrderGetOrdersExample = new TradingOrderGetOrdersExample();
        tradingOrderGetOrdersExample.createCriteria().andItemidEqualTo(tradingListingData.getItemId()).andCreatedtimeBetween(DateUtils.turnToDateStart(new Date()),DateUtils.turnToDateEnd(new Date()));
        List<TradingOrderGetOrders> liorder = this.tradingOrderGetOrdersMapper.selectByExample(tradingOrderGetOrdersExample);
        if(liorder!=null&&liorder.size()>0){
            for(TradingOrderGetOrders to:liorder){
                TaskComplementExample taskComplementExample = new TaskComplementExample();
                taskComplementExample.createCriteria().andSoureIdEqualTo(to.getId());
                List<TaskComplement> litask = this.taskComplementMapper.selectByExampleWithBLOBs(taskComplementExample);
                TradingVariation tvs = null;
                if(litask==null||litask.size()==0){
                    TradingItemWithBLOBs tradingItemWithBLOBs = this.iTradingItem.selectByItemId(tradingListingData.getItemId());
                    if(tradingItemWithBLOBs!=null){
                        TradingVariations tradingVariations= this.iTradingVariations.selectByParentId(tradingItemWithBLOBs.getId());
                        if(tradingVariations!=null){
                            List<TradingVariation> livar = this.iTradingVariation.selectByParentId(tradingVariations.getId());
                            if(livar!=null&&livar.size()>0){
                                for(TradingVariation tv:livar){
                                    if (to.getVariationsku()==null||tv==null){continue;}
                                    if(to.getVariationsku().equals(tv.getSku())){
                                        tvs = tv;
                                    }
                                }
                            }
                        }
                    }
                    if(tvs==null){
                        continue;
                    }
                    TaskComplement tc = new TaskComplement();
                    tc.setToken(token);
                    tc.setSite(siteid);
                    tc.setTaskFlag("0");
                    tc.setSku(to.getVariationsku());
                    tc.setRepValue((tvs.getQuantity() + Long.parseLong(to.getQuantitypurchased())) + "");
                    tc.setCreateDate(new Date());
                    tc.setItemId(tradingListingData.getItemId());
                    tc.setOldValue(tvs.getQuantity() + "");
                    tc.setEbayAccount(tradingListingData.getEbayAccount());
                    tc.setDataType("1");
                    tc.setSoureId(to.getId());
                    iTaskComplement.saveTaskComplement(tc);
                    tvs.setQuantity((tvs.getQuantity()+Long.parseLong(to.getQuantitypurchased())));
                    try {
                        this.iTradingVariation.saveVariation(tvs);
                    } catch (Exception e) {
                        logger.error("更新多属性报错！",e);

                    }
                }
            }
        }
    }
}
