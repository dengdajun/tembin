/**
 * Created by Administrtor on 2015/4/7.
 */
var searchi;
$(document).ready(function(){
    $("#SMTorderTableList1").initTable({
        url:path + "/SMTorder/ajax/loadSMTorderList.do?",
        columnData:[
            {title:"",name:"ch",width:"1%",align:"left",format:makeOption1},
            {title:"图片/订单号",name:"pictureUrl",width:"4%",align:"center",format:makeOption2},
            {title:"<div align='center' style='width: 100%;'>商品/SKU</div>",name:"skuCode",width:"16%",align:"left",format:makeOption4},
            {title:"售价",name:"price",width:"8%",align:"center",format:makeOption3},
            {title:"修改时间",name:"gmtmodified",width:"8%",align:"center"},
            {title:"创建时间",name:"gmtcreate",width:"8%",align:"center"},
            {title:"状态",name:"status",width:"8%",align:"center"},
             {title:"<div align='center' style='width: 100%;'>操作</div>",name:"option1",width:"2%",align:"center",format:makeOption5}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        showDataNullMsgContext:'没有订单记录!'
    });
    refreshTable(1);
    initSearchInput("",1);

    });
function makeOption5(json){
    var hs="";
    hs="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"',this,'"+json.id+"'); value='1' doaction=\"readed\" >查看详情</li>";
    hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"',this,'"+json.id+"'); value='2' doaction=\"look\" >上传跟踪</li>";
    hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"',this,'"+json.id+"'); value='3' doaction=\"look\" >发送留言</li>";
/*    hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"',this,'"+json.id+"'); value='4' doaction=\"look\" >退款功能</li>";*/
    hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"',this,'"+json.id+"'); value='5' doaction=\"look\" >添加备注</li>";
/*    hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"',this,'"+json.id+"'); value='6' doaction=\"look\" >删除</li>";*/
    hs+="<li style=\"height:25px;\"  onclick=selectOperation('"+json.orderid+"',this,'"+json.id+"'); value='7' doaction=\"look\" >查看日志</li>";
    var pp={"liString":hs};
    return "<div style='width: 100%' align='center'>"+getULSelect(pp)+"</div>";
    /* return htm;*/
}
function selectOperation(orderid,obj,id){
    var value=$(obj).attr("value");
    if(value=="1"){
        viewOrder(orderid);
    }
    if(value=="2"){
        modifyOrderStatus(orderid);
    }
    if(value=="3"){
        sendMessage(orderid);
    }
  /*  if(value=="4"){
        issueRefund(orderid);
    }*/
    if(value=="5"){
        addComment1(id);
    }
   /* if(value=="6"){
        deleteOrder(id,obj);
    }*/
    if(value=="7"){
        viewSystemlog(id);
    }

}
function addComment1(id){
    var url=path+"/SMTorder/addComment.do?ids="+id;
    SMTGetOrders=openMyDialog({title: '添加备注',
        content: 'url:'+url,
        icon: 'succeed',
        width:600,
        lock:true
    });
}
function sendMessage(orderid){
    var url=path+'/SMTorder/initOrdersSendMessage.do?orderid='+orderid;
    OrderGetOrders=openMyDialog({title: '发送留言',
        content: 'url:'+url,
        icon: 'succeed',
        width:800,
        height:550,
        lock:true
    });
}
function viewOrder(orderid){
    var url=path+'/SMTorder/viewOrderGetOrders.do?orderid='+orderid;
    OrderGetOrders=openMyDialog({title: '查看订单详情',
        content: 'url:'+url,
        icon: 'succeed',
        width:1200,
        height:850,
        lock:true
    });
}
function modifyOrderStatus(orderid){
    var url=path+'/SMTorder/modifyOrderStatus.do?orderid='+orderid;
    OrderGetOrders=openMyDialog({title: '上传跟踪号',
        content: 'url:'+url,
        icon: 'succeed',
        width:700,
        lock:true
    });
}
function addComment(i){
    var table="#SMTorderTableList"+i;
    var checkboxs=$(table).find("input[name=checkbox]:checked");
    if(checkboxs&&checkboxs.length>0){
    var ids="";
    for(var i=0;i<checkboxs.length;i++){
    if(i==(checkboxs.length-1)){
    ids+=$(checkboxs[i]).attr("value1");
    }else{
    ids+=$(checkboxs[i]).attr("value1")+",";
    }
    }
    var url=path+"/SMTorder/addComment.do?ids="+ids;
    SMTGetOrders=openMyDialog({title: '添加备注',
    content: 'url:'+url,
    icon: 'succeed',
    width:600,
    lock:true
    });
    }else{
    alert("请选择需要添加备注的订单");
    }
    }
function refreshTable(i){
    $("#SMTorderTableList"+i).selectDataAfterSetParm({});
    }
function makeOption1(json){
    var htm="<input type='checkbox' id='checkbox' name='checkbox' value1='"+json.id+"'  value='"+json.orderid+"' />";
    return htm;
    }
function makeOption2(json) {
    var htm1 = "<img src='"+json.pictureUrl+ "' style='width: 50px;height:50px;' /><br>";
    var htm = "<font style='color:gray'>" + json.orderid + "<font>";
    return htm1 + htm;
    }
function makeOption3(json) {
    return json.price+" "+json.cur;
    }
function makeOption4(json){
    var productName=json.productName;
    if(productName.length>100){
    productName=productName.substring(0,100)+"...";
    }
    var htm="<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">"+json.firstName+" "+json.lastName+"  </font> <font style='color: #808080'>( "+json.buyerEmail+")</font></span>";
    htm+="<span style=\"width:100%; float:left\"><font color=\"#5F93D7\"><a href='"+json.productSnapUrl+"' target=\"1\" title='"+productName+"'>"+productName+"</a></font><br><font style='float: left;color: #808080'>("+json.productId+")</font>";
    if(json.logisticsNo&&json.logisticsNo!=""){
    htm+="<span class=\"newbgspan_3\" style='margin-top:2px;margin-left:8px;border-radius: 3px;height: 12px;line-height:12px;'>"+json.logisticsServiceName+"</span>&nbsp;<span class=\"newbgspan_3\" style='margin-top:2px;border-radius: 3px;height: 12px;line-height:12px;'>"+json.logisticsNo+"</span>";
    }

    /* var skuShow=json.variationsku;//如果多属性sku为空，那么就显示主sku
     skuShow=(skuShow==null||skuShow=='')?(json.sku):skuShow;*/
    if(json.skuCode&&json.skuCode!=''){
    htm+="</span><span style=\"width:100%; float:left; color:#8BB51B\">"+json.skuCode+"</span>";

    }

    /*if(json.feedbackMessage!=""){
     htm+="<span style='width:100%; float:left'><font color='#5F93D7' style='margin-bottom: 100px;'>B：</font><span style='margin-bottom: 20px;'>";
     if(json.feedbackMessageType=="Positive"){
     htm+="<img src='"+path+"/img/new_one_1.gif' width='12' height='12'>";
     }else if(json.feedbackMessageType=="Neutral"){
     htm+="<img src='"+path+"/img/new_one_2.gif' width='12' height='12'>";
     }else{
     htm+="<img src='"+path+"/img/new_one_3.gif' width='12' height='12'>";
     }
     htm+=   "</span><font style='color: #000000'>&nbsp;"+json.feedbackMessage+"</font></span>";
     }
     if(json.feedbackMessage1!=""){
     htm+="<span style='width:100%; float:left'><font color='#5F93D7'>S：</font><span><img src='"+path+"/img/add.png' width='12' height='12'></span><font style='color: #000000'>&nbsp;"+json.feedbackMessage1+"</font></span>";
     }*/
    if(json.comment&&json.comment!=""){
    htm+="<br/><span id='commentId' class=\"newdf\" style='border-radius: 3px;' title=\""+json.comment+"\">备注："+json.comment+"</span>";
    }
    return htm;
    }
var SMTGetOrders;
function ebayListPage(){
    var url=path+"/SMTorder/ebayListPage.do";
    SMTGetOrders=openMyDialog({title: '同步订单',
    content: 'url:'+url,
    icon: 'succeed',
    width:600,
    lock:true,
    zIndex:500
    });
    }
function queryCountry(n,flag,day){
    var scop="span[scop=queryCountry"+n+"]";
    var time=$(scop);
    for(var i=0;i<time.length;i++){
    if(i==(flag-1)){
    $(time[i]).attr("class","newusa_ici");
    $("#countryQ"+n).val(day);
    }else{
    $(time[i]).attr("class","newusa_ici_1");
    }
    }
    query1();
    }
function queryTime(n,flag,day){
    var scop="span[scop=queryTime"+n+"]";
    var time=$(scop);
    for(var i=0;i<time.length;i++){
    if(i==(flag-1)){
    $(time[i]).attr("class","newusa_ici");
    /* <input type="hidden" id="countryQ"/>
     <input type="hidden" id="typeQ"/>
     <input type="hidden" id="daysQ"/>*/
    $("#daysQ"+n).val(day);
    $("#starttime"+n).val("");
    $("#endtime"+n).val("");
    }else{
    $(time[i]).attr("class","newusa_ici_1");
    }
    }
    if(flag<6){
    document.getElementById("time"+searchi).innerHTML="";
    query1();
    }
    }
function queryStatus(n,flag,day){
    var scop="span[scop=queryStatus"+n+"]";
    var time=$(scop);
    for(var i=0;i<time.length;i++){
    if(i==(flag-1)){
    $(time[i]).attr("class","newusa_ici");
    $("#statusQ"+n).val(day);
    }else{
    $(time[i]).attr("class","newusa_ici_1");
    }
    }
    query1();
    }
function addstartTimeAndEndTime(obj,i){
    var time=$("#time"+i);
    var query="query1();";
    var t=$("input[id=starttime"+i+"]");
    if(t.length==0){
    var span="<span style=\"float: left;color: #5F93D7;\">从</span><input class=\"form-controlsd \" style=\"float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9\" id=\"starttime"+i+"\"  type=\"text\" onfocus=\"WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})\"/>" +
    "<span style=\"float: left;color: #5F93D7;\">到</span><input class=\"form-controlsd \" style=\"float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9\" id=\"endtime"+i+"\"  type=\"text\" onfocus=\"WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})\"/>"
    +"<input value1='input"+i+"' style='border:0;background-color:#ffffff;float: left;color: #5F93D7;height: 26px;' value='确定' onclick='"+query+"' type='button'>";
    $(time).append(span);
    }
    }
function query(n){
    var daysQ=$("#daysQ"+n).val();
    var countryQ=$("#countryQ"+n).val();
    var typeQ=$("#typeQ"+n).val();
    var itemType=$("#itemType"+n).val();
    var content=$("#content"+n).select2("data")!=null?$("#content"+n).select2("data").text:"";
    var table="#SMTorderTableList"+n;
    var starttime=$("#starttime"+n).val();
    var endtime=$("#endtime"+n).val();
    var statusQ=$("#statusQ"+n).val();
    var accountQ=$("#accountQ"+n).val();
    if(content!=""&&itemType!="title"){
    content = content.trim();
    }
    refreshTable1(table,countryQ,typeQ,daysQ,itemType,content,null,starttime,endtime,statusQ,accountQ,null);
    }
function query1(){

    var daysQ=$("#daysQ"+searchi).val();
    var countryQ=$("#countryQ"+searchi).val();
    var typeQ=$("#typeQ1"+searchi).val();
    var itemType=$("#itemType"+searchi).val();
    var content=$("#content"+searchi).select2("data")!=null?$("#content"+searchi).select2("data").text:"";
    var table="#SMTorderTableList"+searchi;
    var starttime=$("#starttime"+searchi).val();
    var endtime=$("#endtime"+searchi).val();
    var statusQ=$("#statusQ"+searchi).val();
    var accountQ=$("#accountQ"+searchi).val();
    /*alert(searchi);*/
    if(content!=""&&itemType!="title"){
    content = content.trim();
    }
    if(searchi==1){
    refreshTable1(table,countryQ,typeQ,daysQ,itemType,content,null,starttime,endtime,statusQ,accountQ,null);
    }else if(searchi==2){
    refreshTable1(table,countryQ,typeQ,daysQ,itemType,content,'unpaid',starttime,endtime,statusQ,accountQ,null);
    }else if(searchi==3){
    refreshTable1(table,countryQ,typeQ,daysQ,itemType,content,'uncleared',starttime,endtime,statusQ,accountQ,null);
    }else if(searchi==4){
    refreshTable1(table,countryQ,typeQ,daysQ,itemType,content,'paid',starttime,endtime,statusQ,accountQ,null);
    }else if(searchi==5){
    refreshTable1(table,countryQ,typeQ,daysQ,itemType,content,'cancel',starttime,endtime,statusQ,accountQ,null);
    }else{
    var folder=$("#menu"+searchi).attr("name2");
    refreshTable1(table,countryQ,typeQ,daysQ,itemType,content,null,starttime,endtime,statusQ,accountQ,folder);
    }

    }
function refreshTable1(table,countryQ,typeQ,daysQ,itemType,content,status,starttime,endtime,statusQ,accountQ,folderId){
    $(table).initTable({
        url:path + "/SMTorder/ajax/loadSMTorderList.do?",
        columnData:[
            {title:"",name:"ch",width:"1%",align:"left",format:makeOption1},
            {title:"图片/订单号",name:"pictureUrl",width:"4%",align:"center",format:makeOption2},
            {title:"<div align='center' style='width: 100%;'>商品/SKU</div>",name:"skuCode",width:"16%",align:"left",format:makeOption4},
            {title:"售价",name:"price",width:"8%",align:"center",format:makeOption3},
            {title:"修改时间",name:"gmtmodified",width:"8%",align:"center"},
            {title:"创建时间",name:"gmtcreate",width:"8%",align:"center"},
            {title:"状态",name:"status",width:"8%",align:"center"},
            {title:"<div align='center' style='width: 100%;'>操作</div>",name:"option1",width:"2%",align:"center",format:makeOption5}
            /* {title:"操作&nbsp;&nbsp;",name:"option1",width:"2%",align:"center",format:makeOption1}*/
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        showDataNullMsgContext:'没有订单记录!'
    });
    $(table).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"countryQ":countryQ,"typeQ":typeQ,"daysQ":daysQ,"itemType":itemType,"content":content,"status":status,"starttime1":starttime,"endtime1":endtime,"statusQ":statusQ,"accountQ":accountQ,"folderId":folderId});
    }
