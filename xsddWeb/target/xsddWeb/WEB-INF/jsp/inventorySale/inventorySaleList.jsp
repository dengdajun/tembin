<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/12/22
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/select2/mySelect2.js" /> ></script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function(){
            initSearchInput('1');
            initSearchInput1('1');
            $("#itemInventoryTableList").initTable({
                url:path + "/inventorySale/ajax/loadItemInventoryList.do?",
                columnData:[
                    {title:"第三方库存名",name:"dataSource",width:"19%",align:"left",format:makeOption5},
                    {title:"SKU",name:"sku",width:"19%",align:"left"},
                    {title:"仓库号",name:"storageNo",width:"19%",align:"left"},
                    {title:"仓库",name:"warehouse",width:"19%",align:"left"},
                    {title:"利用",name:"availStock",width:"19%",align:"left"},
                    {title:"操作",name:"status",width:"5%",align:"center",format:makeOption4}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });

            $("#itemInventoryTableList1").initTable({
                url:path + "/inventorySale/ajax/loadItemInventoryList.do?",
                columnData:[
                    {title:"第三方库存名",name:"dataSource",width:"20%",align:"left",format:makeOption1},
                    {title:"SKU",name:"sku",width:"20%",align:"left"},
                    {title:"数量",name:"quantity",width:"20%",align:"left",format:makeOption2},
                    {title:"状态",name:"status",width:"20%",align:"left"},
                    {title:"操作",name:"status",width:"2%",align:"center",format:makeOption3}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable1(0);
        });

        function chageOldDom(obj){
            initSearchInput($(obj).val());
            $("input[id='qeuryContent1']").val('');
        }
        /**初始化select2搜索框*/
        function initSearchInput(bs){
            var _url="";
            var _map={};
            if(bs=='1'){
                _url=path+"/informationType/ajax/loadOrgIdItemInformationList.do";
                _map={id:"sku",text:"sku"};
            }

            mySelect2I([{url:_url,doitAfterSelect:query1,
                data:{currInputName:"content"},bs:"#qeuryContent1",multiple:false,maping:_map}]);
        }

        function chageOldDom1(obj){
            initSearchInput1($(obj).val());
            $("input[id='qeuryContent2']").val('');
        }
        /**初始化select2搜索框*/
        function initSearchInput1(bs){
            var _url="";
            var _map={};
            if(bs=='1'){
                _url=path+"/informationType/ajax/loadOrgIdItemInformationList.do";
                _map={id:"sku",text:"sku"};
            }

            mySelect2I([{url:_url,doitAfterSelect:query2,
                data:{currInputName:"content"},bs:"#qeuryContent2",multiple:false,maping:_map}]);
        }
        function makeOption1(json){
            return "四海游";
        }
        function makeOption4(json){
            var hs="";
            hs+="<li style=\"height:25px;\" onclick=editChuKouYi('"+json.sku+"') doaction=\"readed\" >编辑</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function makeOption5(json){
            if(json.dataSource=="1"){
                return "出口易";
            }
            if(json.dataSource=="2"){
                return "第四方";
            }
        }
        var inventorySale;
        function editChuKouYi(sku){
            var url=path + "/inventorySale/editChuKouYi.do?sku="+sku;
            inventorySale=openMyDialog({title: '编辑',
                content: 'url:'+url,
                icon: 'succeed',
                width:650,
                lock:true,
                zIndex:1000
            });
        }
        function makeOption3(json){
            var hs="";
            hs+="<li style=\"height:25px;\" onclick=editSiHaiYou('"+json.sku+"') doaction=\"readed\" >编辑</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function editSiHaiYou(sku){
            var url=path + "/inventorySale/editSiHaiYou.do?sku="+sku;
            inventorySale=openMyDialog({title: '编辑',
                content: 'url:'+url,
                icon: 'succeed',
                width:650,
                lock:true,
                zIndex:1000
            });
        }
        function makeOption2(json){
            if(json.quantity&&json.quantity>0){
                return json.quantity+"";
            }else{
                return "0";
            }
        }
        function refreshTable1(content){
            $("#itemInventoryTableList").selectDataAfterSetParm({"content":content+1,"flag":"true"});
        }
        function refreshTable2(){
            $("#itemInventoryTableList1").selectDataAfterSetParm({"flag":"false"});
        }
        function query1(){
            var type=$("#selectName1").val();
            var content=$("#qeuryContent1").select2("data")!=null?$("#qeuryContent1").select2("data").text:"";
            $("#itemInventoryTableList").selectDataAfterSetParm({"content":tableFlag+1,"flag":"true","type":type,"content1":content});
        }
        function query2(){
            var type=$("#selectName2").val();
            var content=$("#qeuryContent2").select2("data")!=null?$("#qeuryContent2").select2("data").text:"";
            $("#itemInventoryTableList1").selectDataAfterSetParm({"flag":"false","type":type,"content1":content});
        }
    </script>
</head>
<body>
<div class="new_all" <%--style="width: "--%>>
    <div class="here">当前位置：首页 &gt; 销售管理 &gt; <b>库存销售</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <script type="text/javascript">
            var tableFlag=0;
            function setTab(name,cursel,n){
                for(i=1;i<=n;i++){
                    var menu=document.getElementById(name+i);
                    var con=document.getElementById("con_"+name+"_"+i);
                    if(i<3&&i>0){
                        con=document.getElementById("con_"+name+"_"+1);
                    }
                    menu.className=i==cursel?"new_tab_1":"new_tab_2";
                    if(i<3&&i>0){
                        con.style.display=(1==cursel||2==cursel)?"block":"none";
                    }else{
                        con.style.display=i==cursel?"block":"none";
                    }
                }
                if(cursel<3&&cursel>0){
                    refreshTable1(cursel-1);
                    tableFlag=cursel-1;
                }else if(cursel==3){
                    refreshTable2();
                }
            }
        </script>

        <div class="new_tab_ls">
          <%--  <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,4)">全部</dt>
            <dt id="menu2" class="new_tab_2" onclick="setTab('menu',2,4)">出口易</dt>
            <dt id="menu3" class="new_tab_2" onclick="setTab('menu',3,4)">递四方</dt>
            <dt id="menu4" class="new_tab_2" onclick="setTab('menu',4,4)">四海游</dt>--%>
               <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,3)">出口易</dt>
               <dt id="menu2" class="new_tab_2" onclick="setTab('menu',2,3)">递四方</dt>
               <dt id="menu3" class="new_tab_2" onclick="setTab('menu',3,3)">四海游</dt>
        </div>
        <div class="Contentbox">
            <div>
            <%--    <div id="con_menu_1" style="display: block;">
                    <div style="width: 100%;float: left;height: 5px"></div>
                    <div id="itemInventoryTableList2">123</div>
                    <div class="page_newlist">
                    </div>
                    <!--综合结束 -->
                </div>--%>
                <br/>
                <div id="con_menu_1" style="display: block;">
                    <div class="newsearch">
                        <span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG" style="width:82px;background-position: 67px 10px;">
                    <span id="sleHid" style="width: 80px;">
