/**
 * Created by Administrtor on 2014/8/15.
 * 加载物品类别属性以及保持并提交的js
 *
 * 类别id，存放属性大类的div id，点击div后执行的方法名,选择属性值的table
 * getCategorySpecificsData("63516","attList","afterClickAttr","attTable");
 * 在提交之前调用asyCombox2InputData()同步数据
 *
 * 页面上应该定义两个元素
 * <div id="attList"></div>
 <table id="attTable">
 <tr>
 <th>属性名</th>
 <th>属性值</th>
 <th>操作</th>
 </tr>
 </table>
 * */

function getCategorySpecificsData(id,indiv,funName,attTable){
    var siteID=$(document.getElementsByName("site")).eq(0).val();
    $('#'+indiv).html('');
    if(localStorage.getItem("category_att_ID"+siteID+""+id)!=null){
        var json= eval("(" + localStorage.getItem("category_att_ID"+siteID+""+id) + ")");
        var jdata=json.result;
        if(jdata==null||jdata==""){
            getSpec(id,indiv,funName,attTable);
        }else{
            getAttMainMenu(jdata,indiv,funName,attTable);
        }
    }else{
        getSpec(id,indiv,funName,attTable);
    }
}

function getSpec(id,indiv,funName,attTable){
    var siteID=$(document.getElementsByName("site")).eq(0).val();
    var data={"parentCategoryID":id,"siteID":siteID};
   // oldAlert(_invokeGetData_type);
    $().invoke(
        path+"/ajax/getCategorySpecifics.do",
        data,
        [
            function(m,r){
                if(r==null || r==''){return;}
                var json1= eval("(" + r + ")");
                if(json1.result==null||json1.result.length==0){
                    return;
                }
                if(localStorage.getItem("category_att_ID"+siteID+""+id)==null){
                    localStorage.setItem("category_att_ID"+siteID+""+data.parentCategoryID,r);
                }
                var json= eval("(" + localStorage.getItem("category_att_ID"+siteID+""+data.parentCategoryID) + ")");
                var jdata=json.result;
              //  oldAlert(jdata)
                returnSelectStr(jdata);
                getAttMainMenu(jdata,indiv,funName,attTable);
                //alert(localStorage.getItem("aaa").length);
            },
            function(m,r){
                alert(r)}
        ],{stringFormat:true}
    );

}


/**属性数组去重*/
function AttrArrDistinct(ar){
    var m,n=[],o= {};
    for(var i in ar){
        var ob=ar[i]["itemId"];
        var bbb=true;
        for(var ii in n){
            var ob1=n[ii]["itemId"];
            if(ob==ob1){
                bbb=false;
                break;
            }
        }
        if(bbb){
            n.push(ar[i])
        }
    }
return n
}
/**获取属性的种类列表*/
function getAttMainMenu(json,indiv,funName,attTable){
    if((json==null || json.length==0)||json[0]['itemEnName']=='noval'){return;}
    var m=new Array();
    for(var i in json){
        //console.log(json[i]['itemId'])
        var m1={"itemId":json[i]['itemId'],"minV":json[i]['minV']};
        m.push(m1);
    }
    var finalm=AttrArrDistinct(m);
    var parentid=json[0]['itemParentId'];
    for(var i in finalm){
        var domid=replaceTSFH((finalm[i]["itemId"]));
         //var dv="<a data-toggle=\"modal\" href=\"#myModal\" ><img src=\"../../img/new_add.png\" width=\"18\" height=\"18\"> Country / Region of Manufacture</a>"
        //var dv="<a id=div"+domid+" onclick="+funName+"(this,'"+parentid+"','"+attTable+"') class='att_mb-tag'>"+(finalm[i])+"</a>";
        var dv="<a style='padding-left: 10px' id=div"+domid+" onclick="+funName+"(this,'"+parentid+"','"+attTable+"') data-toggle=\"modal\">" ;
        if((finalm[i]["minV"])==1){
            dv+= "<img  src="+path+"/img/new_add1.png width=\"18\" height=\"18\">" ;
        }else{
            dv+= "<img  src="+path+"/img/new_add.png width=\"18\" height=\"18\">" ;
        }

            dv+= ""+(finalm[i]["itemId"])+"</a>";
        $('#'+indiv).append(dv);
    }
    m=null;
}

/**筛选出满足条件的数据*/
function queryData(queryParm,parentid){
    var siteID=$(document.getElementsByName("site")).eq(0).val();
    var json1= eval("(" + localStorage.getItem("category_att_ID"+siteID+""+parentid) + ")");
    var json=json1.result;
    return  $.grep(json,function(n,i){
        return n['itemId']==queryParm;
    });
}

/**比较自定义属性中已定义的和待定的*/
function compar2SepcAttr(){
    var attTable=$("#attTable");
    var attdiv=$("#typeAttrs");
    attTable.find("tr").each(function(i,d){
        if(i==0){return true;}
        var trid=$(d).attr("id");
        if(trid!=null&& trid!=''){return true;}
        var attName=$(d).find("td > input").eq(0).val();
        var attdivId=replaceTSFH(attName);

        $("#div"+attdivId).hide();

        //var attNameVal=$(d).find("td > input").eq(1).val();
    });

}

