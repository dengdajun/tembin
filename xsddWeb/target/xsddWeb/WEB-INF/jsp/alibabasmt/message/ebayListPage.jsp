<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/3/27
  Time: 11:19
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
    <script type="text/javascript" src=<c:url value ="/js/batchAjaxUtil.js" /> ></script>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function closedialog(){
            W.SMTMessage.close();
        }
        function sycOrder(){
            var checks=$("input[scope=checkbox]:checked");
            if(checks!=null&&checks.length>0){
                var ids="";
                for(var i=0;i<checks.length;i++){
                    if(i==(checks.length-1)){
                        ids+=$(checks[i]).val();
                    }else{
                        ids+=$(checks[i]).val()+",";
                    }
                }
                $("#id").val(ids);
                var data=$("#orderForm").serialize();
                var url = path + "/SMTmessage/ajax/sycMessage.do?";
                $().invoke(url,data,
                        [function (m, r) {
                            alert(r);
                           // W.refreshTable();
                            W.SMTMessage.close();
                            Base.token();
                        },
                            function (m, r) {
                                alert(r);
                                Base.token();
                            }]
                );
            }

        }
        function selectAllcheckBox(obj){
            var checks=$("input[scope=checkbox]");
            if(obj.checked){
                for(var i=0;i<checks.length;i++){
                    checks[i].checked=true;
                }
            }else{
                for(var i=0;i<checks.length;i++){
                    checks[i].checked=false;
                }
            }
        }
    </script>
</head>
<body>
<div class="modal-body">
    <form id="orderForm" class="form-horizontal" role="form">
        <input type="hidden" name="id" value="" id="id">
        <table>
            <tr>
                <td>请选择需要同步的ebay账号:</td>
                <td></td>
            </tr>
            <tr>
                <td ><input type="checkbox" name="checkbox" scope="allCheckbox" onchange="selectAllcheckBox(this);" > 全选</td>
                <td align="left">上次同步时间</td>
            </tr>
            <c:forEach items="${acounts}" var="acount" varStatus="status" begin="0">
                <tr>
                    <td><input type="checkbox" scope="checkbox" value="${acount.id}"/>${acount.smtAccountName}<br/></td>
                    <td></td>
                </tr>
            </c:forEach>
        </table>
    </form>
    <div class="modal-footer">
        <button type="button" class="net_put" onclick="sycOrder();">保存</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
    </div>
</div>
</body>
</html>

