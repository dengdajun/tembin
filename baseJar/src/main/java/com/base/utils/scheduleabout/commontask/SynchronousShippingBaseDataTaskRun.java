package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.mapper.TradingDataDictionaryMapper;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingDataDictionaryExample;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.xmlutils.SamplePaseXml;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrtor on 2015/5/6.
 * 抓取运输选项----不参与定时任务
 */
public class SynchronousShippingBaseDataTaskRun  extends BaseScheduledClass implements Scheduledable {

    static Logger logger = Logger.getLogger(SynchronousShippingBaseDataTaskRun.class);

    @Override
    public void run() {
        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        TradingDataDictionaryMapper tradingDataDictionaryMapper =  (TradingDataDictionaryMapper) ApplicationContextUtil.getBean(TradingDataDictionaryMapper.class);
        List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByType("site");
        logger.error("同步运输选项开始。。。。。。。。。。");
        if(litdd!=null){
            for(TradingDataDictionary tdd:litdd){
                List<BasicHeader> headers = new ArrayList<BasicHeader>();
                headers.add(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL","879"));
                headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "5d70d647-b1e2-4c7c-a034-b343d58ca425"));
                headers.add(new BasicHeader("X-EBAY-API-APP-NAME","sandpoin-1f58-4e64-a45b-56507b02bbeb"));
                headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","936fc911-c05c-455c-8838-3a698f2da43a"));
                headers.add(new BasicHeader("X-EBAY-API-SITEID",tdd.getName1()));
                headers.add(new BasicHeader("X-EBAY-API-CALL-NAME", APINameStatic.GeteBayDetails));
                HttpClient httpClient= HttpClientUtil.getHttpsClient();
                String xml = this.getXml();
                String res= null;
                try {
                    res = HttpClientUtil.post(httpClient, "https://api.sandbox.ebay.com/ws/api.dll", xml, "UTF-8", headers);
                } catch (Exception e) {
                    logger.error("调用获取运输选项报错：",e);
                    e.printStackTrace();
                }
                try {
                    List<TradingDataDictionary> li = SamplePaseXml.getShippingSelect(res);
                    if(li!=null&&li.size()>0){
                        for(TradingDataDictionary tdds:li){
                            tdds.setParentId(tdd.getId());
                            TradingDataDictionaryExample tdde = new TradingDataDictionaryExample();
                            tdde.createCriteria().andTypeEqualTo(tdds.getType()).andParentIdEqualTo(tdds.getParentId()).andNameEqualTo(tdds.getName()).andValueEqualTo(tdds.getValue());
                            List<TradingDataDictionary> litdy = tradingDataDictionaryMapper.selectByExample(tdde);
                            if(litdy==null||litdy.size()==0){
                                tradingDataDictionaryMapper.insertSelective(tdds);
                            }else{
                                tdds.setId(litdy.get(0).getId());
                                tradingDataDictionaryMapper.updateByPrimaryKeySelective(tdds);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("调用获取运输选项，xml为："+res,e);
                    e.printStackTrace();
                }
            }
            logger.error("同步运输选项结束》>>>>>>>>>>>>>>>>>>>>>>");
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }

    public String getXml(){
        String xml="";
        xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GeteBayDetailsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**vVcRVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**Z2MB0OtmO4JsPFBZPcjclippjnZG4bwpcpXYRXDdc6wEppv5m/WiCvsjyTKWoVXCMxQl2se3U6Vn93oBL6zg8EcR3GCXCC3ZbTpEQ3lBX8avBrME9VHo0RcfcE7oLVtnBAuSffy3Dk5ICUNyU7g57/rHw8d5DnO3JeitpQcTLKAInt+sEZslri3wa4Mx0xgyFW5OF3w8mNK8ib8+57PTHcApnp8xRTAlIVuwW3F/fGbSFVReS07/MulzlFXBoW/ZPLq+L2aLFpn5s+IB5/gB0HoDo5uGzRnALmXxUz8BuwJMrUE29830W7xVSEaYSYsOcJyue6PjJKyZt0rXf8TNHusXCHX240dWllrjMVxS7pEHgKb/FKfd/79PH3rXTFmuexesXS6H1lRmHBBE1iknFwtzzS+UeN22Rd6W+hjSjuOHB33o2gMS5cOdVXHuHyOQ6VJU3bJL/eNDgyB+wz3HhZmz6sF+lmLIRKP82H1QXdlwdGdpVhAhyqnE4FH4qTgPBMxv6c4jRL5BRuyUZDLeJI1WXmaZ4pNMss+MiME7Qu+7bP7S2TZhmValbfW/FvqSrxR9LlHji7iQSsz2m56x5TLjOtkFWjRxmB6C1wzBVtzdILzbvmA/1+9RlMevalW6bg22irusiv7iuD/AnC9pZ0Sju2XK/7WpjVW4/lZyBmRbqHQJPbU/5MU3xrM8pTV8rZmPfQrRh2araaWGIBE5IW3gsTrETpRUQybXd/a107ee61GwXEUqVat1EfznFpIs</eBayAuthToken>" +
                "  </RequesterCredentials>\n" +
                "  <DetailName>ShippingServiceDetails</DetailName>\n" +
                "</GeteBayDetailsRequest>";
        return xml;
    }
    @Override
    public String getMark() {
        return null;
    }

    @Override
    public Integer crTimeMinu() {
        return 5;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONOUS_SHIPPING_BASE_DATA;
    }
}
