/**
 * Created by Administrtor on 2015/6/18.
 */
$(document).ready(function(){
    loadRule();
});
function loadRule(){
    $("#ruleShow").initTable({
        url:path + "/promotionalsale/ajax/loadPromotionalSaleQueryList.do",
        columnData:[
            {title:"名称",name:"promotionalsaleName",width:"8%",align:"left",format:function(json){return "&nbsp;&nbsp;"+json.promotionalsaleName}},
            {title:"站点",name:"siteName",width:"8%",align:"left",format:getSiteImg},
            {title:"ebay账号",name:"ebayAccountName",width:"8%",align:"left"},
            {title:"促销类型",name:"discounttype",width:"8%",align:"center",format:getproType},
            {title:"值",name:"discountvalue",width:"8%",align:"center"},
            {title:"开始时间",name:"promotionalsalestarttime",width:"8%",align:"center"},
            {title:"结束时间",name:"promotionalsaleendtime",width:"8%",align:"center"},
            {title:"状态",name:"status",width:"8%",align:"center"},
            {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"option1",width:"8%",align:"left",format:doAmat}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTablebuyer();
}
function getproType(json){
    if(json.discounttype=="Price"){
        return "降价";
    }else if(json.discounttype=="Percentage"){
        return "折扣";
    }
}
function doAmat(json){
    var hs = "";
    hs += "<li style='height:25px' onclick=addItemtoSet('" + json.id + "','"+json.siteid+"','"+json.ebayaccountid+"') value='" + json.id + "' doaction=\"look\" >添加商品</li>";
    var pp={"liString":hs,"marginLeft":"-50px"};
    return getULSelect(pp);
}



function getSiteImg(json){
    var html='<img src="'+path+json.siteurl+'"/>';
    return html;
}

function refreshTablebuyer(){
    $("#ruleShow").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}

function addData(){
    if($(".new_tab_1").attr("id")=="menu1"){
        addRule();
    }else if($(".new_tab_1").attr("id")=="menu2"){

    }
}

var rulePage
function addRule(){
    rulePage=$.dialog({title: '新增促销规则',
        content: 'url:/xsddWeb/promotionalsale/addRule.do',
        icon: 'succeed',
        width:800,
        lock:true
    });
}

function addItemtoSet(id,siteid,ebayaccount){
    rulePage=$.dialog({title: '新增商品到规则',
        content: 'url:/xsddWeb/promotionalsale/addItemToRule.do?ruleId='+id+"&site="+siteid+"&ebayAccount="+ebayaccount,
        icon: 'succeed',
        width:1200,
        height:600,
        lock:true
    });
}
