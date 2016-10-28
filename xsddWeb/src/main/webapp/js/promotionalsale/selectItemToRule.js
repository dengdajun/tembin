/**
 * Created by Administrtor on 2015/6/23.
 */
var api = frameElement.api, W = api.opener;
$(document).ready(function() {
    loadRuleItem();
    loadItemSelect();
    mySelect2I([{url:_url,doitAfterSelect:selectData,
        data:{currInputName:"content"},bs:"#_selectvalue",multiple:false,maping:_map}]);
});

function loadItemSelect(){
    $("#item").initTable({
        url:path + "/promotionalsale/ajax/loadListingDataItemQueryList.do",
        columnData:[
            {title:"选择",name:"itemId",width:"8%",align:"left",format:checkSelect1},
            {title:"名称",name:"title",width:"8%",align:"left",format:getTitle1},
            {title:"图片",name:"picUrl",width:"8%",align:"left",format:getImge1},
            {title:"SKU",name:"sku",width:"8%",align:"left"}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTableItem();
}
function selectListingItemData(){
    if($("select[name='selecttype']").find("option:selected").val()==null||$("select[name='selecttype']").find("option:selected").val()==undefined||$("select[name='selecttype']").find("option:selected").val()==""){
        alert("未选择查询类型！");
        return;
    }
    var selectType = $("select[name='selecttype']").find("option:selected").val();
    var selectValue = $("#_selectvalue").val();
    if(selectValue==""||selectValue==null||selectValue==undefined){
        alert("查询值未输入！");
        return;
    }

    var site = $("#site").val();
    var ebayAccount = $("#ebayAccount").val();
    var ruleId = $("#ruleId").val();
    $("#item").selectDataAfterSetParm({"site":site, "ebayAccount":ebayAccount,"ruleId":ruleId,"selectType":selectType,"selectValue":selectValue});
}
function refreshTableItem(){
    var site = $("#site").val();
    var ebayAccount = $("#ebayAccount").val();
    var ruleId = $("#ruleId").val();
    $("#item").selectDataAfterSetParm({"site":site, "ebayAccount":ebayAccount,"ruleId":ruleId});
}

function getImge1(json){
    return "<img src='"+json.picUrl+"'/>"
}

function checkSelect1(json){
    var html="<input type='checkbox' name='select' onchange='selectItem("+json.itemId+",this)'/>";
    return html;
}
function getTitle1(json){
    var html="";
    if(json.title.length>20){
        html+=json.title.substr(0,20)+"...";
    }else{
        html+=json.title;
    }
    return html;
}
function selectItem(itemId,obj){
    var ruleId = $("#ruleId").val();
    var action = "add";
    if($(obj).prop("checked")){//选中商品到规则表中
        var url=path+"/promotionalsale/ajax/saveItemToRuel.do";
        var data = {"action":action,"ruleId":ruleId,"itemId":itemId};
        $().invoke(url,data,
            [function(m,r){
                loadRuleItem();
                loadItemSelect();
            },
                function(m,r){
                    alert(r);
                }]
        );
    }

}
function loadRuleItem(){
    $("#selectItem").initTable({
        url:path + "/promotionalsale/ajax/loadListingDataOnSelectItemQueryList.do",
        columnData:[
            {title:"选择",name:"itemId",width:"8%",align:"left",format:checkSelect2},
            {title:"名称",name:"title",width:"8%",align:"left",format:getTitle1},
            {title:"图片",name:"picUrl",width:"8%",align:"left",format:getImge1},
            {title:"SKU",name:"sku",width:"8%",align:"left"}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTableSelectItem();
}

function checkSelect2(json){
    var html="<input type='checkbox' name='select' onchange='selectItem2("+json.itemId+",this)'/>";
    return html;
}
function selectItem2(itemId,obj){
    var ruleId = $("#ruleId").val();
    var action = "del";
    if($(obj).prop("checked")){//选中商品到规则表中
        var url=path+"/promotionalsale/ajax/saveItemToRuel.do";
        var data = {"action":action,"ruleId":ruleId,"itemId":itemId};
        $().invoke(url,data,
            [function(m,r){
                loadRuleItem();
                loadItemSelect();
            },
                function(m,r){
                    alert(r);
                }]
        );
    }
}
function refreshTableSelectItem(){
    var site = $("#site").val();
    var ebayAccount = $("#ebayAccount").val();
    var ruleId = $("#ruleId").val();
    $("#selectItem").selectDataAfterSetParm({"site":site, "ebayAccount":ebayAccount,"ruleId":ruleId});
}


function apiSu(){
    var ruleId = $("#ruleId").val();
    var url=path+"/promotionalsale/ajax/saveApiData.do";
    var data = {"ruleId":ruleId};
    $().invoke(url,data,
        [function(m,r){
            alert(r);
            W.rulePage.close();
            W.refreshTablebuyer();
        },
            function(m,r){
                alert(r);
                W.rulePage.close();
                W.refreshTablebuyer();
            }],{isConverPage:true}
    );
}