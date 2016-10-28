/**
 * Created by Administrtor on 2015/3/19.
 * 页面加载，加载第一层分类信息
 */
$(document).ready(function () {
    _sku='smt';
    loadCategory('','1',"false");
    seriesLoadScripts(ueditorJSS_,function(){
        $().image_editor.init("initSelectPic"); //编辑器的实例id
        $().image_editor.show("selectPic"); //上传图片的按钮id
    });
    initData();
    setTimeout(function () {
        moreAttrShow();
    }, 1000);

});

function initData(){
    $("select[name='deliverytime']").find("option[value='"+deliverytime+"']").attr("selected", true);
    $("select[name='wsvalidnum']").find("option[value='"+wsvalidnum+"']").attr("selected", true);
    $("select[name='productunit']").find("option[value='"+productunit+"']").attr("selected", true);
    if(imageurls!=null&&imageurls.length>0){
        var img=imageurls.split(";");
        var picstr="";
        for(var i=0;i<img.length;i++){
            picstr += '<li><div style="position:relative"><img src='+img[i]+' height="80px" width="78px" />' +
                '<div style="text-align: right;background-color: dimgrey;"><img src="'+path+'/img/newpic_ico.png" onclick="removeThisPic(this)"></div>';
            picstr += "</li>";
        }
        if(picstr!=""){
            $("#showStaticPic").html(picstr);
        }
    }
    tjPicNumber();
    loadCategoryAttr(categoryid);
}
function tjPicNumber(){
    $("#picNumber").text($("#showStaticPic").find("li").length);
}
function removeThisPic(obj){
    $(obj).parent().parent().parent().remove();
    tjPicNumber();
}
/**
 * 加载第一级分类
 * @param id
 */
function loadCategory(id,level,isleaf){
    removeDiv(level);
    if(isleaf=="true"){
        var showName = "";
        $("select[name='selectCategory']").each(function(i,d){
            showName+=$(d).find("option:selected").text()+" －〉 ";
        });
        $("#showCategoryName").html(showName.substr(0,showName.length-4));
        $("#categoryid").val(id);
        loadCategoryAttr(id);
        return;
    }
    if(id==null||id==""){
        id="0";
    }

    $().invoke(
            path+"/alieproduct/ajax/getAlieCategoryList.do?id="+id+"&level="+level,
        {},
        [
            function(m,r){
                var domnum=$("#selectcate").find('#div_'+level).length;
                var divid;
                if(domnum==0){
                    divid=addDvi();
                }else{
                    divid='div_'+level;
                }
                var map = r.lim;
                var select="<select size='8' name='selectCategory' style='width: 250px' multiple=\"multiple\">";
                for(var i =0;i<map.length;i++){
                    var m = map[i];
                    select+="<option onclick=loadCategory('"+ m.id+"','"+ m.level+"','"+ m.isleaf+"') value='"+ m.id+"'>"+m.names.zh+"</option>";
                }
                select+="</select>"
                $('#'+divid).html(select);
            },
            function(m,r){
                alert(r)
            }
        ]
    );
}

/**添加div到页面*/
function addDvi(){
    var n=makeDivId();
    var id="div_"+ n;
    var className="divl_buju_left";
    var div="<div id="+id+" class="+className+" style=\"width: 270px;height:160px\">1</div>";
    $('#selectcate').append(div);
    return id;
}
function makeDivId(){
    var n=$("#selectcate div").length;
    return (parseInt(n)+1);
}

/**判断需要移除的div*/
function removeDiv(level) {
    if (level == undefined) {
        return
    }
    var levelInt = parseInt(level);
    $("#selectcate").find('div[id^=div_]').each(function (i, d) {
        var idn = strGetNum(d.id.replace("_", ""));
        if (parseInt(idn) >= level) {
            $(d).remove();
        }
    });
}
/**
 * 查询产品分类属性
 */