/**点击div后的事件*/
function afterClickAttr(obj, parentid,attTable) {
    var ohtmlTem=$(obj).html();
    var ohtml=ohtmlTem.substr(ohtmlTem.indexOf(">")+1,ohtmlTem.length-1);
    var domid=replaceTSFH(ohtml);
    var optionData = queryData(ohtml, parentid);

    var thisDID="_select"+domid;
    var inp="<input id="+thisDID+" style='width:200px;' type=\"text\" class=\"multiSelect\">";
    var tr = "<tr id=tr"+domid+" >" + "<td style='text-align: center'><input type='hidden' name='name' value=\""+ohtml+"\" >" +
        ohtml + "</td>" +
        "<td onclick='onclickmulAttrTD(this)' style='text-align: center'><span name='values' style='color: rgb(30, 144, 255); display: inline;'>"+optionData[0]['itemEnName']+"</span>" +
        "<input id=_value"+domid+" type='hidden' name='value'>" + inp + "</td>" +
        "<td style='text-align: center'>" + "<img src='/xsddWeb/img/del1.png' style='width: 8px;height: 8px;' onclick='removeThisTr(this)' />" + "</td></tr>";
    $('#'+attTable).append(tr);

    var sdata=[{bs:("#"+thisDID),multiple:false,maping:{id:"itemEnName",text:"itemEnName"},
        doitAfterSelect:bodyClick,
        sdata:optionData}];
    initMySelectNoPost(sdata);
    $("#s2id_"+thisDID).hide();
    $(obj).hide();
    $("#"+thisDID).select2("data",{"text":(optionData[0]['itemEnName'])});
    return;

    /*var select = "<select id=\""+"_select"+domid+"\" class='easyui-combobox' style='width:200px;'>";
    for (var i in optionData) {
        select += "<option value=\"" + optionData[i]['itemEnName'] + "\">" + optionData[i]['itemEnName'] + "</option>";
    }
    select += "</select>";

    var tr = "<tr id=tr"+domid+" >" + "<td style='text-align: center'><input type='hidden' name='name' value=\""+ohtml+"\" >" +
        ohtml + "</td>" +
        "<td onclick='onclickmulAttrTD(this)' style='text-align: center'><span name='values' style='color: rgb(30, 144, 255); display: inline;'>"+optionData[0]['itemEnName']+"</span>" +
        "<input id=_value"+domid+" type='hidden' name='value'>" + select + "</td>" +
        "<td style='text-align: center'>" + "<img src='/xsddWeb/img/del1.png' style='width: 8px;height: 8px;' onclick='removeThisTr(this)' />" + "</td></tr>";
    $('#'+attTable).append(tr);
    $(obj).hide();
    //try{

        $.parser.parse();
        $('#'+attTable).find("span[class='combo']").hide();
        $('#'+attTable).find("span[name='values']").show();

        $('#'+attTable).find("input").each(function(i,d){
            if($(d).hasClass("combo-text")){
                $(d).removeAttr("onblur");
                $(d).attr("onblur","onblurMulAttrInput(this)")
            }

        });*/



    //}catch (e){}
}
/**combox失去焦点的时候执行*/
function onblurMulAttrInput(inpu){

    var bst=false;

    setTimeout(function(){
        $("div").each(function(i,d){
            if($(d).hasClass("panel combo-p")){
                if($(d).css("display")=='block'){
                    bst=true;
                }
            }
        });

        if(bst==false){
            var selectid=$(inpu).parents("td").eq(0).find("select[id^='_select']").eq(0).attr("id");
            setTimeout(function(){
                $('#'+selectid).siblings("span[name='values']").html($("#"+selectid).combobox('getValue'));
                $('#attTable').find("span[class='combo']").hide();
                $('#attTable').find("span[name='values']").show();
            },200);
        }

    },500);

    return;
}
function onclickmulAttrTD(td){
    $(td).find("span").hide();
    $(td).find("span").each(function(i,d){
        if($(d).attr("name")==null){
            $(d).show();
        }
    });

    $(td).find("input").each(function(i,d){
        if($(d).hasClass("multiSelect")){
            var inpid=$(d).attr("id");
            $("#s2id_"+inpid).show();
            $("#"+inpid).select2("data",{"text":($(td).find("span").eq(0).html())});
        }
    });
    return;

    /*$(td).find("input").each(function(i,d){
        if($(d).hasClass("combo-text")){
            $(d).focus();
        }
    });*/
}

/**同步combox的数据*/
function asyCombox2InputData(){
    //alert($("#ddd").combobox('getValue'))

    $("input[id^='_select']").each(function(i,d){
        var baseID=d.id.substr(7, d.id.length-7);
        $('#_value'+baseID).val($("#_select"+baseID).select2("data").text);
    });

    /*$("select[id^='_select']").each(function(i,d){
        var baseID=d.id.substr(7, d.id.length-7);
        $('#_value'+baseID).val($("#_select"+baseID).combobox('getValue'))
    });*/

}

function removeThisTr(a){
    var tr= a.parentNode.parentNode;
    var baseId= tr.id.substr(2, tr.id.length-2);
    $('#div'+baseId).show();
    $(tr).remove();
}