<select id="selectName1" name="type" onchange="chageOldDom(this)" style="color: #737FA7;width: 80px;float: left">
    <option selected="selected" value="1">SKU</option>
</select>
</span>
</span>

                        <div class="vsearch">
                            <input id="qeuryContent1" name="conteny" type="text" style="width:200px;float: left"  multiple class="multiSelect"><input onclick="query1();" style="display: none;" name="newbut" type="button" class="key_2"></div>
                    </div>
                    <div style="width: 100%;float: left;height: 5px"></div>
                    <div id="itemInventoryTableList"></div>
                    <div class="page_newlist">

                    </div>
                    <!--综合结束 -->
                </div>
                <!--结束 -->
                <div id="con_menu_3" style="display: none;">
                    <div class="newsearch">
                        <span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG" style="width:82px;background-position: 67px 10px;">
                    <span id="sleHid" style="width: 80px;">
<select id="selectName2" name="type" onchange="chageOldDom1(this)" style="color: #737FA7;width: 80px;float: left">
    <option selected="selected" value="1">SKU</option>
</select>
</span>
</span>
                        <div class="vsearch">
                            <input id="qeuryContent2" name="conteny" type="text" style="width:200px;float: left"  multiple class="multiSelect"><input onclick="query2();" name="newbut"  style="display: none;" type="button" class="key_2"></div>
                    </div>
                    <div style="width: 100%;float: left;height: 5px"></div>
                    <div id="itemInventoryTableList1"></div>
                    <div class="page_newlist">
                    </div>
                    <!--综合结束 -->
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
