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
        $(function(){
            $("#sendForm").validationEngine();
        });
        function submitCommit(){
            if(!$("#sendForm").validationEngine("validate")){
                return;
            }
            var url=path+"/SMTorder/apiGetOrdersSendMessage.do";
            var data=$("#sendForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
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
            W.OrderGetOrders.close();
        }
    </script>
</head>
<body>
<form id="sendForm">
    </br></br>
      <input type="hidden" name="orderid" value="${orderid}">
    <input type="hidden" name="smtAcountId" value="${smtAcountId}">
内容：<textarea id="body" name="body" class="validate[required]" style="width: 700px;height: 400px;"></textarea>

</form>
<div class="modal-footer">
    <div align="right" style="margin-right: 150px;">
    <button type="button" class="btn btn-newco" onclick="submitCommit();">发送</button>
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button>
    </div>
</div>
</body>
</html>
