<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/21
  Time: 17:27
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
            if(!$("#sendForm").validationEngine("validate")){
                return;
            }
            var url=path+"/order/apiGetOrdersSendMessage.do?msource=sellerSend";
            var data=$("#sendForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.viewsendMessage.close();
                        W.viewsendMessage1.close();
                        W.OrderGetOrders.close();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function closedialog(){
            W.viewsendMessage.close();
            W.viewsendMessage1.close();
            W.OrderGetOrders.close();
        }
        $(document).ready(function(){
            $("#sendForm").validationEngine();
        });
    </script>
</head>
<body>
<form id="sendForm">
    </br></br>
<input type="hidden" name="itemid" value="${order.itemid}">
<input type="hidden" name="buyeruserid" value="${order.buyeruserid}">
    <input type="hidden" name="selleruserid" value="${order.selleruserid}">
    <input type="hidden" name="transactionid" value="${order.transactionid}">
    <input type="hidden" name="ebayId" value="${order.ebayId}">
    <div align="center" style="width: 90%">
主题：<input type="text" name="subject" style="width: 600px" class="validate[maxSize[80]]"/>
        <br/><br/>
内容：<textarea id="body" name="body" style="width: 600px;height: 300px;"></textarea>
    </div>

</form>
<div class="modal-footer" style="text-align: right">
    <button type="button" class="btn btn-newco" onclick="submitCommit();">发送</button>
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal" style="margin-right: 100px;" >关闭</button>
</div>
</body>
</html>
