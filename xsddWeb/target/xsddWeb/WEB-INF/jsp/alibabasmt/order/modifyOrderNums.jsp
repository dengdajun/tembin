<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/11/17
  Time: 16:26
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
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            var url=path+"/SMTorder/apiModifyOrdersMessage.do";
            var data=$("#modifyOrderStatus").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable1();
                        W.OrderGetOrders.close();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }],{isConverPage:true}
            );
        }
        function closedialog(){
            W.OrderGetOrders.close();
        }
    </script>
</head>
<body>
<div class="modal-body">
    <form id="modifyOrderStatus" class="form-horizontal" role="form">

        <table width="100%" border="0" style="margin-left:20px;">
            <tbody>
            <tr>
                <td width="16%" height="28" align="center"><strong>买家账号</strong></td>
                <td width="16%" height="28" align="center"><strong>订单号</strong></td>
                <td width="16%" height="28" align="center"><strong>货运商</strong></td>
                <td width="16%" height="28" align="center"><strong>追踪号</strong></td>
            </tr>
            <c:forEach items="${orders}" var="order" begin="0" varStatus="status">
                <tr>
                    <td height="28" align="center">
                        <div class="newselect" style="margin-top:9px;width: auto;">
                                ${order.buyerloginid}
                        </div>
                    </td>
                    <td height="28" align="center">

                        <div class="newselect" style="margin-top:9px;width: auto;">
                            <input style="width: auto"  name="orderid${status.index}" value="${order.orderid}" class="form-controlsd" type="hidden">
                                ${order.orderid}
                        </div>
                    </td>
                    <td height="28" align="center">
                        <div class="newselect" style="margin-top:9px;width: auto;">
                            <input style="width: auto;margin-left: 40px;" name="ShippingCarrierUsed${status.index}" id="ShippingCarrierUsed${status.index}" class="form-controlsd" type="text">
                        </div>
                    </td>
                    <td height="28" align="center">
                        <div class="newselect" style="margin-top:9px;width: auto;">
                            <input style="width: auto;margin-left: 40px;" name="ShipmentTrackingNumber${status.index}" id="ShipmentTrackingNumber${status.index}" class="form-controlsd" type="text">
                        </div>
                    </td>
                </tr>
            </c:forEach>

           <%-- <tr>
                <td height="28" align="right">追踪号：</td>
                <td height="28"><div class="newselect" style="margin-top:9px;">
                    <input name="ShipmentTrackingNumber" id="ShipmentTrackingNumber" class="form-controlsd" type="text">
                </div></td>
                <td height="28">&nbsp;</td>
            </tr>
            <tr>
                <td height="28" align="right">货运商：</td>
                <td height="28"><div class="newselect" style="margin-top:9px;">
                    <input name="ShippingCarrierUsed" id="ShippingCarrierUsed" class="form-controlsd" type="text">
                </div></td>
                <td height="28">&nbsp;</td>
            </tr>--%>
            <tr>
                <td colspan="1" align="center">发货类型:</td>
                <td width="16%" height="28" align="center">
                    <input name="refund" id="allSend" onclick="" type="radio" value="all">all
                </td>
                <td width="16%" height="28" align="center">
                    <input name="refund" id="partSend" type="radio" value="part">part
                </td>

            </tr>
            <tr>
                <td height="28" align="right">&nbsp;</td>
                <td height="28" ></td>
                <td height="28"></td>
                <td height="28" style=" padding-top:22px;"><button type="button" class="net_put" onclick="submitCommit();">保存</button><button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></td>
            </tr>
            </tbody></table>

    </form>
</div>
</body>
</html>
