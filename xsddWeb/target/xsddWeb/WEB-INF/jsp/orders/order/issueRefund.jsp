<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/24
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <style>
        .table-a table{border:1px solid rgba(0, 0, 0, 0.23)
        }
    </style>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function closedialog(){
            W.viewsendMessage.close();
        }
        var amountpaid='${order.amountpaid}';
        function submit3(){
            var url=path+"/order/sendRefund.do?";
            var data=$("#sendRefund").serialize();
            var a=confirm("确认退款");
            if(a==true){
                $().invoke(url,data,
                        [function(m,r){
                            alert(r);
                            W.viewsendMessage.close();
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }
        }

        /**绑定验证事件*/
        function bindVailEvent(){
            $("#amout").bind({
                keypress:function(){return inputNUMAndPoint(event,this,2);} ,
                blur:function(){
                    if($(this).val()=='' || !isNaN($(this).val())){}else{
                        $(this).val(0);
                        alert('费用请输入数字!');
                        $(this).focus();
                    }
                    return false;
                }
            });
        }
        function changeImg(){
            $("#capImg").hide().attr('src', path+'/orderRefundCaptchaAction.do?x=' + Math.floor(Math.random()*100) ).fadeIn();
        }
        $(document).ready(
                function(){
                    bindVailEvent();
                }
        );
    </script>
</head>
<body>
<div style="padding: 30px;">
<form id="sendRefund">
    <input type="hidden" name="transactionid" value="${order.transactionid}">
    <input type="hidden" name="ebayId" value="${order.ebayId}">
    <input type="hidden" name="itemId" value="${order.itemid}">
<table width="100%" border="0">
    <tbody><tr>
        <td height="32" align="left">
            <input name="refund" id="fullRefund" onclick="" type="radio" value="1"> Issue a full refund
            <input name="refund" id="partialRefund" type="radio" value="2">
            Issue a partial refund
        </td>
    </tr>
    <tr>
        <td height="32" align="left">
            <input type="text" id="amout" name="amout" class="form-controlsd" >
                <select name="currency" >
                    <c:forEach items="${currencys}" var="sites">
                        <option  <c:if test="${sites.value1==nowurrency}" >selected="selected"</c:if> value="${sites.value1}" >${sites.value1}</option>
                    </c:forEach>
            </select>
            填写退款金额
        </td>
    </tr>
    <tr>
        <td height="32" align="left">
            <div><input type="text" id="valiStr" name="valiStr" class="form-controlsd"  >验证码</div>
            <br><br><div><img id="capImg"  onclick="changeImg(this)" src="" alt="点击获取验证码"/></div>
        </td>
    </tr>
    </tbody></table>
</form>
</div>
<div class="modal-footer">
    <button type="button" class="net_put" onclick="submit3();">保存</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
<%--    <button type="button" class="btn btn-newco" onclick="submit3();">确定</button>
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button>--%>

</div>
<table width="100%" border="0">
    <tbody><tr>
        <td height="32" align="left">
            <c:forEach items="${refunds}" var="refund">
                <span style="color: red;margin-left: 30px;">${refund.refunddesc}</span><br/>
            </c:forEach>
        </td>
    </tr>
    </tbody></table>

</body>
</html>
