<%@ page import="java.net.URLDecoder" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/3/4
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
  String tempVal=URLDecoder.decode(request.getParameter("c"));
  String xx=new String(tempVal.getBytes("ISO-8859-1"),"utf-8");
%>
<%=xx%>

</body>
</html>
