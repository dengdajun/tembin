<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/4
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src=
            <c:url value="/js/item/addItem2.js"/>></script>
    <link href=
          <c:url value="/js/gridly/css/style.css"/> rel='stylesheet' type='text/css'>
    <script src=
            <c:url value="/js/gridly/js/jquery.sortable.js"/> type='text/javascript'></script>
    <script type="text/javascript">
        var itemInformation;
        $(document).ready(function(){
            $("#ItemInformationListTable").initTable({
                url:path + "/information/ajax/loadItemInformationList.do?",
                columnData:[
                    {title:"选择",name:"option",width:"8%",align:"left",format:makeOption4},
                    {title:"图片",name:"pictureUrl",width:"8%",align:"left",format:makeOption2},
                    {title:"商品SKU",name:"sku",width:"8%",align:"left"},
                    {title:"商品名称",name:"name",width:"8%",align:"left"},
                    {title:"标签",name:"remark",width:"8%",align:"left"},
                    {title:"分类",name:"typeName",width:"8%",align:"left"},
                    {title:"状态",name:"pictureUrl",width:"8%",align:"left",format:makeOption3}
                ],
                selectDataNow:false,
                showIndex:false,
                isrowClick: true,
                rowClickMethod: function (obj,o){
                    $("input[type='radio'][name='information']").attr("checked",false);
                    $("input[type='radio'][name='information'][value='"+obj.id+"']").prop("checked",true);
                }
            });
            refreshTable();
        });
        function refreshTable(){
            $("#ItemInformationListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption2(json){
            var htm="<img src="+json.pictureUrl+" style=\" width: 50px;height: 50px; \">";
            return htm;
        }
        function makeOption3(json){
            if(json.pictureUrl){
                return "ok";
            }else{
                return "none";
            }
        }
        function makeOption4(json){
            var htm = "<input type=\"radio\"  name=\"information\" value=" + json.id + " />";
            htm+="<input type='hidden' id='productname_"+json.id+"' value='"+json.name+"'/>";
            htm+="<input type='hidden' id='productsku_"+json.id+"' value='"+json.sku+"'/>";
            return htm;
        }
        function okSelect(){
            var api = frameElement.api, W = api.opener;
            var val = $("input[type='radio'][name='information']:checked").val();
            if(val==null||val==""){
                alert("请选择产品");
                return;
            }
            var productname = $("#productname_"+val).val();
            var productsku = $("#productsku_"+val).val();
            W._sku = productsku;
            W.document.getElementById('itemName').value=productname;
            W.document.getElementById('sku').value=productsku;
            W.isShowPicLink();
            initDraug();
            var ss = new Array() ;
            var ahref = $(W.document.getElementById("showPics")).find("a");
            var j=0;
            for(var i = 0;i<ahref.length;i++){
                if(ahref[i].id!=null&&ahref[i].id!=""){
                    ss[j]=ahref[i].id.substr(ahref[i].id.indexOf("_")+1,ahref[i].id.length);
                    j++;
                }
            }
            var urll = path+"/information/ajax/getPicList.do?informationid="+val;
            $().invoke(
                    urll,
                    {},
                    [function (m, r) {
                        for(var js = 0;js<ss.length;js++){
                            var str = '';
                            for(var i =0;i< r.length;i++){
                                str += '<li><div style="position:relative"><input type="hidden" name="PictureDetails_'+ ss[js]+'.PictureURL" value="' + r[i].attrvalue + '">' +
                                        '<img src=' + r[i].attrvalue + ' height="80px" width="80px" />' +
                                        '<div style="text-align: right;background-color: dimgrey;"><img src="'+path+'/img/newpic_ico.png" onclick="removeThis(this)"></div></div>';
                                str += "</li>";
                            }
                            $(W.document.getElementById("picture_"+ss[js])).html(str);
                        }
                        W.Porduct.close();
                    },
                        function (m, r) {
                            alert(r);
                        }]
            );

        }
        function initDraug(){
            $('.gbin1-list').sortable().bind('sortupdate', function() {
                $('#msg').html('position changed').fadeIn(200).delay(1000).fadeOut(200);
            });
            return $('.gridly .delete').click(function (event) {
                event.preventDefault();
                event.stopPropagation();
                $(this).closest('.brick').remove();
                return $('.gridly').gridly('layout');
            });
        }
    </script>
</head>
<body>
<div style="height: 34px;"></div>
<input type="button" value="确定" onclick="okSelect()">
<div id="ItemInformationListTable"></div>
</body>

</html>
