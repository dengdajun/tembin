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
            var url=path+"/order/apiGetOrdersEvaluteSendMessage.do";
            var data=$("#sendForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.OrderSendEvaluateMessage.close();
                        /*W.viewsendMessage1.close();*/
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );

        }
    </script>
</head>
<body>
<form id="sendForm">
<input type="hidden" name="itemid" value="${order.itemid}">
<input type="hidden" name="buyeruserid" value="${order.buyeruserid}">
    <input type="hidden" name="selleruserid" value="${order.selleruserid}">
    <input type="hidden" name="transactionid" value="${order.transactionid}">
评论：<textarea  name="CommentText" style="width: 700px;height: 400px;"></textarea>
<input type="button" value="发送" onclick="submitCommit();"/>
</form>
</body>
</html>
