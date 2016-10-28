/**
 * Created by Administrtor on 2015/5/11.
 */
$(document).ready(function () {
    $("#showTable").initTable({
        url:path+'/ajax/checkPicUrlEbay.do',
        columnData:[
            {title:"图片",name:"Options",width:"3%",align:"center",format:picUrl},
            {title:"图片地址",name:"url",width:"16%",align:"left"},
            {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"Option1",width:"4%",align:"left",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        showDataNullMsg:false
    });
    refreshTable();
});
function picUrl(json){
    var html = "<img src='"+json.url+"' style='width:40px;height:40px;'/>";
    return html;
}
/**组装操作选项*/
function makeOption1(json){
    var hs="";
    hs+="<li style='height:25px' onclick=uploadPic('"+json.id+"') value='"+json.id+"' doaction=\"look\" >上传</li>";
    hs+="<li style='height:25px' onclick=deltePic('"+json.id+"','"+json.mackId+"') value='"+json.id+"' doaction=\"look\" >删除</li>";
    var pp={"liString":hs};
    return getULSelect(pp);
}

function refreshTable(){
    var api = frameElement.api, W = api.opener;
    var picid ="";
    var morepicid = "";
    for(var i=0;i<W.document.getElementsByName("pic_mackid").length;i++){
        picid+=W.document.getElementsByName("pic_mackid")[i].value+",";
    }
    for(var i=0;i<W.document.getElementsByName("pic_mackid_more").length;i++){
        morepicid+=W.document.getElementsByName("pic_mackid_more")[i].value+",";
    }
    var param={};
    if(picid!=null&&picid!=""){
        param={"pic_mackid":picid,"pic_mackid_more":morepicid};
    }
    $("#showTable").selectDataAfterSetParm(param);
}
/**
 * 上传图片到ebay图片服务器
 * @param ids
 */
function uploadPic(ids){
    var api = frameElement.api, W = api.opener;
    var ebayAccount=W.document.getElementsByName("ebayAccounts")[0].value;
    $().invoke(
            path+'/ajax/uploadPicToEbay.do?ids='+ids+"&ebayAccount="+ebayAccount,
        {},
        [function (m, r) {
            refreshTable();
        },
            function (m, r) {
                refreshTable();
            }],{isConverPage:true}
    )
}

function deltePic(ids,mackid){
    var api = frameElement.api, W = api.opener;

    $().invoke(
            path+'/ajax/deltePic.do?ids='+ids,
        {},
        [function (m, r) {
            $(W.document.getElementsByName("pic_mackid")).each(function(i,d){
                if($(d).val()==mackid){
                    $(d).parent().parent().remove();
                }
            });
            refreshTable();
        },
            function (m, r) {
                refreshTable();
            }],{isConverPage:true}
    )
}