function loadCategoryAttr(cateid){
    if(property!=null&&property!=""){
        property = eval("("+property+")");
    }
    $().invoke(
            path+"/alieproduct/ajax/getAlieCategoryAttr.do?cateid="+cateid,
        {},
        [
            function(m,r){
                var map = r.lim;
                var attrValue="";
                var str="<div style='float:inherit;width: 100%;margin: 4px;'><table>";
                for(var i =0;i<map.length;i++){
                    var m = map[i];
                    if(!m.sku){//只显示非SKU属性的数据
                        str+="<tr>";
                        str+="<td style='text-align: right;width: 80px;'>";
                        str+="<input type='hidden' name='smtAeopaeProductProperties["+i+"].attrnameid' value='"+ m.id+"'/><input type='hidden' name='smtAeopaeProductProperties["+i+"].attrname' value='"+m.names.zh+"'/>";
                        if(m.required){
                            str+=m.names.zh+"<span style='color: red;vertical-align: sub;'>*</span>";
                        }else{
                            str+=m.names.zh;
                        }
                        str+=":</td>";
                        if(m.values!=null&&m.values.length>0){
                            str+="<td>";
                            if(m.attributeShowTypeValue=="check_box"){
                                for (var j = 0; j < m.values.length; j++) {
                                    var ns = m.values[j];
                                    var aaa=false;
                                    for(var ps=0;ps<property.length;ps++){
                                        aaa=false;
                                        if(m.id==property[ps].attrnameid&&ns.id==property[ps].attrvalueid){
                                            aaa=true;
                                            break;
                                        }
                                    }
                                    if(aaa){
                                        str += "<span><input checked='true' type='checkbox' name='smtAeopaeProductProperties["+i+"].attrvalueid' value='" + ns.id + "' style='margin-left: 4px;margin-right: 4px;'><span style='vertical-align: text-bottom;'>" + ns.names.zh + "</span></span>";
                                    }else{
                                        str += "<span><input type='checkbox' name='smtAeopaeProductProperties["+i+"].attrvalueid' value='" + ns.id + "' style='margin-left: 4px;margin-right: 4px;'><span style='vertical-align: text-bottom;'>" + ns.names.zh + "</span></span>";
                                    }
                                }
                            }else {
                                str += "<select name='smtAeopaeProductProperties["+i+"].attrvalueid'>"
                                str += "<option value=''>---请选择---</option>"
                                for (var j = 0; j < m.values.length; j++) {
                                    var ns = m.values[j];
                                    var aaa=false;
                                    for(var ps=0;ps<property.length;ps++){
                                        aaa=false;
                                        if(m.id==property[ps].attrnameid&&ns.id==property[ps].attrvalueid){
                                            aaa=true;
                                            break;
                                        }
                                    }
                                    if(aaa){
                                        str += "<option value='" + ns.id + "' selected='true'>" + ns.names.zh + "</option>";
                                    }else {
                                        str += "<option value='" + ns.id + "'>" + ns.names.zh + "</option>";
                                    }
                                }
                                str += "</select>";
                            }
                            str+="</td>";
                        }else{
                            str+="<td><input type='text' class='form-control'/></td>"
                        }
                    }else{//SKU属性，多属性
                        if(m.id!="200007763"){
                            attrValue+="<tr><td style='text-align: right;width: 80px;'>"+m.names.zh+":</td>";
                            attrValue+="<td>";
                            if(m.values!=null&&m.values.length>0){
                                for (var j = 0; j < m.values.length; j++) {
                                    var ns = m.values[j];
                                    var aaa=false;
                                    var sku = eval("{"+lisku+"}");
                                    if(sku!=null&&sku!=undefined){
                                        for(var ps=0;ps<sku.length;ps++){
                                            var proli = sku[ps].listSmtskuPro;
                                            aaa=false;
                                            for(var nsbs=0;nsbs<proli.length;nsbs++){
                                                if(m.id==proli[nsbs].skupropertyid&&proli[nsbs].propertyvalueid!=undefined&&ns.id==proli[nsbs].propertyvalueid){
                                                    aaa=true;
                                                    break;
                                                }
                                            }
                                            if(aaa){
                                                break;
                                            }
                                        }
                                    }
                                    if(aaa){
                                        attrValue += "<span><input type='checkbox' checked='checked' onchange=moreAttrShow() customized='" + m.customizedPic + "' name='more_" + m.id + "' zhid='" + ns.id + "' zhname='" + ns.names.zh + "' value='" + ns.id + "' style='margin-left: 4px;margin-right: 4px;'><span style='vertical-align: text-bottom;'>" + ns.names.zh + "</span></span>";
                                    }else {
                                        attrValue += "<span><input type='checkbox' onchange=moreAttrShow() customized='" + m.customizedPic + "' name='more_" + m.id + "' zhid='" + ns.id + "' zhname='" + ns.names.zh + "' value='" + ns.id + "' style='margin-left: 4px;margin-right: 4px;'><span style='vertical-align: text-bottom;'>" + ns.names.zh + "</span></span>";
                                    }
                                }
                            }
                            attrValue+="</td></tr>";
                            attrValue+="<tr><td colspan='2' id='showselect_"+ m.id+"' attrid='"+ m.id+"' showname='"+m.names.zh+"' name='showselect' customized='"+ m.customizedPic+"' style='padding-left: 80px;'></td></tr>"
                        }
                    }
                }
                str+="</table></div>";
                $("#showAllAttr").html(str);
                if(attrValue!=""&&attrValue!=null){
                    $("#moreAttrValue").html("<table>"+attrValue+"</table>");
                }
            },
            function(m,r){
                alert(r)
            }
        ]
    );
}
var names="";
/**
 * 加载多属性ＳＫＵ，带图片
 */
