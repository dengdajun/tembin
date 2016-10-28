<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.WEB-INF/jsp/commonImport.jsp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<%--<%@include file="/WEB-INF/jsp/smallFormImport.jsp" %>--%>
<html>
<head>
    <title></title>
    <script type="text/javascript" src=
            <c:url value="/js/promotionalsale/salerule.js"/>${jsfileVersion}></script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
        .selectboxit-container .selectboxit{
            width: 300px;
        }
        .selectboxit-options {
            width: 270px;
        }
    </style>
    <script>

    </script>
    <link href=
          <c:url value="/css/basecss/conter.css"/> type="text/css" rel="stylesheet"/>
</head>
<c:set value="${itemAddress}" var="item"/>
<body>

<%--<div class="modal-header">
    <div class="newtitle">物品所在地</div>
</div>--%>
<div class="modal-body">
    <form class="form-horizontal" role="form" id="Form">
        <table width="90%" border="0" style="margin-left:40px;">
            <tr style="height: 34px;">
                <td width="13%" height="28" align="right">名称：</td>
                <td width="87%" height="28">
                    <div class="newselect" style="margin-top:0px;">
                        <input type="text" name="promotionalsaleName" id="promotionalsaleName" style="width: 300px;" value="${item.name}" placeholder=""
                               class="form-control validate[required]">
                        <input value="" name="id" id="id" type="hidden">
                    </div>
                </td>
            </tr>
            <tr style="height: 34px;">
                <td width="20%" height="28" align="right">ebay账号：</td>
                <td width="87%" height="28">
                    <div class="newselect" style="margin-top:0px;">
                        <c:forEach items="${ebayList}" var="ebay">
                            <em style="color:#48a5f3"><input type="checkbox" name="ebayaccountid" class="validate[required]" value="${ebay.id}"
                                                             shortName="${ebay.ebayNameCode}">${fn:toUpperCase(ebay.ebayNameCode)}</em>
                        </c:forEach>
                    </div>
                </td>
            </tr>
            <tr style="height: 34px;">
                <td height="28" align="right">站点：</td>
                <td height="28">
                    <select name="siteid" data-size="8">
                        <c:forEach items="${siteList}" var="sites">
                            <option value="${sites.id}" curid="${sites.value1}">${sites.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr style="height: 34px;">
                <td width="13%" height="28" align="right">促销类型：</td>
                <td width="87%" height="28">
                    <div class="newselect" style="margin-top:0px;">
                        <select name="discounttype" class="validate[required]" onchange="selectOp(this)">
                            <option value="Price">降价</option>
                            <option value="Percentage">折扣</option>
                        </select>
                        <span id="priceid" style="color: red;">　*　卖价＝现价－所填写的值</span>
                        <span id="percentageid" style="display: none;color: red;">　*　卖价＝现价*(1-所填写的值/100)</span>
                    </div>
                </td>
            </tr>
            <tr style="height: 34px;">
                <td width="13%" height="28" align="right">值：</td>
                <td width="87%" height="28">
                    <div class="newselect" style="margin-top:0px;">
                        <input type="text" name="discountvalue" style="width: 300px;" id="discountvalue" onkeypress="return inputNUMAndPoint(event,this,2)" value="${item.postalcode}"
                               placeholder="" class="form-control validate[required]">
                    </div>
                </td>
            </tr>
            <tr>
                <td width="13%" height="28" align="right">开始时间：</td>
                <td width="87%" height="28"><div class="newselect">
                    <input name="promotionalsalestarttime"  class="form-control validate[required]" id="promotionalsalestarttime" value="<fmt:formatDate value="${dis.disStarttime}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </div></td>
            </tr>
            <tr>
                <td width="13%" height="28" align="right">结束时间：</td>
                <td width="87%" height="28"><div class="newselect">
                    <input name="promotionalsaleendtime"  class="form-control validate[required]" id="promotionalsaleendtime" value="<fmt:formatDate value="${dis.disEndtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </div></td>
            </tr>
            <tr style="height: 34px;">
                <td height="28" align="right"></td>
                <td height="28" style=" padding-top:22px;">
                    <button type="button" class="net_put" onclick="saveRule();">确定</button>
                    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closeRulePage()">关闭</button>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
