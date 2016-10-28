<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/10
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<%--<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>--%>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/icons.css"/>"/>
    <script>
        $(document).ready(function() {
            keymoveerrorlist();
        });
        function keymoveerrorlist(){

            var urls = path + "/keymove/getKeyMoveErrorList.do";
            $("#errorList").initTable({
                url:urls,
                columnData:[
                    {title:"物品号",name:"itemId",width:"20%",align:"left"},
                    {title:"SKU",name:"sku",width:"20%",align:"left"},
                    {title:"账号",name:"paypalId",width:"20%",align:"left"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        }
        function  refreshTable(){
            var pressid = '${pressid}';
            var param={"pressid":pressid};
            $("#errorList").selectDataAfterSetParm(param);
        }
    </script>
    <style>
        .progress-label{
            float:left;
            margin-left:40%;
            margin-top:3px;
        }
    </style>
</head>
<body>
<div style="width: 100%">
    <div id="errorList">

    </div>
</div>
</body>
</html>