function initSearchInput(bs,n){
    searchi=n;
    var _url="";
    var _map={};
    if(bs=='sku'){
    _url=path+"/informationType/ajax/loadOrgIdItemInformationList.do";
    _map={id:"sku",text:"sku"};
    }

    mySelect2I([{url:_url,doitAfterSelect:query1,
    data:{currInputName:"content"},bs:"#content"+n,multiple:false,maping:_map}]);
    }
function chageOldDom(obj,n){
    cleanInput();
    initSearchInput($(obj).val(),n);
    $("input[id=content"+n+"]").val('');
    }
function selectAllCheck(i,obj){
    var table="#SMTorderTableList"+i;
    var checkboxs=$(table).find("input[name=checkbox]");
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
function cleanInput(){
    var inputs=$("input[class=key_2]");
    for(var i=0;i<inputs.length;i++){
    document.getElementById("content"+(i+1)).value="";
    }
    }
function selectCountrys(){
    var dts=$("#tabRemark").find("dt");
    var countryNames=$("#countryNames").val();
    var num=dts.length;
    var url=path+"/SMTorder/selectCountrys.do?num="+num+"&countryNames="+countryNames;
    SMTGetOrders=openMyDialog({title: '选择国家',
    content: 'url:'+url,
    icon: 'succeed',
    width:1050,
    height:600,
    lock:true
    });
    }
var OrderGetOrders;
function addTabRemark(){
    var url=path+"/order/selectTabRemark.do?folderType=smtOrderFolder";
    OrderGetOrders=openMyDialog({title: '选择文件夹',
    content: 'url:'+url,
    icon: 'succeed',
    width:500,
    lock:true
    });
    }
function refleshTabRemark(folderType){
    var url=path+"/order/refleshTabRemark.do?folderType="+folderType;
    $().invoke(url,null,
    [function(m,r){
    var div=document.getElementById("tabRemark");
    var remarks=$(div).find("dt[scop=tabRemark]");
    for(var i=0;i<remarks.length;i++){
    $(remarks[i]).remove();
    }
    var dts=$(div).find("dt");
    var total=5+ r.length;
    for(var i=0;i<6;i++){
    var dt=dts[i];
    $(dt).removeAttr("onclick");
    $(dt).attr("onclick","setTab('menu',"+(i+1)+","+total+")");
    initSearchInput("",(i+1));
    }
    var htm="";
    for(var i=0;i< r.length;i++){
    htm+="<dt scop=\"tabRemark\" id=\"menu"+(i+6)+"\" name1='"+(i+6)+"' name2='"+r[i].id+"' name='"+r[i].configName+"' class=\"new_tab_2\" onclick=\"setTab('menu',"+(i+6)+","+ (r.length+5)+")\">"+r[i].configName+"</dt>";
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
function moveFolder(i){
    var table="#SMTorderTableList"+i;
    var checkboxs=$(table).find("input[name=checkbox]:checked");
    if(checkboxs&&checkboxs.length>0){
    var date="";
    for(var j=0;j<checkboxs.length;j++){
    if(j==0){
    date=date+"orderid["+j+"]="+$(checkboxs[j]).val();
    }else{
    date=date+"&orderid["+j+"]="+$(checkboxs[j]).val();
    }
    }
    var url=path+"/SMTorder/moveFolder.do?"+date;
    OrderGetOrders=openMyDialog({title: '移动订单',
    content: 'url:'+url,
    icon: 'succeed',
    width:800,
    lock:true
    });
    }else{
    alert("请选择需要移动的订单");
    }
    }
function downloadOrders(status){
    var ids="";
    var table=$("#SMTorderTableList"+searchi);
    var folder=null;
    var url=path+"/SMTorder/downloadOrders.do?";
    if(searchi>5){
    folder=$("#menu"+searchi).attr("name2");
    }
    if(status!=null){
    url=url+"status="+status;
    if(folder!=null){
    url=url+"&folderId="+folder;
    }
    }else{
    if(folder!=null){
    url=url+"folderId"+folder;
    }
    }
    var inputs=$(table).find("input[id=checkbox][name=checkbox]:checked");
    if(inputs&&inputs.length>0){
    for(var i=0;i<inputs.length;i++){
    if(i==(inputs.length-1)){
    ids+=$(inputs[i]).attr("value1");
    }else{
    ids+=$(inputs[i]).attr("value1")+",";
    }
    }
    url=url+"&ids="+ids;
    }
    window.open(url);
    }
function modifyOrderNums(i){
    var table="#SMTorderTableList"+i;
    var checkboxs=$(table).find("input[name=checkbox]:checked");
    if(checkboxs&&checkboxs.length>0){
    var date="";
    for(var j=0;j<checkboxs.length;j++){
    if(j==0){
    date=date+"id["+j+"]="+$(checkboxs[j]).attr("value1");
    }else{
    date=date+"&id["+j+"]="+$(checkboxs[j]).attr("value1");
    }
    }
    var url=path+"/SMTorder/modifyOrderNums.do?"+date;
    OrderGetOrders=openMyDialog({title: '移动订单',
    content: 'url:'+url,
    icon: 'succeed',
    width:800,
    lock:true
    });
    }else{
    alert("请选择需要上传跟踪号的订单");
    }
    }
/*  function sycOneOrder(){
    var url=path+"/SMTorder/sycOneOrder.do";
    OrderGetOrders=openMyDialog({title: '同步单个订单',
    content: 'url:'+url,
    icon: 'succeed',
    width:600,
    height:200,
    lock:true
    });
    }*/