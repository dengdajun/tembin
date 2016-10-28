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
                        }]
            );
        }
        function changeStatus(){
            var shippStatus=$("#shippStatus").val();
            if(shippStatus=='true'){
                $("#ShipmentTrackingNumber").attr("disabled",false);
                $("#ShippingCarrierUsed").attr("disabled",false);
            }else{
                $("#ShipmentTrackingNumber").attr("disabled",true);
                $("#ShippingCarrierUsed").attr("disabled",true);
            }

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
                    <td width="16%" height="28" align="right">发货类型：</td>
                    <td width="16%" height="28" align="center">
                        <div  style="width:40%;float: left"><input name="refund" id="allSend" onclick="" type="radio" value="all">all</div>
                        <div style="width:40%;float: left"><input name="refund" id="partSend" type="radio" value="part">part</div>
                    </td>
                </tr>
                <tr>
                    <td width="16%" height="28" align="right">orderid：</td>
                    <td width="41%" height="28"><div class="newselect" style="margin-top:9px;">
                        <input name="orderid0" value="${order.orderid}" class="form-controlsd" type="text">
                    </div></td>
                    <td width="43%" height="28"></td>
                </tr>
                <tr>
                    <td height="28" align="right">追踪号：</td>
                    <td height="28"><div class="newselect" style="margin-top:9px;">
                        <input name="ShipmentTrackingNumber0" id="ShipmentTrackingNumber" class="form-controlsd" type="text">
                    </div></td>
                    <td height="28">&nbsp;</td>
                </tr>
                <tr>
                    <td height="28" align="right">货运商：</td>
                    <td height="28"><div class="newselect" style="margin-top:9px;">
                        <input name="ShippingCarrierUsed0" id="ShippingCarrierUsed" class="form-controlsd" type="text">
                    </div></td>
                    <td height="28">&nbsp;</td>
                </tr>
                <tr>
                    <td height="28" align="right">&nbsp;</td>
                    <td height="28" style=" padding-top:22px;"><button type="button" class="net_put" onclick="submitCommit();">保存</button><button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></td>
                    <td height="28">&nbsp;</td>
                </tr>
                </tbody></table>

        </form>
    </div>

<%--    <form id="modifyOrderStatus">
    <input type="hidden" name="selleruserid" value="${order.selleruserid}"/>
    <table id="statusTable">
        <tr>
            <td>TransactionID:</td>
            <td><input type="text" name="transactionid" value="${order.transactionid}" /></td>
        </tr>
        <tr>
            <td>ItemID:</td>
            <td><input type="text" name="itemid" value="${order.itemid}"/></td>
        </tr>
        <tr>
            <td>修改发货状态:</td>
            <td><select id="shippStatus" name="shippStatus" onchange="changeStatus();">
                <option value="true">true</option>
                <option value="false">false</option>
            </select></td>
        </tr>
        <tr>
            <td>追踪号:</td>
            <td><input type="text" name="ShipmentTrackingNumber" id="ShipmentTrackingNumber"/></td>
        </tr>
        <tr>
            <td>货运商:</td>
            <td><input type="text" name="ShippingCarrierUsed" id="ShippingCarrierUsed"/></td>
        </tr>
    </table>
    <input type="button" value="修改发货状态" onclick="submitCommit();"/>
</form>--%>
</body>
</html>
