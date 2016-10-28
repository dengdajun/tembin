<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script>
        $(document).ready(function(){
            initSearchInput();
            setTimeout(function(){
                onloadTable();
            },500);
        });
        function chageOldDom(obj){
            initSearchInput($(obj).val());
            $("input[id='selectvalue']").val('');
        }
        /**初始化select2搜索框*/
        function initSearchInput(bs){
            var _url="";
            var _map={};
            if(bs=='sku'){
                _url=path+"/informationType/ajax/loadOrgIdItemInformationList.do";
                _map={id:"sku",text:"sku"};
            }

            mySelect2I([{url:_url,doitAfterSelect:refreshTableabc,
                data:{currInputName:"content"},bs:"#selectvalue",multiple:false,maping:_map}]);
        }
        function onloadTable(){
            $("#clientAssessTable").initTable({
                url:path+"/ajax/loadClientAssessTable.do",
                columnData:[
                    {title:"买家",name:"commentinguser",width:"6%",align:"center"},
                    {title:"评价内容",name:"commenttext",width:"40%",align:"left",format:getMassess},
                    {title:"评价类型",name:"commenttype",width:"6%",align:"center",format:getcommentType},
                    {title:"EBAY账号",name:"ebayAccount",width:"6%",align:"center"},
                    {title:"商品",name:"itemtitle",width:"20%",align:"left",format:getTitle},
                    {title:"评价时间",name:"commenttime",width:"6%",align:"center",format:getDateStr},
                    {title:"操作",name:"Option1",width:"6%",align:"center",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        }
        function getMassess(json){
            var html  = json.commenttext;
            if(json.replyflag=="1"){
                html+="</br><span style='color: green'>Reply:"+json.replytext+"</span>";
            }
            return html;
        }
        /**组装操作选项*/
        function makeOption1(json){
            var hs="";
            hs+="<li style='height:25px' onclick=replytext('"+json.id+"') value='"+json.id+"' doaction=\"look\" >回复</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        var myreply
        function replytext(id){
            myreply = openMyDialog({title: '回复',
                content: 'url:'+path+'/feedbackreply.jsp',
                icon: 'tips.gif',
                width:450,
                button: [
                    {
                        name: '确定',
                        callback: function (iwins, enter) {
                            var reason = "";
                            reason = myreply.content.document.getElementById("centents").value;
                            if(reason.length>80){
                                alert("回复内容不能超过80个字符！");
                                return;
                            }
                            var url = path+"/ajax/replyFeedBack.do?id=" + id+"&reply="+reason;
                            $().invoke(url, {},
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
                ]
            });
        }
        function getcommentType(json){
            var html="";
            if(json.commenttype=="Positive"){
                html="<img src='"+path+"/img/new_one_1.gif'/>"
            }else if(json.commenttype=="Neutral"){
                html="<img src='"+path+"/img/new_one_2.gif'/>"
            }else if(json.commenttype=="Negative"){
                html="<img src='"+path+"/img/new_one_3.gif'/>"
            }
            return html;
        }
        function  refreshTable(){
            var selectValue = "";
            var param={};
            param={"commentAmount":$("input[type='hidden'][name='commentAmount']").val(),"commentType":$("input[type='hidden'][name='commentType']").val(),"selecttype":$("select[name='selecttype']").val(),"selectvalue":selectValue};
            $("#clientAssessTable").selectDataAfterSetParm(param);
        }
        function  refreshTableabc(){
            var selectValue = $("#selectvalue").select2("data")!=null?$("#selectvalue").select2("data").text:"";
            var param={};
            param={"commentAmount":$("input[type='hidden'][name='commentAmount']").val(),"commentType":$("input[type='hidden'][name='commentType']").val(),"selecttype":$("select[name='selecttype']").val(),"selectvalue":selectValue};
            $("#clientAssessTable").selectDataAfterSetParm(param);
        }

        function getTitle(json){
            return "<a target='_blank' tlitle='"+json.itemtitle+"' href='"+serviceItemUrl+json.itemid+"'>"+json.itemtitle+"</a>";
        }
        function getDateStr(json){
            return "<span style='color: #7B7B7B;'>"+json.commenttime.replace(" ","</br>")+"</span>";
        }

        function selectListType(obj){
            var div=$(obj).parent();
            var as=$(div).find("a");
            for(var i=0;i<as.length;i++){
                var span=$(as[i]).find("span");
                $(span[0]).attr("class","newusa_ici_1");
            }
            var span1=$(obj).find("span");
            $(span1[0]).attr("class","newusa_ici");
            $("input[name='commentType']").val($(obj).attr("value"));
            refreshTable();
        }
        function selectAmount1(obj){
            var div=$(obj).parent();
            var as=$(div).find("a");
            for(var i=0;i<as.length;i++){
                var span=$(as[i]).find("span");
                $(span[0]).attr("class","newusa_ici_1");
            }
            var span1=$(obj).find("span");
            $(span1[0]).attr("class","newusa_ici");
            $("input[name='commentAmount']").val($(obj).attr("value"));
            refreshTable();
        }
    </script>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/select2/select2.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/select2/select2.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/select2/mySelect2.js" /> ></script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 客户管理 > <b>客户评价</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls" id="selectModel">
            <dt id=menu1 name="autoAssess" class=new_tab_1 onclick="setAssessTab(this)">客户评价</dt>
        </div>

        <form id="clientAssess">
        <input type="hidden" name="commentType">
        <input type="hidden" name="commentAmount">
        <div class=Contentbox id="Contentbox">
            <div class="new_usa" style="margin-top:20px;">
                <li class="new_usa_list">
                    <span class="newusa_i">评价类型：</span>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value=""><span
                            class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Positive"><span
                            class="newusa_ici_1">好评</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Neutral"><span
                            class="newusa_ici_1">中评</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Negative"><span
                            class="newusa_ici_1">差评</span></a>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">选择账号：</span>
                    <a href="javascript:void(0)" onclick="selectAmount1(this)" value=""><span
                            class="newusa_ici">全部</span></a>
                    <c:forEach items="${ebays}" var="ebay">
                        <a href="javascript:void(0)" onclick="selectAmount1(this)" value="${ebay.ebayName}"><span
                                class="newusa_ici_1">${fn:toUpperCase(ebay.ebayNameCode)}</span></a>
                    </c:forEach>
                </li>
                <div class="newsearch">
                    <span class="newusa_i">搜索内容：</span>
                    <%--<a href="javascript:void(0)" onclick="selectAllData(this)" value="">
                        <span class="newusa_ici">全部</span>
                    </a>--%>
                        <span id="sleBG" style="width:82px;background-position: 67px 10px;">
                            <span id="sleHid" style="width: 80px;">
                                <select name="selecttype" class="select" style="color: #737FA7;width: 80px;" onchange="chageOldDom(this)">
                                    <option selected="selected" value="">选择类型</option>
                                    <option value="ItemID">物品号</option>
                                    <option value="TransactionID">交易号</option>
                                    <option value="OrderLineItemID">定单号</option>
                                    <option value="ItemTitle">物品title</option>
                                </select>
                            </span>
                        </span>
                    <div class="vsearch">
                        <input id="selectvalue" name="selectvalue" type="text" style="width:200px;float: left"  multiple class="multiSelect">
                        <input name="newbut" style="display: none;" onclick="refreshTable()" type="button" class="key_2">
                    </div>
                </div>
                <div style="width: 100%;float: left;height: 5px"></div>
                <div id="clientAssessTable"></div>
            </div>
        </div>
        </form>
    </div>

</div>
</body>
</html>
