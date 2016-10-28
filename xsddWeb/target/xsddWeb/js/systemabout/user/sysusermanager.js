/**
 * Created by Administrator on 2014/9/19.
 */

$(document).ready(function(){
    accountManagerTab();
    if(t!=null&&t!=""&&t=='qf'){
        console.log(t)
        setTab('menu',6,7);
        if(u=='self'){
            //setTab('menu',6,7);
        }else{//subUser
            $("#menu2,#menu3,#menu4,#menu5,#menu7").hide();
        }
    }
});

/**账户管理获取数据*/
function accountManagerTab(){
    $("#accountManager").initTable({
        url:path + "/systemuser/getSysManData.do",
        columnData:[
            {title:"姓名",name:"userName",width:"8%",align:"left"},
            {title:"角色",name:"roleName",width:"8%",align:"left"},
            {title:"邮箱",name:"userEmail",width:"8%",align:"left"},
            {title:"电话",name:"telPhone",width:"8%",align:"left"},
            {title:"状态",name:"status11",width:"8%",align:"left",format:makeStatus},
            {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshTable({});
}
/**操作*/
function makeOption1(json){
    var select1="" ;
    if(json.status==1 || json.status=='1'){
        select1+= "<li style='height:25px' onclick=doAccount(this) value='"+json.userId+"' doaction=\"stop\">停用</li>" ;
    }else if(json.status==0 || json.status=='0'){
        select1+= "<li style='height:25px' onclick=doAccount(this) value='"+json.userId+"' doaction=\"start\">启用</li>" ;
    }
    select1+= "<li style='height:25px' onclick=doAccount(this) value='"+json.userId+"' doaction=\"edit\">编辑</li>" ;
    var pp={"liString":select1};
    return getULSelect(pp);
    /*var select1="<div class=\"ui-select\" style=\"width:8px\">" +
        "<select onchange='doAccount(this)'>" ;
    select1+= "<option  value='x'>请选择</option>" ;
    if(json.status==1 || json.status=='1'){
        select1+= "<option  value='"+json.userId+"' doaction=\"stop\">停用</option>" ;
    }else if(json.status==0 || json.status=='0'){
        select1+= "<option  value='"+json.userId+"' doaction=\"start\">启用</option>" ;
    }
    select1+= "<option  value='"+json.userId+"' doaction=\"edit\">编辑</option>" ;
    select1+= "</select></div>";
    return select1;*/
}
/**关于列表操作的下拉======================*/
function doAccount(obj){
    var optionV=obj//$(obj).find("option:selected");
    var v=$(optionV).attr('value');
    if(v=='x'){return;}
    var d=$(optionV).attr('doaction');
    if(d=='edit'){
        editAccount(v);
        return;
    }

    if(d=='start'){
        if(!confirm("启用账户会扣除相应的费用，是否继续？")){
            return;
        }
    }

    var data={"userid":v,"doaction":d};
    $().invoke(
        path+"/systemuser/operationAccount.do",
        data,
        [function(m,r){
            alert(r);
            showStopAccount(document.getElementById("showStop"));
            Base.token();

        },
        function(m,r){
            alert(r)
            //showStopAccount(document.getElementById("showStop"));
            Base.token();

        }]
    );
}
/**打开编辑页面*/
function editAccount(useridd){
    openAdduserWindow(useridd);
}
/**关于列表操作的下拉结束======================*/

/**状态*/
function makeStatus(json){
    var imgurlpr=path+"/img/";
    if(json.status==1 || json.status=='1'){
        imgurlpr+="new_yes.png";
    }else if(json.status==0 || json.status=='0'){
        imgurlpr+="new_no.png";
    }else{
        imgurlpr+="";
    }

    return "<img src='"+imgurlpr+"' />";
}
/**显示已禁用的成员*/
function showStopAccount(obj){
    if($(obj).prop("checked")){
        refreshTable({"isShowStop":"all"})
    }else{
        $("#accountManager").deleteSpecUserParm(['isShowStop']);
        refreshTable({});
    }
}
/**刷新帐号列表*/
function refreshTable(p){
    if(p==null){p={}}
    $("#accountManager").selectDataAfterSetParm(p);
}

/**tab切换方法*/
function setTab(name,cursel,n){
    for(i=1;i<=n;i++){
        var menu=document.getElementById(name+i);
        var con=document.getElementById("con_"+name+"_"+i);
        menu.className=i==cursel?"new_tab_1":"new_tab_2";
        con.style.display=i==cursel?"block":"none";
    }
    if(cursel==4){
        queryRoleList();
    }else if(cursel==6){
        queryAliPay();
    }else if(cursel==7){
        queryPayLog();
    }
}
/**
 * 查询交费记录
 */
function queryPayLog(){
    $("#queryPayList").initTable({
        url:path + "/systemuser/queryPayLogList.do",
        columnData:[
            {title:"费用时间",name:"costsDate",width:"8%",align:"left",format:getdateStr},
            {title:"类型",name:"costsType",width:"8%",align:"left",format:getType},
            {title:"费用类型",name:"billType",width:"8%",align:"left",format:billType},
            {title:"金额",name:"amount",width:"8%",align:"left"}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshPaylogListTable();
}
function getdateStr(json){
    var html="";
    if(json.billType=="recharge"){
        html=json.createTime;
    }else{
        html=json.costsDate;
    }
    return html;
}
function getType(json){
    var htmlstr= "";
    if(json.costsType=="ebayAccount"){
        htmlstr="ebay账号："+json.accName;
    }else if(json.costsType=="subUser"){
        htmlstr="用户名："+json.accName;
    }
    return htmlstr;
}
function billType(json){
    var html="";
    if(json.billType=="recharge"){
        html="充值";
    }else if(json.billType=="deduct"){
        html="扣款";
    }
    return html;
}

function refreshPaylogListTable(){
    var p={};
    $("#queryPayList").selectDataAfterSetParm(p);
}

/**
 * 查询交费信息
 */
function queryAliPay(){
    $().invoke(
            path+"/systemuser/getAliPay.do",
        {},
        [function(m,r){
            if(r!=null&&r!=undefined){
                $("#balanceFee").text(r.balanceFee);
                $("#accountNumber").text(r.accountNumber);
                $("#ebayNumber").text(r.ebayNumber);
                $("#startDate").text(r.startDate);
                $("#payFee").text(r.payFee);
                $("#upPayFee").text(r.upPayFee);
            }
        },
            function(m,r){

            }]
    );
}

/**添加账户，弹窗*/
var openAdduserWindowD;
function openAdduserWindow(userid){
    var url=path+"/systemuser/";
    if(userid!=null){
       url+="editSubUserInit.do?userID="+userid;
    }else{
        url+="addSubUserInit.do";
    }
openAdduserWindowD = openMyDialog(
    {
        title:'',
        id : "dig" + (new Date()).getTime(),
        content:"url:"+url,
        width : 600,
        height : 600,
        max:false,
        min:false,
        lock : true

    }
);
    url="";
}

/**修改密码*/
function changePWDFun(){
    var oldp=$('#oldPWD').val();
    var newp=$('#newPWD').val();
    var newp2=$('#newPWD2').val();
    if(oldp==null || newp==null || newp2==null){alert("原密码和新密码必须输入");return;}
    if(newp!=newp2){alert("两次输入的新密码不同！");return;}
    $().invoke(
        path+"/systemuser/changePWD.do",
        {"oldPWD":oldp,"newPWD":newp},
        [function(m,r){
            alert(r);
            $("#oldPWD,#newPWD,#newPWD2").val('');
            Base.token();
        },
        function(m,r){
            alert(r)
            Base.token();
        }]
    );
}
/**
 * 续费
 */
var tenFeePage
function renFee(){
    tenFeePage = openMyDialog({title: '续费',
        content: 'url:'+path+'/systemuser/tenFeePage.do',
        icon: 'tips.gif',
        width: 400,
        button: [
            {
                name: '确定',
                callback: function (iwins, enter) {
                    var newWindow = window.open();
                    var totalFee = tenFeePage.content.document.getElementById("totalFee").value;
                    var url = path + "/alipay/createOrder.do?totalFee=" +totalFee;
                    reOpenPage(newWindow,url);
                }
            }
        ]
    });
}

/**重新打开页面*/
function reOpenPage(newWindow,url){
    newWindow.location.href = url;
    //window.open(tokenPageUrl+tokenParm);
}