/**对商品图片div进行重新排序*/
function reSortItemPic(){
    var m=new Map();
    $("ul[id^='picture']").find("li").each(function(i,d){
        var k=$(d).css("top");
        if(m.containsKey(k)){
            var kkArr= m.get(k);
            kkArr.push(d);
        }else{
            var ka=new Array();
            ka.push(d);
            m.put(k,ka);
        }
    });
    var ks=m.keys;
    var domArr;
    for(var i in ks){
        var dArrKey=ks[i];
        domArr = m.get(dArrKey);
        domArr.sort(function(a,b){
            var al=parseInt(strGetNum($(a).css("left"))) ;
            var bl=parseInt(strGetNum($(b).css("left"))) ;
            if(al>bl){
                return 1;
            }else if(al==bl){
                return 0;
            }else{
                return -1;
            }
        });
    }
    $("ul[id^='picture']").empty();
    for ( var ii in domArr){
        $("ul[id^='picture']").append($(domArr[ii])[0].outerHTML);
    }
    initDraug();
    domArr=null;
    m.clear();
}
function getLocalStorage(name){
    var a="",b=0;
    $("input[type='radio'][name='"+name+"']").each(function(i,d){
        if(localStorage.getItem(name+"_userselect." + $(d).val()) != null){
            if(parseInt(localStorage.getItem(name+"_userselect." + $(d).val()))>b){
                b=parseInt(localStorage.getItem(name+"_userselect." + $(d).val()));
                a=$(d).val();
            }
        }
    });
    return a;
}

function getTemplateNumber(){
    var a=0;
    for(var i =0;i<localStorage.length;i++){
        if(localStorage.key(i).indexOf("template_")!=-1){
            var json= eval("("+localStorage.getItem(localStorage.key(i))+")");
            if(parseInt(json.countnumber)>a){
                a=parseInt(json.countnumber);
            }
        }
    }
    return a;
}

