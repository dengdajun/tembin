<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/19
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title>系统账户管理</title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/icons.css"/>"/>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/user/sysusermanager.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/user/rolemanager.js" /> ></script>
    <script type="text/javascript">
        var t="${t}";
        var u="${u}";
    </script>
</head>
<body>
<div class="new_all">
<div class="here">当前位置：首页 > 系统设置 > <b>系统账户</b></div>
<div class="a_bal"></div>
<div class="new">

<div class="new_tab_ls">
    <dt style="display: none" id=menu1 class=new_tab_2 onmouseover="setTab('menu',1,5)">账户信息yy</dt>
    <dt id=menu3 class=new_tab_1 onclick="setTab('menu',3,7)">账户管理</dt>
    <dt id=menu4 class=new_tab_2 onclick="setTab('menu',4,7)">角色设置</dt>
    <dt id=menu5 class=new_tab_2 onclick="setTab('menu',5,7)" style="display: none">系统初始化</dt>
    <dt id=menu2 class=new_tab_2 onclick="setTab('menu',2,7)">修改密码</dt>
    <dt id=menu6 class=new_tab_2 onclick="setTab('menu',6,7)">账单管理</dt>
    <dt id=menu7 class=new_tab_2 onclick="setTab('menu',7,7)">交费记录</dt>
</div>
<div class=Contentbox>
<div>
<div id=con_menu_1 class=hover>

</div>



<div style="DISPLAY: none" id=con_menu_2>
    <form id="changePWDForm">
        <table width="100%" border="0" align="left" cellspacing="0" style="margin-top:20px;">
            <tr>
                <td width="10%" height="40" align="right">原始密码</td>
                <td width="82%" height="40"><input id="oldPWD" name="oldPWD" type="password" class="newps"></td>
            </tr>
            <tr>
                <td width="10%" height="40" align="right" >新密码</td>
                <td height="40"><input id="newPWD" name="newPWD" type="password" class="newps"></td>
            </tr>
            <tr>
                <td width="10%" height="40" align="right">确认密码</td>
                <td height="40"><input id="newPWD2" name="ps" type="password" class="newps"></td>
            </tr>
            <tr>
                <td height="40" align="right">&nbsp;</td>
                <td height="40"><input onclick="changePWDFun()" name="提交" type="button" class="net_put" value="保 存"></td>
            </tr>
        </table></form>
</div>
<div  id=con_menu_3>
    <div class="new_usa" style="margin-top:20px;">
        <div class="newds">
            <div class="newsj_left"><span class="newusa_ici_del_in"><input onclick="showStopAccount(this)" type="checkbox" name="checkbox" id="showStop" /></span>
                <span class="newusa_ici_open">显示已禁用的成员</span><div id="newtipi3">

                </div>
            </div><div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" onclick="openAdduserWindow()">添加成员</a></div>
        </div>
    </div>
    <div id="accountManager">

    </div>
</div>

<div style="DISPLAY: none" id=con_menu_4>
    <div class="new_usa" style="margin-top:20px;">
        <div class="newds">
            <div class="newsj_left">
            </div><div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" onclick="addRoleFun()" class="">添加角色</a></div>
        </div>
    </div>

    <div id="roleManager"></div>
</div>

<div style="DISPLAY: none" id=con_menu_5>
    <h2 style="margin-top:20px;">系统初始化功能将会把您账号下的所有信息全部清空，请谨慎操作。</h2>

    <form action="" method="get">
        <table width="100%" border="0" align="left" cellspacing="0" >
            <tr>
                <td width="10%" height="40" align="right" >请输入密码</td>
                <td height="40"><input name="newps" type="password" class="newps"></td>
            </tr>
            <tr>
                <td width="10%" height="40" align="right">再次输入密码</td>
                <td height="40"><input name="ps" type="password" class="newps"></td>
            </tr>
            <tr>
                <td width="10%" height="40" align="right"></td>
                <td height="40"><input name="" type="checkbox" value="" style="margin-left:12px;"> 我已了解风险</td>
            </tr>
            <tr>
                <td height="40" align="right">&nbsp;</td>
                <td height="40">
                    <input name="提交" type="submit" class="net_put" value="确认初始化"></td>
            </tr>
        </table></form>
</div>
<div style="DISPLAY: none" id=con_menu_6>
    <form action="" method="get">
        <div style="padding: 10px; padding-top: 20px;">余额：<span id="balanceFee" style="color: red;">0.00</span>￥.</div>
        <div style="padding: 10px;">子账号<span id="accountNumber">0</span>个；ebay账号<span id="ebayNumber">0</span>个，每月应交<span id="payFee">0.00</span>￥；上次充值<span id="upPayFee">0.00</span>￥；上次充值时间：<span id="startDate"></span></div>
        <div style="padding-left: 150px;padding-top: 20px;"><input onclick="renFee()" name="提交" type="button" class="net_put" value="续 费"></div>
    </form>
</div>
<div style="DISPLAY: none" id=con_menu_7>
    <div id="queryPayList">

    </div>
</div>
<!--结束 -->
</div>
</div>
</div>
</div>

</body>
</html>
