<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
This is final.
<%
	session = request.getSession();
	int index = (int)session.getAttribute("i");
	String finalPath = (String)session.getAttribute("path");
	for(int i=0; i<index+1; i++) { %>
		<audio>
		<source src="<%= finalPath %>" alt="오디오 입니다">
		</audio>
		<% } %>
	
	
</body>
</html>