//把用户常用的选择信息记录下来
function checkLocalStorage(){
    //模板选择记录
    var templateId = $("#templateId").val();
    if(templateId!=null&&templateId!=""){
        var json= eval("("+localStorage.getItem("template_"+templateId)+")");
        if(json!=null){
            localStorage.setItem("template_" + templateId,"{\"countnumber\":\""+(parseInt(json.countnumber)+1)+"\",\"result\":{\"templateId\":\""+json.result.templateId+"\",\"templateUrl\":\""+json.result.templateUrl+"\"}}");
        }else{
            localStorage.setItem("template_" + templateId,"{\"countnumber\":\"1\",\"result\":{\"templateId\":\""+templateId+"\",\"templateUrl\":\""+$("#templateUrl").attr("src")+"\"}}");
        }
    }


    $("input[type='radio'][name='buyerId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("buyerId_userselect." + $(d).val()) != null) {
                localStorage.setItem("buyerId_userselect." + $(d).val(), parseInt(localStorage.getItem("buyerId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("buyerId_userselect." + $(d).val(), 1);
            }
        }
    });

    $("input[type='radio'][name='discountpriceinfoId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("discountpriceinfoId_userselect." + $(d).val()) != null) {
                localStorage.setItem("discountpriceinfoId_userselect." + $(d).val(), parseInt(localStorage.getItem("discountpriceinfoId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("discountpriceinfoId_userselect." + $(d).val(), 1);
            }
        }
    });

    $("input[type='radio'][name='itemLocationId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("itemLocationId_userselect." + $(d).val()) != null) {
                localStorage.setItem("itemLocationId_userselect." + $(d).val(), parseInt(localStorage.getItem("itemLocationId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("itemLocationId_userselect." + $(d).val(), 1);
            }
        }
    });

    $("input[type='radio'][name='payId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("payId_userselect." + $(d).val()) != null) {
                localStorage.setItem("payId_userselect." + $(d).val(), parseInt(localStorage.getItem("payId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("payId_userselect." + $(d).val(), 1);
            }
        }
    });

    $("input[type='radio'][name='returnpolicyId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("returnpolicyId_userselect." + $(d).val()) != null) {
                localStorage.setItem("returnpolicyId_userselect." + $(d).val(), parseInt(localStorage.getItem("returnpolicyId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("returnpolicyId_userselect." + $(d).val(), 1);
            }
        }
    });

    $("input[type='radio'][name='shippingDeailsId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("shippingDeailsId_userselect." + $(d).val()) != null) {
                localStorage.setItem("shippingDeailsId_userselect." + $(d).val(), parseInt(localStorage.getItem("shippingDeailsId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("shippingDeailsId_userselect." + $(d).val(), 1);
            }
        }
    });
    $("input[type='radio'][name='sellerItemInfoId']").each(function(i,d){
        if($(d).prop("checked")) {
            if (localStorage.getItem("sellerItemInfoId_userselect." + $(d).val()) != null) {
                localStorage.setItem("sellerItemInfoId_userselect." + $(d).val(), parseInt(localStorage.getItem("sellerItemInfoId_userselect." + $(d).val())) + 1);
            } else {
                localStorage.setItem("sellerItemInfoId_userselect." + $(d).val(), 1);
            }
        }
    });
}
var timerPage
function selectTimer(obj){

    var urls = path+'/selectTimer.do';
    timerPage = $.dialog({title: '选择定时时间',
        content: 'url:'+urls,
        icon: 'succeed',
        width:500,
        lock:true
    });
    //saveData(this,'timeSave')
}
var ifEXE_Param = "";
/**保存并提交*/
var picList;
var ispic = false;
function saveData(objs,name) {

    /*if($("#sku").val()==null||$("#sku").val()==""){
        alert("未输入SKU");
        return;
    }*/
    if($("input[name='ebayAccounts']:checked").length==0){
        alert("未选择需要刊登的ebay账号，请选择！");
        return;
    }
    $("#moreAttrs tr:gt(0)").each(function(i,d) {
        $(d).find("input[name='SKU']").each(function (ii, dd) {
            if($(dd).val()==$("#sku").val()&&$("#sku").val()!=""){
                oldAlert("多属性SKU不能与主SKU相同，请检查后修改！");
                return;
            }
        });
    });
    var is_price = false;
    if($("select[name='listingType']").find("option:selected").val()=="FixedPriceItem"){
        $("input[name^='StartPrice.value']").each(function(i,d){
            if($(d).val()==""){
                is_price=true
            }
            if($(d).val()=="0"||parseFloat($(d).val())<1){
                is_price=true;
            }
        });
    }

    if(is_price){
        alert("输入的价格不正确,必须大于1!");
        return;
    }

    if($("input[type='radio'][name='buyerId']:checked").val()==null||$("input[type='radio'][name='buyerId']:checked").val()==""){
        alert("买家要求未选择");
        return ;
    }
    if($("input[type='radio'][name='itemLocationId']:checked").val()==null||$("input[type='radio'][name='itemLocationId']:checked").val()==""){
        alert("物品所在地未选择！");
        return ;
    }
    if(($("input[type='radio'][name='payId']:checked").val()==null||$("input[type='radio'][name='payId']:checked").val()=="")&&$("input[type='checkbox'][name='ebayAccounts']:checked").length==1){
        alert("支付方式未选择或有ebay账号未关联paypal,请到:系统设置-》账号绑定-》ebay账号-》操作列，选择对应的paypal!");
        return ;
    }
    if($("input[type='radio'][name='returnpolicyId']:checked").val()==null||$("input[type='radio'][name='returnpolicyId']:checked").val()==""){
        alert("退货政策未选择！");
        return ;
    }
    if($("input[type='radio'][name='shippingDeailsId']:checked").val()==null||$("input[type='radio'][name='shippingDeailsId']:checked").val()==""){
        alert("运输选项未选择！");
        return ;
    }
    if($("input[type='radio'][name='sellerItemInfoId']:checked").val()==null||$("input[type='radio'][name='sellerItemInfoId']:checked").val()==""){
        var conf = confirm("你确定不选择商品描述？");
        if(conf == true) {

        } else {
            return;
        }
    }

    if($.type(objs)=="string"){
        objs=$("a[name='"+objs+"']");
    }
    bodyClick();//自定义属性
    asyCombox2InputData();//同步comebox的数值
    //reSortItemPic();//对经过排序的图片进行重新排列,后台按前台这个序顺读取
    domReIndex("picture","PictureDetails");//对重新排列后的元素进行重新索引
    $("#dataMouth").val(name);
    if(name=="othersave"){
        $("#id").val(null);
    }
    if(name=="Backgrounder"){
        $("#ListingMessage").val("1");
    }else{
        $("#ListingMessage").val("0");
    }
    if(!$("#form").validationEngine("validate")){
        $('#form').validationEngine('updatePromptsPosition')
        return;
    }
    if(countChoosePic()==0&&name!="save"){
        alert("还未上传图片，请选择图片上传！");
        return;
    }
    if(countChoosePic()>11*$("input[type='checkbox'][name='ebayAccounts']:checked").length){
        alert("最多只能上传12张图片，上传图片已超过上传限制！");
        return;
    }
    var checkAttName = new Map();
    $("input[type='hidden'][name='name']").each(function(i,d){
        checkAttName.put($(d).val(),$(d).val());
    });
    for(var i=0;i<checkAttName.keys.length;i++){
        if(checkAttName.keys[i]==$("input[name='attr_Name']").val()){
            alert("多属性与自定义属性重名！请检查");
            return ;
        }
    }

    var pciValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i,d) {
        if($(d).find("input[name='attr_Value']").val()!=undefined&&$(d).find("input[name='attr_Value']").val()!=""){
            pciValue.put($(d).find("input[name='attr_Value']").val(),$(d).find("input[name='attr_Value']").val());
        }
    });
    for(var i=0;i<pciValue.keys.length;i++){
        $("input[type='hidden'][name='"+replaceTSFH(pciValue.keys[i])+"']").each(function(ii,dd){
            $(dd).prop("name","Variations.Pictures.VariationSpecificPictureSet["+i+"].PictureURL["+ii+"]");
        });
        $("input[type='hidden'][name='VariationSpecificValue_"+replaceTSFH(pciValue.keys[i])+"']").prop("name","Variations.Pictures.VariationSpecificPictureSet["+i+"].VariationSpecificValue");
    }
    var nameList = $("input[type='hidden'][name='name']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });
    var valueList = $("input[type='hidden'][name='value']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });
    var isattrName = false;
    $("input[type='hidden'][name='attr_Name']").each(function(i,d){
        if($(d).val()==undefined||$(d).val()==""){
            isattrName=true;
        }
        var t="Variations.VariationSpecificsSet.NameValueList["+i+"].Name";
        $(d).prop("name",t);
    });
    if(isattrName){
        alert("多属性主属性名称未填写！");
        return ;
    }
    var len = $("#moreAttrs tr:eq(0) th").length - 5;
    for(var j=0;j<len ;j++){
        $("#moreAttrs tr:gt(0) td:nth-child("+(j+5)+")").each(function (i,d) {
            $(d).find("input[name='attr_Value']").each(function(ii,dd){
                $(dd).prop("name","Variations.VariationSpecificsSet.NameValueList["+j+"].Value["+i+"]");
            });
        });
    }
    $("#moreAttrs tr:gt(0)").each(function(i,d){
        $(d).find("input[name='SKU'],input[name='StartPrice.value'],input[name='Quantity']").each(function(ii,dd){
            var name_ = $(dd).prop("name");
            $(dd).prop("name","Variations.Variation["+i+"]."+name_);
        });
    });
    $("#Description").val(myDescription.getContent());
    checkLocalStorage();
    var data = $('#form').serialize();
    var urll = "/xsddWeb/saveItem.do";
    $(objs).attr("disabled",true);
    $().invoke(
        urll,
        data,
        [function (m, r) {
            //oldAlert(r);
            Base.token();
            ifEXE_Param = "to";
            alert(r);
        },
            function (m, r) {
                alert(r);
                var json ;
                try{
                    json = eval("(" + r + ")");
                    if(json.isFlag=="1"){
                        $("#id").val(json.tradingItemId);
                        //alert(json.message)
                    }else{
                        //alert(r);
                    }
                }catch (e){

                }
                Base.token();
                $(objs).attr("disabled",false);
                //document.location = path+"/itemManager.do";
            }],{isConverPage:true}
    )
}

function checkPic(objs,name){
    var picid = "";
    var morepicid = "";
    $("input[name='pic_mackid'][type='hidden']").each(function(i,d){
        picid+=$(d).val()+",";
    });
    $("input[name='pic_mackid_more'][type='hidden']").each(function(i,d){
        morepicid+=$(d).val()+",";
    });
    $().invoke(
        path+'/ajax/checkPicUrlEbay.do?pic_mackid='+picid+"&pic_mackid_more="+morepicid,
        {},
        [function (m, r) {
            if(r.list.length>0){
                var data = $('#form').serialize();
                var url=path+"/ajax/toPicList.do";
                picList=openMyDialog({title: '未上传图片列表',
                    content: 'url:'+url,
                    icon: 'tips.gif',
                    width:850,
                    height:300,
                    lock:true,
                    button: [
                        {
                            name: '确定',
                            callback: function (iwins, enter) {
                                if($(picList.content.document.getElementById("showTable")).find("tr").length>1){
                                    alert("还有图片未上传到ebay图片服务器上，物品不能刊登；请上传图片！");
                                    return;
                                }else{
                                    if(name=="previewItem"){
                                        previewItem();
                                    }else if(name=="checkEbayFee"){
                                        checkEbayFee();
                                    }else{
                                        saveData(objs,name);
                                    }
                                }
                            }
                        }
                    ]
                });
            }else{
                if(name=="previewItem"){
                    previewItem();
                }else if(name=="checkEbayFee"){
                    checkEbayFee();
                }else{
                    saveData(objs,name);
                }
            }
        },
            function (m, r) {

            }],{isConverPage:false}
    )


    /**/
}

function afterMyAlert(){
    //$(objs).attr("disabled",false);
    if(ifEXE_Param=="to"){
        ifEXE_Param = "";
        if(url.indexOf("information/editItem.do")>0){
            var api = frameElement.api, W = api.opener;
            W.itemInformation.close();
        }else if(url.indexOf("source=listingManager")>0){
            window.close();
        }else{
            document.location = path+"/itemManager.do";
        }
    }
}


function checkEbayFee(){
    if(!$("#form").validationEngine("validate")){
        return;
    }
    if($("input[name='ebayAccounts']:checked").length==0){
        alert("未选择需要刊登的ebay账号，请选择！");
        return;
    }
    if(countChoosePic()>11*$("input[type='checkbox'][name='ebayAccounts']:checked").length){
        alert("最多只能上传12张图片，上传图片已超过上传限制！");
        return;
    }
    var pciValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i,d) {
        if($(d).find("input[name='attr_Value']").val()!=undefined&&$(d).find("input[name='attr_Value']").val()!=""){
            pciValue.put($(d).find("input[name='attr_Value']").val(),$(d).find("input[name='attr_Value']").val());
        }
    });
    for(var i=0;i<pciValue.keys.length;i++){
        $("input[type='hidden'][name='"+replaceTSFH(pciValue.keys[i])+"']").each(function(ii,dd){
            $(dd).prop("name","Variations.Pictures.VariationSpecificPictureSet["+i+"].PictureURL["+ii+"]");
        });
        $("input[type='hidden'][name='VariationSpecificValue_"+replaceTSFH(pciValue.keys[i])+"']").prop("name","Variations.Pictures.VariationSpecificPictureSet["+i+"].VariationSpecificValue");
    }

    var nameList = $("input[type='hidden'][name='name']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });
    var valueList = $("input[type='hidden'][name='value']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });

    $("input[type='hidden'][name='attr_Name']").each(function(i,d){
        var t="Variations.VariationSpecificsSet.NameValueList["+i+"].Name";
        $(d).prop("name",t);
    });
    var len = $("#moreAttrs tr:eq(0) th").length - 5;
    for(var j=0;j<len ;j++){
        $("#moreAttrs tr:gt(0) td:nth-child("+(j+5)+")").each(function (i,d) {
            $(d).find("input[name='attr_Value']").each(function(ii,dd){
                $(dd).prop("name","Variations.VariationSpecificsSet.NameValueList["+j+"].Value["+i+"]");
            });
        });
    }
    $("#moreAttrs tr:gt(0)").each(function(i,d){
        $(d).find("input[name='SKU'],input[name='StartPrice.value'],input[name='Quantity']").each(function(ii,dd){
            var name_ = $(dd).prop("name");
            $(dd).prop("name","Variations.Variation["+i+"]."+name_);
        });
    });
    $("#Description").val(myDescription.getContent());
    checkLocalStorage();
    var data = $('#form').serialize();
    var urll = path+"/ajax/checkEbayFee.do";
    $().invoke(
        urll,
        data,
        [function (m, r) {
            var str = "";
            if(r.length>0){
                for(var i=0;i< r.length;i++) {
                    var m = r[i];
                    str+="<div>"+m.name+":"+ m.value+"</div>";
                }
            }else{
                str+="<div>暂时未发现收费项目</div>";
            }
            var tent = "<div>"+str+"</div>";
            var editPage = $.dialog({title: '收费项目',
                content: tent,
                icon: 'tips.gif',
                width: 400
            });
        },
            function (m, r) {
                alert(r)
            }],{isConverPage:true}
    )
}
/**
 * 预览商品
 */
function previewItem(){
    if(!$("#form").validationEngine("validate")){
        return;
    }
    if($("input[name='ebayAccounts']:checked").length==0){
        alert("未选择需要刊登的ebay账号，请选择！");
        return;
    }
    if(countChoosePic()>11*$("input[type='checkbox'][name='ebayAccounts']:checked").length){
        alert("最多只能上传12张图片，上传图片已超过上传限制！");
        return;
    }
    var pciValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i,d) {
        if($(d).find("input[name='attr_Value']").val()!=undefined&&$(d).find("input[name='attr_Value']").val()!=""){
            pciValue.put($(d).find("input[name='attr_Value']").val(),$(d).find("input[name='attr_Value']").val());
        }
    });
    for(var i=0;i<pciValue.keys.length;i++){
        $("input[type='hidden'][name='"+replaceTSFH(pciValue.keys[i])+"']").each(function(ii,dd){
            $(dd).prop("name","Variations.Pictures.VariationSpecificPictureSet["+i+"].PictureURL["+ii+"]");
        });
        $("input[type='hidden'][name='VariationSpecificValue_"+replaceTSFH(pciValue.keys[i])+"']").prop("name","Variations.Pictures.VariationSpecificPictureSet["+i+"].VariationSpecificValue");
    }

    var nameList = $("input[type='hidden'][name='name']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });
    var valueList = $("input[type='hidden'][name='value']").each(function(i,d){
        var name_= $(d).prop("name");
        var t="ItemSpecifics.NameValueList["+i+"].";
        $(d).prop("name",t+name_);
    });

    $("input[type='hidden'][name='attr_Name']").each(function(i,d){
        var t="Variations.VariationSpecificsSet.NameValueList["+i+"].Name";
        $(d).prop("name",t);
    });
    var len = $("#moreAttrs tr:eq(0) th").length - 5;
    for(var j=0;j<len ;j++){
        $("#moreAttrs tr:gt(0) td:nth-child("+(j+5)+")").each(function (i,d) {
            $(d).find("input[name='attr_Value']").each(function(ii,dd){
                $(dd).prop("name","Variations.VariationSpecificsSet.NameValueList["+j+"].Value["+i+"]");
            });
        });
    }
    $("#moreAttrs tr:gt(0)").each(function(i,d){
        $(d).find("input[name='SKU'],input[name='StartPrice.value'],input[name='Quantity']").each(function(ii,dd){
            var name_ = $(dd).prop("name");
            $(dd).prop("name","Variations.Variation["+i+"]."+name_);
        });
    });
    $("#Description").val(myDescription.getContent());
    checkLocalStorage();
    var data = $('#form').serialize();

    var urll = path+"/previewItem.do";
    var newWindow = window.open();
    if(newWindow==null||newWindow==undefined){
        alert("浏览器右上角，允许弹出窗口才能预览商品?")
    }else{
        $().invoke(
            urll,
            data,
            [function (m, r) {
                reOpenPage(newWindow,path+"/preview.jsp?itemId="+ r.itemid);
            },
                function (m, r) {
                    alert(r)
                }],{isConverPage:true}
        );
    }

}
/**重新打开页面*/
function reOpenPage(newWindow,url){
    newWindow.location.href = url;
    //window.open(tokenPageUrl+tokenParm);
}

