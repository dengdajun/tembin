<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src=
            <c:url value="/js/promotionalsale/promotionalsaleManager.js"/>${jsfileVersion}></script>
</head>
<body>
<div class="new_all" <%--style="width: "--%>>
    <div class="here">当前位置：首页 &gt; 刊登管理 &gt; <b>促销管理</b></div>
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
            <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,1)">促销规则</dt>
<%--            <dt id="menu2" class="new_tab_2" onclick="setTab('menu',2,2)">商品到规则</dt>--%>
        </div>
        <div class="tbbay" id="showAddButton" style="background-image:url('');position: absolute;top: 60px;right: 40px;z-index: 10000;">
            <img src="../img/adds.png" onclick="addData()">
        </div>
        <div class="Contentbox">
            <div>
                <div id="con_menu_1" style="display: block;">
                    <div id="ruleShow"></div>
                </div>
<%--                <div id="con_menu_2" style="display: none;">
                    <div id="setShow"></div>
                </div>--%>
            </div>
        </div>
    </div>
</div>
</body>
</html>
