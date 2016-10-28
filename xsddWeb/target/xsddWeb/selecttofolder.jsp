<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/4/27
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        $(document).ready(function(){
            var url=path+"/ajax/selfFolder.do?folderType=<%=request.getParameter("foldertype")%>";
            $().invoke(url,{},
                    [function(m,r) {
                        if (r == null) {
                            alert("请先创建文件夹！");
                            return;
                        } else {
                            var htmlstr = "<div>选择文件夹：</div>";
                            htmlstr += "<div>";
                            for (var i = 0; i < r.length; i++) {
                                htmlstr += "<div><input type='radio' name='folderid' value='" + r[i].id + "'/><span style='vertical-align: text-bottom;'>" + r[i].configName + "</span></div>";
                            }
                            htmlstr += "</div>";
                            $("#show").html(htmlstr);
                        }
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        });
    </script>
</head>
<body style="background-color: #ffffff">
<div id="show" style="padding-top: 10px;padding-left: 15px;">

</div>
</body>
</html>
