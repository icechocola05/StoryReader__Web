<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="org.json.simple.JSONArray" %>
<%@page import="org.json.simple.JSONObject" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	session = request.getSession();
	int index = (int)session.getAttribute("i");
	JSONArray resultJson = new JSONArray();
	resultJson = (JSONArray) session.getAttribute("resultJson");
	String finalPath = (String) session.getAttribute("path");
	System.out.println(finalPath);
	
	for(int i=0; i<index+1; i++) { %>
	<% 
	//JSONObject innerObj = resultJson.getJSONObject(i);
	//String text = innerObj.getString("text");
	%>
	<audio controls>
    <source src="output/<%= i %>.wav" type="audio/wav">
	</audio>
	<br>
	<% } %>
	
	
</body>
</html>