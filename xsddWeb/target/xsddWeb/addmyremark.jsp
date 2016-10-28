<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/4/27
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script>
        var reason = '<%=request.getParameter("reason")%>';
    </script>
</head>
<body>
<div class='textarea' style="padding-top: 10px;">
    <span style="font-size: 12px;vertical-align: top;">备注：</span><textarea cols='30' rows='5' id='centents' ><%=request.getParameter("reason")%></textarea>
</div>
</body>
</html>
