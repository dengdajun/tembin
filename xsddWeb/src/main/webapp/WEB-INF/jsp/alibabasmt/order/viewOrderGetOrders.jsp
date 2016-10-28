<%@ page import="java.util.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/14
  Time: 16:52
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
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>
    <script type="text/javascript">
        var clone=null;
        var api = frameElement.api, W = api.opener;
        var flag=false;
        var s="";
        var r1="";
        /*$(document).ready(function(){

            var  hidem =$("body").find("#hideme").length;
            if(hidem>0){
                var uls=$("#add").find("ul");
                var mes=""
                for(var i= 0;i<uls.length;i++){
                    mes=mes+$("#add").find("ul").eq(i).html();
                }
                $("#add").html(mes);
            }
          *//*  var orders=${orders};
            for(var i=0;i<orders.length;i++){
                $("#frameLeft").attr("src",path + "/order/viewOrderAbstractLeft.do?orderId=${orderId}");
                $("#frameRight").attr("src",path + "/order/viewOrderAbstractRight.do?orderId=${orderId}");
                $("#frameDown").attr("src",path + "/order/viewOrderAbstractDown.do?orderId=${orderId}");
            }*//*
    *//*       *//**//* $("#frameLeft").attr("src",path + "/order/viewOrderAbstractLeft.do?orderId=${orderId}");
            $("#frameRight").attr("src",path + "/order/viewOrderAbstractRight.do?orderId=${orderId}");
            $("#frameDown").attr("src",path + "/order/viewOrderAbstractDown.do?orderId=${orderId}");*//*
            $("#frameBuyHistory").attr("src",path + "/order/viewOrderBuyHistory.do?orderId=${orderId}");
            var messageFlag="${messageFlag}";
        *//*    if(messageFlag=='true'){
                W.refreshTable();
            }*//*
            var messageID="${messageID}";
            if(messageID&&messageID!=""){
                var sender="${sender}";
                var url=path+"/message/ajax/updateReadStatus.do?messageid="+messageID+"&sender="+sender;
                $().invoke(url,null,
                        [function(m,r){
                            var noReadCount=W.document.getElementById("noReadCount");
                            noReadCount.innerHTML="未读("+r+")&nbsp;"
                            Base.token;
                        },
                            function(m,r){
                                Base.token();
                            }]
                );
            }

        });*/
        function dialogClose(){
            W.OrderGetOrders.close();
        }
        var viewsendMessage;
        var viewsendMessage1= W.OrderGetOrders;
        function sendMessage(){
            var url=path+'/order/initOrdersSendMessage.do?orderid=${order.orderid}';
            viewsendMessage=openMyDialog({title: '发送消息',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                height:500,
                lock:true
            });
        }
        function boxChange(){
            flag=!flag;
            if(flag){
                $("#tranCheckBox").val("true");
            }else{
                $("#tranCheckBox").val("false");
            }
        }
        function submitCommit(){
            var url=path+"/order/ajax/updateOrder.do";
            var data=$("#tranForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token;
                        W.refreshTable();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );

        }
        function submitCommit1(){
            if(!$("#sendForm").validationEngine("validate")){
                return;
            }
            var text=$("#textarea").val();

            var url=path+"/order/apiGetOrdersSendMessage.do?";
            var data=$("#sendForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        var content=$("#textarea").val();
                        var myDate=new Date();
                        var mytime=myDate.toLocaleString();
                        var year=mytime.substring(0,4);
                        var month=mytime.substring(5,7);
                        var day=mytime.substring(8,10);
                        var time1=myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
                        var day1=year+"-"+month+"-"+day+" ";
                        var div="<p class=\"admin\">"+s+"</p>"+
                        "<div class=\"admin_co\">"+
                                "<div class=\"admin_co_1\"></div>"+
                        "<ul>Hi "+r1+".: )<br/>"+content+"";
                        div+="<span>发送于:"+day1+time1+"</span>";
                        /*div+="<span style=\"color: red\">发送失败于:"+day1+time1+"!</span>";*/
                        div+="</ul>"+
                        "<div class=\"admin_co_2\"></div></div>"+
                        "<div class=\"dpan\"></div>";
                        $("#add").append(div);
                        document.getElementById("textarea").innerHTML="";
                        W.OrderGetOrders.close();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            var content=$("#textarea").val();
                            var myDate=new Date();
                            var mytime=myDate.toLocaleString();
                            var year=mytime.substring(0,4);
                            var month=mytime.substring(5,7);
                            var day=mytime.substring(8,10);
                            var time1=myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
                            var day1=year+"-"+month+"-"+day+" ";
                            var div="<p class=\"admin\">"+s+"</p>"+
                                    "<div class=\"admin_co\">"+
                                    "<div class=\"admin_co_1\"></div>"+
                                    "<ul>Hi "+r1+".: )<br/>"+content+"";
                           /* div+="<span>发送于:"+day1+time1+"</span>";*/
                            div+="<span style=\"color: red\">发送失败于:"+day1+time1+"!</span>";
                            div+="</ul>"+
                                    "<div class=\"admin_co_2\"></div></div>"+
                                    "<div class=\"dpan\"></div>";
                            $("#add").append(div);
                            Base.token();
                        }],{isConverPage:true}
            );
        }
        function selectSendMessage(){
            var url=path+'/message/selectSendMessage.do?transactionid=${order.transactionid}&ebayId=${order.ebayId}&paypal=${paypals[0]}&itemId=${order.itemid}';
            sentmessage = openMyDialog({title: '选择消息模板',
                content:'url:'+url,
                icon: 'succeed',
                width:800,
                height:400,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        /*$(document).ready(function(){
            $("#sendForm").validationEngine();
            var paypal=$("#paypal").val();
            if(paypal!=""){
                var url=path+"/order/ajax/paypalAmount.do?orderId="+paypal;
                $().invoke(url,null,
                        [function(m,r){
                            var spans=document.getElementsByName("price");
                            var spans1=document.getElementsByName("totalPrice");
                            var spans2=document.getElementsByName("price1");
                            for(var i=0;i< r.length;i++){
                                for(var j=0;j<spans.length;j++){
                                    if(r.length>spans.length){
                                        if(r[j]==""){
                                            spans[j].innerHTML="";
                                        }else{
                                            spans[j].innerHTML=r[j];
                                        }
                                    }else{
                                        if(r[i]==""){
                                            spans[i].innerHTML="";
                                        }else{
                                            spans[i].innerHTML=r[i];
                                        }
                                    }
                                }
                            }
                            if(r.length==0){
                                for(var i=0;i<spans.length;i++){
                                    spans[i].innerHTML="";
                                }
                            }
                            for(var i=0;i<spans.length;i++){
                                if(spans[i].innerHTML!=""){
                                    if(spans[i].innerHTML!="账号未验证"){
                                        var price10=parseFloat(spans[i].innerHTML);
                                        var price11=parseFloat(spans1[i].innerHTML);
                                        var price12=(price11-price10).toFixed(2);
                                        spans2[i].innerHTML=price12+" USD";
                                        spans[i].innerHTML=spans[i].innerHTML;
                                    }
                                }else{
                                    spans2[i].innerHTML=spans1[i].innerHTML+" USD";
                                }
                            }
                            for(var i=0;i<spans.length;i++){
                                if(spans[i].innerHTML==""||spans[i].innerHTML=="账号未验证"){
                                    if(spans[i].innerHTML==""){
                                        $(spans[i]).attr("style","color:red;");
                                        spans[i].innerHTML="获取paypal费用失败";
                                    }else{
                                        $(spans[i]).attr("style","color:red;");
                                        spans[i].innerHTML="paypal账号未验证";
                                    }
                                }else{
                                    spans[i].innerHTML=spans[i].innerHTML+" USD";
                                }
                            }
                            Base.token();
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }

        });*/
    function liTrack(obja){
        var trackNum=$(obja).html();
        if(trackNum==null || trackNum==''){return;}
        window.open("http://www.91track.com/track/"+trackNum);
    }
    function lianjieEbay(itemid){
        window.open(itemid);
    }
        var itemInformation;
    function connectItemInformation(sku){
        var url=path+"/order/ajax/connectItemInformation.do?sku="+sku;
        $().invoke(url,null,
                [function(m,r){
                    var url=path+"/information/addItemInformation.do?id="+ r.id+"&orderFlag=true";
                    itemInformation = openMyDialog({title: '商品信息',
                        content:'url:'+url,
                        icon: 'succeed',
                        width:725,
                        height:700,
                        parent:api,
                        lock:true,
                        zIndex:2000
                    });
                    Base.token();
                },
                    function(m,r){
                        alert(r);
                        Base.token();
                    }]
        );
    }
    function lianjieItemid(itemid){
        window.open(itemid);
    }
    </script>
    <style>
        .table-a table{border:1px solid rgba(0, 0, 0, 0.23)
        }
        #left { float: left; }
        #right {float: left}
        #center {float: inherit}
        #rightCenter { float:left; }
    </style>
</head>
<body>

<div class="modal-body">
<script type="text/javascript">
    function setvTab(name,cursel,n){
        for(i=1;i<=n;i++){
            var svt=document.getElementById(name+i);
            var con=document.getElementById("new_"+name+"_"+i);
            svt.className=i==cursel?"new_ic_1":"";
            con.style.display=i==cursel?"block":"none";
        }
    }
</script>
<div class="modal-header">
<table width="100%" border="0" style="margin-top:-20px;">
    <tbody><tr>
        <td style="line-height:30px;"><span style=" color:#2395F3; font-size:24px; font-family:Arial, Helvetica, sans-serif">
       <%-- <c:if test="${flag=='true'}">
            ${order.buyeruserid}</span> [来自eBay账号： 的买家]
            <input type="hidden" name="orderId" id="paypal" value="">
        </c:if>--%>
            ${message.sender}</span> [来自速卖通账号：${details.buyerloginid} 的买家]&nbsp;&nbsp;物品号:<%--<a href="javascript:void(0);" onclick="lianjieItemid('${message.itemid}');">${message.itemid}</a>--%>
            <input type="hidden" name="orderId" id="paypal" value="${details.orderid}">
        </td>
    </tr>
            <tr>
                <td>备注:${orderTable.comment}</td>
            </tr>
    <tr>
        <td><div class="new_tab">
            <div class="new_tab_left"></div>
            <div class="new_tab_right"></div>
                    <dt id="svt1" class="new_ic_1" onclick="setvTab('svt',1,2)">订单摘要</dt>
                    <dt id="svt2"  onclick="setvTab('svt',2,2)" class="">回复消息</dt>
        </div></td>
    </tr>

    </tbody></table>
    <div id="new_svt_1"  style="display: block;">
    <c:forEach items="${childrenOrders}" var="childrenOrder">
    <table width="100%" border="0">
        <tbody><tr>
            <td width="772"></td>
            <td width="9" rowspan="7" valign="top">&nbsp;</td>
            <td rowspan="7" valign="top" style="margin-left:20px; padding-left:15px; padding-top:20px; padding-right:20px; line-height:25px;background:#F4F4F4">
                <table width="100%" border="0">
                    <tbody><tr>
                        <td><strong>收件地址</strong>
                            <br>${address.detailaddress}&nbsp;${address.address2}
                            <br>${address.city},${address.province}
                         <%--   <br>${orders[status.index].postalcode}--%>
                            <br>${address.country}
                            <br>${address.phonenumber}
                            <br>${buyerInfo.email}</td>
                    </tr>
                    </tbody></table><span class="moneyok">
                       <c:if test="${details.fundstatus=='PAY_SUCCESS'}">
                           已付款
                       </c:if>
                        <c:if test="${details.fundstatus=='NOT_PAY'}">
                            未付款
                        </c:if>
                        <c:if test="${details.fundstatus=='WAIT_SELLER_CHECK'}">
                            卖家验款
                        </c:if>
                    </span>
                <span class="voknet"></span>
                <table width="100%" border="0">
                    <tbody>
                    <tr>
                        <td><strong>卖家邮件:</strong>${buyerInfo.email}</td>
                    </tr>
                    <tr>
                        <td><strong>备注:</strong>
                            ${orderTable.comment}</td>
                    </tr>
                    </tbody></table>
            </td>
        </tr>
        <tr>
            <td width="772" height="30"><span style="color:#2395F3; font-size:16px; font-family:Arial, Helvetica, sans-serif">1</span>  &nbsp; &nbsp;<strong>买家名字</strong>：${details.buyersignerfullname}&nbsp;&nbsp;&nbsp;&nbsp;<strong>买家email</strong>：${buyerInfo.email}</td>
        </tr>
        <tr>
            <td height="40" bgcolor="#F4F4F4" style="font-size:18px; font-family:'微软雅黑', '宋体', Arial">&nbsp;交易信息</td>
        </tr>
        <tr>
            <td><table width="100%" border="0">
                <tbody><tr>
                    <td width="41%" rowspan="${orderCounts[status.index]+11}"><a href="javascript:void(0)" onclick="lianjieEbay('${childrenOrder.productsnapurl}')"><img src="${childrenOrder.productimgurl}" width="297" height="247"></a></td>
                    <td width="21%" height="30"><strong>productId</strong><br></td>
                    <td width="38%" height="30">${childrenOrder.productid}</td>
                </tr>
                <tr>
                    <td height="30" width="21%"><strong>售出日期</strong><br></td>
                    <td width="38%" height="30"><fmt:formatDate value="${details.gmtcreate}" pattern="yyyy-MM-dd HH:mm"/></td>
                </tr>
                <tr>
                    <td height="30" width="21%"><strong>销售数量</strong><br></td>
                    <td width="38%" height="30">${childrenOrder.productcount}</td>
                </tr>
                <tr>
                    <td height="30" width="21%"><strong>售价</strong><br></td>
                    <td width="38%" height="30">${childrenOrder.productunitprice} ${childrenOrder.productunitpricecur}</td>
                </tr>
                <tr>
                    <td height="30" width="21%"><strong>成交费</strong><br></td>
                    <td width="38%" height="30">${details.escrowfee}${childrenOrder.productunitpricecur}</td>
                </tr>
                <tr>
                    <td height="30" width="21%"><strong>SKU</strong><br></td>
                    <td width="38%" height="30"><a href="javascript:void(0)">${childrenOrder.skucode}</a></td>
                </tr>
                <tr>
                    <td height="30" width="21%"><strong>variation SKU</strong><br></td>
                    <td width="38%" height="30">${orders[status.index].variationsku}</td>
                </tr>
                <c:forEach items="${specificseLists[status.index]}" var="specificse">
                    <tr>
                        <td height="30" width="21%"><strong>${specificse.name}</strong><br></td>
                        <td width="38%" height="30">${specificse.value}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td height="30" width="21%"><strong>买家选择运输</strong><br></td>
                    <td width="38%" height="30"> ${orders[status.index].selectedshippingservice}</td>
                </tr>
                <tr>
                    <td height="30" width="21%"><strong>买家选择运输费用</strong><br></td>
                    <td width="38%" height="30">${orders[status.index].selectedshippingservicecost}</td>
                </tr>
                <tr>
                    <td height="30" width="21%"><strong>保险费</strong><br></td>
                    <td width="38%" height="30">--包括在运输中--</td>
                </tr>
                <tr>
                   <td height="30" width="21%"><strong>付款状态</strong><br></td>
                    <td width="38%" height="30">
                        <c:if test="${details.fundstatus=='PAY_SUCCESS'}">
                            已付款
                         </c:if>
                        <c:if test="${details.fundstatus=='NOT_PAY'}">
                                未付款
                        </c:if>
                        <c:if test="${details.fundstatus=='WAIT_SELLER_CHECK'}">
                            卖家验款
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td width="41%" rowspan="10"><font color="#2395F3">${details.buyersignerfullname} ( ${buyerInfo.email})</font></td>
                    <td height="30"><strong>付款方式</strong></td>
                    <td height="30">${details.paymenttype}</td>
                </tr>
                </tbody></table></td>

        </tr>
        <tr>
            <td height="40" bgcolor="#F4F4F4" style="font-size:18px; font-family:'微软雅黑', '宋体', Arial">&nbsp;${details.paymenttype}付款</td>
        </tr>
        <tr>
            <td><table width="100%" border="0">
                <tbody><tr>
                    <td height="30"><strong>${details.paymenttype}交易号</strong></td>
                    <td height="30"> <strong>付款日期</strong></td>
                    <td width="8%" height="30" align="center"> <strong>状态</strong></td>
                    <td width="15%" height="30"> <strong>总额</strong></td>
                    <td width="15%" height="30"> <strong>PayPal费用</strong></td>
                    <td width="15%" height="30"> <strong>净额</strong></td>
                </tr>
                <c:if test="${details.fundstatus=='PAY_SUCCESS'}">
                <tr>
                    <td height="30">${paypals[status.index]}</td>
                    <td height="30"><fmt:formatDate value="${details.gmtpaysuccess}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td height="30" align="center">
                    <c:if test="${details.fundstatus=='PAY_SUCCESS'}">
                        <img src="<c:url value ="/img/new_yes.png"/>" width="22" height="22">
                    </c:if>
                    <c:if test="${details.fundstatus!='PAY_SUCCESS'}">
                        <img src="<c:url value ="/img/new_no.png"/>" width="22" height="22">
                    </c:if>
                    </td>
                    <td width="15%" height="30"><span name="totalPrice">${orders[status.index].total}</span> USD</td>
                    <td width="15%" height="30"><span name="price"></span></td>
                    <td  width="15%" height="30"><span name="price1"></span></td>
                </tr>
                </c:if>
                    <c:if test="${orders[status.index].paymentstatus!='Succeeded'||orders[status.index].paymentstatus==null}">
                        <c:if test="${orders[status.index].status=='Complete'&&orders[status.index].orderstatus=='Completed'&&orders[status.index].amountpaid>0.0}">
                            <tr><td height="30">${paypals[status.index]}</td>
                            <td height="30"><fmt:formatDate value="${orders[status.index].paidtime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td height="30" align="center">
                                <c:if test="${orders[status.index].status=='Complete'&&orders[status.index].orderstatus=='Completed'&&orders[status.index].amountpaid>0.0}">
                                    <img src="<c:url value ="/img/new_yes.png"/>" width="22" height="22">
                                </c:if>
                                <c:if test="${!(orders[status.index].status=='Complete'&&orders[status.index].orderstatus=='Completed'&&orders[status.index].amountpaid>0.0)}">
                                    <img src="<c:url value ="/img/new_no.png"/>" width="22" height="22">
                                </c:if>
                            </td>
                            <td width="15%" height="30"><span name="totalPrice">${orders[status.index].total}</span> USD</td>
                            <td width="15%" height="30"><span name="price"></span></td>
                            <td  width="15%" height="30"><span name="price1"></span></td></tr>
                        </c:if>
                        <c:if test="${orders[status.index].status!='Complete'&&orders[status.index].orderstatus!='Completed'&&orders[status.index].amountpaid<=0.0}">
                            <tr>
                                <td height="30"></td>
                                <td height="30"></td>
                                <td height="30" align="center"></td>
                                <td width="15%" height="30"></td>
                                <td width="15%" height="30"></td>
                                <td width="15%" height="30"></td>
                            </tr>
                        </c:if>
                    </c:if>
                <c:if test="${orders[status.index].status=='Incomplete'}">
                    <tr>
                        <td height="30"></td>
                        <td height="30"></td>
                        <td height="30" align="center"></td>
                        <td width="15%" height="30"></td>
                        <td width="15%" height="30"></td>
                        <td width="15%" height="30"></td>
                    </tr>
                </c:if>

                <tr>
                    <td height="30" colspan="6" style="color:#F00"><span class="voknet"></span><br>
                           <br/>
                        <c:if test="${refundAmountDesc!=null&&refundAmountDesc!=''}">
                            退款:${refundAmountDesc},操作日期:<fmt:formatDate value="${refundDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </c:if>
                    </td>
                </tr>
                </tbody></table></td>
        </tr>
        </tbody></table>
    </c:forEach>
        <hr/>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" onclick="dialogClose();" data-dismiss="modal">关闭</button>
    </div>
</div>

    <div style="display: none;" id="new_svt_2">
    <table width="100%" border="0">
        <tbody><tr>
            <td width="772"></td>
            <td width="9" rowspan="5" valign="top">&nbsp;</td>
            <td rowspan="5" valign="top" style="margin-left:20px; padding-left:15px; padding-top:20px; padding-right:20px; line-height:25px;background:#F4F4F4"><strong>订单号</strong><br>
                ${order.orderid}(已配货)<br>
                <strong>物流跟踪号</strong><br>
                <a href="javascript:void(0)" onclick="liTrack(this);">${order.shipmenttrackingnumber}</a><br>
                <strong>发货时间：</strong><br>
                ${order.shippedtime}<br>
                <strong>付款时间：</strong><br>
                ${order.paidtime}<br>
                <strong>运输方式：</strong><br>
                ${order.selectedshippingservice}
                <c:forEach items="${orders}" begin="0"  varStatus="status">
                <span class="voknet"></span>
                <table width="100%" border="0">
                    <tbody><tr>
                        <td width="56"><strong>商品${status.index+1}</strong></td>
                        <td style="color:#63B300">CNDL</td>
                    </tr>
                    <tr>
                        <td><img src="${pictures[status.index]}" width="46" height="46"></td>
                        <td style=" color:#5F93D7"><a href="javascript:void(0)" onclick="lianjieEbay(this,${orders[status.index].itemid});">${orders[status.index].title}</a></td>
                    </tr>
                    </tbody></table>
                </c:forEach>
                <%--<span class="voknet"></span>

                <table width="100%" border="0">
                    <tbody><tr>
                        <td width="56"><strong>商品2</strong></td>
                        <td style="color:#63B300">CNDL</td>
                    </tr>
                    <tr>
                        <td><img src="../../img/no_pic_1.png" width="46" height="46"></td>
                        <td style=" color:#5F93D7">标题标题标题标题标题标题标题标题标题标题标题<br>
                            标题标题标题标题标题...</td>
                    </tr>
                    </tbody></table>--%>
            </td>
        </tr>
        <tr>
            <%--<td width="772" align="center" bgcolor="#F6F6F6" style="color:#2395F3">查看更多历史信息...</td>--%>
        </tr>
        <tr>
            <td>
                <div id="add" class="newbook" style="height: 350px;">
                    <p style="text-align: right;">
                    </p>
                <c:forEach items="${addMessage1}" var="addMessages">
                    <c:forEach items="${addMessages}" var="addMessage">
                    <c:if test="${addMessage.sender!='eBay'}">
                    <c:if test="${addMessage.sender==sender}">
                        <p class="user"></p>
                        <div class="user_co">
                            <div class="user_co_1"></div>
                            <ul><br/>
                                <c:if test="${addMessage.failnumber!=1000}">
                                    <span style="color:lightgray ">发送日期:<fmt:formatDate value="${addMessage.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>
                                </c:if>
                                <br/> ${addMessage.body}
                                <span></span>
                            </ul>
                            <div class="user_co_2"></div>
                        </div>
                        <div class="dpan"></div>
                    </c:if>
                    <c:if test="${addMessage.sender==recipient}">
                        <script type="text/javascript">
                             s="${addMessage.sender}";
                             r1="${addMessage.recipientid}";
                        </script>
                        <p class="admin"></p>

                        <div class="admin_co">
                            <div class="admin_co_1"></div>
                            <ul>
                                <br/>
                                <c:if test="${addMessage.failnumber!=1000}">
                                    <span style="color:lightgray ">发送日期:<fmt:formatDate value="${addMessage.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>
                                </c:if>
                                <br/>${addMessage.body}
                                <c:if test="${addMessage.replied=='true'}">
                                    <span></span>
                                </c:if>
                                <c:if test="${addMessage.replied=='false'}">
                                    <span style="color: red"></span>
                                </c:if>
                            </ul>
                            <div class="admin_co_2"></div>
                        </div>
                        <div class="dpan"></div>
                    </c:if>
                    </c:if>
                    <c:if test="${addMessage.sender=='eBay'}">
                        <p class="user"></p>
                        <div class="user_co">
                            <div class="user_co_1"></div>
                            <ul style="width: 620px;"><br/> ${addMessage.body}
                                <span></span>
                            </ul>
                            <div class="user_co_2"></div>
                        </div>
                        <div class="dpan"></div>
                    </c:if>
                    </c:forEach>
                </c:forEach>
                <%--<div class="newbook">
                <p style="text-align: right;">aasla_karih 2014-08-20 22:38</p>
                <p class="user">containyou，您好！</p>
                <div class="user_co">
                    <div class="user_co_1"></div>
                    <ul>Hi again.: )
                        <span>发送与:2014-08-20 22:38</span>
                    </ul>
                    <div class="user_co_2"></div>
                </div>--%>
                <%--<div class="dpan"></div>
                <p class="admin">携宇科技</p>

                <div class="admin_co">
                    <div class="admin_co_1"></div>
                    <ul>Hi again.: )
                        <span>发送与:2014-08-20 22:38</span>
                    </ul>
                    <div class="admin_co_2"></div>
                </div>
                <p class="user">containyou，您好！</p>
                <div class="user_co">
                    <div class="user_co_1"></div>
                    <ul>Hi again.: )
                        <span>发送与:2014-08-20 22:38</span>
                    </ul>
                    <div class="user_co_2"></div>
                </div>
                <div class="dpan"></div>
                <p class="admin">携宇科技</p>

                <div class="admin_co">
                    <div class="admin_co_1"></div>
                    <ul>Hi again.: )
                        <span>发送与:2014-08-20 22:38</span>
                    </ul>
                    <div class="admin_co_2"></div>
                </div>--%>
            </div></td>
        </tr>
        <tr>
            <td>
                <form id="sendForm">
                    <input type="hidden" name="orderid" value="${orderid}">
                    <input type="hidden" name="buyerid" value="${buyerid}">
                    <input type="hidden" name="subject" value="${message.subject}">
                    <input type="hidden" name="smtAcountId" value="${smtAcountId}">
                    <c:if test="${flag=='true'}">
                        <textarea name="body"  id="textarea" style="width:772px;color: #000000;" rows="5"  class="newco_one validate[required]"></textarea>
                    </c:if>
                    <c:if test="${flag=='false'}">
                        <textarea name="body"  id="textarea" style="width:1125px;color: #000000;" rows="5"   class="newco_one1 validate[required]"></textarea>
                    </c:if>

                </form>
                <div class="modal-footer" style="margin-top:20px; float:left; width:100%;">
                <%--<button type="button" class="btn btn-primary">保存</button>--%>
                <button type="button" class="btn btn-primary" onclick="selectSendMessage();">选择模板</button>
                <button type="button" class="btn btn-newco" onclick="submitCommit1();">回复</button>
                <button type="button" class="btn btn-default" onclick="dialogClose();" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="viewPrimevalMessage();" data-dismiss="modal">原始邮件</button>
                </div>
            </td>
        </tr>
        <tr>
            <td width="772"> </td>
        </tr>
        </tbody></table>

</div></div>


</div>
</body>
</html>
