/**
 * Created by Administrtor on 2015/7/13.
 */
$(document).ready(function(){
    storeCategoryList();
});
/**组装操作选项*/
function makeOption0(json){
    var htm="<input type=radio name='id' value='"+json.id+"' categoryId='"+json.storeCategoryId+"' categoryName='"+json.storeCategoryName+"' />";
    return htm;
}
function storeCategoryList(){
    $("#storeCategory").initTable({
        url:path + "/ajax/loadStoreCategoryList.do",
        columnData:[
            {title:"选择",name:"id",width:"8%",align:"left",format:makeOption0},
            {title:"分类ID",name:"storeCategoryId",width:"8%",align:"left"},
            {title:"分类名称",name:"storeCategoryName",width:"8%",align:"left"}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        onlyFirstPage:true
    });
    refreshTableStoreCategory();
}
function refreshTableStoreCategory(){
    var ebayAccountId = $("#ebayAccountId").val();
    $("#storeCategory").selectDataAfterSetParm({"ebayAccountId":ebayAccountId});
}
var api = frameElement.api, W = api.opener;
function parentStore() {
    var d = $("input[type='radio'][name='id']:checked");
    W.document.getElementById('storeCategoryId').value = $(d).val();
    W.document.getElementById('Storefront.storeCategoryID').value = $(d).attr("categoryId");
    var cateName = $(d).attr("categoryName");
    if (cateName.indexOf("-") != -1) {
        cateName = cateName.substr(cateName.lastIndexOf("-") + 1, cateName.length);
    }
    W.document.getElementById('Storefront.storeCategoryName').value=cateName;
    W.storeCategoryPage.close();
}
