<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/2/11
  Time: 16:01
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
        $(document).ready(function(){
            $("#caseimg").attr("src",itemListIconUrl_+"${cases.itemid}.jpg");
        });
    </script>
</head>
<body>
<br/><br/>
<c:if test="${refundFlag=='true'}">
    <table  style="margin-left: 50px;">
        <tr><td colspan="4"><strong>Refund summary</strong></td></tr>
        <tr>
            <td width="20%" align="center"><img id="caseimg" src="" style='width: 50px;height:50px;'/></td>
            <td width="60%" align="center">${cases.itemtitle}</td>
            <td width="10%" align="center">${cases.casequantity}</td>
            <td width="10%" align="center">${cases.caseamount}</td>
        </tr>
    </table>
</c:if>

    <table  style="margin-left: 50px;">
        <tr >
            <td  colspan="3"><strong>Here's what happened</strong></td>
        </tr>
        <c:forEach items="${histories}" var="history">
            <c:if test="${history.authorrole=='SELLER'}">
                <tr>
                    <td width="10%">Seller</td>
                    <td width="30%">${history.creationdate}:</td>
                    <td width="60%">${history.activity}</td>
                </tr>
                <tr>
                    <td>Seller</td>
                    <td >Comments:</td>
                    <td>${history.note}</td>
                </tr>
                <tr><td colspan="3"><hr/></td></tr>
            </c:if>
            <c:if test="${history.authorrole=='BUYER'}">
                <tr>
                    <td width="10%">Buyer</td>
                    <td width="30%">${history.creationdate}:</td>
                    <td width="60%">${history.activity}</td>
                </tr>
                <tr>
                    <td>Buyer</td>
                    <td >Comments:</td>
                    <td >${history.note}</td>
                </tr>
                <tr><td colspan="3"><hr/></td></tr>
            </c:if>
            <c:if test="${history.authorrole=='EBAY'}">
                <tr>
                    <td width="10%">Ebay</td>
                    <td width="30%">${history.creationdate}:</td>
                    <td width="60%">${history.activity}</td>
                </tr>
                <tr>
                    <td>Ebay</td>
                    <td >Comments:</td>
                    <td >${history.note}</td>
                </tr>
                <tr><td colspan="3"><hr/></td></tr>
            </c:if>
        </c:forEach>
    </table>
</body>
</html>
