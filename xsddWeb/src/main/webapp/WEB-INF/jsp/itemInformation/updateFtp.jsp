<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/4/22
  Time: 17:20
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
            W.pictureManager.close();
        }
        function submitCommit(){
            if(!$("#updatePtpForm").validationEngine("validate")){
                return;
            }
            var a=confirm("确认修改密码");
            if(a==true) {
                var url = path + "/information/ajax/saveFtpPassword.do";
                var data = $("#updatePtpForm").serialize();
                $().invoke(url, data,
                        [function (m, r) {
                            alert(r);
                            W.pictureManager.close();
                            Base.token();
                        },
                            function (m, r) {
                                alert(r);
                                Base.token();
                            }]
                );
            }
        }
        $(document).ready(function(){
            $("#updatePtpForm").validationEngine();
        });
    </script>
</head>
<body>
<br/>
<form id="updatePtpForm">
    <table align="center">
        <tr>
            <td>密码:</td>
            <td><input name="password" id="mima" value=""  class="form-controlsd  validate[minSize[6],maxSize[20]]" /></td>
        </tr>
        <tr>
            <td>确认密码:</td>
            <td><input name="confirmPassword" id="querenmima" value=""  class="form-controlsd  validate[equals[mima]]"/></td>
        </tr>
        <tr>
            <td></td>
            <td align="right"> <br/><br/><button type="button" class="net_put" onclick="submitCommit();">保存</button>
                <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></td>
        </tr>
    </table>
</form>
</body>
</html>