/*
function loadMoreMainAttr(){
    var str = "";
    $("td[name='showselect'][customized='true']").each(function(i,d){
        str+="<table style='width: 60%;' class='mytable-striped'><tbody><tr style='height: 32px;'>";
        str+="<td>"+$(d).attr("showname")+"</td>";
        str+="<td>自定义名称</td>";
        str+="<td>图片（无图片可以不填）</td></tr></tbody>";
        $("input[type='checkbox'][name='more_"+$(d).attr("attrid")+"']:checked").each(function(ii,dd){
            if($(dd).attr("checked")=="checked"){
                str+="<tr>";
                str+="<td><input type='hidden' name='smtAeopaeProductSkuFroms["+i+"].skuPropertyList["+ii+"].skupropertyid' value='"+$(d).attr("attrid")+"'/><input type='hidden' name='smtAeopaeProductSkuFroms["+i+"].skuPropertyList["+ii+"].propertyvalueid' value='"+$(dd).attr("zhid")+"'/>"+$(dd).attr("zhname")+"</td>";
                str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+i+"].skuPropertyList["+ii+"].propertyvaluedefinitionname' class='form-control'/></td>";
                str+="<td><input type='file' name='smtAeopaeProductSkuFroms["+i+"].skuPropertyList["+ii+"].skuimage'/></td>";
                str+="</tr>";
            }
        });
        names=$(d).attr("showname");
        $(d).html(str);
    });

    //加载SKU主要的信息
    var html="";
    html+="<table style='width: 60%;'  class='mytable-striped'><tbody><tr style='height: 32px;'>";
    html+="<td>名称</td>";
    html+="<td>零售价</td>";
    html+="<td>库存</td>";
    html+="<td>库存量</td>";
    html+="<td>商品编码</td></tr></tbody>";
    html+="</table>"
}
*/
/**
 * 加载多属性，系统自己定义好的
 */
/*function loadMoreAttr(){
    var str="";
    var is=0;
    $("td[name='showselect'][customized='false']").each(function(is,ds){
        if($("input[type='checkbox'][customized='true']:checked").length>0){
            str+="<table style='width: 60%;'  class='mytable-striped'><tbody><tr style='height: 32px;'>";
            str+="<td>"+names+"</td>";
            str+="<td>规格</td>";
            str+="<td>零售价</td>";
            str+="<td>库存</td>";
            str+="<td>库存量</td>";
            str+="<td>商品编码</td></tr></tbody>";

            $("input[type='checkbox'][customized='true']:checked").each(function(i,d){
                $("input[type='checkbox'][customized='false']:checked").each(function(ii,dd){
                    str+="<tr>";
                    str+="<td>"+$(d).attr("zhname")+"</td>";
                    str+="<td>"+$(dd).attr("zhname")+"</td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+is+"].skuprice' class='form-control'/></td>";
                    str+="<td><select><option>有货</option></select></td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+is+"].ipmskustock' class='form-control'/></td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+is+"].skucode' class='form-control'/></td></tr>";
                    is++;
                });
            });
        }else{
            str+="<table style='width: 60%;'  class='mytable-striped'><tbody><tr style='height: 32px;'>";
            str+="<td>"+names+"</td>";
            str+="<td>零售价</td>";
            str+="<td>库存</td>";
            str+="<td>库存量</td>";
            str+="<td>商品编码</td></tr></tbody>";
            $("input[type='checkbox'][customized='true']:checked").each(function(ii,dd){
                str+="<tr>";
                str+="<td>"+$(dd).attr("zhname")+"</td>";
                str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+is+"].skuprice' class='form-control'/></td>";
                str+="<td><select><option>有货</option></select></td>";
                str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+is+"].ipmskustock' class='form-control'/></td>";
                str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+is+"].skucode' class='form-control'/></td></tr>";
                is++;
            });
        }
        $(ds).html(str);
    });
}*/
/**
 * 选择单位，变动其它单位信息
 */
