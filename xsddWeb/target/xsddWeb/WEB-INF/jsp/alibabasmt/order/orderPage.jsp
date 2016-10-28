<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/3/21
  Time: 10:41
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
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/select2/mySelect2.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/alibabasmt/order/orderPage.js" />></script>
</head>
<body>
<input type="hidden" id="countryNames" value="US,GB,DE,AU">
<c:forEach begin="1" end="5"  varStatus="status">
    <input type="hidden" id="countryQ${status.index}"/>
    <input type="hidden" id="typeQ${status.index}"/>
    <input type="hidden" id="daysQ${status.index}"/>
    <input type="hidden" id="statusQ${status.index}"/>
    <input type="hidden" id="accountQ${status.index}"/>
</c:forEach>
<c:forEach items="${folders}"  begin="0" varStatus="status">
    <input type="hidden" id="countryQ${status.index+6}"/>
    <input type="hidden" id="typeQ${status.index+6}"/>
    <input type="hidden" id="daysQ${status.index+6}"/>
    <input type="hidden" id="statusQ${status.index+6}"/>
    <input type="hidden" id="accountQ${status.index+6}"/>
</c:forEach>
<div class="new_all">
<div class="here">当前位置：首页 &gt; 刊登管理 &gt; <b>范本</b></div>
<div class="a_bal"></div>
<div class="new">
<script type="text/javascript">
    function setTab(name,cursel,n){
        for(i=1;i<=n;i++){
            var menu=document.getElementById(name+i);
            var con=document.getElementById("con_"+name+"_"+i);
            menu.className=i==cursel?"new_tab_1":"new_tab_2";
            if(con){
                con.style.display=i==cursel?"block":"none";
            }else{
                con.style.display="none";
            }

        }
        searchi=cursel;
        initSearchInput("",cursel);
        query1();
    }
</script>
<div id="tabRemark" class="new_tab_ls">
    <c:if test="${folders==null}">
        <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,5)">近期销售</dt>
        <dt id="menu2" class="new_tab_2" onclick="setTab('menu',2,5)">未付款</dt>
        <dt id="menu3" class="new_tab_2" onclick="setTab('menu',3,5)">未结清付款</dt>
        <dt id="menu4" class="new_tab_2" onclick="setTab('menu',4,5)">已付款</dt>
        <dt id="menu5" class="new_tab_2" onclick="setTab('menu',5,5)">申请取消</dt>
    </c:if>
    <c:if test="${folders!=null}">
        <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,${folderNum+5})">近期销售</dt>
        <dt id="menu2" class="new_tab_2" onclick="setTab('menu',2,${folderNum+5})">未付款</dt>
        <dt id="menu3" class="new_tab_2" onclick="setTab('menu',3,${folderNum+5})">未结清付款</dt>
        <dt id="menu4" class="new_tab_2" onclick="setTab('menu',4,${folderNum+5})">已付款</dt>
        <dt id="menu5" class="new_tab_2" onclick="setTab('menu',5,${folderNum+5})">申请取消</dt>
        <c:forEach items="${folders}"  begin="0" varStatus="status">
            <dt scop="tabRemark" id="menu${status.index+6}" name1="${status.index+6}" name2="${folders[status.index].id}" name="${folders[status.index].configName}" class="new_tab_2" onclick="setTab('menu',${status.index+6},${folderNum+5})">${folders[status.index].configName}</dt>
        </c:forEach>
    </c:if>
