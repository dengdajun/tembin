<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/3/4
  Time: 17:27
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
            var url=path+"/priceTracking/ajax/viewPricingTracking.do?sku=${sku}&ebayAcount=${ebayAcount}";
            $("#priceTrackingListTable").initTable({
                url:url,
                columnData:[
                    {title:"SKU",name:"sku",width:"20%",align:"center"},
                    {title:"物品号",name:"itemid1",width:"20%",align:"center"},
                    {title:"价格",name:"itemPrice1",width:"20%",align:"center",format:makeOption2},
                    {title:"竞争对手物品号",name:"itemid",width:"20%",align:"center"},
                    {title:"竞争对手价格",name:"currentprice",width:"20%",align:"center",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function makeOption2(json){
            return json.itemPrice1+" USD";
        }
        function makeOption1(json){
            return json.currentprice+" "+json.currencyid;
        }
        function refreshTable(){
            $("#priceTrackingListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
    </script>
</head>
<body>
<div id="priceTrackingListTable"></div>
</body>
</html>