function selectUnit(obj){
    $("span[name='unit']").text($(obj).find("option:selected").text());
}
/**
 * 选择多属性执行的方法
 */
function moreAttrShow(){
    var mmain = { maps: []};
    var mainid = "";
    $("input[type='checkbox'][customized='true']:checked").each(function(i,d){
        var names = $(d).prop("name");
        mainid = names.substr(5,names.length);
        var tempstr = { name: $(d).attr("zhname"), value: $(d).val()};
        mmain.maps.push(tempstr);
    });
    var sku = eval("{"+lisku+"}");
    //加载主要的分类属性
    if(mmain.maps.length>0){
        var str ="";
        str+="<table style='width: 60%;' class='mytable-striped'><tbody><tr style='height: 32px;'>";
        str+="<td>分类</td>";
        str+="<td>自定义名称</td>";
        str+="<td>图片（无图片可以不填）</td></tr></tbody>";
        for(var i = 0;i<mmain.maps.length;i++){
            var aa = mmain.maps[i];
            str+="<tr>";
            str+="<td>"+aa.name+"</td>";
            if(sku!=null&&sku!=undefined){
                var abcd = false;
                for(var is=0;is<sku.length;is++){
                    var skuss = sku[is];
                    for(var isi=0;isi<skuss.listSmtskuPro.length;isi++){
                        var skupro = skuss.listSmtskuPro[isi];
                        if(aa.value==skupro.propertyvalueid){
                            if(skupro.propertyvaluedefinitionname!=undefined&&skupro.propertyvaluedefinitionname!=""){
                                str+="<td><input type='text' name='propertyvaluedefinitionname' class='form-control' value='"+skupro.propertyvaluedefinitionname+"' /></td>";
                            }else{
                                str+="<td><input type='text' name='propertyvaluedefinitionname' class='form-control' value='' /></td>";
                            }
                            str+="<td><input type='file' name='skuimage'/></td>";
                            abcd=true;
                            break;
                        }
                    }
                    if(abcd){
                        break;
                    }
                }
                if(!abcd){
                    str+="<td><input type='text' name='propertyvaluedefinitionname' class='form-control'/></td>";
                    str+="<td><input type='file' name='skuimage'/></td>";
                }
            }else{
                str+="<td><input type='text' name='propertyvaluedefinitionname' class='form-control'/></td>";
                str+="<td><input type='file' name='skuimage'/></td>";
            }
            str+="</tr>";
        }
        str+="</table>";
        //console.log(str);
        $("#showselect_"+mainid).html(str);
    }
    //加载分类属性
    var mdel = {mapss : []};
    $("input[type='checkbox'][customized='false']:checked").each(function(i,d){
        var tempstr = { name: $(d).attr("zhname"), value: $(d).val()}
        mdel.mapss.push(tempstr);
    });
    var str = "";
    if(mmain.maps.length>0){
        str+="<table style='width: 60%;'  class='mytable-striped'><tbody><tr style='height: 32px;'>";
        str+="<td>分类</td>";
        if(mdel.mapss.length>0){
            str+="<td>规格</td>";
        }
        str+="<td>零售价</td>";
        str+="<td>库存</td>";
        str+="<td>库存量</td>";
        str+="<td>商品编码</td></tr></tbody>";
        for(var i = 0;i<mmain.maps.length;i++) {
            var aa = mmain.maps[i];
            if(mdel.mapss.length>0){
                for(var ii=0;ii<mdel.mapss.length;ii++){
                    var bb = mdel.mapss[ii];
                    var abcd = false;
                    var skuss = null;
                    if(sku!=null&&sku!=undefined){
                        for(var is=0;is<sku.length;is++) {
                            skuss = sku[is];
                            var flagstr = JSON.stringify(skuss)
                            if(flagstr.indexOf(aa.value)>0&&flagstr.indexOf(bb.value)>0){
                                abcd=true;
                                break;
                            }
                        }
                    }
                    if(abcd){
                        str+="<tr>";
                        str+="<td>"+aa.name+"</td>";
                        str+="<td>"+bb.name+"<input type='hidden' name='smtAeopaeProductSkuFroms["+ii+"].parentValue' value='"+aa.value+"'/></td>";
                        str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].skuprice' class='form-control' value='"+skuss.skuprice+"'/></td>";
                        if(skuss.skustock=="1"){
                            str+="<td><select name='smtAeopaeProductSkuFroms["+ii+"].skustock'><option value='1' selected>有货</option><option value='0'>无货</option></select></td>";
                        }else{
                            str+="<td><select name='smtAeopaeProductSkuFroms["+ii+"].skustock'><option value='1'>有货</option><option value='0' selected>无货</option></select></td>";
                        }
                        str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].ipmskustock' class='form-control' value='"+skuss.ipmskustock+"'/></td>";
                        str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].skucode' class='form-control' value='"+skuss.skucode+"'/></td></tr>";
                    }else{
                        str+="<tr>";
                        str+="<td>"+aa.name+"</td>";
                        str+="<td>"+bb.name+"</td>";
                        str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].skuprice' class='form-control'/></td>";
                        str+="<td><select name='smtAeopaeProductSkuFroms["+i+"].skustock'><option value='1' selected>有货</option><option value='0'>无货</option></select></td>";
                        str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].ipmskustock' class='form-control'/></td>";
                        str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].skucode' class='form-control'/></td></tr>";
                    }
                }
            }else{
                str+="<tr>";
                var abcd = false;
                var skuss = null;
                if(sku!=null&&sku!=undefined){
                    for(var is=0;is<sku.length;is++) {
                        skuss = sku[is];
                        for (var isi = 0; isi < skuss.listSmtskuPro.length; isi++) {
                            var skupro = skuss.listSmtskuPro[isi];
                            if(aa.value==skupro.propertyvalueid){
                                abcd=true;
                                break;
                            }
                        }
                        if(abcd){
                            break;
                        }
                    }
                }
                if(abcd){
                    str+="<td>"+aa.name+"<input type='hidden' name='smtAeopaeProductSkuFroms["+i+"].parentValue' value='"+aa.value+"'</td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+i+"].skuprice' class='form-control' value='"+skuss.skuprice+"'/></td>";
                    if(skuss.skustock=="1"){
                        str+="<td><select name='smtAeopaeProductSkuFroms["+i+"].skustock'><option value='1' selected>有货</option><option value='0'>无货</option></select></td>";
                    }else{
                        str+="<td><select name='smtAeopaeProductSkuFroms["+i+"].skustock'><option value='1'>有货</option><option value='0' selected>无货</option></select></td>";
                    }

                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+i+"].ipmskustock' class='form-control' value='"+skuss.ipmskustock+"'/></td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+i+"].skucode' class='form-control' value='"+skuss.skucode+"'/></td></tr>";
                }else{
                    str+="<td>"+aa.name+"</td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+i+"].skuprice' class='form-control'/></td>";
                    str+="<td><select name='smtAeopaeProductSkuFroms["+i+"].skustock'><option value='1' selected>有货</option><option value='0'>无货</option></select></td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+i+"].ipmskustock' class='form-control'/></td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+i+"].skucode' class='form-control'/></td></tr>";
                }

            }
        }
    }else{
        str+="<table style='width: 60%;'  class='mytable-striped'><tbody><tr style='height: 32px;'>";
        if(mdel.mapss.length>0){
            str+="<td>规格</td>";
        }
        str+="<td>零售价</td>";
        str+="<td>库存</td>";
        str+="<td>库存量</td>";
        str+="<td>商品编码</td></tr></tbody>";
        if(mdel.mapss.length>0){
            for(var ii=0;ii<mdel.mapss.length;ii++){
                str+="<tr>";
                var bb = mdel.mapss[ii];
                var abcd = false;
                var skuss = null;
                if(sku!=null&&sku!=undefined){
                    for(var is=0;is<sku.length;is++) {
                        skuss = sku[is];
                        for (var isi = 0; isi < skuss.listSmtskuPro.length; isi++) {
                            var skupro = skuss.listSmtskuPro[isi];
                            if(bb.value==skupro.propertyvalueid){
                                abcd=true;
                                break;
                            }
                        }
                        if(abcd){
                            break;
                        }
                    }
                }
                if(abcd){
                    str+="<td>"+bb.name+"<input type='hidden' name='smtAeopaeProductSkuFroms["+ii+"].parentValue' value='"+bb.value+"'</td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].skuprice' class='form-control' value='"+skuss.skuprice+"'/></td>";
                    if(skuss.skustock=="1"){
                        str+="<td><select name='smtAeopaeProductSkuFroms["+ii+"].skustock'><option value='1' selected>有货</option><option value='0'>无货</option></select></td>";
                    }else{
                        str+="<td><select name='smtAeopaeProductSkuFroms["+ii+"].skustock'><option value='1'>有货</option><option value='0' selected>无货</option></select></td>";
                    }
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].ipmskustock' class='form-control' value='"+skuss.ipmskustock+"'/></td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].skucode' class='form-control' value='"+skuss.skucode+"'/></td></tr>";
                }else{
                    str+="<td>"+bb.name+"</td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].skuprice' class='form-control'/></td>";
                    str+="<td><select name='smtAeopaeProductSkuFroms["+ii+"].skustock'><option value='1' selected>有货</option><option value='0'>无货</option></select></td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].ipmskustock' class='form-control'/></td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms["+ii+"].skucode' class='form-control'/></td></tr>";
                }


            }
        }else{
            if(sku!=null&&sku!=undefined){
                var skuss = null;
                for(var is=0;is<sku.length;is++) {
                    skuss = sku[is];
                    str+="<tr>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms[0].skuprice' class='form-control' value='"+skuss.skuprice+"'/></td>";
                    if(skuss.skustock=="1"){
                        str+="<td><select name='smtAeopaeProductSkuFroms["+ii+"].skustock'><option value='1' selected>有货</option><option value='0'>无货</option></select></td>";
                    }else{
                        str+="<td><select name='smtAeopaeProductSkuFroms["+ii+"].skustock'><option value='1'>有货</option><option value='0' selected>无货</option></select></td>";
                    }
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms[0].ipmskustock' class='form-control' value='"+skuss.ipmskustock+"'/></td>";
                    str+="<td><input type='text' name='smtAeopaeProductSkuFroms[0].skucode' class='form-control' value='"+skuss.skucode+"'/></td></tr>";
                }
            }
        }
    }
    str+="</table>";
    $("#moreAttrPic").html(str);
    //loadMoreMainAttr();
    //loadMoreAttr();
}
/**
 * 保存产品信息，后台处理
 */
function saveData(name){
    var data = $('#form').serialize();
    if(name=="save"){
        $().invoke(
                path+"/alieproduct/ajax/saveProductData.do",
            data,
            [
                function(m,r){
                    alert(r);
                },
                function(m,r){
                    alert(r)
                }
            ]
        );
    }
}

/**
 * 选择图片操作
 * @param obj
 */
var picType;
function selectAliePic(obj){
    picType = $(obj).attr("pictype");
    afterUploadCallback = {"imgURLS": addAliePictrueUrl};
}

/**
 * 上付完图片后续操作，比如显示，显示在那个地方的操作
 */
function addAliePictrueUrl(urls){
    if(picType=="mainPic"){
        var picstr="";
        for (var i = 0; i < urls.length; i++) {
            var imgsrc = urls[i].src.replace("http@", "http:");
            picstr += '<li><div style="position:relative"><img src='+imgsrc+' height="80px" width="78px" />' +
                '<div style="text-align: right;background-color: dimgrey;"><img src="'+path+'/img/newpic_ico.png" onclick="removeThisPic(this)"></div>';
            picstr += "</li>";
        }
        if(picstr!=""){
            $("#showStaticPic").append(picstr);
        }
        tjPicNumber();
    }else{

    }

}