</div>
<div class="Contentbox">
<div id="con_menu_1" class="hover" style="display: block;">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list" name="selectCountrys" name1="selectCountrys1"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry1"  onclick="queryCountry(1,1,null);">全部&nbsp;</span></a><a href="javascript:void(0);"><span class="newusa_ici_1" scop="queryCountry1"  onclick="queryCountry(1,2,'US');"><img src="<c:url value ="/img/usa_1.png"/> ">美国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry1"  onclick="queryCountry(1,3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry1"  onclick="queryCountry(1,4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry1"  onclick="queryCountry(1,5,'AU');"><img src="<c:url value ="/img/AU.jpg"/>">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici">全部</span></a><span class="newusa_ici_1">固价</span><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryTime1" onclick="queryTime(1,1,null)">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryTime1" onclick="queryTime(1,2,'1')">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime1" onclick="queryTime(1,3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime1" onclick="queryTime(1,4,'7');" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime1" onclick="queryTime(1,5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
            <%-- <span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime1"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
             <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime1"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
            <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,1)"><span scop="queryTime1" onclick="queryTime(1,6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time1"></span>
            <li class="new_usa_list"><br/><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus1"  onclick="queryStatus(1,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus1"  onclick="queryStatus(1,2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus1"  onclick="queryStatus(1,3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus1"  onclick="queryStatus(1,4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus1"  onclick="queryStatus(1,5,'4');">未妥投&nbsp;</span></a></li>
        <div class="newsearch">
            <br/><span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG">
 <span id="sleHid" style="width: 85px;">
<select name="type"  style="color: #737FA7;width: 85px;float: left" id="itemType1" onchange="chageOldDom(this,1)" >
    <option selected="selected" >选择类型</option>
    <option value="buyerLoginId">买家登陆ID</option>
    <option value="productId">产品号</option>
    <option value="sku">SKU</option>
    <option value="title">产品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="orderId">订单号</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="content1" name="" type="text"   style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query1();" type="button" class="key_2"></div>
        </div>
</div>
        <div class="newds">
            <div class="newsj_left" style="margin-bottom: 10px;">
                <span class="newusa_ici_del_in"><input style="margin-left: 2px;" type="checkbox" name="checkbox" id="allCheckbox" onclick="selectAllCheck(1,this);"></span>
                <span class="newusa_ici_del" onclick="addComment(1);">添加备注</span>
                <span class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span>
                <span onclick="downloadOrders(null);" class="newusa_ici_del">下载订单</span>
                <span onclick="moveFolder(1);" class="newusa_ici_del">移动到文件夹</span>
                <span onclick="modifyOrderNums(1);" class="newusa_ici_del">上传跟踪号</span>
                <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
                <%--<span onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>--%>
            </div>
            <div>
        </div>
            <div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" onclick="ebayListPage();" class="">同步订单</a></div>
        </div>
    </div>

    <!--综合结束 -->
    <div id="SMTorderTableList1"></div>
</div>

<div style="display: none;" id="con_menu_2">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list" name="selectCountrys" name1="selectCountrys2"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry2"  onclick="queryCountry(2,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry2"  onclick="queryCountry(2,2,'US');"><img src="<c:url value ="/img/usa_1.png"/> " >美国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry2"  onclick="queryCountry(2,3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry2"  onclick="queryCountry(2,4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry2"  onclick="queryCountry(2,5,'AU');"><img src="<c:url value ="/img/AU.jpg"/> ">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici">全部</span></a><span class="newusa_ici_1">固价</span><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span scop="queryTime2" onclick="queryTime(2,1,null)" class="newusa_ici">全部&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime2" onclick="queryTime(2,2,1)" class="newusa_ici_1">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime2" onclick="queryTime(2,3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime2" onclick="queryTime(2,4,'7')" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime2" onclick="queryTime(2,5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
            <%--<span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime2"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
            <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime2"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
            <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,2)"><span scop="queryTime2" onclick="queryTime(2,6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time2"></span>
            <li class="new_usa_list"><br/><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus2"  onclick="queryStatus(2,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus2"  onclick="queryStatus(2,2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus2"  onclick="queryStatus(2,3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus2"  onclick="queryStatus(2,4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus2"  onclick="queryStatus(2,5,'4');">未妥投&nbsp;</span></a></li>
            <div class="newsearch">
                <br/><span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG">
<span id="sleHid" style="width: 85px;">
<select name="type"  style="color: #737FA7;width: 85px;float: left" id="itemType2" onchange="chageOldDom(this,2);">
    <option selected="selected" >选择类型</option>
    <option value="buyerLoginId">买家登陆ID</option>
    <option value="productId">产品号</option>
    <option value="sku">SKU</option>
    <option value="title">产品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="orderId">订单号</option>
</select>
</span>
</span>
                <div class="vsearch">
                    <input id="content2" name="" type="text" style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query1();" type="button" class="key_2"></div>
            </div>
            </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input style="margin-left: 2px;" type="checkbox" name="checkbox" id="allCheckbox" onclick="selectAllCheck(2,this);"></span>
                <span class="newusa_ici_del" onclick="addComment(2);">添加备注</span>
                <span class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span>
                <span onclick="downloadOrders('unpaid');" class="newusa_ici_del">下载订单</span>
                <span onclick="moveFolder(2);" class="newusa_ici_del">移动到文件夹</span>
                <span onclick="modifyOrderNums(2);" class="newusa_ici_del">上传跟踪号</span>
                <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
               <%-- <span onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>--%></div>
            <div>
        </div>
            <div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" onclick="ebayListPage();"  class="">同步订单</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div id="SMTorderTableList2"></div>

</div>
<div style="display: none;" id="con_menu_3">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list" name="selectCountrys" name1="selectCountrys3"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry3"  onclick="queryCountry(3,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry3"  onclick="queryCountry(3,2,'US');"><img src="<c:url value ="/img/usa_1.png"/> " >美国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry3"  onclick="queryCountry(3,3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry3"  onclick="queryCountry(3,4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry3"  onclick="queryCountry(3,5,'AU');"><img src="<c:url value ="/img/AU.jpg"/> ">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici">全部</span></a><span class="newusa_ici_1">固价</span><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span scop="queryTime3" onclick="queryTime(3,1,null)" class="newusa_ici">全部&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime3" onclick="queryTime(3,2,'1')" class="newusa_ici_1">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime3" onclick="queryTime(3,3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime3" onclick="queryTime(3,4,'7')" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime3" onclick="queryTime(3,5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
            <%--<span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime3"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
            <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime3"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
            <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,3)"><span scop="queryTime3" onclick="queryTime(3,6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time3"></span>
            <li class="new_usa_list"><br/><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus3"  onclick="queryStatus(3,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus3"  onclick="queryStatus(3,2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus3"  onclick="queryStatus(3,3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus3"  onclick="queryStatus(3,4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus3"  onclick="queryStatus(3,5,'4');">未妥投&nbsp;</span></a></li>
            <div class="newsearch">
                <br/><span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG">
<span id="sleHid" style="width: 85px;">
<select name="type"  style="color: #737FA7;width: 85px;float: left" id="itemType3" onchange="chageOldDom(this,3);">
    <option selected="selected" >选择类型</option>
    <option value="buyerLoginId">买家登陆ID</option>
    <option value="productId">产品号</option>
    <option value="sku">SKU</option>
    <option value="title">产品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="orderId">订单号</option>
</select>
</span>
</span>
                <div class="vsearch">
                    <input id="content3" name="" type="text"  style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query1();" type="button" class="key_2"></div>
            </div>
            </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input style="margin-left: 2px;" type="checkbox" name="checkbox" id="allCheckbox" onclick="selectAllCheck(3,this);"></span>
                <span class="newusa_ici_del" onclick="addComment(3);">添加备注</span>
                <span class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span>
                <span onclick="downloadOrders('uncleared');" class="newusa_ici_del">下载订单</span>
                <span onclick="moveFolder(3);" class="newusa_ici_del">移动到文件夹</span>
                <span onclick="modifyOrderNums(3);" class="newusa_ici_del">上传跟踪号</span>
                <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
                <%--<span onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>--%></div>
            <div>
        </div>
            <div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" onclick="ebayListPage();" class="">同步订单</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div id="SMTorderTableList3"></div>
</div>
<div style="display: none;" id="con_menu_4">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list" name="selectCountrys" name1="selectCountrys4"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry4"  onclick="queryCountry(4,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry4"  onclick="queryCountry(4,2,'US');"><img src="<c:url value ="/img/usa_1.png"/> " >美国</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry4"  onclick="queryCountry(4,3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry4"  onclick="queryCountry(4,4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry4"  onclick="queryCountry(4,5,'AU');"><img src="<c:url value ="/img/AU.jpg"/> ">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici">全部</span></a><span class="newusa_ici_1">固价</span><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span scop="queryTime4" onclick="queryTime(4,1,null)" class="newusa_ici">全部&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime4" onclick="queryTime(4,2,'1')" class="newusa_ici_1">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime4" onclick="queryTime(4,3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime4" onclick="queryTime(4,4,'7')" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime4" onclick="queryTime(4,5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
            <a href="javascript:void(0)" id="addstartTimeAndEndTimeID" onclick="addstartTimeAndEndTime(this,4)"><span scop="queryTime4" onclick="queryTime(4,6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time4"></span>
            <li class="new_usa_list"><br/><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus4"  onclick="queryStatus(4,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus4"  onclick="queryStatus(4,2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus4"  onclick="queryStatus(4,3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus4"  onclick="queryStatus(4,4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus4"  onclick="queryStatus(4,5,'4');">未妥投&nbsp;</span></a></li>
            <div class="newsearch">
                <br/><span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG">
  <span id="sleHid" style="width: 85px;">
<select name="type"  style="color: #737FA7;width: 85px;float: left" id="itemType4" onchange="chageOldDom(this,4);">
    <option selected="selected" >选择类型</option>
    <option value="buyerLoginId">买家登陆ID</option>
    <option value="productId">产品号</option>
    <option value="sku">SKU</option>
    <option value="title">产品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="orderId">订单号</option>
</select>
</span>
</span>
                <div class="vsearch">
                    <input id="content4" name="" type="text"  style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query1();" type="button" class="key_2"></div>
            </div>
            </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input style="margin-left: 2px;" type="checkbox" name="checkbox" id="allCheckbox" onclick="selectAllCheck(4,this);"></span>
                <span class="newusa_ici_del" onclick="addComment(4);">添加备注</span>
                <span class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span>
                <span onclick="downloadOrders('paid');" class="newusa_ici_del">下载订单</span>
                <span onclick="moveFolder(4);" class="newusa_ici_del">移动到文件夹</span>
                <span onclick="modifyOrderNums(4);" class="newusa_ici_del">上传跟踪号</span>
                <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
                <%--<span onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>--%></div>
            <div>
        </div>
            <div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" onclick="ebayListPage();" class="">同步订单</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div id="SMTorderTableList4"></div>

</div>
<div style="display: none;" id="con_menu_5">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list" name="selectCountrys" name1="selectCountrys5"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry5"  onclick="queryCountry(5,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry5"  onclick="queryCountry(5,2,'US');"><img src="<c:url value ="/img/usa_1.png"/> " >美国</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry5"  onclick="queryCountry(5,3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry5"  onclick="queryCountry(5,4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry5"  onclick="queryCountry(5,5,'AU');"><img src="<c:url value ="/img/AU.jpg"/> ">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici">全部</span></a><span class="newusa_ici_1">固价</span><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a></li>
        <div class="newsearch">
            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span scop="queryTime5" onclick="queryTime(5,1,null)" class="newusa_ici">全部&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime5" onclick="queryTime(5,2,'1')" class="newusa_ici_1">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime5" onclick="queryTime(5,3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime5" onclick="queryTime(5,4,'7')" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime5" onclick="queryTime(5,5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
            <%--<span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime3"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
            <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime3"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
            <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,5)"><span scop="queryTime5" onclick="queryTime(5,6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time5"></span>
            <li class="new_usa_list"><br/><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus5"  onclick="queryStatus(5,1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus5"  onclick="queryStatus(5,2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus5"  onclick="queryStatus(5,3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus5"  onclick="queryStatus(5,4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus5"  onclick="queryStatus(5,5,'4');">未妥投&nbsp;</span></a></li>
            <div class="newsearch">
                <br/><span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG">
  <span id="sleHid" style="width: 85px;">
<select name="type"  style="color: #737FA7;width: 85px;float: left" id="itemType5" onchange="chageOldDom(this,5);">
    <option selected="selected" >选择类型</option>
    <option value="buyerLoginId">买家登陆ID</option>
    <option value="productId">产品号</option>
    <option value="sku">SKU</option>
    <option value="title">产品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="orderId">订单号</option>
</select>
</span>
</span>
                <div class="vsearch">
                    <input id="content5" name="" type="text"  style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query1();" type="button" class="key_2"></div>
            </div>
        </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input style="margin-left: 2px;" type="checkbox" name="checkbox" id="allCheckbox" onclick="selectAllCheck(5,this);"></span>
                <span class="newusa_ici_del" onclick="addComment(5);">添加备注</span>
                <span class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span>
                <span onclick="downloadOrders('cancel');" class="newusa_ici_del">下载订单</span>
                <span onclick="moveFolder(5);" class="newusa_ici_del">移动到文件夹</span>
                <span onclick="modifyOrderNums(5);" class="newusa_ici_del">上传跟踪号</span>
                <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
                <%--<span onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>--%></div>
            <div>
            </div>
            <div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" onclick="ebayListPage();" class="">同步订单</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div id="SMTorderTableList5"></div>

</div>
<c:forEach items="${folders}"  begin="0" varStatus="status">
    <div style="display: none;" id="con_menu_${status.index+6}">
        <!--综合开始 -->
        <div class="new_usa" style="margin-top:20px;">
            <li class="new_usa_list" name="selectCountrys" name1="selectCountrys${status.index+6}"><span class="newusa_i" style="width: 75px;">收件人国家：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryCountry${status.index+6}"  onclick="queryCountry(${status.index+6},1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry${status.index+6}"  onclick="queryCountry(${status.index+6},2,'US');"><img src="<c:url value ="/img/usa_1.png"/> " >美国</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry${status.index+6}"  onclick="queryCountry(${status.index+6},3,'GB');"><img src="<c:url value ="/img/UK.jpg"/> ">英国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry${status.index+6}"  onclick="queryCountry(${status.index+6},4,'DE');"><img src="<c:url value ="/img/DE.png"/> ">德国&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryCountry${status.index+6}"  onclick="queryCountry(${status.index+6},5,'AU');"><img src="<c:url value ="/img/AU.jpg"/> ">澳大利亚</span></a><a href="javascript:void(0)" onclick="selectCountrys();"><span style="padding-left: 20px;vertical-align: middle;color: royalblue">更多...</span></a></li>
            <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">刊登类型：</span><a href="javascript:void(0)"><span class="newusa_ici">全部</span></a><span class="newusa_ici_1">固价</span><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a><a  href="javascript:void(0)"><span class="newusa_ici_1">多属性</span></a></li>
            <div class="newsearch">
                <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span scop="queryTime${status.index+6}" onclick="queryTime(${status.index+6},1,null)" class="newusa_ici">全部&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime${status.index+6}" onclick="queryTime(${status.index+6},2,'1')" class="newusa_ici_1">今天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime${status.index+6}" onclick="queryTime(${status.index+6},3,'2')" class="newusa_ici_1">昨天&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime${status.index+6}" onclick="queryTime(${status.index+6},4,'7')" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span scop="queryTime${status.index+6}" onclick="queryTime(${status.index+6},5,'30')" class="newusa_ici_1">30天以内&nbsp;</span></a>
                    <%--<span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime3"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
                    <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime3"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
                <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,${status.index+6})"><span scop="queryTime${status.index+6}" onclick="queryTime(${status.index+6},6,null)" class="newusa_ici_1">自定义&nbsp;</span></a><span style="float: left" id="time${status.index+6}"></span>
                <li class="new_usa_list"><br/><span class="newusa_i" style="width: 75px;">订单状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="queryStatus${status.index+6}"  onclick="queryStatus(${status.index+6},1,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus${status.index+6}"  onclick="queryStatus(${status.index+6},2,'1');">已付款&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus${status.index+6}"  onclick="queryStatus(${status.index+6},3,'2');">已发货&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus${status.index+6}"  onclick="queryStatus(${status.index+6},4,'3');">妥投&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="queryStatus${status.index+6}"  onclick="queryStatus(${status.index+6},5,'4');">未妥投&nbsp;</span></a></li>
                <div class="newsearch">
                    <br/><span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG">
  <span id="sleHid" style="width: 85px;">
<select name="type"  style="color: #737FA7;width: 85px;float: left" id="itemType${status.index+6}" onchange="chageOldDom(this,${status.index+6});">
    <option selected="selected" >选择类型</option>
    <option value="buyerLoginId">买家登陆ID</option>
    <option value="productId">产品号</option>
    <option value="sku">SKU</option>
    <option value="title">产品标题</option>
    <option value="trackNum">跟踪号</option>
    <option value="person">联系人</option>
    <option value="buyerEmail">买家电邮</option>
    <option value="comment">备注</option>
    <option value="orderId">订单号</option>
</select>
</span>
</span>
                    <div class="vsearch">
                        <input id="content${status.index+6}" name="" type="text"  style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query1();" type="button" class="key_2"></div>
                </div>
            </div>
            <div class="newds">
                <div class="newsj_left">
                    <span class="newusa_ici_del_in"><input style="margin-left: 2px;" type="checkbox" name="checkbox" id="allCheckbox" onclick="selectAllCheck(${status.index+6},this);"></span>
                    <span class="newusa_ici_del" onclick="addComment(${status.index+6});">添加备注</span>
                    <span class="newusa_ici_del" onclick="addTabRemark();">管理文件夹</span>
                    <span onclick="downloadOrders(null);" class="newusa_ici_del">下载订单</span>
                    <span onclick="moveFolder(${status.index+6});" class="newusa_ici_del">移动到文件夹</span>
                    <span onclick="modifyOrderNums(${status.index+6});" class="newusa_ici_del">上传跟踪号</span>
                    <span onclick="viewSystemlog('');" class="newusa_ici_del">查看日志</span>
                    <%--<span onclick="sycOneOrder();" class="newusa_ici_del">同步单个订单</span>--%></div>
                <div>
                </div>
                <div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" onclick="ebayListPage();" class="">同步订单</a></div>
            </div>
        </div>
        <!--综合结束 -->
        <div id="SMTorderTableList${status.index+6}"></div>

    </div>
</c:forEach>

</div>
</div></div>
</body>
</html>