function selectAccount(obj){
    if($("select[name='listingType']").find("option:selected").val()==""||$("select[name='listingType']").find("option:selected").val()==null){
        alert("请先选择刊登类型！");
        $(obj).prop("checked",false);
    }
    if($("select[name='listingType']").find("option:selected").val()=="2"&&$("input[type='checkbox'][name='ebayAccounts']:checked").length>1){
        alert("多属性不允许多账号刊登！");
        $(obj).prop("checked",false);
    }
    var ebayAcc = "";
    var ebayAccountId = "";
    $("input[type='checkbox'][name='ebayAccounts']:checked").each(function(i,d){
        ebayAccountId = $(d).val();
        ebayAcc+="&ebayId="+$(d).val();
    });
    isStore(ebayAccountId);
    $("#totalPic").text($("input[type='checkbox'][name='ebayAccounts']:checked").length*12);
    loadShippingDeails(ebayAcc);
    getPayIdStr(ebayAcc);
    $("#showPics").text("");
    $("#showPics").html("");
    isShowPicLink();
    initTitle();
    initPrice();
}
function getPayIdStr(ebayAcc){
    var urll = path+"/ajax/getPaypalIdStr.do?1=1"+ebayAcc;
    $().invoke(
        urll,
        {},
        [function (m, r) {
            var paypalId = "";
            for(var i=0;i< r.length;i++){
                if(r[i]!=undefined){
                    paypalId+="&paypalId="+r[i];
                }
            }
            if(paypalId!=""){
                loadPayOption(paypalId);
            }
        },
            function (m, r) {
                alert(r)
            }]
    );
}
function getSiteImg(json){
    var html='<img title="'+json.siteName+'" src="'+path+json.siteImg+'"/>';
    return html;
}
/**判断是否显示图片上传按钮*/
function isShowPicLink(){
    //初始化描述信息编辑器
    $("input[type='checkbox'][name='ebayAccounts']:checked").each(function(i,d){
        /*if(_sku==null || _sku ==''){
            return;
        }*/
        if($("#apicUrls_"+$(d).val())[0]!=null){
            return;
        }
        //初始化上传图片
        var showStr = "<div class='panel' style='display: block'>";
        showStr +=" <section class='example' ><ul class='gbin1-list' style='padding-left: 20px;' id='picture_"+$(d).val()+"'></ul></section> ";
        showStr +=" <script type=text/plain id='picUrls_"+$(d).val()+"'></script> ";
        if(_sku==null || _sku ==''){
            showStr +=" <div style='padding-left: 20px;'>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='remote' id='apicUrlsOther_" + $(d).val() + "' onclick='selectPic(this)' style=''>选择外部图片</a></b>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' id='apicUrlsclear_" + $(d).val() + "' onclick='clearAllPic(this)' style=''>清空所选图片</a></b>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' id='apicUrlsCopy_" + $(d).val() + "' onclick='copyPic(this)' style=''>复制图片到描述</a></b>" +
                "</div> </div> ";
        }else{
            showStr +=" <div style='padding-left: 20px;'>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='upload' id='apicUrls_"+$(d).val()+"' onclick='selectPic(this)'>选择图片</a></b>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='online' id='apicUrlsSKU_" + $(d).val() + "' onclick='selectPic(this)' style=''>选择SKU图片</a></b>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' bsid='remote' id='apicUrlsOther_" + $(d).val() + "' onclick='selectPic(this)' style=''>选择外部图片</a></b>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' id='apicUrlsclear_" + $(d).val() + "' onclick='clearAllPic(this)' style=''>清空所选图片</a></b>" +
                "<b class='new_button' style='margin: 10px;'><a href='javascript:void(0)' id='apicUrlsCopy_" + $(d).val() + "' onclick='copyPic(this)' style=''>复制图片到描述</a></b>" +
                "</div> </div> ";
        }

        $("#showPics").append(showStr);
        if(_sku==null || _sku ==''){
            $().image_editor.init("apicUrlsOther_" + $(d).val()); //编辑器的实例id
            $().image_editor.show("apicUrlsOther_" + $(d).val()); //上传图片的按钮id
        }else {
            $().image_editor.init("picUrls_" + $(d).val()); //编辑器的实例id
            $().image_editor.show("picUrls_" + $(d).val());

            $().image_editor.init("apicUrls_" + $(d).val());
            $().image_editor.show("apicUrls_" + $(d).val()); //上传图片的按钮id        }

            $().image_editor.init("apicUrlsSKU_" + $(d).val()); //编辑器的实例id
            $().image_editor.show("apicUrlsSKU_" + $(d).val()); //上传图片的按钮id

            $().image_editor.init("apicUrlsOther_" + $(d).val()); //编辑器的实例id
            $().image_editor.show("apicUrlsOther_" + $(d).val()); //上传图片的按钮id
        }
    });
}


