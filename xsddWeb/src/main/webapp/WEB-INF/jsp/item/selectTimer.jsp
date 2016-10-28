<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            var val = $("#timerStr").val();
            if(val==""||val==null){
                alert("定时时间必须选择！");
                return ;
            }
            $(W.document).find("input[type='hidden'][name='timerListing']").val(val);
            if(W.document.location.href.indexOf("editItem.do")>0|| W.document.location.href.indexOf("addItem.do")>0){
                W.saveData("timeSave","timeSave");
            }else{
                W.saveDatas("timeSave","timeSave");
            }
            W.timerPage.close();
        }
        function returnhtml(){
            $("#innerTimer").show();
            var selectTimer = $("#timerStr").val();
            var d = new Date(Date.parse(selectTimer.replace(/-/g,"/")));
            d = d.valueOf()-3600000*15;
            d = new Date(d);
            var nowStr = d.format("yyyy-MM-dd hh:mm:ss");
            nowStr = nowStr.substring(0,10)+"T"+nowStr.substring(11)+".000Z";
            $("#Str").html(nowStr);
        }
    </script>
</head>
<body>
<table  style="width: 400px;margin-left: 100px;margin-top: 40px;">
    <tr>
        <td style="text-align: right;">北京时间：</td>
        <td>
            <input name="timerStr" id="timerStr" style="height: 24px;width: 150px" type="text" size="20"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-{%d}',onpicked:returnhtml})" class="form-control">
            <input type="button" value="确定" onclick="submitCommit()"/>
        </td>
    </tr>
    <tr id="innerTimer" style="display: none;">
        <td style="text-align: right;height: 34px;">国际时间：</td>
        <td id="Str">

        </td>
    </tr>
</table>
</body>
</html>
