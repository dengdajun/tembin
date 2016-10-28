<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <script type="text/javascript" src=<c:url value="/js/alibabasmt/product/addalieproduct.js"/>${jsfileVersion}></script>
    <script type="text/javascript" src=
            <c:url value="/js/batchAjaxUtil.js"/>></script>
    <link href=
          <c:url value="/js/gridly/css/style.css"/> rel='stylesheet' type='text/css'>
    <title></title>
    <script>
        var deliverytime = '${sp.deliverytime}';
        var wsvalidnum = '${sp.wsvalidnum}';
        var imageurls = '${sp.imageurls}';
        var productunit = '${sp.productunit}';
        var categoryid = '${sp.categoryid}';
        var property = '${property}';
        var lisku = '${lisku}';
    </script>
    <style>
        .form-control{
            height: 28px;
        }
        .mytable-striped tbody tr td {
            border-top: 1px solid #bfd9ff;
            border-top-width: 1px;
            border-top-style: solid;
            border-top-color: rgb(191, 217, 255);
        }
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div class="new_all">
    <form id="form" class="new_user_form">
        <div class="here">当前位置：首页 > 速卖通 > <b>产品编辑界面</b></div>
        <div class="a_bal"></div>
        <div class="new">
            <h1>分类信息</h1>
            <div style="float:inherit;width: 100%;">
                <div style="float:inherit;width: 100%;margin-left: 50px;">
                    <input type="hidden" name="categoryid" id="categoryid"/>
                    <input type="text" name="selectkeys" id="selectkeys"  style="width:400px;height: 28px;" class="form-control" size="100" >
                    <button class="net_put"  onclick="runPrice()">搜索</button>
                </div>
                <div id="selectcate" style="float:inherit;width: 100%;margin-left: 46px;"></div>
                <div style="float:inherit;width: 100%;margin-left: 50px;" id="showCategoryName"></div>
            </div>
            <h1>产品基本属性</h1>
            <div id="showAllAttr" style="float:inherit;width: 100%;margin-left: 50px;">

            </div>

            <li style="height: 35px;margin-bottom: 5px;margin-top: 5px;">
                <dt>物品标题</dt>
                <div class="new_left">
                    <input type="text" name="subject" id="subject" style="width:400px;"
                           class="validate[required,maxSize[80]] form-control" value="${sp.subject}" size="100"><span id="incount">0</span>/80
                </div>
            </li>
            <li style="height: 35px;margin-bottom: 5px;margin-top: 5px;">
                <dt>关键字</dt>
                <div class="new_left">
                    <input type="text" name="keyword" id="keyword" style="width:400px;"
                           class="validate[required,maxSize[80]] form-control" value="${sp.keyword}" size="100">
                </div>
            </li>
            <li style="height: 35px;margin-bottom: 5px;margin-top: 5px;">
                <dt>零售价</dt>
                <div class="new_left">
                    US $
                    <input type="text" name="productprice" id="productprice" style="width:200px;display:initial;float: none;"
                           class="validate[required] form-control" value="${sp.productprice}">/<span name="unit">包</span>
                </div>
            </li>
            <li style="height: 35px;margin-bottom: 5px;margin-top: 5px;">
                <dt>备货期</dt>
                <div class="new_left">
                    <select name="deliverytime">
                        <c:forEach var="num" begin="1" end="60">
                            <option value="${num}">${num}</option>
                        </c:forEach>
                    </select>天
                </div>
            </li>
            <li style="height: 35px;margin-bottom: 5px;margin-top: 5px;">
                <dt>产品有效期</dt>
                <div class="new_left">
                    <select name="wsvalidnum">
                        <c:forEach var="num" begin="1" end="30">
                            <option value="${num}">${num}</option>
                        </c:forEach>
                    </select>天
                </div>
            </li>
            <h1>
                商品图片
                <span style="font-weight: 100;padding-left: 30px;font-size: 12px;">
                    已选择图片：<span id="picNumber">0</span>张｜最多6张图片&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <span style="padding-left: 300px;color: darkorange;">上传图片像素小于500会自动删除！</span>
                </span>
            </h1>
            <div style="float:inherit;width: 100%;margin-left: 50px;">
                <section class='example' ><ul class='gbin1-list' style='padding-left: 20px;' id='showStaticPic'></ul></section>
                <script type=text/plain id='initSelectPic'></script>
                <div  style="float:inherit;width: 100%;">
                    <b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='upload' id='selectPic' pictype="mainPic" onclick='selectAliePic()'>选择图片</a></b>
                </div>
            </div>
            <div  style="float:inherit;width: 100%;">
                <div id="showPic" style="float:inherit;width: 100%;margin-left: 50px;"></div>
                <div id="moreAttrValue" style="float:inherit;width: 100%;margin-left: 50px;"></div>
                <div id="moreAttrPic" style="float:inherit;width: 100%;margin-left: 50px;"></div>
            </div>
            <h1>
                包装信息
            </h1>
            <li style="height: 35px;margin-bottom: 5px;margin-top: 5px;">
                <dt>产品单位</dt>
                <div class="new_left">
                    <select name="productunit" onchange="selectUnit(this)">
                        <option value="">---请选择---</option>
                        <c:forEach items="${unitList}" var="unit">
                            <option value="${unit.value}">${unit.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li style="height: 35px;margin-bottom: 5px;margin-top: 5px;">
                <dt>包装后的重量</dt>
                <div class="new_left">
                    <input type="text" name="grossweight" id="grossweight" style="width:400px;"
                           class="validate[required] form-control" value="${sp.grossweight}" size="100">KG/<span name="unit">包</span>
                </div>
            </li>
            <li style="height: 35px;margin-bottom: 5px;margin-top: 5px;">
                <dt>包装后的尺寸</dt>
                <div class="new_left">
                    <input type="text" name="packagelength" id="packagelength" style="width:80px;display:initial;float: none;" class="validate[required] form-control" value="${sp.packagelength}" size="100">*
                    <input type="text" name="packagewidth" id="packagewidth" style="width:80px;display:initial;float: none;" class="validate[required] form-control" value="${sp.packagewidth}" size="100">*
                    <input type="text" name="packageheight" id="packageheight" style="width:80px;display:initial;float: none;" class="validate[required] form-control" value="${sp.packageheight}" size="100">
                    <span>单位：厘米，每包1500cm3</span>
                </div>
            </li>
            <li  style="height: 50px;margin-bottom: 5px;margin-top: 5px;">
            </li>
        </div>
    </form>
</div>
<div id="new_view" class="new_view" style="position: fixed;bottom: 0px;overflow: visible" >
    <li><a href="javascript:void(0)" onclick="saveData('save')">保存产品</a></li>
    <li><a href="javascript:void(0)" onclick="closeWindow()">关闭</a></li>
</div>
</body>
</html>
