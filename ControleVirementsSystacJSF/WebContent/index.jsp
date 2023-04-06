<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%
	String url = "/views/home/Main.jsf?uid=".concat(request.getParameter("uid"));
    ViewHelper.userId = request.getParameter("uid");
%>

<%@page import="com.afb.portal.presentation.tools.ViewHelper"%>
<!-- default skins: DEFAULT, plain, emeraldTown, blueSky, wine, japanCherry, ruby, classic, deepMarine, NULL -->
<html>
	<body>
		<jsp:forward page="<%=url %>"></jsp:forward>
	</body>
</html>