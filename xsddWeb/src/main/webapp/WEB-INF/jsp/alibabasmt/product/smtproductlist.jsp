<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <script type="text/javascript" src=<c:url value="/js/alibabasmt/product/smtproductlist.js"/>${jsfileVersion}></script>
    <title></title>
</head>
<body>
<div class="new_all">
    <form id="form" class="new_user_form">
        <div class="here">当前位置：首页 > 速卖通 > <b>产品列表界面</b></div>
        <div class="a_bal"></div>
        <div id="selectWhere">

        </div>
        <div class="tbbay" id="showAddButton" style="background-image:url('');position: absolute;top: 30px;right: 60px;z-index: 10000;">
            <img src="../img/adds.png" onclick="addSmtProductPage()">
        </div>
        <div id="showData">

        </div>
    </form>
</div>
</body>
</html>
