<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/5/14
  Time: 15:47
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
        function submitCommit(){
            if(!$("#sendForm").validationEngine("validate")){
                return;
            }
            var url=path+"/sitemessage/sendMessage.do";
            var data=$("#sendForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.readMessageWindow.close();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function closedialog(){
            W.readMessageWindow.close();
        }
        $(document).ready(function(){
            $("#sendForm").validationEngine();
        });
    </script>
</head>
<body>
<form id="sendForm">
    <input type="hidden" name="messageFrom" value="${messageFrom}">
    <input type="hidden" name="messageTo" value="${messageTo}">
    <input type="hidden" name="messageId" value="${sitemessage.messageId}">
    <input type="hidden" name="subject" value="${sitemessage.messageTitle}">
    </br></br>
    <table align="center">
        <tr><td> <div id="add" class="newbook" style="height: 280px;width:772px;">
            <c:forEach items="${messages}" var="message">
                <c:if test="${message.messageFrom==loginId}">
                    <p class="admin"></p>
                    <div class="admin_co">
                        <div class="admin_co_1"></div>
                        <ul>
                            <span style="color:lightgray ">发件人:${message.fromName}</span><br/>
                            <span style="color:lightgray ">收件人:${message.toName}</span>
                                <span style="color:lightgray">发送日期:<fmt:formatDate value="${message.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>
                            <br/>&nbsp;&nbsp;&nbsp;&nbsp;${message.message}
                                <span></span>
                        </ul>
                        <div class="admin_co_2"></div>
                    </div>
                    <div class="dpan"></div>
                </c:if>
                <c:if test="${message.messageFrom!=loginId}">
                    <p class="user"></p>
                    <div class="user_co">
                        <div class="user_co_1"></div>
                        <ul>
                            <span style="color:lightgray ">发件人:${message.fromName}</span><br/>
                            <span style="color:lightgray ">收件人:${message.toName}</span>
                             <span style="color:lightgray ">发送日期:<fmt:formatDate value="${message.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>
                            <br/>&nbsp;&nbsp;&nbsp;&nbsp; ${message.message}
                            <span></span>
                        </ul>
                        <div class="user_co_2"></div>
                    </div>
                    <div class="dpan"></div>
                </c:if>
            </c:forEach>
        </div></td></tr>
        <tr><td><div align="center" style="width: 90%">
            <textarea name="body" id="body" style="width:772px;color: #000000;" rows="5" class="newco_one validate[required]"></textarea>
        </div></td></tr>
    </table>
</form>
<div class="modal-footer" style="text-align: right">
    <button type="button" class="btn btn-newco" onclick="submitCommit();">发送</button>
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal" style="margin-right: 100px;" >关闭</button>
</div>
</body>
</html>
