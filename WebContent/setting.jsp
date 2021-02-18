<%@page import="java.io.*"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
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
	<% Table t=(Table)session.getAttribute("table"); %>
	<div class="content1">
		<textarea rows="10" cols="90"><%= t.getMainTxt() %></textarea>
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
			for(int i=0;i<t.getLength();i++){//table객체의 문장 수 만큼 
			%>
		<tbody>
			<!--request attribute로 Table 객체 받아오기-->
			
			
			<td><%=t.getSpeaker(i) %></td>
			<td>
				<!-- voice option 붙이기-->
				<select id='voice' name='voice'>
					<% for (int j=0 ; j<21 ; j++){ %>
					<option value=<%=t.getVoiceVal(j)%>><%=t.getVoiceOp(j) %></option>
					<% } %>
				</select>
			</td>
			<td>
				<!-- emotion option 붙이기-->
				<select id='emotion' name='emotion'>
					<% for (int j=0 ; j<5 ; j++){ %>
					<option value=<%=t.getEmoVal(j) %>><%=t.getEmoOp(j) %></option>
					<% } %>
				</select>
			</td>
			<td>
				<!-- emotion intensity 붙이기-->
				<input type="range" name="range" min="0" max ="1" step="0.1">
			</td>
			<!-- sentence 붙이기-->
			<td><%=t.getSent(i) %></td>
		
		</tbody>
		<%} %>

	</table>
	<input type="SUBMIT">
	

</form>
</body>
</html>