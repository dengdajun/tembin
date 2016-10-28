<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        var orgId = '${orgId}';
    </script>
    <script type="text/javascript" src=
            <c:url value="/js/listingset/listingsetPage.js"/>${jsfileVersion}></script>
</head>
<body>
<div class="new_all" <%--style="width: "--%>>
    <div class="here">当前位置：首页 &gt; 刊登管理 &gt; <b>刊登设置</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <script type="text/javascript">
            var tableFlag = 0;
            function setTab(name, cursel, n) {
                for (i = 1; i <= n; i++) {
                    var menu = document.getElementById(name + i);
                    var con = document.getElementById("con_" + name + "_" + i);
                    if (i < 3 && i > 0) {
                        con = document.getElementById("con_" + name + "_" + 1);
                    }
                    menu.className = i == cursel ? "new_tab_1" : "new_tab_2";
                    if (i < 3 && i > 0) {
                        con.style.display = (1 == cursel || 2 == cursel) ? "block" : "none";
                    } else {
                        con.style.display = i == cursel ? "block" : "none";
                    }
                }
            }
        </script>

        <div class="new_tab_ls">
            <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,1)">拍买设置</dt>
        </div>
        <div class="Contentbox">
            <div>
                <div id="con_menu_1" style="display: block;">
                    <div id="ruleShow">
                        <form id="form1">
                            <table>
                                <tr style="height: 34px;">
                                    <td colspan="2">
                                        <input value="${orgId}" name="orgId" id="orgId" type="hidden"/>
                                        <input name="id" id="id" type="hidden"/>
                                        <input type="checkbox" name="autoListing" id="autoListing" value="0"/>开启结束拍卖后立即刊登</td>
                                </tr>
                                <tr style="height: 34px;">
                                    <td>开始时间：</td>
                                    <td><input name="startDate" style="height: 26px;"  class="form-control validate[required]" id="startDate" value="<fmt:formatDate value="${dis.disStarttime}" pattern="yyyy-MM-dd"/>" type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/></td>
                                </tr>
                                <tr style="height: 34px;">
                                    <td>结束时间：</td>
                                    <td><input name="endDate"  style="height: 26px;"  class="form-control validate[required]" id="endDate" value="<fmt:formatDate value="${dis.disEndtime}" pattern="yyyy-MM-dd"/>" type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/></td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <button type="button" class="net_put" onclick="saveListingSet();">确定</button>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
