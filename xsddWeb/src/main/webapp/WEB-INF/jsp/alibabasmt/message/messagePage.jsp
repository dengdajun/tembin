<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/4/13
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#SMTMessageTableList").initTable({
                url:path + "/SMTmessage/ajax/loadSMTmessageList.do?",
                columnData:[
                    {title:"",name:"ch",width:"1%",align:"left",format:makeOption1},
                    {title:"状态",name:"pictureUrl",width:"4%",align:"center",format:makeOption2},
                    {title:"内容",name:"skuCode",width:"16%",align:"left",format:makeOption3},
                    {title:"From > to",name:"price",width:"8%",align:"center",format:makeOption4},
                    {title:"SKU",name:"gmtmodified",width:"8%",align:"center"},
                    {title:"创建时间",name:"gmtcreate",width:"8%",align:"center"},
                    {title:"<div align='center' style='width: 100%;'>操作</div>",name:"option1",width:"2%",align:"center",format:makeOption5}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                showDataNullMsgContext:'没有订单记录!'
            });
            refreshTable();
        });
        function refreshTable(){
            $("#SMTMessageTableList").selectDataAfterSetParm({});
        }
        function makeOption1(json){

        }
        function makeOption2(json){

        }
        function makeOption3(json){

        }
        function makeOption4(json){

        }
      var SMTMessage;
      function ebayListPage(){
          var url=path+"/SMTmessage/ebayListPage.do?";
          SMTMessage=openMyDialog({title: '同步订单',
              content: 'url:'+url,
              icon: 'succeed',
              width:600,
              lock:true,
              zIndex:500
          });
      }
    </script>
</head>
<body>
<input type="button" value="同步站内信" onclick="ebayListPage();">
<div id="SMTMessageTableList"></div>
</body>
</html>
