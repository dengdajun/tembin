function saveListingSet(){
    var url=path+"/ajax/saveListingSet.do";
    if(!$("#form1").validationEngine("validate")){
        $('#form1').validationEngine('updatePromptsPosition')
        return;
    }
    var data = $('#form1').serialize();
    $().invoke(url,data,
        [function(m,r){
            alert(r);
            Base.token();
        },
            function(m,r){
                alert(r);
            }],{isConverPage:true}
    );
}


function loadListingSet(){
    var url=path+"/ajax/loadListingSet.do";
    $().invoke(url,{"orgId":orgId},
        [function(m,r){
            if(r.autoListing=="0"){
                $("#autoListing").prop("checked",true);
            }
            $("#id").val(r.id);
            $("#startDate").val(r.startDate);
            $("#endDate").val(r.endDate);
        },
            function(m,r){
                alert(r);
            }],{isConverPage:true}
    );
}
$(document).ready(function(){
    loadListingSet();
});