function initTitle(){
    $("#titleDiv").text("");
    $("input[type='checkbox'][name='ebayAccounts']:checked").each(function(i,d) {
        var titleHtml="";
        titleHtml+=' <li  style="height: 35px;"> ';
        titleHtml+=' <dt>物品标题('+$(d).attr("shortName")+')</dt> ';
        titleHtml+=' <div class="new_left"> ';
        titleHtml+=' <input type="text" name="Title_'+$(d).val()+'" value="'+title+'" id="Title" style="width:600px;" ';
        titleHtml+=' class="validate[required,maxSize[80]] form-control" size="100" ';
        titleHtml+=' onkeyup="incount(this)"><span id="incount">'+titleLength+'</span>/80 ';
        titleHtml+=' </div> ';
        titleHtml+=' </li> ';
        titleHtml+=' <li style="height: 35px;display:none;"> ';
        titleHtml+=' <dt>子标题('+$(d).attr("shortName")+')</dt> ';
        titleHtml+=' <div class="new_left"> ';
        titleHtml+=' <input type="text" name="SubTitle_'+$(d).val()+'" value="'+subtitle+'" style="width:600px;" class="validate[maxSize[55]] form-control" id="SubTitle_'+$(d).val()+'" ';
        titleHtml+=' size="100"> ';
        titleHtml+=' </div> ';
        titleHtml+=' </li><li class="flip" style="padding-left:260px;padding-bottom: 20px;height: 5px;" onclick="showSubTitle(this)"><img src="'+path+'/img/new_list_ico.png" class="abc"></li> ';
        $("#titleDiv").append(titleHtml);
    });
}
function initPrice(){
    var curName=$("select[name='site']").find("option:selected").attr("curid");
    $("#oneAttr").text("");
    $("input[type='checkbox'][name='ebayAccounts']:checked").each(function(i,d) {
        var priceHtml="";
        priceHtml+=' <li> ';
        priceHtml+=' <dt>商品价格('+$(d).attr("shortName")+')</dt> ';
        priceHtml+=' <div class="new_left"> ';
        priceHtml+=' <input type="text" name="StartPrice.value" style="width:300px;" class="validate[required] form-control" value="'+itemPrice+'" onkeypress="return inputNUMAndPoint(event,this,2)" /> ';
        priceHtml+='<label name="curName" style="border: 1px solid #cccccc;background-color: #eee;line-height: 26px;height: 26px;position: absolute;text-align: center;width: 110px;left: 340px;-webkit-border-top-right-radius: 5px;-webkit-border-bottom-right-radius: 5px;">'+curName+'</label>';
        priceHtml+=' </div> ';
        priceHtml+=' </li> ';
        priceHtml+=' <li name="abcpaimai"> ';
        priceHtml+=' <dt>商品数量('+$(d).attr("shortName")+')</dt> ';
        priceHtml+=' <div class="new_left"> ';
        priceHtml+=' <input type="text" style="width:300px;" class="validate[required] form-control" name="Quantity" value="'+itemCount+'" onkeypress="return inputOnlyNUM(event,this)"/> ';
        priceHtml+=' </div> ';
        priceHtml+='  </li> ';
        $("#oneAttr").append(priceHtml);
    });
}
//选择产品
var Porduct;
function selectProduct(){
    if($("select[name='listingType']").find("option:selected").val()==""||$("select[name='listingType']").find("option:selected").val()==null){
        alert("请先选择刊登类型！");
        return;
    }
    if($("input[type='checkbox'][name='ebayAccounts']:checked").length<1){
        alert("请选择刊登账号！");
        return;
    }
    Porduct = $.dialog({
        id:"itemClass_",
        title: '选择产品信息',
        content: 'url:' + path + '/selectItemInformation.do',
        icon: 'succeed',
        zIndex:2000,
        width: 750,
        height:700,
        lock: true
    });
}

