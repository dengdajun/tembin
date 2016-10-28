/**
 * Created by Administrtor on 2015/4/2.
 */
$(document).ready(function () {
    loadTableData();
});
function loadTableData(){
    $("#showData").initTable({
        url:path+"/alieproduct/ajax/getSmtProductList.do",
        columnData: [
            {title:"选择",name:"id",width:"2%",align:"center"},
            {title:"图片",name:"imgSrc",width:"8%",align:"center",format:imgUrl},
            {title:"标题",name:"subject",width:"20%",align:"center"},
            {title:"备货期",name:"deliverytime",width:"8%",align:"center"},
            {title:"单位",name:"unitName",width:"4%",align:"center"},
            {title:"长",name:"packagelength",width:"2%",align:"center"},
            {title:"宽",name:"packagewidth",width:"2%",align:"center"},
            {title:"高",name:"packageheight",width:"2%",align:"center"},
            {title:"毛重",name:"grossweight",width:"2%",align:"center"},
            {title:"有效天数",name:"wsvalidnum",width:"6%",align:"center"},
            {title:"产品状态",name:"productstatustype",width:"6%",align:"center"},
            {title:"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"Option1",width:"6%",align:"left",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        isrowClick: true,
        rowClickMethod: function (obj,o){

        }
    });
    refreshTable();
}

function refreshTable(){
    var param={};
    $("#showData").selectDataAfterSetParm(param);
}

function imgUrl(json){
    var htm="<a target='_blank' href='#'><img width='50px' height='50px' src='"+json.imgSrc+"'/></a>";
    return htm;
}
/**组装操作选项*/
function makeOption1(json){
    var hs="";
    hs += "<li style='height:25px' onclick=editsmtProduct('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
    var pp={"liString":hs};
    return getULSelect(pp);
}

function addSmtProductPage(){
    location.href=path+"/alieproduct/addalieproduct.do";
}

function editsmtProduct(productId){
    location.href=path+"/alieproduct/eidtSmtpProduct.do?productId="+productId;
}