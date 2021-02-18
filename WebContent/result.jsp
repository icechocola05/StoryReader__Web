<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


	<%
	session = request.getSession();
	model.Table t = (Table)session.getAttribute("table");
	int n = t.getLength();
	for (int i = 0; i < n; i++)
		out.println(t.getVoiceName(i) + " " + t.getEmo1(i) + " " + t.getEmo2(i) + " " + t.getSent(i) + "<br>");
	%>

	<form method="post" action="makeJsonServlet">
	<input type="submit">
	</form>



</body>
</html>