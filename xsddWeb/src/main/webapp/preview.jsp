<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String xx=request.getParameter("itemId");
    request.setAttribute("xx", xx);
%>
<c:catch var ="catchException">
    <c:import url="http://www.sandbox.ebay.com/itm/${xx}" />
</c:catch>
<c:if test="${catchException!=null}">
    物品不在线！
</c:if>
