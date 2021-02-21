<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	session = request.getSession();
	String t = (String)session.getAttribute("jsoncheck");
	%>
	<div style="background-color:yellow">
	<%out.println(t);%>
	</div>
	<form method="post" action="fileConnection">
	<input type="submit">
	</form>
</body>
</html>