function showSubTitle(obj){
    if($(obj).find("img").attr("class")=="abc"){
        $(obj).find("img").attr("class","abc1");
    }else{
        $(obj).find("img").attr("class","abc");
    }
    $(obj).parent().find("li").each(function(i,d){
        if(i==($(obj).index()-1)){
            if($(d).is(":hidden")){
                $(d).show();
            }else{
                $(d).hide();
            }
        }
    });
}

function showSubCategory(obj){
    if($(obj).find("img").attr("class")=="abc"){
        $(obj).find("img").attr("class","abc1");
        $("#secodaryCate").show();
    }else{
        $(obj).find("img").attr("class","abc");
        $("#secodaryCate").hide();
    }
}

/**异步加载ueditor的几个js*/
function loadEditor(ebayAccount,jsonstr){
    seriesLoadScripts(ueditorJSS_,function(){
        //初始化描述信息编辑器
        myDescription = UE.getEditor('myDescription', ueditorToolBar);
        //初始化图片上传按扭
        if(ebayAccount!=""){
            $().image_editor.init("picUrls_" + ebayAccount); //编辑器的实例id
            $().image_editor.show("apicUrls_" + ebayAccount); //上传图片的按钮id
            $().image_editor.show("apicUrlsSKU_" + ebayAccount); //上传图片的按钮id
            $().image_editor.show("apicUrlsOther_" + ebayAccount); //上传图片的按钮id
        }
        //初始化多属性图片按扭

        if(jsonstr!=""){
            var json = eval("(" + jsonstr + ")");
            if(json!=null){
                for(var i=0;i<json.length;i++){
                    $().image_editor.init(replaceTSFH(json[i].a));
                    $().image_editor.show(replaceTSFH(json[i].b));
                }
            }
        }
        //模板图片初始化
        $().image_editor.init("blankImg_main"); //编辑器的实例id
        $().image_editor.show("blankImg_id"); //上传图片的按钮id
        if(url.indexOf("addItem.do") != -1){
            $().image_editor.init("picUrls"); //编辑器的实例id
            $().image_editor.show("apicUrls"); //上传图片的按钮id
        }
    });
}
function PrimaryCategoryShowFlag(){
    var CategoryId = $("#PrimaryCategory").val();
    var listingTypes = $("select[name='listingType']").val();
    var siteID=$(document.getElementsByName("site")).eq(0).val();
    if(listingTypes=="2"&&CategoryId!=""&&siteID!=""){
        var data = {"siteId":siteID,"categoryId":CategoryId};
        var urll = path+"/category/checkSelectCategoryVariationed.do";
        $().invoke(
            urll,
            data,
            [function (m, r) {
                if(r!=null&&r.variationsenabled=="false"){
                    $("#twoAttr").hide();
                    $("#PrimaryCategoryShowFlag").show();
                }else{
                    $("#twoAttr").show();
                    $("#PrimaryCategoryShowFlag").hide();
                }
            },
                function (m, r) {
                    alert(r)
                }]
        );
    }else{
        $("#PrimaryCategoryShowFlag").hide();
    }
}
function copyPic(obj){
    $("input[name$='PictureURL']").each(function(i,d){
        myDescription.execCommand('insertHtml',"<img name='itemImg' src=" + $(d).val() + " />&nbsp;" );
    });
}
var storeCategoryPage;
function selectStoreCategory(ebayAccountId){
    storeCategoryPage=$.dialog({
        id:"storeClass_",
        title: '选择店铺分类',
        content: 'url:' + path + '/store/selectStoreCategory.do?ebayAccountId='+ebayAccountId,
        icon: 'succeed',
        zIndex:2000,
        width: 650,
        height:400,
        lock: true
    });
}

function selectStoreType(obj){
    var ebayAccountId = "";
    $("input[type='checkbox'][name='ebayAccounts']:checked").each(function(i,d){
        ebayAccountId = $(d).val();
    });
    selectStoreCategory(ebayAccountId);
}
function isStore(ebayAccountId){
    var data = {"ebayAccountId":ebayAccountId};
    var urll = path+"/ajax/isStore.do";
    $().invoke(
        urll,
        data,
        [function (m, r) {
            if(r.isStore=="1"&&$("input[type='checkbox'][name='ebayAccounts']:checked").length==1){
                $("#StoreCategoryshow").show();
            }else if(r.isStore=="0"){
                $("#StoreCategoryshow").hide();
            }
        },
            function (m, r) {
                alert(r)
            }]
    );
}