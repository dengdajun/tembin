<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/3/5
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<html>
<head>
    <script type="text/javascript">
        <%
            String rootPath = request.getContextPath();
        %>
        var path='<%=rootPath%>';
        var _token="";
    </script>

    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/basecss/base.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery-1.9.0.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery-migrate-1.2.1.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery-blockui/jquery.blockUI.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery.cookie.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/base.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/util.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/table/jquery.table.js" /> ></script>

    <script type="text/javascript">


        $(document).ready(function(){
            $("#systemLog,#onlineUser").html("等待中.....");

            $("body").queue(function(){queryLog()} );
            $("body").queue(function(){queryOnlineUser()});
            $("body").queue(function(){queryZH()});
            $("body").queue(function(){queryWait()});

            /*queryLog();
            queryZH();
            queryWait();
            queryOnlineUser();*/
        });

        function queryLog(){
            $().invoke( path+"/getLogList.do",
                    {},
                    function(m,r){
                        var p=path+"/downLog.do?fileName="
                        $("#systemLog").html("")
                        if(r!=null){
                            var logss = r.split("|");
                            for(var ll in logss){
                                $("#systemLog").append("<a href="+p+""+logss[ll]+" >"+logss[ll]+"</a>&nbsp;|&nbsp;")
                            }
                        }
                        $("body").dequeue();
                    })
        }

        //查询在线用户
        function queryOnlineUser(){
            $().invoke( path+"/queryOnlineUser.do",
                    {},
            function(m,r){
                $("#onlineUser").html(r);
                $("body").dequeue();
            })
        }

        //查询帐号
        function queryZH(){
            $("#mainZH").initTable({
                url:"/xsddWeb/countPageQueryAlluserList.do",
                columnData:[
                    {title:"ebay账户",name:"userLoginId",width:"8%",align:"left"},
                    {title:"创建时间",name:"createTime",width:"8%",align:"left"},
                    {title:"最近登录",name:"newLoinTime",width:"8%",align:"left"},
                    {title:"手机号码",name:"telPhone",width:"8%",align:"left"},
                    {title:"操作",name:"",width:"8%",align:"left",format:function(json){return "<a onclick=chaxun(this,'"+json.userId+"') href='javascript:void(0)'>查看</a>"}},
                    {title:"context",name:"",width:"50%",align:"left"}

                ],
                selectDataNow:true,
                isrowClick:false,
                showIndex:false,
                sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 50},
                onlyFirstPage:true
            });
            $("body").dequeue();
        }

        function chaxun(obj,userid){
            $().invoke(
                    path+"/queryPageListingCount.do",
                    {"userID":userid},
                    function(m,r){
                        var td=obj.parentNode;
                        $(td).next().html(r);
                    }
            );
        }

        //=====================开通账号==================================
        function queryWait(){
            $("#waitActive").initTable({
                url:"/xsddWeb/queryWaitActiveUserList.do",
                columnData:[
                    {title:"ebay账户",name:"userLoginId",width:"8%",align:"left"},
                    {title:"创建时间",name:"createTime",width:"8%",align:"left"},
                    {title:"手机号码",name:"telPhone",width:"8%",align:"left"},
                    {title:"公司",name:"orgName",width:"8%",align:"left"},
                    {title:"操作",name:"",width:"8%",align:"left",format:function(json){return "<a onclick=kaitong(this,'"+json.userId+"') href='javascript:void(0)'>开通</a>"}}

                ],
                selectDataNow:true,
                isrowClick:false,
                showIndex:false,
                sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 50},
                onlyFirstPage:true
            });
            $("body").dequeue();
        }

        function kaitong(o,userid){
            $().invoke(
                    path+"/activeUser.do",
                    {"userID":userid},
                    function(m,r){
                        $("#waitActive").refresh();
                        alert(r);
                    }
            );
        }


    </script>

    <title>统计</title>
</head>
<body>
<table>
    <tr>
        <td>用户总数</td>
        <td>主账户数</td>
        <td>停用账户数</td>
    </tr>
    <tr>
        <td>${allUser}</td>
        <td>${mainUser}</td>
        <td>${stopUser}</td>
    </tr>
</table>


在线用户
<div id="onlineUser"></div>
<br/><br/><br/>

日志下载
<div id="systemLog"></div>
<br/><br/><br/>

<div id="waitActive"></div>
<br/><br/><br/>
<div id="mainZH"></div>
</body>
</html>
