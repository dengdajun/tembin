e<%--
  Created by IntelliJ IDEA.
  User: wula
  Date: 2014/6/22
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>

    <script>
        function submitForm(obj){
            var name=$(obj).attr("name");
            var map=new Map();
            map.put("buyerId","/xsddWeb/BuyerRequirementDetailsList.do");
            map.put("itemAddr","/xsddWeb/ItemAddressList.do");
            map.put("payPal","/xsddWeb/PayPalList.do");
            map.put("returnpolicy","/xsddWeb/ReturnpolicyList.do");
            map.put("discountPriceInfo","/xsddWeb/discountPriceInfoList.do");
            map.put("descriptionDetails","/xsddWeb/DescriptionDetailsList.do");
            map.put("itemList","/xsddWeb/itemList.do");
            map.put("templateinittable","/xsddWeb/TemplateInitTableList.do");
            map.put("messagegetmymessage",path+"/message/MessageGetmymessageList.do");
            map.put("bindEbayAccount",path+"/user/bindEbayAccount.do");
            map.put("shipping",path+"/shippingDetailsList.do");
            map.put("orders",path+"/order/getOrdersList.do");
            map.put("orderItem",path+"/orderItem/orderItemList.do");
            map.put("listing",path+"/getListingItemList.do");
            map.put("usercases",path+"/userCases/userCasesList.do");
            map.put("itemInformation",path+"/information/itemInformationList.do");
            map.put("keymove",path+"/keymove/userSelectSite.do");
            map.put("itemInformationType",path+"/informationType/itemInformationTypeList.do");
            map.put("sendMessage",path+"/sendMessage/sendMessageList.do");
            map.put("autoSendMessage",path+"/sendMessage/autoSendMessageList.do");
            map.put("virtualSKU",path+"/virtualSKU/virtualSKUList.do");
            map.put("inventory",path+"/inventory/InventoryList.do");
            map.put("addalieproduct",path+"/alieproduct/addalieproduct.do");
            map.put("smtproductlist",path+"/alieproduct/smtproductlist.do");
            map.put("iframes",path+"/test/toIframe.do");
            top.location=map.get(name);
        }
        function abcd(obj){
            var urll=path+"/ajax/saveFeedBackAll.do";
            $().invoke(
                    urll,
                    {},
                    [function(m,r){
                        alert(r)
                    },
                        function(m,r){
                            alert(r)
                        }]

            );
        }

        function testSize(obj){
            var urll=path+"/ajax/getCountSize.do";
            $().invoke(
                    urll,
                    {"itemid":"221270069427"},
                    [function(m,r){
                        alert(r)
                    },
                        function(m,r){
                            alert(r)
                        }]

            );
        }
        var copyPage
        function copyMoreItem(objs){
            $().invoke(
                    path+"/ajax/selectEbayAccountMore.do",
                    {},
                    [function(m,r){
                        var data = r;
                        var optionStr = "";
                        for(var i=0;i<data.length;i++){
                            optionStr+="<option value='"+data[i].id+"'>"+data[i].ebayName+"</option>"
                        }
                        var tent = "<div>被复制账号<select id='startSelect' name='startSelect'>"+optionStr+"</select></div><br><div>复制到账号<select id='endSelect' name='endSelect'>"+optionStr+"</select></div>";
                        copyPage = openMyDialog({title: '备注',
                            content: tent,
                            icon: 'tips.gif',
                            width: 300,
                            button: [
                                {
                                    name: '确定',
                                    callback: function (iwins, enter) {
                                        var reason = "";
                                        var startAccount = $(iwins.parent.document.getElementById("startSelect")).val();
                                        var endAccount = $(iwins.parent.document.getElementById("endSelect")).val();
                                        if(startAccount==null||startAccount==undefined||endAccount==null||endAccount==undefined){
                                            alert("被复制账号跟复制到账号都必须选择！");
                                            return false;
                                        }
                                        var url = path + "/ajax/copyTradingItemtoEbayAccount.do?startAccount=" + startAccount + "&endAccount=" + endAccount;
                                        $().invoke(url, {},
                                                [function (m, r) {
                                                    alert(r);
                                                },
                                                    function (m, r) {
                                                        alert(r);
                                                    }],{isConverPage:false}
                                        );
                                    }
                                }
                            ]
                        });
                    },
                        function(m,r){
                            alert(r)
                        }]

            );
        }
    </script>
</head>
<body>
<form id="form" action="">
    ${ccc}llsss
        <div><input type="button" value="买家要求" name="buyerId" onclick="submitForm(this)"/></div>
        <div><input type="button" value="物品所在地" name="itemAddr" onclick="submitForm(this)"/></div>
        <div><input type="button" value="付款选项" name="payPal" onclick="submitForm(this)"/></div>
        <div><input type="button" value="退还选项" name="returnpolicy" onclick="submitForm(this)"/></div>
        <div><input type="button" value="折扣" name="discountPriceInfo" onclick="submitForm(this)"/></div>
        <div><input type="button" value="运输选项" name="shipping" onclick="submitForm(this)"/></div>
        <div><input type="button" value="卖家描述" name="descriptionDetails" onclick="submitForm(this)"/></div>
        <div><input type="button" value="范本列表" name="itemList" onclick="submitForm(this)"/></div>
        <div><input type="button" value="模板" name="templateinittable" onclick="submitForm(this)"/></div>
        <div><input type="button" value="接受消息" name="messagegetmymessage" onclick="submitForm(this)"/></div>
        <div><input type="button" value="绑定ebay帐号" name="bindEbayAccount" onclick="submitForm(this)"/></div>
        <div><input type="button" value="订单管理" name="orders" onclick="submitForm(this)"/></div>
        <div><input type="button" value="订单商品" name="orderItem" onclick="submitForm(this)"/></div>
        <div><input type="button" value="获取商品反反馈" name="abcds" onclick="abcd(this)"/></div>

        <div><input type="button" value="获取商品反馈数量" name="abcds地" onclick="testSize(this)"/></div>
        <div><input type="button" value="在线商品管理" name="listing" onclick="submitForm(this)"/></div>
        <div><input type="button" value="纠纷" name="usercases" onclick="submitForm(this)"/></div>
        <div><input type="button" value="产品信息" name="itemInformation" onclick="submitForm(this)"/></div>
        <div><input type="button" value="一键搬家" name="keymove" onclick="submitForm(this)"/></div>
        <div><input type="button" value="产品信息分类" name="itemInformationType" onclick="submitForm(this)"/></div>
        <div><input type="button" value="查看发送消息" name="sendMessage" onclick="submitForm(this)"/></div>
        <div><input type="button" value="查看自动发送消息" name="autoSendMessage" onclick="submitForm(this)"/></div>
        <div><input type="button" value="虚拟SKU" name="virtualSKU" onclick="submitForm(this)"/></div>
        <div><input type="button" value="库存" name="inventory" onclick="submitForm(this)"/></div>
        <div><input type="button" value="新增产品" name="addalieproduct" onclick="submitForm(this)"/></div>
        <div><input type="button" value="速卖通产品列表界面" name="smtproductlist" onclick="submitForm(this)"/></div>
        <div><input type="button" value="简板" name="iframes" onclick="submitForm(this)"/></div>
        <div><input type="button" value="复制多范本" onclick="copyMoreItem(this)"/></div>
</form>
</body>
</html>
