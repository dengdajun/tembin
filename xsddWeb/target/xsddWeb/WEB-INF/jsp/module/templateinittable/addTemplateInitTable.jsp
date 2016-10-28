<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/5
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>

    <script type="text/javascript">
        var imageUrlPrefix = '${imageUrlPrefix}';//图片前缀
    </script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/module/templateinittable/addTemplatePage.js" /> ></script>

    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/ajaxFileUpload/ajaxfileupload.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/ajaxfileupload.js" /> ></script>

</head>
<script  type="text/javascript">
    _sku="_template";
    var ue =UE.getEditor('myEditor',ueditorToolBar);


    var api = frameElement.api, W = api.opener;
    function submitCommit(){
        var content=ue.getContent();
        $("#templateHtml").val(ue.getContent());
        var url=path+"/ajax/saveTemplateInitTable.do";
        var data=$("#TemplateInitTableForm").serialize();
        $().invoke(url,data,
                [function(m,r){
                    alert(r);
                    W.refreshTable();
                    W.TemplateInitTable.close();
                    Base.token();
                },
                    function(m,r){
                        alert(r);
                        Base.token();
                    }]
        );
    }
    function downloadOrders(id){
        if(id&&id!=''){
            var url=path+"/downloadTemplateInitTable.do?id="+id;
            window.open(url);
        }else{
            alert("没有该模板!");
        }

    }
</script>
<body>
<br/>
<div style="height: 500px">
    <form id="TemplateInitTableForm">
        <input type="hidden" name="id" value="${TemplateInitTable.id}"/>
        <input type="hidden" name="templateHtml" id="templateHtml"/>
        <input type="hidden" name="templateViewUrl" id="templateViewUrl" value="${TemplateInitTable.templateViewUrl}" />
         <%--level:<input type="text" name="level" value="${TemplateInitTable.TLevel}"/>--%>

            <select name="level">
                <option value="1">用户自定义</option>
             </select>

        模板名字:<input type="text" name="templateName" value="${TemplateInitTable.templateName}"/>
    </form>
    选择图片:<input type="file" accept="image/*" id="multipartFiles" name="multipartFiles"/>

    &nbsp;<button onclick="uploadViewImg()" class="net_put">上传</button>


    <div id="editor">
        <div id="editor_toolbar_box">
            <div id="editor_toolbar">
                <%--<<input type="button" value="插入空白图片" onclick="ue.execCommand('blankimg')" style="height:24px;line-height:20px"/>
                &nbsp;span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;

                <input type="button" value="PaymentMethodTitle" onclick="ue.execCommand('paymentmethodtitle')" style="height:24px;line-height:20px"/>
                <input type="button" value="PaymentMethod" onclick="ue.execCommand('paymentmethod')" style="height:24px;line-height:20px"/>&nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;

                <input type="button" value="ShippingDetailTitle" onclick="ue.execCommand('shippingdetailtitle')" style="height:24px;line-height:20px"/>
                <input type="button" value="ShippingDetail" onclick="ue.execCommand('shippingdetail')" style="height:24px;line-height:20px"/>&nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;

                <input type="button" value="SalesPolicyTitle" onclick="ue.execCommand('salespolicytitle')" style="height:24px;line-height:20px"/>
                <input type="button" value="SalesPolicy" onclick="ue.execCommand('salespolicy')" style="height:24px;line-height:20px"/>&nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;

                <input type="button" value="AboutUsTitle" onclick="ue.execCommand('aboutustitle')" style="height:24px;line-height:20px"/>
                <input type="button" value="AboutUs" onclick="ue.execCommand('aboutus')" style="height:24px;line-height:20px"/>&nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;

                <input type="button" value="ContactUsTitle" onclick="ue.execCommand('ContactUsTitle')" style="height:24px;line-height:20px"/>
                <input type="button" value="ContactUs" onclick="ue.execCommand('contactus')" style="height:24px;line-height:20px"/>&nbsp;<span style="color: #ff0000;font-size: 24px;"><strong>|</strong></span>&nbsp;
            --%></div>
        </div>
        <script id="myEditor" type="text/plain" style="width:975px;height:400px;">如果需要重新编辑保存,请先下载模板后编辑,再将编辑后的html复制到编辑框中</script><%--${TemplateInitTable.templateHtml}--%>
        <table style="color: red" >
            <tr><td>{PaymentMethodTitle}:替换卖家描述中Payment所对应的标题</td><td>{PaymentMethod}:替换卖家描述中Payment所对应的内容</td></tr>
            <tr><td>{ShippingDetailTitle}:替换卖家描述中Shipping & Handing所对应的标题</td><td>{ShippingDetail}:替换卖家描述中Shipping & Handing所对应的内容</td></tr>
            <tr><td>{SalesPolicyTitle}:替换卖家描述中Guarantee所对应的标题</td><td>{SalesPolicy}:替换卖家描述中Guarantee所对应的标题</td></tr>
            <tr><td>{AboutUsTitle}:替换卖家描述中Feedback所对应的内容</td><td>{AboutUs}:替换卖家描述中Feedback所对应的内容</td></tr>
            <tr><td>{ContactUsTitle}: 替换卖家描述中Contact us所对应的标题</td><td>{ContactUs}:替换卖家描述中Contact us所对应的内容</td></tr>
            <tr><td>{ProductDetail}:替换刊登物品的描述内容</td><td>{templateTitle}:替换物品标题</td></tr>
            <tr><td colspan="2">图片替换:第一种方式:html中符合属性name="blankimg"和src是blank.jpg结尾的img标签;<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二种方式:html中符合属性src是{templateImage1}这种类型的img标签(比如src="{templateImage1}"、src="{templateImage2}"、src="{templateImage3}",依次类推)</td></tr>
        </table>
    </div>
<div style="bottom: 1px;">
    <input type="button" value="保存" class="net_put_1" onclick="submitCommit();"/>
    <input type="button" value="下载模板" class="net_put" onclick="downloadOrders(${TemplateInitTable.id});"/>
</div>

</div>

</body>
</html>
