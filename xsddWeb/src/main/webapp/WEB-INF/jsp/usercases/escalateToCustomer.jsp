<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/2/10
  Time: 10:49
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
        function closedialog(){
            W.escalate.close();
        }
    </script>
</head>
<body>
<br/><br/>

    <table>
    <c:if test="${flag=='true'}">
        <tr>
            <td width="20%"></td>
            <td width="40%"><strong style="font-size: 15px;">The case is pending</strong></td>
            <td width="40%"></td>
        </tr>
        <tr>
            <td></td>
            <td><hr/></td>
            <td><hr/></td>
        </tr>
        <tr>
            <td></td>
            <td colspan="2"><div  style="margin-left: 20px"><strong>Hang on,we're still reviewing your case</strong><br/>We'll be in touch with you soon</div></td>
        </tr>
        <tr>
            <td></td>
            <td><hr/></td>
            <td><hr/></td>
        </tr>
        <tr>
            <td></td>
            <td><div style="margin-left: 20px">Case ID:</div></td>
            <td>${cases.caseid}</td>
        </tr>
        <tr>
            <td></td>
            <td><div style="margin-left: 20px">Buyer ID:</div></td>
            <td>${cases.buyerid}</td>
        </tr>
        <tr>
            <td></td>
            <td><div style="margin-left: 20px">Request reason:</div></td>
            <td>${EBPcase.openreason}</td>
        </tr>
        <tr>
            <td></td>
            <td ><div style="margin-left: 20px">Case reason:</div></td>
            <td>
                <c:if test="${type=='ITEM_SHIPPED_WITH_TRACKING'}">
                    I refunded the buyer,but the buyer isn't satisfied
                </c:if>
                <c:if test="${type=='TROUBLE_COMMUNICATION_WITH_BUYER'}">
                    I'm having trouble communicating with the buyer
                </c:if>
                <c:if test="${type=='ITEM_SHIPPED_WITH_TRACKING'}">
                    I shipped the item with tracking details
                </c:if>
                <c:if test="${type=='OTHER'}">
                    Other
                </c:if>
            </td>
        </tr>
    </c:if>
    <c:if test="${flag=='false'}">
        <tr>
            <td width="20%"></td>
            <td width="40%">提交失败原因:</td>
            <td width="40%">${message}</td>
        </tr>
    </c:if>
    </table>


<br/>
<div style="bottom: 1px;margin-right:100px;" align="right">
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button></div>
</body>
</html>
