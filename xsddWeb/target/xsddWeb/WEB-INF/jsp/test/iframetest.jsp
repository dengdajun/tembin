
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script>
        function onclicks(obj){
            /*var map=new Map();
            map.put("productManager","http://task.tembin.com:8080/xsddWeb/information/itemInformationList.do");
            map.put("picManager","http://task.tembin.com:8080/xsddWeb/information/pictureList.do");
            map.put("keyMove","http://task.tembin.com:8080/xsddWeb/keymove/userSelectSite.do");
            map.put("tempManager","http://task.tembin.com:8080/xsddWeb/moduleManager.do");
            map.put("docManager","http://task.tembin.com:8080/xsddWeb/itemManager.do");
            map.put("listingManager","http://task.tembin.com:8080/xsddWeb/listingdataManager.do");
            map.put("templateManager","http://task.tembin.com:8080/xsddWeb/TemplateInitTableList.do");
            map.put("autoManager","http://task.tembin.com:8080/xsddWeb/complement/complementManager.do");
            $("#mainFrame").attr("src",map.get($(obj).attr("name")));*/
            var url = "http://192.168.0.205:8080/xsddWeb/information/itemInformationList.do";
            if(obj=="productManager"){
                url = "http://192.168.0.205:8080/xsddWeb/information/itemInformationList.do";
            }else if(obj=="picManager"){
                url = "http://192.168.0.205:8080/xsddWeb/information/pictureList.do";
            }else if(obj=="keyMove"){
                url = "http://192.168.0.205:8080/xsddWeb/keymove/userSelectSite.do";
            }else if(obj=="tempManager"){
                url = "http://192.168.0.205:8080/xsddWeb/moduleManager.do";
            }else if(obj=="docManager"){
                url = "http://192.168.0.205:8080/xsddWeb/itemManager.do";
            }else if(obj=="listingManager"){
                url = "http://192.168.0.205:8080/xsddWeb/listingdataManager.do";
            }else if(obj=="templateManager"){
                url = "http://192.168.0.205:8080/xsddWeb/TemplateInitTableList.do";
            }else if(obj=="autoManager"){
                url = "http://192.168.0.205:8080/xsddWeb/complement/complementManager.do";
            }
            document.getElementById("mainFrame").src=url;

        }
    </script>
    <style>
        span{
            padding: 5px;
        }
    </style>
</head>
<body>
<div>
    <span><a href="javascript:void(0)" onclick="onclicks('productManager')" name="productManager">商品管理</a></span>
    <span><a href="javascript:void(0)" onclick="onclicks('picManager')" name="picManager">图片管理</a></span>
    <span><a href="javascript:void(0)" onclick="onclicks('keyMove')" name="keyMove">一键搬家</a></span>
    <span><a href="javascript:void(0)" onclick="onclicks('tempManager')" name="tempManager">模块管理</a></span>
    <span><a href="javascript:void(0)" onclick="onclicks('docManager')" name="docManager">范本管理</a></span>
    <span><a href="javascript:void(0)" onclick="onclicks('listingManager')" name="listingManager">在线商品</a></span>
    <span><a href="javascript:void(0)" onclick="onclicks('templateManager')" name="templateManager">刊登模板</a></span>
    <span><a href="javascript:void(0)" onclick="onclicks('autoManager')" name="autoManager">自动补数</a></span>
</div>
<div>
    <iframe id="mainFrame" target="_blank" src="" width="100%" height="1000px">

    </iframe>
</div>
</body>
</html>
