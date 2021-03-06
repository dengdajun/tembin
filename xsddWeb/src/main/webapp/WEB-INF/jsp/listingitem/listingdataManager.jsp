<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/23
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
        .addHover:hover{
            text-decoration:underline
        }
    </style>
    <title></title>

    <script>
        var loadurl=path+"/ajax/ListingItemList.do?1=1";
        var maplisting = new Map();
        maplisting.put("listing", path + "/ajax/ListingItemList.do?1=1");
        maplisting.put("sold", path + "/ajax/ListingItemList.do?flag=1");
        maplisting.put("unsold", path + "/ajax/ListingItemList.do?flag=2");
        maplisting.put("updatelog", path + "/ajax/getListItemDataAmend.do?1=1");
        var nameFolder = "listing";
        function setTab(obj) {
            nameFolder = $(obj).attr("name");
            $(obj).parent().find("dt").each(function (i, d) {
                if ($(d).attr("name") == nameFolder) {
                    $(d).attr("class", "new_tab_1");
                } else {
                    $(d).attr("class", "new_tab_2");
                }
            });
            if(nameFolder=="updatelog"){
                $("#amendlog").show();
                $("div .newds").hide();
                $("div .newsearch").css({"margin-bottom":"0px"});
                $("[name='attrshow']").each(function(i,d){
                    $(d).hide();
                });

            }else{
                $("#amendlog").hide();
                $("div .newds").show();
                $("div .newsearch").css({"margin-bottom":"20px"});
                $("[name='attrshow']").each(function(i,d){
                    $(d).show();
                });
            }
            $("a").each(function(i,d){
                if($(d).find("span").text()=="全部"){
                    $(d).find("span").attr("class","newusa_ici");
                }else if($(d).find("span").text()!=""){
                    $(d).find("span").attr("class","newusa_ici_1");
                }
            });
            if(nameFolder.indexOf("myFolder_")==-1){
                loadurl=maplisting.get(nameFolder);
            }else{
                loadurl=path + "/ajax/ListingItemList.do?1=1&folderid="+$(obj).attr("val");
            }
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }

        }

        function itemstatus(json){
            var htm = "";
            if(json.endisflag=="1"){
                htm="<img src='"+path+"/img/new_yes.png'>";
            }else{
                htm="<img src='"+path+"/img/new_no.png'>";
            }
            return htm;
        }
        function amendIsFlag(json){
            var htm="";
            if(json.endisflag=="1"){
                htm="";
            }else{
                if(json.endid!=null&&json.endid!=""){
                    htm="<a onclick=continueWork('"+json.id+"','"+json.endid+"') >继续处理</a>";
                }
            }
            return htm;
        }
        function continueWork(id,endid){
            var url = path + "/ajax/continueWork.do?id=" + id+"&&endid="+endid;
            $().invoke(url, {},
                    [function (m, r) {
                        alert(r);
                        Base.token();
                        onloadTableamend(loadurl);
                    },
                        function (m, r) {
                            alert(r);
                            Base.token();
                            onloadTableamend(loadurl);
                        }],{isConverPage:true}
            );
        }
        function getSiteImg(json){
            var html='<img title="'+json.siteName+'" src="'+path+'/img/'+json.site+'.jpg"/>';
            return html;
        }
        function onloadTableamend(urls){
            $("#itemListingTable").initTable({
                url:urls,
                columnData:[
                    {title:"图片",name:"Option1",width:"4%",align:"left",format:picUrl},
                    {title:"物品标题",name:"title",width:"8%",align:"left"},
                    {title:"SKU",name:"sku",width:"8%",align:"center"},
                    {title:"ebay账户",name:"ebayAccount",width:"8%",align:"left"},
                    {title:"站点",name:"site",width:"4%",align:"center",format:getSiteImg},
                    {title:"刊登类型",name:"listingType",width:"6%",align:"center",format:listingType},
                    {title:"价格",name:"price",width:"8%",align:"center",format:getPriceHtml},
                    {title:"数量/已售",name:"price",width:"8%",align:"center",format:tjCount},
                    {title:"修改时间",name:"amendTime",width:"8%",align:"left"},
                    {title:"操作内容",name:"content",width:"8%",align:"left"},
                    {title:"状态",name:"content",width:"4%",align:"left",format:itemstatus},
                    {title:"操作",name:"Option1",width:"8%",align:"left",format:amendIsFlag}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                isrowClick: true,
                rowClickMethod: function (obj,o){
                    if($(event.target).prop("type")=="checkbox"){
                        return;
                    }
                    if($("input[type='checkbox'][name='listingitemid'][val='"+obj.id+"']").prop("checked")){
                        $("input[type='checkbox'][name='listingitemid'][val='"+obj.id+"']").prop("checked",false);
                    }else{
                        $("input[type='checkbox'][name='listingitemid'][val='"+obj.id+"']").prop("checked",true);
                    }
                }
            });
            refreshTable();
        }

        function selectCounty(obj){
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");

            if(loadurl.indexOf("&county=")!=-1){
                if(loadurl.substr(loadurl.indexOf("county=")).indexOf("&")!=-1){
                    var str = loadurl.substr(loadurl.indexOf("county="));
                    loadurl = loadurl.substr(0,loadurl.indexOf("&county="))+str.substr(str.indexOf("&"))+"&county="+$(obj).attr("value");
                }else{
                    loadurl = loadurl.substr(0,loadurl.indexOf("&county="))+"&county="+$(obj).attr("value");
                }
            }else{
                loadurl = loadurl+"&county="+$(obj).attr("value");
            }
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }
        function selectListType(obj){
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");

            if(loadurl.indexOf("&listingtype=")!=-1){
                if(loadurl.substr(loadurl.indexOf("listingtype=")).indexOf("&")!=-1){
                    var str = loadurl.substr(loadurl.indexOf("listingtype="));
                    loadurl = loadurl.substr(0,loadurl.indexOf("&listingtype="))+str.substr(str.indexOf("&"))+"&listingtype="+$(obj).attr("value");
                }else{
                    loadurl = loadurl.substr(0,loadurl.indexOf("&listingtype="))+"&listingtype="+$(obj).attr("value");
                }
            }else{
                loadurl = loadurl+"&listingtype="+$(obj).attr("value");
            }
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }
        function selectEbayAccount(obj){
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");
            if(loadurl.indexOf("&ebayaccount=")!=-1){
                if(loadurl.substr(loadurl.indexOf("ebayaccount=")).indexOf("&")!=-1){
                    var str = loadurl.substr(loadurl.indexOf("ebayaccount="));
                    loadurl = loadurl.substr(0,loadurl.indexOf("&ebayaccount="))+str.substr(str.indexOf("&"))+"&ebayaccount="+$(obj).attr("value");
                }else{
                    loadurl = loadurl.substr(0,loadurl.indexOf("&ebayaccount="))+"&ebayaccount="+$(obj).attr("value");
                }
            }else{
                loadurl = loadurl+"&ebayaccount="+$(obj).attr("value");
            }
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }
        var selectStr = "";
        var selectQuery="";
        function selectData(){
            var urls = loadurl;
            var selectType = $("select[name='selecttype']").find("option:selected").val();
            var selectValue = $("#_selectvalue").val();
            if(selectType==""){
                alert("请选择查询类型！");
                return;
            }
            if(urls.indexOf(selectQuery)>0){
                urls = urls.replace(selectQuery,"");
                urls=urls+"&selectType="+selectType+"&selectValue="+selectValue;
            }else{
                urls=urls+"&selectType="+selectType+"&selectValue="+selectValue;
            }
            if(selectType!=null&&selectType!=""){
                selectQuery="&selectType="+selectType+"&selectValue="+selectValue;
            }
            loadurl = urls;
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }

        function selectAllData(obj){
            var urls = loadurl;
            if(urls.indexOf(selectQuery)>0) {
                urls = urls.replace(selectQuery,"");
            }
            $(obj).parent().find("select[name='selecttype']").val("");
            $(obj).parent().find("#_selectvalue").val("");
            loadurl = urls;
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }
        var amendType = "";
        var amendFlag = "";
        function selectAmendType(obj){
            var urls = loadurl;
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");
            if(urls.indexOf(amendType)>0) {
                urls = urls.replace(amendType,"");
                urls = urls+"&amendType="+$(obj).attr("value");
            }else{
                urls = urls+"&amendType="+$(obj).attr("value");
            }
            amendType="&amendType="+$(obj).attr("value");
            loadurl = urls;
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }

        function selectAmendFlag(obj){
            var urls = loadurl;
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");
            if(urls.indexOf(amendFlag)>0) {
                urls = urls.replace(amendFlag,"");
                urls = urls+"&amendFlag="+$(obj).attr("value");
            }else{
                urls = urls+"&amendFlag="+$(obj).attr("value");
            }
            amendFlag="&amendFlag="+$(obj).attr("value");
            loadurl = urls;
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }
        function getCentents(){
            alert($("#centents").val());
        }
        function listingType(json){
            var htm="";
            var titlestr = "";
            if(json.listingType=="2"){
                htm="lx.png";
                titlestr="多属性";
            }else if(json.listingType=="Chinese"){
                htm="bids.png";
                titlestr="拍卖";
            }else if(json.listingType=="FixedPriceItem"){
                htm="buyit.png";
                titlestr="固价";
            }

            return "<img  width='16' title='"+titlestr+"' height='16' src='"+path+"/img/"+htm+"'>";
        }
        var endItemReason;
        function endItemByid(itemidStr){
            var tent = "<div>原因<select id='centents' name='centents'><option value='Incorrect'>Incorrect</option>" +
                    "<option value='LostOrBroken'>LostOrBroken</option>" +
                    "<option value='NotAvailable'>NotAvailable</option>" +
                    "<option value='OtherListingError'>OtherListingError</option>" +
                    "<option value='SellToHighBidder'>SellToHighBidder</option>" +
                    "<option value='Sold'>Sold</option></select></div>";
            endItemReason = openMyDialog({title: '提前结束原因',
                content: 'url:'+path+'/enditemreason.jsp',
                icon: 'tips.gif',
                width: 200,
                height:50,
                button: [
                    {
                        name: '确定',
                        callback: function (iwins, enter) {
                            var reason = "";
                            if (endItemReason.content.document.getElementById("centents").value == "") {
                                alert("结束原因必填！");
                                return false;
                            } else {
                                //alert(iwins.parent.document.getElementById("centents").selectedIndex);
                                reason = endItemReason.content.document.getElementById("centents").value;
                                var url = path + "/ajax/endItem.do?itemidStr=" + itemidStr+"&reason="+reason;
                                $().invoke(url, {},
                                        [function (m, r) {
                                            /*alert(r);
                                            Base.token();
                                            if("updatelog"==nameFolder){
                                                onloadTableamend(loadurl);
                                            }else{
                                                onloadTable(loadurl);
                                            }*/
                                            for(var i = 0;i< r.length;i++){
                                                var tld = r[i];
                                                //$("input[type='checkbox'][name='listingitemid'][value='"+tld.itemId+"']").parent().parent().slideUp();
                                                $("input[type='checkbox'][name='listingitemid'][value='"+tld.itemId+"']").parent().parent().prop("id",tld.itemId);
                                                $("#"+tld.itemId).css('background-color','red');
                                                $("#"+tld.itemId).hide(1500);
                                                $("#"+tld.itemId).slideUp(1500,function(){
                                                    $("#"+tld.itemId).remove();
                                                });
                                            }
                                        },
                                            function (m, r) {
                                                alert(r);
                                                Base.token();
                                            }]
                                );
                            }
                        }
                    }
                ]
            });
        }
        function endItem(obj) {
            $("obj").attr({style:"color:red"});
            var item = $("input[name='listingitemid']");
            var itemidStr = "";
            for (var i = 0; i < item.length; i++) {
                if ($(item[i])[0].checked) {
                    itemidStr += $(item[i])[0].value + ",";
                    $("input[type='checkbox'][name='listingitemid'][value='"+$(item[i])[0].value+"']").parent().parent().css("backgroundColor", "#FB6C6C");
                }
            }
            if(itemidStr==""){
                alert("请选择需结束的商品！");
                return ;
            }
            endItemByid(itemidStr);

        }
        var OrderGetOrders;
        function addTabRemark(){
            var url=path+"/order/selectTabRemark.do?folderType=listingFolder";
            OrderGetOrders=openMyDialog({title: '选择文件夹',
                content: 'url:'+url,
                icon: 'succeed',
                width:800
            });
        }

        /**
        *表格调 价
         */
        var tablePricewin=null;
        function tablePrice(obj){
            var url=path+"/getTablePriceList.do";
            tablePricewin=openMyDialog({title: '表格调价列表',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                lock: true,
                height:400
            });
        }
        //--------------------------
        function refleshTabRemark(folderType){
            var url=path+"/order/refleshTabRemark.do?folderType="+folderType;
            $().invoke(url,null,
                    [function(m,r){
                        var div=document.getElementById("tab");
                        var remarks=$(div).find("dt[scop=tabRemark]");
                        for(var i=0;i<remarks.length;i++){
                            $(remarks[i]).remove();
                        }
                        var htm="";
                        for(var i=0;i< r.length;i++){
                            htm += "<dt scop=\"tabRemark\"  name='myFolder_" + i + "' val='" + r[i].id + "' class=new_tab_2 onclick='setTab(this)'>" + r[i].configName + "</dt>";
                        }
                        $(div).append(htm);
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        //----------------------------------------

        $(document).ready(function(){
            initSearchInput();
            var url=path+"/ajax/selfFolder.do?folderType=listingFolder";
            $().invoke(url,{},
                    [function(m,r){
                        var htmlstr='<dt name="listing" class=new_tab_1 onclick="setTab(this)">在线</dt>'
                                +'<dt name="sold" class=new_tab_2 onclick="setTab(this)">已售</dt>'
                                +'<dt name="unsold" class=new_tab_2 onclick="setTab(this)">未卖出</dt>'
                                +'<dt name="updatelog" class=new_tab_2 onclick="setTab(this)">在线修改日志</dt>';
                        if(r!=null) {
                            for (var i = 0; i < r.length; i++) {
                                htmlstr += "<dt scop=\"tabRemark\"  name='myFolder_" + i + "' val='" + r[i].id + "' class=new_tab_2 onclick='setTab(this)'>" + r[i].configName + "</dt>";
                            }
                        }
                        $("#tab").html(htmlstr);
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }

            if(localStorage.getItem("siteListStr")!=null){
                $("#li_countyselect").html(localStorage.getItem("siteListStr"));
            }


        });

        /**初始化搜索框*/
        function initSearchInput(bs){
            var _url="";
            var _map={};
            if(bs=='sku'){
                _url=path+"/informationType/ajax/loadOrgIdItemInformationList.do";
                _map={id:"sku",text:"sku"};
            }else if(bs=="address"){
                _url=path+"/ajax/queryItemAddress.do";
                _map={id:"sku",text:"sku"};
            }

            mySelect2I([{url:_url,doitAfterSelect:selectData,
                data:{currInputName:"content"},bs:"#_selectvalue",multiple:false,maping:_map}]);
        }
        function chageOldDom(obj){
            initSearchInput($(obj).val());
            $("input[id='_selectvalue']").val('');
        }

        var mytoFolder
        function toFolder(idStr){
            var mytoFolder = openMyDialog({title: '选择移动到的文件夹',
                content: 'url:'+path+'/selecttofolder.jsp?foldertype=listingFolder',
                icon: 'tips.gif',
                width: 200,
                button: [
                    {
                        name: '确定',
                        callback: function (iwins, enter) {
                            var folderid = "";
                            for(var is=0;is<mytoFolder.content.document.getElementsByName("folderid").length;is++){
                                if(mytoFolder.content.document.getElementsByName("folderid")[is].checked){
                                    folderid = mytoFolder.content.document.getElementsByName("folderid")[is].value;
                                }
                            }
                            if (folderid == "") {
                                alert("请选择文件夹！");
                                return false;
                            } else {
                                var url = path + "/ajax/shiftListingToFolder.do?idStr=" + idStr+"&folderid="+folderid;
                                $().invoke(url, {},
                                        [function (m, r) {
                                            alert(r);
                                            Base.token();
                                            if("updatelog"==nameFolder){
                                                onloadTableamend(loadurl);
                                            }else{
                                                onloadTable(loadurl);
                                            }
                                        },
                                            function (m, r) {
                                                alert(r);
                                                Base.token();
                                            }]
                                );
                            }
                        }
                    }
                ]
            });
        }
        function shiftToFolder(obj) {
            var item = $("input[name='listingitemid']");
            var idStr = "";

            for (var i = 0; i < item.length; i++) {
                if ($(item[i])[0].checked) {
                    idStr += $($(item[i])[0]).attr("val") + ",";
                }
            }
            if(idStr==""){
                alert("请选择需要移动的商品！");
                return ;
            }
            toFolder(idStr);
        }
        function getTitle(json){
            var html="";
            if(json.title.length>70){
                html = "<span style='word-break:break-all;' title='"+json.title+"'><a target='_blank' href='"+getSiteUrl("3",json.site)+json.itemId+"'>"+json.title.substr(0,70)+".....</a></span>";
            }else{
                if(json.title.length>20){
                    html = "<span style='word-break:break-all;' title='"+json.title+"'><a target='_blank' href='"+getSiteUrl("3",json.site)+json.itemId+"'>"+json.title+"</a></span>";
                }else{
                    html = "<span style='word-break:break-all;' title='"+json.title+"'><a target='_blank' href='"+getSiteUrl("3",json.site)+json.itemId+"'>"+json.title+"</a></span>";
                }
            }
            html+="</br><span style='color: #7B7B7B;'>物品号："+json.itemId+"</span>";
            var remark = "";
            if(json.remark!=null&&json.remark!=""){
                if(json.remark.length>25){
                    remark = json.remark.substr(0,25)+"......";
                }else{
                    remark = json.remark;
                }
                html+="</br><span class='newdf' title='"+json.remark+"'>备注："+remark+"</span>";
            }else{
                html+="</br><span class='newdf' title='' style='display: none'></span>";
            }
            return html;
        }
        function getSku(json){

            var html = "";
            if(json.docId!=null&&json.docId!=""){
                html+="&nbsp;&nbsp;<a target='_blank' href='" + path + "/editItem.do?id=" + json.docId + "&source=listingManager' title='" + json.docTitle + "'><span>";
                html+= cutstr(json.docTitle,10) ;//json.docTitle.substring(0,8);
                html+= "...</span></a>";
            }
            if(json.sku!=null&&json.sku!="") {
                html += "</br>&nbsp;&nbsp;<a target='_blank' href='"+getSiteUrl("3",json.site)+json.itemId+"'><span style='color:#8BB51B;'>"+json.sku+"</span></a>";
            }
            return html
        }
        function getChars(str) {
            var i = 0;
            var c = 0;
            var unicode = 0;
            var len = 0;
            if (str == null || str == "") {
                return 0;
            }
            len = str.length;
            for(i = 0; i < len; i++) {
                unicode = str.charCodeAt(i);
                if (unicode < 127) { //判断是单字符还是双字符
                    c += 1;
                } else { //chinese
                    c += 2;
                }
            }
            return c;
        }
        var descStatic
        var orderClu = "";
        var orderFlag = "";
        function orderList(obj){
            var des = "";
            orderFlag
            if($(obj).attr("val")=="0"){//默认状态为降序，之前为升序
                $(obj).attr("val","1");
                des="desc";
            }else{
                $(obj).attr("val","0");
                des="asc";
            }
            descStatic=$(obj).attr("val");
            var desc = $(obj).attr("colu");
            orderClu = desc;
            orderFlag = $(obj).attr("val");
            onloadTable(loadurl+"&descStr="+desc+"&desStr="+des);
            $("#itemListingTable").find("span[colu='"+desc+"']").attr("val",descStatic);

        }
        function onloadTable(urls){
            $("#itemListingTable").initTable({
                url:urls,
                columnData:[
                    {title:"选择",name:"itemName",width:"2%",align:"left",format:makeOption0},
                    {title:"图片",name:"Option1",width:"3%",align:"center",format:picUrl},
                    {title:"<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='title' val='0'>物品标题</span></a>",name:"title",width:"16%",align:"left",format:getTitle},
                    {title:"<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='doc.item_name' val='0'>&nbsp;&nbsp;范本</span></a>/<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='sku' val='0'>SKU</span></a>",name:"sku",width:"8%",align:"left",format:getSku},
                    {title:"<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='ebay_account' val='0'>ebay账户</span></a>",name:"ebayAccount",width:"4%",align:"left",format:getEbayAccount},
                    {title:"<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='site' val='0'>站点</span></a>",name:"site",width:"2%",align:"left",format:getSiteImg},
                    {title:"<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='listing_type' val='0'>刊登类型</span></a>",name:"listingType",width:"4%",align:"center",format:listingType},
                    {title:"<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='price' val='0'>价格</span></a>",name:"price",width:"6%",align:"center",format:getPriceHtml},
                    {title:"<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='Quantity' val='0'>数量</span></a>/<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='QuantitySold' val='0'>已售</span></a>",name:"Option1",width:"6%",align:"center",format:tjCount},
                    {title:"<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='ListingDuration' val='0'>刊登天数</span></a>",name:"listingduration",width:"4%",align:"center",format:getDuration},
                    {title:"<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='StartTime' val='0'>刊登时间</span></a>",name:"StartTime",width:"4%",align:"left",format:getStartTime},
                    {title:"<a class='addHover'><span onclick='orderList(this)' style='cursor: pointer;' colu='EndTime' val='0'>结束时间</span></a>",name:"endtime",width:"4%",align:"left",format:getendTime},
                    {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"Option1",width:"4%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                afterLoadTable:function(){
                    if(orderClu!=""){
                        $("#itemListingTable").find("span[colu='"+orderClu+"']").attr("val",orderFlag);
                    }
                }
            });
            refreshTable();
        }
        function getEbayAccount(json){
            return "<span style='color: #7B7B7B;'>"+json.ebayAccount+"</span>";
        }
        function getendTime(json){
            html="";
            html = "<span style='color: #7B7B7B;'>"+json.endtime.replace(" ","</br>")+"</span>";
            return html;
        }
        function getStartTime(json){
            html="";
            html = "<span style='color: #7B7B7B;'>"+json.starttime.replace(" ","</br>")+"</span>";
            return html;
        }
        function getDuration(json){
            var html = "<span style='color: #7B7B7B;'>";
            if(json.listingduration.indexOf("Days_")!=-1){
                html+=json.listingduration.substr(json.listingduration.indexOf("_")+1,json.listingduration.length);
            }else{
                html+=json.listingduration;
            }
            html+="</span>"
            return html;
        }
        function showText(obj){
            var div=$(obj).parent();
            $(obj).hide();
            $(div).find("input[type='hidden']").val($(div).find("span[name=inputNum]").text());
            $(div).find("input[type='hidden']").prop("type","text").focus();
        }
        var priceTracking=null;
        function showPriceList(obj,sku,ebayAcount){
            /*$(obj).dialog({
                content:"aaaaaaaaaaaaaaaa",
                autoOpen: true,//如果设置为true，则默认页面加载完毕后，就自动弹出对话框；相反则处理hidden状态。
                bgiframe: true, //解决ie6中遮罩层盖不住select的问题
                width: 600,
                modal:true//这个就是遮罩效果
               *//* buttons: {
                    "Ok": function() {
                        test();//在这里调用函数
                        $(this).dialog("close");
                    },
                    "Cancel": function() {
                        $(this).dialog("close");
                    }
                }*//*
            });*/
            /*$(obj).gips({delay:500,pause:500,imagePath: path+'/js/gips/images/close.png','theme': 'green',autoHide: true,placement:'right',text:'在这儿加入需要展示的代码！'});
            */
            var url=path+"/priceTracking/viewPricingTracking.do?sku="+sku+"&ebayAcount="+ebayAcount;
            priceTracking=openMyDialog({title: '竞争对手',
                content: 'url:'+url,
                icon: 'succeed',
                width:500,
                height:200,
                lock:true
            });
        }
        function addPriceTracking(htm){
           var prices= $(htm).find("span[scop=priceTracking1]");
            var price=prices[0];
            var sku=$(price).attr("name1");
            var ebayAcount=$(price).attr("name2");
            var url = path + "/priceTracking/ajax/viewPricingTracking1.do?sku="+sku+"&ebayAcount="+ebayAcount;
            $().invoke(url, null,
                    [function (m, r) {
                        if(r=="true"){
                            var content=price.innerHTML;
                            var str1="<img width='12px' height='12px' onclick='showPriceList(this,\""+sku+"\",\""+ebayAcount+"\")' src='"+path+"/img/search_price.gif'>";
                            var prices1=$(document).find("span[scop=priceTracking1][name1="+sku+"]");
                            for(var i=0;i<prices1.length;i++){
                                var price1=prices1[i];
                                price1.innerHTML=content+str1;
                            }
                        }
                        Base.token();
                    },
                        function (m, r) {
                            Base.token();
                        }]
            )
          /*  var lables=document.getElementsByName("priceTracking1");
            console.debug(lables);
            console.debug(lables.item[0]);*/
            /*var sku=$(lable).attr("name1");
            var url = path + "/priceTracking/ajax/viewPricingTracking1.do?sku="+sku;
            $().invoke(url, null,
                    [function (m, r) {
                        if(r=="true"){
                            var content=lable.innerHTML;
                            console.debug(content);
                            var str1="<img width='12px' height='12px'onmouseout='closeshowPriceList();' OnMouseOver='showPriceList(this,\""+json.sku+"\")' src='"+path+"/img/search_price.gif'>";
                        }
                        Base.token();
                    },
                        function (m, r) {
                            Base.token();
                        }]
            );*/
            /*for(var i=0;i<lables.length;i++){
                alert(1);
                *//*var lable=lables[i];
                var sku=$(lable).attr("name1");
                var url = path + "/priceTracking/ajax/viewPricingTracking1.do?sku="+sku;
                $().invoke(url, null,
                        [function (m, r) {
                            if(r=="true"){
                                var content=lable.innerHTML;
                                console.debug(content);
                                var str1="<img width='12px' height='12px'onmouseout='closeshowPriceList();' OnMouseOver='showPriceList(this,\""+json.sku+"\")' src='"+path+"/img/search_price.gif'>";
                            }
                            Base.token();
                        },
                            function (m, r) {
                                Base.token();
                            }]
                );*//*
            }*/

        }
        function getPriceHtml(json){
            var htm="";
            var str="";
            if("updatelog"!=nameFolder&&json.listingType=="FixedPriceItem") {
                htm = "<div style='display:inline;' ><span name='inputNum' onclick='showText(this)'  style='color: dodgerblue;' >"+json.price.toFixed(2)+"</span>"+
                        "<input  onkeypress='return inputNUMAndPoint(event,this,2)' type='hidden' name='price' onblur='updateItemPrice(this)'" +
                        " ids='"+json.id+"' itemId='"+json.itemId+"' " +
                        "  class='newinputt'  oldval='"+json.price.toFixed(2)+"' value='"+json.price.toFixed(2)+"'/>&nbsp;<span scop='priceTracking1' name2='"+json.ebayAccount+"' name1='"+json.sku+"' style='color: #7B7B7B;'>" +json.currencyId+str+"</span><img src='' style='display: none'/></div></br>" ;
                if(json.shippingPrice==null||parseInt(json.shippingPrice)==0){

                }else{
                    htm+="<span scop='priceTracking1' name2='"+json.ebayAccount+"' name1='"+json.sku+"'  style='color: #7B7B7B;'>+$"+json.shippingPrice.toFixed(2)+"&nbsp;"+json.currencyId+str+"</span>";
                }
            }else{
                if(json.shippingPrice==null||parseInt(json.shippingPrice)==0){
                    htm = "<span scop='priceTracking1' name2='"+json.ebayAccount+"' name1='"+json.sku+"' style='color: #7B7B7B;'>"+json.price.toFixed(2)+"&nbsp;"+json.currencyId+str+"</span>";
                }else{
                    htm = "<span scop='priceTracking1' name2='"+json.ebayAccount+"' name1='"+json.sku+"' style='color: #7B7B7B;'>"+json.price.toFixed(2)+"&nbsp;"+json.currencyId+"</br>+$"+json.shippingPrice.toFixed(2)+"&nbsp;"+json.currencyId+str+"</span>";
                }
            }
            addPriceTracking(htm);
            return htm;

        }
        function showImg(obj){
            $(obj).parent().find("img").each(function(i,d){
                $(d).show();
            });
        }
        function hideImg(obj){
            $(obj).parent().find("img").each(function(i,d){
                $(d).hide();
            });
        }
        //修改界面
        function updateItemPrice(obj){
            if(!$.isNumeric($(obj).val())){
                alert("输入的数字无效！")
                $(obj).val($(obj).attr("oldval"));
                return;
            }
            $(obj).parent().find("span[name=inputNum]").text($(obj).val());
            $(obj).parent().find("span[name=inputNum]").show();
            $(obj).prop("type","hidden");
            var price=$(obj).val();
            var itemId=$(obj).attr("itemId");
            var textname = $(obj).attr("name");
            var ids = $(obj).attr("ids");
            var type=textname;

            if(parseFloat(price.trim())!=parseFloat($(obj).attr("oldval").trim())){
                $(obj).parent().find("span").block({ message: "",overlayCSS: {backgroundColor:'#ffffff' }});
                $(obj).parent().find("img").show();
                $(obj).parent().find("img").prop("src",path+"/img/loading2.gif");
                $(obj).parent().find("img").css({height:"28px",width:"28px"});
                var url = path + "/ajax/updateListingData.do?itemId=" + itemId+"&price="+price+"&type="+type+"&ids="+ids;
                $().invoke(url, {},
                        [function (m, r) {
                            Base.token();
                            if(type=="price") {
                                $(obj).attr("oldval", r.price.toFixed(2));
                            }else{
                                $(obj).attr("oldval", r.quantity);
                            }
                            $(obj).parent().find("img").prop("src","");
                            $(obj).parent().find("span").unblock();
                            $(obj).parent().find("img").hide();
                        },
                            function (m, r) {
                                Base.token();
                                if(type=="price"){
                                    $(obj).parent().find("span").text(r.price.toFixed(2));
                                    $(obj).val(r.price.toFixed(2))
                                }else{
                                    $(obj).parent().find("span").text(r.quantity);
                                    $(obj).val(r.quantity)
                                }
                                var errm = r.errMessage;
                                $(obj).parent().find("img").prop({"src":path+"/img/tips.png","title":errm});
                                $(obj).parent().find("span").unblock();
                            }]
                );
            }

        }
        /**组装操作选项*/
        function makeOption0(json){
            var htm="<input type=checkbox name='listingitemid' listingType='"+json.listingType+"' value='"+json.itemId+"' val='"+json.id+"' />";
            return htm;
        }
        function picUrl(json){
            var htm="<a target='_blank' href='"+serviceItemUrl+json.itemId+"'><img width='50px' height='50px' src='"+itemListIconUrl_+json.itemId+".jpg' title='更新时间："+json.updateDate+"'/></a>";
            return htm;
        }
        function tjCount(json){
            //style='width:35px;padding-left:0px;padding-right:0px;height:20px;border: 1px solid #AA17A4;'
            var htm="";
            if("updatelog"!=nameFolder&&json.listingType=="FixedPriceItem") {
                htm="<div style='display:inline;' ><span name='inputNum' onclick='showText(this)'  style='color: dodgerblue;'>"+json.quantity+"</span>" +
                        "<input type='hidden'  onkeypress='return inputOnlyNUM(event,this)' onblur='updateItemPrice(this)' name='quantity' onFocus='showImg(this)' ids='"+json.id+"' itemId='"+json.itemId+"' size='1' " +
                        "class='newinputt' oldval='"+json.quantity+"' value='"+json.quantity+"'/><lable  style='color: #7B7B7B;'>/"+json.quantitysold+"</lable> <img src=''  style='display: none'/></div>";
            }else{
                htm = "<lable  style='color: #7B7B7B;'>"+json.quantity+"/"+json.quantitysold+"</lable>";
            }
            return htm;
        }

        /**组装操作选项*/
        function makeOption1(json){
            var hs="";
            hs+="<li style='height:25px' onclick=toFolder('"+json.id+"') value='"+json.id+"' doaction=\"look\" >移动</li>";
            if("updatelog"!=nameFolder) {
                hs += "<li style='height:25px' onclick=edit('" + json.itemId + "') value='" + json.id + "' doaction=\"look\" >在线编辑</li>";
                hs += "<li style='height:25px' onclick=endItemByid('" + json.itemId + "') value='" + json.id + "' doaction=\"look\" >提前结束</li>";
                hs += "<li style='height:25px' onclick=quickEdit('" + json.id + "','"+json.listingType+"') value='" + json.id + "' doaction=\"look\" >快速编辑</li>";
                hs += "<li style='height:25px' onclick=remark('" + json.id + "','"+json.remark+"') value='" + json.id + "' doaction=\"look\" >备注</li>";
                hs += "<li style='height:25px' onclick=selectLog('" + json.id + "') value='" + json.id + "' doaction=\"look\" >查看日志</li>";
            }
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        //快速编辑
        var quickEdits;
        function quickEdit(id,listingType){
            var url=path+"/quickEdit.do?id="+id+"&&listingType="+listingType;
            quickEdits=openMyDialog({title: '快速编辑',
                content: 'url:'+url,
                icon: 'succeed',
                width:1000,
                lock: true,
                height:400
            });
        }
        //备注
        var myaddremark
        function remark(id,remark){
            if(remark==undefined){
                remark="";
            }else{
                remark = $("input[type='checkbox'][name='listingitemid'][val='" + id + "']").parent().parent().find("td").eq(2).find(".newdf").text().substr(3);
            }
            var tent = "<div class='textarea'>备注：<textarea cols='45' rows='5' id='centents' >"+remark+"</textarea></div>";
            myaddremark = openMyDialog({title: '备注',
                content: 'url:'+path+'/addmyremark.jsp?reason='+remark,
                icon: 'tips.gif',
                width: 300,
                button: [
                    {
                        name: '确定',
                        callback: function (iwins, enter) {
                            var reason = "";
                            /*if (iwins.parent.document.getElementById("centents").value == "") {
                                alert("备注必填！");
                                return false;
                            } else {*/
                                //alert(iwins.parent.document.getElementById("centents").selectedIndex);
                                reason = myaddremark.content.document.getElementById("centents").value;
                                var url = path + "/ajax/addRemark.do?id=" + id+"&remark="+reason;
                                $().invoke(url, {},
                                        [function (m, r) {
                                            //alert(r);
                                            /*Base.token();
                                            if("updatelog"==nameFolder){
                                                onloadTableamend(loadurl);
                                            }else{
                                                onloadTable(loadurl);
                                            }*/
                                            for(var i=0;i< r.length;i++){
                                                var tld  = r[i];
                                                var remark = "";
                                                if(tld.remark!=null&&tld.remark!=""){
                                                    if(tld.remark.length>25){
                                                        remark = tld.remark.substr(0,25)+"......";
                                                    }else{
                                                        remark = tld.remark;
                                                    }
                                                    $("input[type='checkbox'][name='listingitemid'][value='"+tld.itemId+"']").parent().parent().find("td").eq(2).find(".newdf").show();
                                                    $("input[type='checkbox'][name='listingitemid'][value='"+tld.itemId+"']").parent().parent().find("td").eq(2).find(".newdf").html("备注："+remark);
                                                    $("input[type='checkbox'][name='listingitemid'][value='"+tld.itemId+"']").parent().parent().find("td").eq(2).find(".newdf").prop("title",remark);
                                                }else{
                                                    $("input[type='checkbox'][name='listingitemid'][value='"+tld.itemId+"']").parent().parent().find("td").eq(2).find(".newdf").hide();
                                                }
                                            }
                                        },
                                            function (m, r) {
                                                alert(r);
                                                Base.token();
                                            }]
                                );
                            //}
                        }
                    }
                ]
            });
        }
        //查看日志
        function selectLog(id){
            var url=path+"/getListItemDataAmend.do?parentId="+id;
            tablePricewin=openMyDialog({title: '修改在线商品日志',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                lock: true,
                height:400
            });
        }
        function  refreshTable(){
            var selectValue = $("#_selectvalue").select2("data")!=null ? $("#_selectvalue").select2("data").text:"";
            var param={};
            if(selectValue!=null&&selectValue!=""){
                param={"queryValue":selectValue};
            }
            $("#itemListingTable").selectDataAfterSetParm(param);
        }

        function reshTable(){
            $("#itemListingTable").refresh();
        }
        //在线编辑
        var editPage = "";
        function edit(itemid){
            editPage = openMyDialog({title: '编辑在线商品',
                content: 'url:/xsddWeb/editListingItem.do?itemid='+itemid,
                icon: 'succeed',
                width:800,
                height:500
            });
        }

        function changeSelect(obj){
            var item = $("input[name='listingitemid']");
            var idStr = "";
            var isab = false;
            var listingType="";
            var itemId="";
            for (var i = 0; i < item.length; i++) {
                if ($(item[i])[0].checked) {
                    if(listingType==""){
                        listingType=$($(item[i])[0]).attr("listingType");
                    }else{
                        if(listingType==$($(item[i])[0]).attr("listingType")){
                            listingType=$($(item[i])[0]).attr("listingType");
                        }else{
                            isab=true;
                        }
                    }
                    idStr += $($(item[i])[0]).attr("val") + ",";
                    itemId += $($(item[i])[0]).val() + ",";
                }
            }
            if(idStr==""){
                alert("请选择需要处理的商品！");
                $(obj).val("");
                return ;
            }
            if($(obj).val()=="listingEdit"){//在线编辑
                edit(itemId);
            }else if($(obj).val()=="quickEdit"){//快速编辑
                if(isab){
                    alert("不允许不同刊登类型进行编辑！");
                    $(obj).val("");
                    return;
                }
                quickEdit(idStr,listingType)
            }else if($(obj).val()=="remark"){//备注
                remark(idStr)
            }
            $(obj).val("");
        }
    function selectAllEbayAccount(obj){
        alert($(obj).parent().html());
    }
        //同步
    function synListingData(){
        var url = path + "/ajax/myEbayAccount.do";
        $().invoke(url, {},
                [function (m, r) {
                    var ten = "<div>";
                    for(var i=0;i< r.length;i++){
                        ten+="<div><input type='checkbox' name='ebayAccount' id='ebayAccount' value='"+r[i].ebayAccount+"'/>"+r[i].ebayName+"(<font color='blue'>最近同步时间："+r[i].maxDate+"</font>)</div>";
                    }
                    ten+="</div>";
                    openMyDialog({title: '选择EBAY账号',
                        content: ten,
                        icon: 'success.gif',
                        width: 400,
                        button: [
                            {
                                name: '确定',
                                callback: function (iwins, enter) {
                                    var reason = "";
                                    if(cc==1){
                                        for(var i=0;i<iwins.document.getElementsByName("ebayAccount").length;i++){
                                            if($(iwins.document.getElementsByName("ebayAccount")[i]).prop("checked")){
                                                reason+=$(iwins.document.getElementsByName("ebayAccount")[i]).val()+",";
                                            }
                                        }
                                    }else{
                                        for(var i=0;i<iwins.parent.document.getElementsByName("ebayAccount").length;i++){
                                            if($(iwins.parent.document.getElementsByName("ebayAccount")[i]).prop("checked")){
                                                reason+=$(iwins.parent.document.getElementsByName("ebayAccount")[i]).val()+",";
                                            }
                                        }
                                    }

                                    if (reason == "") {
                                        alert("ebay账号必须选择！");
                                        return false;
                                    } else {
                                        var url = path + "/ajax/synListingData.do?ebayAccount="+reason;
                                        $().invoke(url, {},
                                                [function (m, r) {
                                                    alert(r);
                                                    Base.token();
                                                },
                                                    function (m, r) {
                                                        alert(r);
                                                        Base.token();
                                                    }]
                                        );
                                    }
                                }
                            }

                        ]
                    });
                },
                    function (m, r) {
                        alert(r);
                        Base.token();
                    }]
        );
        /**/
    }
        function onselectAlls(obj){
            var item = $("input[name='listingitemid']");
            if($(obj).prop("checked")){
                for (var i = 0; i < item.length; i++) {
                    $($(item[i])[0]).prop("checked",true);
                }
            }else{
                for (var i = 0; i < item.length; i++) {
                    $($(item[i])[0]).prop("checked",false);
                }
            }
        }
        var siteListPage
        function selectSiteList(obj){
            var siteid = "";
            $(obj).parent().find("a").each(function(i,d){
                if($(d).attr("value")!=null&&$(d).attr("value")!=""){
                    siteid+=$(d).attr("value")+",";
                }
            });
            var urls = path+'/selectSiteList.do?siteidStr='+siteid;
            siteListPage = openMyDialog({title: '选择定时时间',
                content: 'url:'+urls,
                icon: 'succeed',
                width:500,
                lock:true,
                background:'#ffffff'
            });
        }
    </script>
<style>
    .newusa_i{
        width: 74px;
    }
</style>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/select2/mySelect2.js" /> ></script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>刊登</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls" id="tab">

        </div>

        <div class=Contentbox>
            <div class="new_usa" style="margin-top:20px;">
                <li class="new_usa_list" id="li_countyselect"><span class="newusa_i">选择国家：</span>
                    <a  href="javascript:void(0)" onclick="selectCounty(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="311"><span class="newusa_ici_1"><img src="<c:url value ='/img/usa_1.png'/> ">美国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="310"><span class="newusa_ici_1"><img src="<c:url value ='/img/UK.jpg'/> ">英国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="298"><span class="newusa_ici_1"><img src="<c:url value ='/img/DE.png'/> ">德国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="291"><span class="newusa_ici_1"><img src="<c:url value ='/img/AU.jpg'/> ">澳大利亚</span></a>
                    <a href="javascript:void(0)" onclick="selectSiteList(this)"><span style="padding-left: 20px;vertical-align: middle;color: #5F93D7">更多...</span></a>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">选择账号：</span>
                    <a href="javascript:void(0)" onclick="selectEbayAccount(this)"  value=""><span class="newusa_ici">全部</span></a>
                    <c:forEach items="${ebayList}" var="ebay">
                        <a href="javascript:void(0)" onclick="selectEbayAccount(this)" value="${ebay.ebayName}"><span class="newusa_ici_1">${fn:toUpperCase(ebay.ebayNameCode)}</span></a>
                    </c:forEach>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">刊登类型：</span>

                    <a href="javascript:void(0)" onclick="selectListType(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="FixedPriceItem"><span class="newusa_ici_1">固价</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="2"><span class="newusa_ici_1">多属性</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Chinese"><span class="newusa_ici_1">拍卖</span></a>
                </li>
                <div id="amendlog" style="display: none;">
                    <li class="new_usa_list">
                        <span class="newusa_i">修改类型：</span>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value=""><span class="newusa_ici">全部</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value="StartPrice"><span class="newusa_ici_1">价格</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value="Title"><span class="newusa_ici_1">标题</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value="Quantity"><span class="newusa_ici_1">数量</span></a>
                    </li>
                    <li class="new_usa_list">
                        <span class="newusa_i">修改状态：</span>
                        <a href="javascript:void(0)" onclick="selectAmendFlag(this)" value=""><span class="newusa_ici">全部</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendFlag(this)" value="1"><span class="newusa_ici_1">成功</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendFlag(this)" value="0"><span class="newusa_ici_1">失败</span></a>
                    </li>
                </div>



                <div class="newsearch">
                    <span class="newusa_i">搜索内容：</span>
                    <a name="t_a" href="javascript:void(0)" onclick="selectAllData(this)" value="">
                        <span class="newusa_ici">全部</span>
                    </a>
                    <span id="sleBG" style="width:82px;background-position: 67px 10px;">
                    <span id="sleHid1" style="width: 80px;">
                    <select name="selecttype" onchange="chageOldDom(this)" class="select" style="color: #737FA7;width: 80px;float: left">
                        <option selected="selected" value="">选择类型</option>
                        <option value="sku">SKU</option>
                        <option value="title">物品标题</option>
                        <option value="item_id">物品号</option>
                        <option value="docname">范本名称</option>
                        <option value="address">物品所在地</option>
                    </select>
                    </span>
                    </span>

                    <span>
                        <input id="_selectvalue" name="selectvalue" type="text" style="width:200px;float: left"  multiple class="multiSelect">
                        <input style="display: none" name="newbut" onclick="selectData()" type="button" class="key_2">
                    </span>

                </div>


                <%--<div class="newsearch">
                    <span class="newusa_i">搜索内容：</span>
                    <a href="javascript:void(0)" onclick="selectAllData(this)" value=""><span class="newusa_ici">全部</span></a>
<span id="sleBG" style="width:82px;background-position: 67px 10px;">
<span id="sleHid" style="width: 80px;">
<select name="selecttype" class="select" style="color: #737FA7;width: 80px;">
    <option selected="selected" value="">选择类型</option>
    <option value="sku">SKU</option>
    <option value="title">物品标题</option>
    <option value="item_id">物品号</option>
    <option value="docname">范本名称</option>
</select>
</span>
</span>

                    <div class="vsearch">
                        <input name="selectvalue" type="text" class="key_1" style="vtical-align:middle;line-height:100%;"><input name="newbut" onclick="selectData()" type="button" class="key_2"></div>
                </div>--%>






                <div class="newds">
                    <div class="newsj_left">

                        <span class="newusa_ici_del_in" style="padding-left: 4px;"><input type="checkbox" onclick="onselectAlls(this)" name="checkbox" id="checkbox"/></span>

                        <div class="numlist" style="padding-left: 8px;">
                            <div class="ui-select" style="margin-top:1px; width:80px;min-width:0px;">
                                <select onm  onchange="changeSelect(this)" style="width: 80px;padding: 0px;">
                                    <option value="">&nbsp;&nbsp;&nbsp;&nbsp;请选择</option>
                                    <option value="listingEdit">&nbsp;&nbsp;在线编辑</option>
                                    <option value="quickEdit">&nbsp;&nbsp;快速编辑</option>
                                    <option value="remark">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注</option>
                                </select>
                            </div>
                            <%--<div class="ui-select" style="margin-top:1px; width:10px">
                                <select>
                                    <option value="AK">移动</option>
                                    <option value="AK">动作</option>
                                </select>
                            </div>--%>
                        </div>
                        <span class="newusa_ici_del" onclick="shiftToFolder(this)">移动到</span>
                        <span class="newusa_ici_del" name="attrshow" onclick="endItem(this)">提前结束</span>
                        <span class="newusa_ici_del" name="attrshow" onclick="tablePrice(this)">表格调价</span><span
                            class="newusa_ici_del"  onclick="addTabRemark();">管理文件夹</span></div>
                    <div class="tbbay" name="attrshow"><a data-toggle="modal" href="javascript:void(0)" class="" onclick="synListingData()">同步eBay</a></div>
                </div>
            </div>
            <%--<iframe src="/xsddWeb/getListingItemList.do?1=1" id="listing_frame" height="1000px;" frameborder="0"
                    width="100%">
            </iframe>--%>
<div style="width: 100%;float: left;height: 5px"></div>
            <div id="itemListingTable" >
            </div>
        </div>

    </div>
</div>
</body>
</html>
