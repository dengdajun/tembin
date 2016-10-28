<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src=<c:url value ="/js/item/selectStoreCategory.js" /> ></script>
</head>
<body>
<div>
    <input type="hidden" name="ebayAccountId" id="ebayAccountId" value="${ebayAccountId}"/>
</div>
<div id="storeCategory"></div>
<b class="new_button"><a data-toggle="modal" href="javascript:void(0)" onclick="parentStore()">确定</a></b>

</body>
</html>
