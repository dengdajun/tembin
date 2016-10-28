<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/13
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>

    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript" src=<c:url value ="/js/orders/getOrdersListJSP.js"/>${jsfileVersion} ></script>
    <script type="text/javascript">
        var OrderGetOrders;
        var onflag = '${onFlag}';
        $(document).ready(function(){
            initSearchInput();  
            initSearchInput1();
            initSearchInput2();
            initSearchInput3();
            initSearchInput4();
            getOrderList1();
            var lllll=0;
            var con="#con_menu_";
            var menu="";
            for(var i=6;i<=${count+5};i++){
                setTab1("menu",i,i);
                var bs=$("#itemType"+i).val();
                initSearchInput5(bs,(i-1),"menu"+i,"OrderGetOrdersListTable"+i);
                $("#s2id_content"+i).attr("style","width: 200px; float: left; display: block;")
            }
            if(onflag=="main"){
                setTab('menu',4,${count+5});
            }

        });

    </script>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/select2/mySelect2.js" /> ></script>
</head>
<body>


<div class="new_all">
<div class="here">当前位置：首页 &gt; 销售管理 &gt; <b>订单</b></div>
<div class="a_bal"></div>
<div class="new">
<input id="count" type="hidden" value="${count+4}">
<div id="tabRemark" class="new_tab_ls">
    <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,${count+5})">近期销售</dt>
    <dt id="menu2" class="new_tab_2" onclick="setTab('menu',2,${count+5})">未付款</dt>
    <dt id="menu3" class="new_tab_2" onclick="setTab('menu',3,${count+5})">未结清付款</dt>
    <dt id="menu4" class="new_tab_2" onclick="setTab('menu',4,${count+5})">已付款</dt>
    <dt id="menu5" class="new_tab_2" onclick="setTab('menu',5,${count+5})">回收站</dt>
    <c:forEach items="${folders}" begin="0"  varStatus="status">
        <dt scop="tabRemark" id="menu${status.index+6}" name1="${status.index+6}" name2="${folders[status.index].id}" name="${folders[status.index].configName}" class="new_tab_2" onclick="setTab1('menu',${status.index+6},${status.count+5})">${folders[status.index].configName}</dt>
    </c:forEach>
</div>

    <form id="queryForm">
        <input type="hidden" id="countryQ1"/>
        <input type="hidden" id="typeQ1"/>
        <input type="hidden" id="daysQ1"/>
        <input type="hidden" id="statusQ1"/>
        <input type="hidden" id="accountQ1">
        <input type="hidden" id="countryQ2"/>
        <input type="hidden" id="typeQ2"/>
        <input type="hidden" id="daysQ2"/>
        <input type="hidden" id="statusQ2"/>
        <input type="hidden" id="accountQ2">
        <input type="hidden" id="countryQ3"/>
        <input type="hidden" id="typeQ3"/>
        <input type="hidden" id="daysQ3"/>
        <input type="hidden" id="statusQ3"/>
        <input type="hidden" id="accountQ3">
        <input type="hidden" id="countryQ4"/>
        <input type="hidden" id="typeQ4"/>
        <input type="hidden" id="daysQ4"/>
        <input type="hidden" id="statusQ4"/>
        <input type="hidden" id="accountQ4">
        <input type="hidden" id="countryQ5"/>
        <input type="hidden" id="typeQ5"/>
        <input type="hidden" id="daysQ5"/>
        <input type="hidden" id="statusQ5"/>
        <input type="hidden" id="accountQ5">
        <input type="hidden" id="countryNames" value="US,GB,DE,AU">
        <c:forEach items="${folders}" begin="0"  varStatus="status">
            <input type="hidden" id="countryQ${status.index+6}"/>
            <input type="hidden" id="typeQ${status.index+6}"/>
            <input type="hidden" id="daysQ${status.index+6}"/>
            <input type="hidden" id="statusQ${status.index+6}"/>
            <input type="hidden" id="accountQ${status.index+6}"/>
        </c:forEach>
    </form>
