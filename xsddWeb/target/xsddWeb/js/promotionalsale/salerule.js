/**
 * Created by Administrtor on 2015/6/18.
 */
var api = frameElement.api, W = api.opener;
function closeRulePage(){
    W.rulePage.close();
}

function saveRule(){
    var proName = $("#promotionalsaleName").val();
    if(!/[a-zA-Z_0-9]+$/ig.test(proName)){
        alert("名称只能用英文、数字、下划线！");
        return;
    }
    var url=path+"/promotionalsale/ajax/saveRuel.do";
    if(!$("#Form").validationEngine("validate")){
        $('#Form').validationEngine('updatePromptsPosition')
        return;
    }
    var data = $('#Form').serialize();
    $().invoke(url,data,
        [function(m,r){
            alert(r);
            Base.token();
            W.rulePage.close();
            W.refreshTablebuyer();
        },
            function(m,r){
                alert(r);
                Base.token();
            }],{isConverPage:true}
    );
}
function selectOp(obj){
    if($(obj).val()=="Price"){
        $("#priceid").show();
        $("#percentageid").hide();
    }else if($(obj).val()=="Percentage"){
        $("#priceid").hide();
        $("#percentageid").show();
    }
}