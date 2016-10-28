<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/4/15
  Time: 14:29
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
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/select2/mySelect2.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jqzoom/jquery.jqzoom.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/batchAjaxUtil.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/zeroclipboard/dist/ZeroClipboard.min.js" /> ></script>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/ajaxFileUpload/ajaxfileupload.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/ajaxfileupload.js" /> ></script>
    <script type="text/javascript">
        var showPicMenuBS_=null;
        $(document).ready(function(){
            initSearchInput("");
            zeroclipInit();
            query1();
        });
        function query1(){
            var url="/information/ajax/loadItemInformationList2.do?";
            $("#pictureListTable").initTable({
                url:path +url ,
                columnData:[
                    {title:"",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                onlyFirstPage:true,
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                showDataNullMsgContext:'没有图片!',
                afterLoadTable:function(){
                    $("#afterTable").show();
                    $("#afterTable").html("");
                    $("#pictureListTable").find("table").hide();
                    var data = $("#pictureListTable").data("option").allData;
                    var html = "<br/><table style='margin-top: 20px;'  border='0' width='100%' align='left' cellspacing='0'>" +
                            "<tr><td id='mulu' colspan='5'><a href='javascript:void(0);' onclick='muluClick();' style='color: #0000ff'>${loginUser}</a></td></tr>" +
                            "<tr>";
                    if(data.length>0){
                        for(var i=0;i<data.length;i++){
                            if(i!=0&&i%5==0){
                                html+="</tr><tr>";
                            }
                            html+=" <td>" +
                                    "<div class='newsj_left' style='float:left;width:178px;height:180px;background:url("+"http://pic2.pbsrc.com/thumbnail/album/frame_square.png"+")'>" +
                                    "<img onerror='errorPic(this);' src='"+data[i].picUrl+"' style='width: 135px;height: 118px;margin-top: 16px;margin-left: 16px;'/><div style='width: 137px;margin-left: 15px;text-align: center'><a href='javascript:void(0);' style='color: #0000ff' onclick='pictureOnclick(\""+data[i].sku+"\");'>"+data[i].sku+"</a></div>"+
                                    "</div>"+
                                    "</td>";
                        }
                    }else{
                        html+="<td id='mulu' colspan='5' style='text-align: center'>没有查询到图片</td>";
                    }
                    html+="</tr></table>";
                    $("#afterTable").append(html);
                    Base.token();
                }
            });
            refreshTable();
        }
        function muluClick(){
            query1();
        }
        function pictureOnclick(sku){
            query2(sku);
        }
        function query2(sku){

            var type=$("#itemType").val();
            var content=sku;
            var url="/information/ajax/loadItemInformationList1.do?sku="+content;
            if(content==''){
                query1();
            }else {
                $("#pictureListTable").initTable({
                    url: path + url,
                    columnData: [
                        {title: "", name: "option1", width: "8%", align: "left", format: makeOption1}
                    ],
                    onlyFirstPage: true,
                    selectDataNow: false,
                    isrowClick: false,
                    showIndex: false,
                    showDataNullMsgContext: '没有图片!',
                    afterLoadTable: function () {
                        $("#afterTable").show();
                        $("#afterTable").html("");
                        $("#pictureListTable").find("table").hide();
                        var data = $("#pictureListTable").data("option").allData;
                        var html = "<table style='margin-top: 20px;' border='0' width='100%' align='left' cellspacing='0'>" +
                                "<tr><td id='mulu' colspan='5'><a href='javascript:void(0);' onclick='muluClick();' style='color: #000000'>${loginUser}</a>&gt;<span style='color: #0000ff'>" + content + "</span></td></tr>" +
                                "<tr id='picTable'>";
                        if (data.length > 0) {
                            for (var i = 0; i < data.length; i++) {
                                if (i != 0 && i % 5 == 0) {
                                    html += "</tr><tr>";
                                }
                                html += "<td style='float: left;width: 20px;margin-top: 55px;;margin-left: 40px;'><input name1='" + data[i]['big'] + "' name='" + data[i]['sku'] + "' type='checkbox' scop='pictrue'></td><td class=\"spic\" style=\"\">" +
                                        "<div id=\"vspic\">" +
                                        "<li bs=" + i + " id='" + "linkbutton" + i + "'><a style='text-align:left;' href=\"javascript:void(0)\"><img src=\"<c:url value ="/img/a1xl.png" />\" width=\"18\" height=\"18\"></a>" +
                                        "<ul id='picul" + i + "'style='margin-left:-160px;'>" +
                                        "<li style='line-height:25px;height: 25px;'><a style='height: 50px;' href=\"javascript:void(0)\" onclick=\"removeThis('" + data[i]['sku'] + "','" + data[i]['big'] + "','')\">删除</a></li>" +
                                        "<li style='display: none'><textarea style='display: none' id='" + "nowimg" + i + "'>${imageUrlPrefix}" + data[i]["loginId"] + "/" + data[i]["sku"] + "/" + data[i]["big"] + "</textarea></li>" +
                                        "<li style='line-height:55px;height: 50px;'><a style='' href=\"javascript:void(0)\" id='" + "doCopy" + i + "' data='" + i + "' data-clipboard-target='nowimg" + i + "' >复制链接</a></li>" +
                                        "</ul>" +
                                        "</li>" +
                                        "</div>" +
                                        "<div class=\"a1fd\"><a href=\"javascript:void(0)\" onclick=\"bigfont('${imageUrlPrefix}" + data[i]["loginId"] + "/" + data[i]["sku"] + "/" + data[i]["big"] + "')\"><img  src=\"<c:url value ="/img/a1fd.png" />\"></a></div>" +
                                        "<input type=\"hidden\" name=\"Picture\" value=\"${imageUrlPrefix}" + data[i]["loginId"] + "/" + data[i]["sku"] + "/" + data[i]["big"] + "\"><div class=\"jqzoom\" \"><img  scop='img' src=\"${imageUrlPrefix}" + data[i]["loginId"] + "/" + data[i]["sku"] + "/" + data[i]["small"] + "\" alt=\"\"   jqimg=\"${imageUrlPrefix}" + data[i]["loginId"] + "/" + data[i]["sku"] + "/" + data[i]["big"] + "\" width=\"120\" height=\"110\"></div></td>";

                            }

                        }else{
                            html+="<td id='mulu' colspan='5' style='text-align: center'>没有查询到图片</td>";
                        }
                        html += "</tr></table>";
                        $("#afterTable").append(html);
                        zeroclipInit();
                        Base.token();
                    }
                });
                refreshTable();
            }
        }
        function initSearchInput(bs){
            var _url="";
            var _map={};
            if(bs=='sku'){
                _url=path+"/informationType/ajax/loadOrgIdItemInformationList.do";
                _map={id:"sku",text:"sku"};
            }

            mySelect2I([{url:_url,doitAfterSelect:query,
                data:{currInputName:"content"},bs:"#content",multiple:false,maping:_map}]);
        }
        function query(){
            var type=$("#itemType").val();
            var content=$("#content").select2("data")!=null?$("#content").select2("data").text:"";
            var url="/information/ajax/loadItemInformationList1.do?";
            if(type=='sku'){
                url+="sku="+content;
            }
            if(content==''){
                query1();
            }else {
                $("#pictureListTable").initTable({
                    url: path + url,
                    columnData: [
                        {title: "", name: "option1", width: "8%", align: "left", format: makeOption1}
                    ],
                    onlyFirstPage: true,
                    selectDataNow: false,
                    isrowClick: false,
                    showIndex: false,
                    showDataNullMsgContext: '没有图片!',
                    afterLoadTable: function () {
                        $("#afterTable").show();
                     $("#afterTable").html("");
                        $("#pictureListTable").find("table").hide();
                        var data = $("#pictureListTable").data("option").allData;
                        var html = "<table style='margin-top: 20px;'  border='0' width='100%' align='left' cellspacing='0'>" +
                                "<tr><td id='mulu' colspan='5'><a href='javascript:void(0);' onclick='muluClick();' style='color: #000000'>${loginUser}</a>&gt;<span style='color: #0000ff'>" + content + "</span></td></tr>" +
                                "<tr id='picTable'>";
                        if (data.length > 0) {
                            for (var i = 0; i < data.length; i++) {
                                if (i != 0 && i % 5 == 0) {
                                    html += "</tr><tr>";
                                }
                                html += "<td style='float: left;width: 20px;margin-top: 55px;;margin-left: 40px;'><input name1='" + data[i]['big'] + "' name='" + data[i]['sku'] + "' type='checkbox' scop='pictrue'></td><td class=\"spic\" style=\"\">" +
                                        "<div id=\"vspic\">" +
                                        "<li bs=" + i + " id='" + "linkbutton" + i + "'><a style='text-align:left;' href=\"javascript:void(0)\"><img src=\"<c:url value ="/img/a1xl.png" />\" width=\"18\" height=\"18\"></a>" +
                                        "<ul id='picul" + i + "'style='margin-left:-160px;'>" +
                                        "<li style='line-height:25px;height: 25px;'><a style='height: 50px;' href=\"javascript:void(0)\" onclick=\"removeThis('" + data[i]['sku'] + "','" + data[i]['big'] + "','')\">删除</a></li>" +
                                        "<li style='display: none'><textarea style='display: none' id='" + "nowimg" + i + "'>${imageUrlPrefix}" + data[i]["loginId"] + "/" + data[i]["sku"] + "/" + data[i]["big"] + "</textarea></li>" +
                                        "<li style='line-height:55px;height: 50px;'><a style='' href=\"javascript:void(0)\" id='" + "doCopy" + i + "' data='" + i + "' data-clipboard-target='nowimg" + i + "' >复制链接</a></li>" +
                                        "</ul>" +
                                        "</li>" +
                                        "</div>" +
                                        "<div class=\"a1fd\"><a href=\"javascript:void(0)\" onclick=\"bigfont('${imageUrlPrefix}" + data[i]["loginId"] + "/" + data[i]["sku"] + "/" + data[i]["big"] + "')\"><img  src=\"<c:url value ="/img/a1fd.png" />\"></a></div>" +
                                        "<input type=\"hidden\" name=\"Picture\" value=\"${imageUrlPrefix}" + data[i]["loginId"] + "/" + data[i]["sku"] + "/" + data[i]["big"] + "\"><div class=\"jqzoom\" \"><img  scop='img' src=\"${imageUrlPrefix}" + data[i]["loginId"] + "/" + data[i]["sku"] + "/" + data[i]["small"] + "\" alt=\"\"   jqimg=\"${imageUrlPrefix}" + data[i]["loginId"] + "/" + data[i]["sku"] + "/" + data[i]["big"] + "\" width=\"120\" height=\"110\"></div></td>";

                            }

                        }else{
                            html+="<td id='mulu' colspan='5' style='text-align: center'>没有查询到图片</td>";
                        }
                        html += "</tr></table>";
                        $("#afterTable").append(html);
                        zeroclipInit();
                        Base.token();
                    }
                });
                refreshTable();
            }
        }
        function bigfont(id){
            var url=path+"/information/bigfont.do?pictureUrl="+id;
            itemInformation=openMyDialog({title: '',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                height:600,
                lock:true,
                zIndex:2000
            });
        }
        function removeThis(sku,pic,picUrl){
            var a=confirm("确认删除");
            if(a==true) {
                var url = path + "/information/ajax/deletePicture.do?";
                var data = {"sku": sku, "pic": pic, "picUrl": picUrl};
                $().invoke(url, data,
                        [function (m, r) {
                            alert(r);
                            refreshTable();
                            Base.token();
                        },
                            function (m, r) {
                                alert(r);
                                Base.token();
                            }]
                );
            }
        }
        function refreshTable(){
            $("#pictureListTable").selectDataAfterSetParm({});
        }
        function makeOption1(json){
            var hs="";
            hs+="<li style='height:25px' onclick=viewTemplateInitTable('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
            if(json.tLevel==1){
                hs+="<li style='height:25px' onclick=editTemplateInitTable('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
            }

            var pp={"liString":hs,"marginLeft":"-50px"};
            return getULSelect(pp);
        }
        function chageOldDom(obj,n){
            cleanInput();
            initSearchInput($(obj).val());
            $("input[id=content]").val('');
        }
        function cleanInput(){
            var inputs=$("input[class=key_2]");
            document.getElementById("content").value="";
        }
        function zeroclipInit(){
            $("a[id^='doCopy']").each(function(i,d){
                var clip = new ZeroClipboard($(d));

                clip.on("ready", function() {
                    // debugstr("Flash movie loaded and ready.");
                    this.on("aftercopy", function(event) {
                        // debugstr("Copied text to clipboard: " + event.data["text/plain"]);
                    });
                });

                clip.on("error", function(event) {
                    $(".demo-area").hide();
                    debugstr('error[name="' + event.name + '"]: ' + event.message);
                    ZeroClipboard.destroy();
                });
            });
            // jquery stuff (optional)
            function debugstr(text) {
                console.log(text);
            }
            $("li[id^='linkbutton']").on("mouseover",function(){
                var indexx=$(this).attr("bs");
                $(this).find("ul").show();
                showPicMenuBS_=indexx;
                var ooo=this;
                $(document).on("mouseover",function(){
                    var e = event || window.event;
                    var elem = e.srcElement||e.target;
                    if(elem.tagName=='IMG' || elem.tagName=='A' || elem.tagName=='OBJECT'){return;}
                    $(ooo).find("ul").hide();
                    //console.log(elem.tagName)
                    $(document).unbind("mouseover");
                });
            })
        }
        function chuLiPotoUrl(url){
            if(url.indexOf("img.tembin.com")>0){
                if(url!=null&&url!=""){
                    url = url.substr(0,url.lastIndexOf("."))+"_small"+url.substr(url.lastIndexOf("."));
                }

            }
            return url;
        }
        /**增加模板缩略图*/
        function uploadViewImg(){
            var content=$("#content").select2("data")!=null?$("#content").select2("data").text:"";
            if(content==null||content==''){
                alert("请输入SKU!");
                return;
            }
            var url=path+"/information/uploadViewImg.do?sku="+content;
            pictureManager=openMyDialog({title: '上传图片',
                content: 'url:'+url,
                icon: 'succeed',
                width:1000,
                height:850,
                close: function(event, ui) {
                    refreshTable();
                },
                lock:true
            });
            /*if($('#multipartFiles').val()==null || $('#multipartFiles').val()==''){
                alert('请选择图片');
                return;
            }
            $('#templateViewUrl').val('')
            var skuname=$("#content").select2("data")!=null?$("#content").select2("data").text:"";
            if(skuname==''){
                alert("请现在搜索框中输入需要上传的SKU!");
                return;
            }
            var files1=$(document).find("input[name=multipartFiles]");
            var fileElementIds=new Array();
            var files=[];
            for(var i=0;i<files1.length;i++){
                //fileElementIds.add($(files1[i]).attr("id"));
                files[i]=$(files1[i]);
            }
            $.ajaxFileUpload({
                //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
                url:path+'/information/upLoadImage.do?',
                secureuri:false,                       //是否启用安全提交,默认为false
                fileElementId:'multipartFiles',           //文件选择框的id属性
                files:files,//[$('#multipartFiles')]
                dataType:'text',                       //服务器返回的格式,可以是json或xml等
                data:{"sku":skuname},
                fileFilter:".jpg,.gif",
                fileSize:1000000,//1000是1k
                success:function(data, status){        //服务器响应成功时的处理函数
                    if(data=='nofile'){alert('没有选择文件');return;};
                    if(data=='nosku'){alert('SKU为空!');return;};
                    refreshTable();
                },
                error:function(data, status, e){ //服务器响应失败时的处理函数
                    alert('图片上传失败，请重试！！');
                }

            });*/
        }
    function selectAllCheck(obj){
        var checkboxs=$(document).find("input[ scop=pictrue]");
        if(obj.checked){
            for(var j=0;j<checkboxs.length;j++){
                checkboxs[j].checked=true;
            }
        }else{
            for(var j=0;j<checkboxs.length;j++){
                checkboxs[j].checked=false;
            }
        }
    }
    function deletePictures(){
        if(a==true) {
            var checkboxs = $(document).find("input[ scop=pictrue]:checked");
            var sku = $("#content").select2("data") != null ? $("#content").select2("data").text : "";
            var pic = "";
            for (var j = 0; j < checkboxs.length; j++) {
                var name1 = $(checkboxs[j]).attr("name1");
                sku = $(checkboxs[j]).attr("name");
                if (j == (checkboxs.length - 1)) {
                    pic = pic + name1;
                } else {
                    pic = pic + name1 + ",";
                }
            }
            removeThis(sku, pic, "");
        }
    }
    function addMultipartFile(obj){
        var input=$(document).find("input[name=multipartFiles]");
        var clone=$(input[0]).clone();
        $(clone).attr("id","multipartFiles"+(input.length+1));
        if((input.length+1)<=4){
            $(input[input.length-1]).after(clone);
        }else{
            var yu=(input.length+1)%4==0?(input.length+1)/4:parseInt((input.length+1)/4)+1;
            var spanfiles=$("#spanfiles"+(yu-1));
            if(spanfiles&&spanfiles.length>0){
                var spanfile=$("#spanfile"+(yu-1));
                $(spanfile).append(clone);
            }else{
                var span="<li id='spanfiles"+(yu-1)+"'><span   class='newusa_i' style='width: 75px;'></span><span id='spanfile"+(yu-1)+"' style='float: left'></span></li>";
                if((yu==2)){
                    $("#spanfiles").after(span);
                    var span1=$(document).find("span[id=spanfile"+(yu-1)+"]");
                    console.debug(span1);
                    $(span1).append(clone);
                }else{
                    $("#spanfiles"+(yu-2)).after(span);
                    var span1=$(document).find("span[id=spanfile"+(yu-1)+"]");
                    console.debug(span1);
                    $(span1).append(clone);
                }
            }

        }

    }
        var pictureManager;
    function updateFtp(){
        var url=path+"/information/updateFtp.do?";
        pictureManager=openMyDialog({title: '修改FTP密码',
            content: 'url:'+url,
            icon: 'succeed',
            width:600,
            lock:true
        });
    }
    function errorPic(obj){
        $(obj).attr("src","http://img.tembin.com/blank.jpg");
    }
    </script>
</head>
<body>
<div class="new_all" <%--style="width: "--%>>
    <input type="hidden" name="templateViewUrl" id="templateViewUrl" value="" />
    <div class="here">当前位置：首页 &gt; 商品管理 &gt; <b>图片管理</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <script type="text/javascript">
            function setTab(name,cursel,n){
                for(i=1;i<=n;i++){
                    var menu=document.getElementById(name+i);
                    var con=document.getElementById("con_"+name+"_"+i);
                    menu.className=i==cursel?"new_tab_1":"new_tab_2";
                    con.style.display=i==cursel?"block":"none";

                }
            }
        </script>

        <div class="new_tab_ls">
            <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,1)">图片管理</dt>
            <%--<dt id="menu2" class="new_tab_2" onclick="setTab('menu',2,5)">商品分类列表</dt>--%>
            <%--<dt id="menu2" class="new_tab_2" onclick="setTab('menu',3,5)">虚拟sku</dt>--%>
        </div>
        <div class="Contentbox">
            <div>
                <div id="con_menu_1" style="display: block;">
                    <!--综合开始 -->
                    <div class="new_usa" style="margin-top:20px;">
                     <%--  <li id="spanfiles"><span  class="newusa_i" style="width: 75px;">选择图片:</span><span id="spanfile" style="float: left"><input type="file" accept="image/*" id="multipartFiles" name="multipartFiles"/><a href="javascript:void(0)" onclick="addMultipartFile(this)"><img title="添加多个上传图片路径" src="/xsddWeb/img/adds.png"/></a> </span></li>
                 --%> <%--      &nbsp;<span style="float: left">SKU:</span><input type="text" id="skuname" class="form-controlsd" style="width: 100px;">--%>
                        <div class="newsearch">
                            <span class="newusa_i" style="width: 75px;">搜索内容：</span>

<span id="sleBG">
<span id="sleHid">
<select name="type"  style="color: #737FA7;width: 85px;float: left" id="itemType" onchange="chageOldDom(this)" >
    <option selected="selected" >选择类型</option>
    <option value="sku">SKU</option>
</select>
</span>
</span>
                            <div class="vsearch">
                                <input id="content" name="" type="text"   style="width:200px;float: left"  multiple class="multiSelect"><input name="newbut" style="display: none;" onclick="query();" type="button" class="key_2"></div>
                        </div>
                        <div class="newds">
                            <div class="newsj_left">
                                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="allCheckbox" onclick="selectAllCheck(this);"></span>
                                <span onclick="deletePictures();" class="newusa_ici_del">批量删除</span>
                                <span onclick="uploadViewImg();" class="newusa_ici_del">上传图片</span>
                                <span onclick="updateFtp();" class="newusa_ici_del">修改FTP密码</span>
                            </div>

                         </div>
                            <input type="hidden" id="remarkForm"/>
                            <input type="hidden" id="informationForm"/>
                            <input type="hidden" id="conmmentForm">
                        </div>
                    </div>
                    <div id="afterTable"></div>

                    <div style="width: 100%;float: left;height: 5px"></div>
                    <div id="pictureListTable"></div>
                    <!--综合结束 -->
                </div>
                <!--结束 -->
            </div>
        </div>
    </div>
</div>
</body>
</html>
