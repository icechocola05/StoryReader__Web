<%@page import="java.io.*"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Story Reader</title>
<link rel="stylesheet" href="CSS/setting.css">

</head>
<body>
	<% 
		//path 설정
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");
				
		String book_title = request.getParameter("bookname");
		String book_author = request.getParameter("bookauthor");
		
		String mainTxt=(String)session.getAttribute("mainTxt");
	%>
	<div class="content1">
		<textarea rows="10" cols="90"><%= mainTxt %></textarea>
	</div>
	<form method="Post" action="setVoiceEmoServlet">
	<table>
		<thead>
			<th>화자</th>
			<th>목소리</th>
			<th>감정 종류</th>
			<th>감정 세기</th>
			<th>문장</th>
		</thead>
		<%
			for(int i=0;i<t.size();i++){//table객체의 문장 수 만큼 
			%>
		<tbody>
			<td><%=t.get(i).getSpeaker()%></td>
			<td>
				<!-- voice option 붙이기-->
				<select id='voice' name='voice<%=i%>'>
					<% for (int j=0 ; j<21 ; j++){ %>
					<option value=<%=table.getVoiceVal(j)%>><%=table.getVoiceOp(j) %></option>
					<% } %>
				</select>
			</td>
			<td>
				<!-- emotion option 붙이기-->
				<select id='emotion' name='emotion<%=i%>'>
					<% for (int j=0 ; j<5 ; j++){ %>
					<option value=<%=table.getEmoVal(j) %>><%=table.getEmoOp(j) %></option>
					<% } %>
				</select>
			</td>
			<td>
				<!-- emotion intensity 붙이기-->
				<input type="range" name="range<%=i%>" min="0" max ="1" step="0.1" value="0.5">
			</td>
			<!-- sentence 붙이기-->
			<td><%=t.get(i).getText()%></td>
		
		</tbody>
		<%} %>

	</table>
	<input type="SUBMIT">
	

</form>
</body>
</html>
