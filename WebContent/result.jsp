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
	String resultSent[] = request.getParameterValues("sent");
	String resultVoice[] = request.getParameterValues("voice");
	String resultEmo1[] = request.getParameterValues("emotion");
	String resultEmo2[] = request.getParameterValues("range");
	int n = resultVoice.length;
	for (int i = 0; i < n; i++)
		out.println(resultVoice[i] + " " + resultEmo1[i] + " " + resultEmo2[i] + " " + t.getSent(i) + "<br>");
	%>





</body>
</html>