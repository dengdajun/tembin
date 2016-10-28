<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/3/17
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title>授权</title>
    <script type="text/javascript">
        function subm(){
            $("#suform").attr("action",path+"/authorize/redirectA.do")
            $("#suform").submit();
        }
    </script>
</head>
<body>
<form id="suform" method="post" action="">
    <input name="state" id="state" />
</form>
<button onclick="subm()">提交</button>
</body>
</html>
