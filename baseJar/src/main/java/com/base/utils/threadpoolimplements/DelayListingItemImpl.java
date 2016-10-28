package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.database.trading.model.TradingTembinListingLog;
import com.base.database.userinfo.model.SystemLog;
import com.base.utils.common.ObjectUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingListingData;
import com.trading.service.ITradingTembinListingLog;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;

/**
 * Created by Administrtor on 2014/9/9.
 * 刊登动作后要执行的方法
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DelayListingItemImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(DelayListingItemImpl.class);

    @Autowired
    private ITradingItem iTradingItem;
    @Autowired
    private ITradingListingData iTradingListingData;

    @Autowired
    private ITradingTembinListingLog iTradingTembinListingLog;

    @Override
    public <T> void doWork(String res,T... t) {
        if(StringUtils.isEmpty(res)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        TradingItemWithBLOBs tradingItem = (TradingItemWithBLOBs) taskMessageVO.getObjClass();
        String ack = null;
        String itemId=null;
        try {
            itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");

            ack=SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                this.iTradingItem.saveListingSuccess(res,itemId);
                TradingItemWithBLOBs itwb = this.iTradingItem.selectByItemId(itemId);
                //更新在线商品表
                this.iTradingListingData.saveTradingListingDataByTradingItem(tradingItem,res);

                TradingTembinListingLog ttll = new TradingTembinListingLog();
                ttll.setDocId(tradingItem.getId());
                ttll.setCreateDate(new Date());
                ttll.setIsTimer("0");
                ttll.setItemId(itemId);
                ttll.setListingType(tradingItem.getListingtype());
                this.iTradingTembinListingLog.saveTradingTembinListingLog(ttll);
                SystemLog sl = SystemLogUtils.selectSystemLogByTableId("ListingItemdo",tradingItem.getId());
                if(sl!=null){
                    sl.setEventname(sl.getEventname()+"_del");
                    SystemLogUtils.updateSystemLog(sl);
                }
            }
            else {
                String errors = "";
                String errorCode="";
                logger.error("刊登失败，请求xml为〉〉〉〉〉〉〉〉》"+res);
                logger.error("刊登失败，返回xml为：：：：：："+res);
                Document document= SamplePaseXml.formatStr2Doc(res);
                if(document==null){
                    errors="刊登失败，返回报文不全,内容为："+res;
                }else {
                    Element rootElt = document.getRootElement();
                    Iterator<Element> e = rootElt.elementIterator("Errors");
                    if (e != null) {
                        while (e.hasNext()) {
                            Element es = e.next();
                            if (es.element("SeverityCode") != null && "Error".equals(es.elementText("SeverityCode"))) {
                                errors += es.elementText("LongMessage") + ";";
                                errorCode+=es.elementText("SeverityCode")+";";
                            }
                        }
                    }
                }
                SystemLog sl = SystemLogUtils.selectSystemLogByTableId("ListingItemdo",tradingItem.getId());
                if(sl==null){
                    sl = new SystemLog();
                }
                sl.setCreatedate(new Date());
                sl.setOperuser(tradingItem.getCreateUser() + "");
                sl.setTableId(tradingItem.getId());
                sl.setEventname("ListingItemdo");
                sl.setOther(errorCode);
                sl.setEventdesc("刊登失败！原因：" + errors);
                SystemLogUtils.saveLog(sl);
                return;
            }
        } catch (Exception e) {
            logger.error("解析xml出错,请稍后到ebay网站确认结果,itemid:"+itemId+res,e);
            return;
        }
        if(ObjectUtils.isLogicalNull(itemId)){return;}
        tradingItem.setItemId(itemId);
        tradingItem.setIsFlag("Success");
        taskMessageVO.setMessageContext(taskMessageVO.getMessageContext()+",itemID:"+itemId);
        try {
            iTradingItem.saveTradingItem(tradingItem);
        } catch (Exception e) {
            logger.error("写入itemid出错tradingItemid:"+tradingItem.getId()+"itemid:"+itemId,e);
        }
    }

    @Override
    public String getType() {
        return SiteMessageStatic.LISTING_ITEM_BEAN;
    }
}
