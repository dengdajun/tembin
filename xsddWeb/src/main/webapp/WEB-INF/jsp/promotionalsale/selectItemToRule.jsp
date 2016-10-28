<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src=
            <c:url value="/js/promotionalsale/selectItemToRule.js"/>${jsfileVersion}></script>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/select2/mySelect2.js" /> ></script>
</head>
<body>
    <input type="hidden" name="ruleId" id="ruleId" value="${ruleId}"/>
    <input type="hidden" name="site" id="site" value="${site}"/>
    <input type="hidden" name="ebayAccount" id="ebayAccount" value="${ebayAccount}"/>
    <div style="height: 100%;padding-top: 20px;">
        <table width="100%">
            <tr>
                <td colspan="2">
                    <button type="button" class="net_put" onclick="apiSu();">确定</button>
                </td>
            </tr>
            <tr>
                <td width="50%">
                    <h1>已选择商品</h1>
                </td>
                <td width="50%">

                    <h1>未选择商品</h1>
                </td>
            </tr>
            <tr>
                <td width="50%">
                    &nbsp;
                </td>
                <td width="50%">
                    <div class="newsearch">
                        <span class="newusa_i">搜索内容：</span>
                    <span id="sleBG" style="width:82px;background-position: 67px 10px;">
                    <span id="sleHid1" style="width: 80px;">
                    <select name="selecttype" onchange="chageOldDom(this)" class="select"
                            style="color: #737FA7;width: 80px;float: left">
                        <option selected="selected" value="">选择类型</option>
                        <option value="sku">SKU</option>
                        <option value="title">物品标题</option>
                        <option value="item_id">物品号</option>
                    </select>
                    </span>
                    </span>
                    <span>
                        <input id="_selectvalue" type="text" style="width:260px;float: left;height: 26px;" multiple
                               class="form-control">
                        <input name="newbut" onclick="selectListingItemData()" type="button" class="key_2">
                    </span>
                    </div>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <div id="selectItem"></div>
                </td>
                <td valign="top">
                    <div id="item"></div>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
