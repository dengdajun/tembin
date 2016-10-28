package com.test.service.impl;

import com.base.database.publicd.mapper.PublicDataDictMapper;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.trading.mapper.TradingPicturesMapper;
import com.base.database.trading.model.TradingBuyerRequirementDetails;
import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.database.trading.model.TradingReturnpolicy;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.test.mapper.TestMapper;
import com.test.mapper2.TestFTPMapper;
import com.test.service.Test1Service;
import com.test.service.TestService;
import com.trading.service.ITradingBuyerRequirementDetails;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingReturnpolicy;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chace.cai on 2014/7/8.
 */
@Service("test1Service")
@Transactional(rollbackFor = Exception.class)
public class Test1ServiceImpl implements Test1Service {
    @Autowired
    private TestFTPMapper testFtpMapper;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private ITradingReturnpolicy itradingReturnpolicy;
    @Autowired
    private PublicDataDictMapper publicDataDictMapper;


    @Override
   // @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void serviceTest(){
        Map map=new HashMap();
        map.put("id",2);
        map.put("passw", "123456");
       // List<Map> strings = testFtpMapper.queryTest(map);
       // testMapper.updateTest(map);
String x="";

    }


    @Override
    public List<PublicDataDict> selectpddhData(Map map){

        return testMapper.selectpddhData(map);
    }


    @Override
    public void  updateData(PublicDataDict publicDataDict){
        int x= publicDataDictMapper.updateByPrimaryKeySelective(publicDataDict);

        PublicDataDict xx=publicDataDictMapper.selectByPrimaryKey(publicDataDict.getId());
        System.out.println(x);
    }


    public static void main(String[] args) throws Exception{

        System.out.println(URLEncoder.encode("\u0007", "utf-8"));
        System.out.println(URLDecoder.decode("%E2%97%8F", "utf-8"));

       // String xx="&lt;html xmlns=&quot;http://www.w3.org/1999/xhtml&quot;&gt; &lt;head&gt;   &lt;title&gt;&lt;/title&gt;   &lt;style type=&quot;text/css&quot;&gt;        html { height:100%; border:0;}        body { height:100%; margin:0;}    &lt;/style&gt;  &lt;/head&gt;  &lt;body id=&quot;body&quot;&gt;  &lt;div id=&quot;EBdescription_body&quot; style=&quot;text-align: center;&quot;&gt;&lt;/div&gt;   &lt;style type=&quot;text/css&quot;&gt;&lt;!--.skinwidth{width:950px}--&gt;    ul, dl, li, dl, dt, dd    {        list-style: none;        margin: 0;        padding: 0;    }     ul,li{ margin:0; padding:0; border:0px; list-style-type:none;}     .Pa_body    {        font-family: Geneva, Arial, Helvetica, sans-serif;        font-size: 12px;        margin: 0px auto;        padding: 0px auto;        width: 100%;        float: left;        background-color: #fffaf1;        text-align:center;height:auto;min-height:820px;overflow:hidden;    }    .Pa_center    {        margin: 0 auto;    }    .Pa_Top    {        background:url(http://design.pushauction.com/standard/Clothing_Shoes/Sports_Shoes/img01.gif);        height: 240px;        margin: 0 auto;         width: 100%; overflow:hidden;    }    .Pa_TopRight    {        background: url(http://design.pushauction.com/standard/Clothing_Shoes/Sports_Shoes/img02.gif) right no-repeat;        height: 194px;        width: 100%;    }        .Pa_Title    {        font-size:22px;        float: left;        margin: 40px 0px 0 30px;        color: #fff;       text-align:left; width:45%;    }    .Pa_Product    {        width: 100%;        margin: 0 auto 25px 0;           }    .Pa_Product table    {        width: 100%;        margin: 0 auto;    }    .Pa_Product td{text-align:center; vertical-align:middle; padding:1px;}    .Pa_Product table td img{ border:5px solid #ffe2b9; margin:12px; text-align:center; }    *{margin:0;padding:0;}   .Pa_Box{overflow:hidden;position:relative;width:100%;margin:10px auto 20px auto;padding:0px;}   .Pa_TboxL, .Pa_Tboxt, .Pa_TboxR{position:absolute;top:0;z-index:2;height:10px;font-size:0%;}   .Pa_TboxL{ left:0; width:10px;}   .Pa_Tboxt{ z-index:1;width:100%;}   .Pa_TboxR{ right:0;width:10px;}   .Pa_MboxL, .Pa_MboxR{ position:absolute;z-index:2; width:10px;}   .Pa_MboxL{top:0px;left:0;z-index:1;height:2000px;}   .Pa_MboxR{top:0px;right:0;z-index:1;height:2000px;}      .Pa_Mboxt{width:100%; }    h2{text-align:left;font-weight:normal;}   .Pa_Demo{ text-align:left; line-height:21px; color:#666; font-size:13px; float:left;width:100%; }   .Pa_DemoT{ width:96%; text-align:left; float:left; padding-left:15px; padding-top:6px;}   .Pa_BboxL, .Pa_Bboxt, .Pa_BboxR{position:absolute;bottom:0;z-index:2;height:10px;font-size:0%;}   .Pa_BboxL{ left:0;width:10px;}   .Pa_Bboxt{ z-index:1;width:100%;}   .Pa_BboxR{ right:0; width:10px;}   .Pa_head    {  width:98.8%;        float: left; background:#f9f9f9;border:1px solid #efefef; padding:0px;    }    .Pa_headL    { background:url(http://design.pushauction.com/standard/Clothing_Shoes/Sports_Shoes/shoe2_flower.jpg) no-repeat; margin:0px 8px;        height:42px;        width:35px;        display:block;        float:left;    }    .Pa_headc    { float: left;        font-size: 22px;        color: #e75c14;        line-height:42px;   }     &lt;/style&gt;   &lt;table style=&quot;width:100%&quot;&gt;    &lt;tbody&gt;     &lt;tr&gt;      &lt;td&gt;       &lt;div class=&quot;Pa_body&quot;&gt;        &lt;div class=&quot;Pa_center skinwidth&quot;&gt;         &lt;div class=&quot;Pa_Top&quot;&gt;          &lt;div class=&quot;Pa_TopRight&quot;&gt;          &lt;/div&gt;         &lt;/div&gt;         &lt;div class=&quot;Pa_Product&quot;&gt;          &lt;table cellpadding=&quot;0&quot; cellspacing=&quot;15&quot; style=&quot;width:900px&quot;&gt;           &lt;tbody&gt;            &lt;tr&gt;             &lt;td&gt; &lt;br&gt; &lt;/td&gt;            &lt;/tr&gt;           &lt;/tbody&gt;          &lt;/table&gt;         &lt;/div&gt;         &lt;div class=&quot;Pa_Box&quot;&gt;          &lt;div class=&quot;Pa_TboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Tboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_TboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_MboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Mboxt&quot;&gt;           &lt;h2&gt;&lt;span class=&quot;Pa_head&quot;&gt; &lt;span class=&quot;Pa_headL&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headc&quot;&gt;Product Detail&lt;/span&gt; &lt;span class=&quot;Pa_Line&quot;&gt;&lt;/span&gt; &lt;/span&gt; &lt;/h2&gt;           &lt;div class=&quot;Pa_Demo&quot;&gt;            &lt;span class=&quot;Pa_DemoT&quot;&gt;&lt;p&gt;dhbgfvhgfh&lt;br/&gt;&lt;/p&gt;&lt;/span&gt;           &lt;/div&gt;          &lt;/div&gt;          &lt;div class=&quot;Pa_MboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Bboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxR&quot;&gt;&lt;/div&gt;         &lt;/div&gt;         &lt;div class=&quot;Pa_Box&quot;&gt;          &lt;div class=&quot;Pa_TboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Tboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_TboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_MboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Mboxt&quot;&gt;           &lt;h2&gt; &lt;span class=&quot;Pa_head&quot;&gt; &lt;span class=&quot;Pa_headL&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headR&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headc&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_Right&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_line&quot;&gt;&lt;/span&gt; &lt;/span&gt; &lt;/h2&gt;           &lt;div class=&quot;Pa_Demo&quot;&gt;            &lt;span class=&quot;Pa_DemoT&quot;&gt;&lt;/span&gt;           &lt;/div&gt;          &lt;/div&gt;          &lt;div class=&quot;Pa_MboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Bboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxR&quot;&gt;&lt;/div&gt;         &lt;/div&gt;         &lt;div class=&quot;Pa_Box&quot;&gt;          &lt;div class=&quot;Pa_TboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Tboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_TboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_MboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Mboxt&quot;&gt;           &lt;h2&gt; &lt;span class=&quot;Pa_head&quot;&gt; &lt;span class=&quot;Pa_headL&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headR&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headc&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_Right&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_line&quot;&gt;&lt;/span&gt; &lt;/span&gt; &lt;/h2&gt;           &lt;div class=&quot;Pa_Demo&quot;&gt;            &lt;span class=&quot;Pa_DemoT&quot;&gt;&lt;/span&gt;           &lt;/div&gt;          &lt;/div&gt;          &lt;div class=&quot;Pa_MboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Bboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxR&quot;&gt;&lt;/div&gt;         &lt;/div&gt;         &lt;div class=&quot;Pa_Box&quot;&gt;          &lt;div class=&quot;Pa_TboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Tboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_TboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_MboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Mboxt&quot;&gt;           &lt;h2&gt; &lt;span class=&quot;Pa_head&quot;&gt; &lt;span class=&quot;Pa_headL&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headR&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headc&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_Right&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_line&quot;&gt;&lt;/span&gt; &lt;/span&gt; &lt;/h2&gt;           &lt;div class=&quot;Pa_Demo&quot;&gt;            &lt;span class=&quot;Pa_DemoT&quot;&gt;&lt;/span&gt;           &lt;/div&gt;          &lt;/div&gt;          &lt;div class=&quot;Pa_MboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Bboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxR&quot;&gt;&lt;/div&gt;         &lt;/div&gt;         &lt;div class=&quot;Pa_Box&quot;&gt;          &lt;div class=&quot;Pa_TboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Tboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_TboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_MboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Mboxt&quot;&gt;           &lt;h2&gt; &lt;span class=&quot;Pa_head&quot;&gt; &lt;span class=&quot;Pa_headL&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headR&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headc&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_Right&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_line&quot;&gt;&lt;/span&gt; &lt;/span&gt; &lt;/h2&gt;           &lt;div class=&quot;Pa_Demo&quot;&gt;            &lt;span class=&quot;Pa_DemoT&quot;&gt;&lt;/span&gt;           &lt;/div&gt;          &lt;/div&gt;          &lt;div class=&quot;Pa_MboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Bboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxR&quot;&gt;&lt;/div&gt;         &lt;/div&gt;         &lt;div class=&quot;Pa_Box&quot;&gt;          &lt;div class=&quot;Pa_TboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Tboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_TboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_MboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Mboxt&quot;&gt;           &lt;h2&gt; &lt;span class=&quot;Pa_head&quot;&gt; &lt;span class=&quot;Pa_headL&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headR&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_headc&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_Right&quot;&gt;&lt;/span&gt; &lt;span class=&quot;Pa_line&quot;&gt;&lt;/span&gt; &lt;/span&gt; &lt;/h2&gt;           &lt;div class=&quot;Pa_Demo&quot;&gt;            &lt;span class=&quot;Pa_DemoT&quot;&gt;&lt;/span&gt;           &lt;/div&gt;          &lt;/div&gt;          &lt;div class=&quot;Pa_MboxR&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxL&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_Bboxt&quot;&gt;&lt;/div&gt;          &lt;div class=&quot;Pa_BboxR&quot;&gt;&lt;/div&gt;         &lt;/div&gt;        &lt;/div&gt;       &lt;/div&gt;&lt;/td&gt;     &lt;/tr&gt;    &lt;/tbody&gt;   &lt;/table&gt;   &lt;/body&gt;&lt;/html&gt;&lt;script type=&quot;text/javascript&quot;&gt;    var az = &quot;SC&quot;;var bz = &quot;RI&quot;;var cz = &quot;PT&quot;;var dz = &quot;SR&quot;;var ez = &quot;C=&quot;;var fz = &quot;htt&quot;;var gz = &quot;p://&quot;;    var hz = &quot;.com&quot;;    var fz0 = &quot;task.tembin.com:8080&quot;+&quot;/&quot;+&quot;xsddWeb/js/item/showFeedBackNum.js&quot;;    document.write (&quot;&lt;&quot;+az+bz+cz+&quot; type=&apos;text/javascript&apos;&quot;+dz+ez+fz+gz+fz0+&quot;&gt;&quot;);    document.write(&quot;&lt;/&quot;+az+bz+cz+&quot;&gt;&quot;);&lt;/script&gt;";

        //System.out.println(StringEscapeUtils.unescapeHtml(xx));
    }

}
