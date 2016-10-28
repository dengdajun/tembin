<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>

    </script>
    <style>
        div{
            font-size: 12px;
            padding-top: 4px;
            padding-bottom: 4px;
        }
    </style>
</head>
<body>
<div>
    <form>
        <div>应交金额：</div>
        <div>交费金额：<input id="totalFee" name="totalFee" type="text" value="" onkeypress='return inputNUMAndPoint(event,this,2)' style='border: 1px solid #cccccc;border-radius: 4px;'></div>
    </form>
</div>
</body>
</html>