<div class="Contentbox" id="ContentboxDiv">
<div id="con_menu_1" style="display: block;">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list" name="selectCountrys" name1="selectCountrys1"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry1"  onclick="queryCountry(1,1,null);">全部&nbsp;</span></a><a href="javascript:void(0);"><span class="newusa_ici_1" scop="queryCountry1"  onclick="queryCountry(1,2,'US');"><img src="<c:url value ="/img/usa_1.png"/> ">美国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry1"  onclick="queryCountry(1,3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry1"  onclick="queryCountry(1,4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry1"  onclick="queryCountry(1,5,'AU');"><img src="<c:url value ="/img/AU.jpg"/>">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">选择账号：</span>
            <a href="javascript:void(0)"><span scop="account1" onclick="selectAccount1(1,1,null);" class="newusa_ici">&nbsp;全部&nbsp;&nbsp;</span></a>
            <c:forEach items="${ebays}" var="ebay" begin="0" varStatus="status">
                <a href="javascript:void(0)"><span scop="account1" onclick="selectAccount1(1,${status.index+2},'${ebay.ebayName}');" class="newusa_ici_1">${fn:toUpperCase(ebay.ebayNameCode)}</span></a>
            </c:forEach>
         </li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryAttr1"  onclick="queryAttr(1,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr1"  onclick="queryAttr(1,2,'fixation');">固价&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr1"  onclick="queryAttr(1,3,'auction');">拍卖&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr1"  onclick="queryAttr(1,4,'multiattribute');">多属性&nbsp;</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryTime1" onclick="queryTime(1,1,null)">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryTime1" onclick="queryTime(1,2,'1')">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime1" onclick="queryTime(1,3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime1" onclick="queryTime(1,4,'7');" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime1" onclick="queryTime(1,5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
           <%-- <span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime1"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
            <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime1"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
            <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,1)"><span scop="queryTime1" onclick="queryTime(1,6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time1"></span>
        </div>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus1"  onclick="queryStatus(1,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus1"  onclick="queryStatus(1,2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus1"  onclick="queryStatus(1,3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)" style="display: none;"><span class="newusa_ici_1" scop="queryStatus1"  onclick="queryStatus(1,4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)"  style="display: none;"><span class="newusa_ici_1" scop="queryStatus1"  onclick="queryStatus(1,5,'4');">未妥投&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus1"  onclick="queryStatus(1,6,'5');">无跟踪号&nbsp;</span></a>
            <a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus1"  onclick="queryStatus(1,7,'6');">已退款&nbsp;</span></a>
        </li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG" style="width:82px;background-position: 67px 10px;">
                    <span id="sleHid" style="width: 80px;">
<select name="type"  style="color: #737FA7;width: 80px;float: left" id="itemType1" onchange="chageOldDom(this)" >
    <option selected="selected" >选择类型</option>
    <option value="buyerId">买家账号</option>
    <option value="itemId">物品号</option>
    <option value="sku">SKU</option>
    <option value="title">物品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="transaction">EBAY交易号</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="content1" name="" type="text"   style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query1();" type="button" class="key_2"></div>
        </div>
        <div class="newds">
            <div class="newsj_left" style="padding: 4px;">

                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="allCheckbox"  onclick="selectAllCheck(1,this);"></span>
                <span class="newusa_ici_del" onclick="addComment(1);">添加备注</span>
                <span class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span>
                <span onclick="downloadOrders(1);" class="newusa_ici_del">下载订单</span>
                <span onclick="moveFolder(1);" class="newusa_ici_del">移动到文件夹</span>
                <span onclick="modifyOrderNums(1);" class="newusa_ici_del">上传跟踪号</span>
                <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
                <span style="display: none" onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>
            </div>
            <div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" onclick="getBindParm();" >同步eBay</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div style="width: 100%;float: left;height: 5px"></div>
    <div id="OrderGetOrdersListTable1" ></div>
    <div class="page_newlist">
    </div>
</div>

<div style="display: none;" id="con_menu_2">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list" name="selectCountrys" name1="selectCountrys2"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry2"  onclick="queryCountry(2,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry2"  onclick="queryCountry(2,2,'US');"><img src="<c:url value ="/img/usa_1.png"/> " >美国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry2"  onclick="queryCountry(2,3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry2"  onclick="queryCountry(2,4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry2"  onclick="queryCountry(2,5,'AU');"><img src="<c:url value ="/img/AU.jpg"/> ">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">选择账号：</span>
            <a href="javascript:void(0)"><span scop="account2" onclick="selectAccount1(2,1,null);" class="newusa_ici">&nbsp;全部&nbsp;&nbsp;</span></a>
            <c:forEach items="${ebays}" var="ebay" begin="0" varStatus="status">
                <a href="javascript:void(0)"><span scop="account2" onclick="selectAccount1(2,${status.index+2},'${ebay.ebayName}');" class="newusa_ici_1">${fn:toUpperCase(ebay.ebayNameCode)}</span></a>
            </c:forEach>
        </li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryAttr2" onclick="queryAttr(2,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr2" onclick="queryAttr(2,2,'fixation');">固价&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr2" onclick="queryAttr(2,3,'auction');">拍卖&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr2" onclick="queryAttr(2,4,'multiattribute');">多属性&nbsp;</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span scop="queryTime2" onclick="queryTime(2,1,null)" class="newusa_ici">全部&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime2" onclick="queryTime(2,2,1)" class="newusa_ici_1">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime2" onclick="queryTime(2,3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime2" onclick="queryTime(2,4,'7')" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime2" onclick="queryTime(2,5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
            <%--<span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime2"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
            <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime2"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
            <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,2)"><span scop="queryTime2" onclick="queryTime(2,6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time2"></span>
        </div>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus2"  onclick="queryStatus(2,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)" style="display: none;"><span class="newusa_ici_1" scop="queryStatus2"  onclick="queryStatus(2,2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)"  style="display: none;"><span class="newusa_ici_1" scop="queryStatus2"  onclick="queryStatus(2,3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)"  style="display: none;"><span class="newusa_ici_1" scop="queryStatus2"  onclick="queryStatus(2,4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)"  style="display: none;"><span class="newusa_ici_1" scop="queryStatus2"  onclick="queryStatus(2,5,'4');">未妥投&nbsp;</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG" style="width:82px;background-position: 67px 10px;">
                    <span id="sleHid" style="width: 80px;">
<select name="type"  style="color: #737FA7;width: 80px;float: left" id="itemType2" onchange="chageOldDom1();">
    <option selected="selected" >选择类型</option>
    <option value="buyerId">买家账号</option>
    <option value="itemId">物品号</option>
    <option value="sku">SKU</option>
    <option value="title">物品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="transaction">EBAY交易号</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="content2" name="" type="text" style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query2();" type="button" class="key_2"></div>
        </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="allCheckbox" onclick="selectAllCheck(2,this);"></span>
                <span class="newusa_ici_del" onclick="addComment(2);">添加备注</span><span class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span><span onclick="downloadOrders(2);" class="newusa_ici_del">下载订单</span>
                <span onclick="moveFolder(2);" class="newusa_ici_del">移动到文件夹</span>
                <span onclick="modifyOrderNums(2);" class="newusa_ici_del">上传跟踪号</span>
                <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
                <span onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>
            </div>
            <div class="tbbay"><a data-toggle="modal" href="#myModal" onclick="getBindParm();" class="">同步eBay</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div style="width: 100%;float: left;height: 5px"></div>
    <div id="OrderGetOrdersListTable2" ></div>
    <div class="page_newlist">
    </div>

</div>
<div style="display: none;" id="con_menu_3">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list" name="selectCountrys" name1="selectCountrys3"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry3"  onclick="queryCountry(3,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry3"  onclick="queryCountry(3,2,'US');"><img src="<c:url value ="/img/usa_1.png"/> " >美国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry3"  onclick="queryCountry(3,3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry3"  onclick="queryCountry(3,4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry3"  onclick="queryCountry(3,5,'AU');"><img src="<c:url value ="/img/AU.jpg"/> ">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">选择账号：</span>
            <a href="javascript:void(0)"><span scop="account3" onclick="selectAccount1(3,1,null);" class="newusa_ici">&nbsp;全部&nbsp;&nbsp;</span></a>
            <c:forEach items="${ebays}" var="ebay" begin="0" varStatus="status">
                <a href="javascript:void(0)"><span scop="account3" onclick="selectAccount1(3,${status.index+2},'${ebay.ebayName}');" class="newusa_ici_1">${fn:toUpperCase(ebay.ebayNameCode)}</span></a>
            </c:forEach>
        </li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryAttr3" onclick="queryAttr(3,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr3" onclick="queryAttr(3,2,'fixation');">固价&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr3" onclick="queryAttr(3,3,'auction');">拍卖&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr3" onclick="queryAttr(3,4,'multiattribute');">多属性&nbsp;</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span scop="queryTime3" onclick="queryTime(3,1,null)" class="newusa_ici">全部&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime3" onclick="queryTime(3,2,'1')" class="newusa_ici_1">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime3" onclick="queryTime(3,3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime3" onclick="queryTime(3,4,'7')" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime3" onclick="queryTime(3,5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
            <%--<span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime3"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
            <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime3"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
            <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,3)"><span scop="queryTime3" onclick="queryTime(3,6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time3"></span>
        </div>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus3"  onclick="queryStatus(3,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus3"  onclick="queryStatus(3,2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus3"  onclick="queryStatus(3,3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)"  style="display: none;"><span class="newusa_ici_1" scop="queryStatus3"  onclick="queryStatus(3,4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)"  style="display: none;"><span class="newusa_ici_1" scop="queryStatus3"  onclick="queryStatus(3,5,'4');">未妥投&nbsp;</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG" style="width:82px;background-position: 67px 10px;">
                    <span id="sleHid" style="width: 80px;">
<select name="type"  style="color: #737FA7;width: 80px;float: left" id="itemType3" onchange="cleanInput();">
    <option selected="selected" >选择类型</option>
    <option value="buyerId">买家账号</option>
    <option value="itemId">物品号</option>
    <option value="sku">SKU</option>
    <option value="title">物品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="transaction">EBAY交易号</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="content3" name="" type="text"  style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query3();" type="button" class="key_2"></div>
        </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox" id="allCheckbox" onclick="selectAllCheck(3,this);"></span>
                <span class="newusa_ici_del" onclick="addComment(3);">添加备注</span><span class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span><span onclick="downloadOrders(3);" class="newusa_ici_del">下载订单</span>
                <span onclick="moveFolder(3);" class="newusa_ici_del">移动到文件夹</span>
                <span onclick="modifyOrderNums(3);" class="newusa_ici_del">上传跟踪号</span>
                <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
                <span onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>
            </div>
            <div>
                <div id="newtipi">
                </div></div>
            <div class="tbbay"><a data-toggle="modal" href="#myModal" onclick="getBindParm();" class="">同步eBay</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div style="width: 100%;float: left;height: 5px"></div>
    <div id="OrderGetOrdersListTable3" ></div>
    <div class="page_newlist">
    </div>
</div>
<div style="display: none;" id="con_menu_4">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list" name="selectCountrys" name1="selectCountrys4"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry4"  onclick="queryCountry(4,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry4"  onclick="queryCountry(4,2,'US');"><img src="<c:url value ="/img/usa_1.png"/> " >美国</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry4"  onclick="queryCountry(4,3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry4"  onclick="queryCountry(4,4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry4"  onclick="queryCountry(4,5,'AU');"><img src="<c:url value ="/img/AU.jpg"/> ">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">选择账号：</span>
            <a href="javascript:void(0)"><span scop="account4" onclick="selectAccount1(4,1,null);" class="newusa_ici">&nbsp;全部&nbsp;&nbsp;</span></a>
            <c:forEach items="${ebays}" var="ebay" begin="0" varStatus="status">
                <a href="javascript:void(0)"><span scop="account4" onclick="selectAccount1(4,${status.index+2},'${ebay.ebayName}');" class="newusa_ici_1" name1="${ebay.ebayName}">${fn:toUpperCase(ebay.ebayNameCode)}</span></a>
            </c:forEach>
        </li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryAttr4" onclick="queryAttr(4,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr4" onclick="queryAttr(4,2,'fixation');">固价&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr4" onclick="queryAttr(4,3,'auction');">拍卖&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr4" onclick="queryAttr(4,4,'multiattribute');">多属性&nbsp;</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span scop="queryTime4" onclick="queryTime(4,1,null)" class="newusa_ici">全部&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime4" onclick="queryTime(4,2,'1')" class="newusa_ici_1">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime4" onclick="queryTime(4,3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime4" onclick="queryTime(4,4,'7')" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime4" onclick="queryTime(4,5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
            <a href="javascript:void(0)" id="addstartTimeAndEndTimeID" onclick="addstartTimeAndEndTime(this,4)"><span scop="queryTime4" onclick="queryTime(4,6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time4"></span>
        </div>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus4"  onclick="queryStatus(4,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"  style="display:none;"><span class="newusa_ici_1" scop="queryStatus4"  onclick="queryStatus(4,2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)" style="display:none;"><span class="newusa_ici_1" scop="queryStatus4"  onclick="queryStatus(4,3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)"  style="display: none;"><span class="newusa_ici_1" scop="queryStatus4"  onclick="queryStatus(4,4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)"  style="display: none;"><span class="newusa_ici_1" scop="queryStatus4"  onclick="queryStatus(4,5,'4');">未妥投&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus4"  onclick="queryStatus(4,6,'5');">无跟踪号&nbsp;</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG" style="width:82px;background-position: 67px 10px;">
                    <span id="sleHid" style="width: 80px;">
<select name="type"  style="color: #737FA7;width: 80px;float: left" id="itemType4" onchange="cleanInput();">
    <option selected="selected" >选择类型</option>
    <option value="buyerId">买家账号</option>
    <option value="itemId">物品号</option>
    <option value="sku">SKU</option>
    <option value="title">物品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="transaction">EBAY交易号</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="content4" name="" type="text"  style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query4();" type="button" class="key_2"></div>
        </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="allCheckbox" onclick="selectAllCheck(4,this);"></span>
                <span id="addComment" class="newusa_ici_del" onclick="addComment(4);">添加备注</span><span id="addtabRemark" class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span><span id="downloadOrder4" onclick="downloadOrders(4);" class="newusa_ici_del">下载订单</span>
                <span id="moveFolder" onclick="moveFolder(4);" class="newusa_ici_del">移动到文件夹</span>
                <span onclick="modifyOrderNums(4);" class="newusa_ici_del">上传跟踪号</span>
                <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
                <span onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>
            </div>
            <div>
                <div id="newtipi">
                </div></div>
            <div class="tbbay"><a data-toggle="modal" href="#myModal" onclick="getBindParm();" class="">同步eBay</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div style="width: 100%;float: left;height: 5px"></div>
    <div id="OrderGetOrdersListTable4" ></div>
    <div class="page_newlist">
    </div>
</div>
<div style="display: none;" id="con_menu_5">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list" name="selectCountrys" name1="selectCountrys5"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry5"  onclick="queryCountry(5,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry5"  onclick="queryCountry(5,2,'US');"><img src="<c:url value ="/img/usa_1.png"/> " >美国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry5"  onclick="queryCountry(5,3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry5"  onclick="queryCountry(5,4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry5"  onclick="queryCountry(5,5,'AU');"><img src="<c:url value ="/img/AU.jpg"/> ">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">选择账号：</span>
            <a href="javascript:void(0)"><span scop="account5" onclick="selectAccount1(5,1,null);" class="newusa_ici">&nbsp;全部&nbsp;&nbsp;</span></a>
            <c:forEach items="${ebays}" var="ebay" begin="0" varStatus="status">
                <a href="javascript:void(0)"><span scop="account5" onclick="selectAccount1(5,${status.index+2},'${ebay.ebayName}');" class="newusa_ici_1">${fn:toUpperCase(ebay.ebayNameCode)}</span></a>
            </c:forEach>
        </li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryAttr5" onclick="queryAttr(5,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr5" onclick="queryAttr(5,2,'fixation');">固价&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr5" onclick="queryAttr(5,3,'auction');">拍卖&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryAttr5" onclick="queryAttr(5,4,'multiattribute');">多属性&nbsp;</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span scop="queryTime5" onclick="queryTime(5,1,null)" class="newusa_ici">全部&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime5" onclick="queryTime(5,2,'1')" class="newusa_ici_1">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime5" onclick="queryTime(5,3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime5" onclick="queryTime(5,4,'7')" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime5" onclick="queryTime(5,5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
            <%--<span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime3"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
            <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime3"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
            <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,5)"><span scop="queryTime5" onclick="queryTime(5,6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time5"></span>
        </div>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus5"  onclick="queryStatus(5,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus5"  onclick="queryStatus(5,2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus5"  onclick="queryStatus(5,3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)"  style="display: none;"><span class="newusa_ici_1" scop="queryStatus5"  onclick="queryStatus(5,4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)" style="display: none;"><span class="newusa_ici_1" scop="queryStatus5"  onclick="queryStatus(5,5,'4');">未妥投&nbsp;</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG" style="width:82px;background-position: 67px 10px;">
                    <span id="sleHid" style="width: 80px;">
<select name="type"  style="color: #737FA7;width: 80px;float: left" id="itemType5" onchange="cleanInput();">
    <option selected="selected" >选择类型</option>
    <option value="buyerId">买家账号</option>
    <option value="itemId">物品号</option>
    <option value="sku">SKU</option>
    <option value="title">物品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="transaction">EBAY交易号</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="content5" name="" type="text"  style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query5();" type="button" class="key_2"></div>
        </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox" id="allCheckbox" onclick="selectAllCheck(5,this);"></span>
                <span class="newusa_ici_del" onclick="addComment(5);">添加备注</span><span class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span><span onclick="downloadOrders(5);" class="newusa_ici_del">下载订单</span>
                <span onclick="moveFolder(5);" class="newusa_ici_del">移动到文件夹</span>
                <span onclick="modifyOrderNums(5);" class="newusa_ici_del">上传跟踪号</span>
                <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
                <span onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>
            </div>
            <div>
                <div id="newtipi">
                </div></div>
            <div class="tbbay"><a data-toggle="modal" href="#myModal" onclick="getBindParm();" class="">同步eBay</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div style="width: 100%;float: left;height: 5px"></div>
    <div id="OrderGetOrdersListTable5" ></div>
    <div class="page_newlist">
    </div>
</div>

</div>
</div></div>

</body>
</html>
