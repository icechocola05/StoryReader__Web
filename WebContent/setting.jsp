<%@page import="java.io.*"%>
<%@page import="java.util.*" %>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Story Reader</title>
<link rel="stylesheet" href="CSS/setting.css">

</head>
<body>
	<% 		
		ArrayList<String> sent = (ArrayList<String>)session.getAttribute("sent");
		ArrayList<String> speaker = (ArrayList<String>)session.getAttribute("speaker");
		
		//DB와 연결
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");

	%>
	
	<form method="Post" action="setVoiceEmotion">
	<table>
		<thead>
			<th>화자</th>
			<th>목소리</th>
			<th>감정 종류</th>
			<th>감정 세기</th>
			<th>문장</th>
		</thead>
		<%
			try {
				Statement voiceSt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, Statement.RETURN_GENERATED_KEYS);
				Statement emotionSt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, Statement.RETURN_GENERATED_KEYS);
				
				voiceSt.execute("SELECT * FROM voice");
				emotionSt.execute("SELECT * FROM emotion");
				
				ResultSet voiceRS = voiceSt.getResultSet();
				ResultSet emotionRS = emotionSt.getResultSet();
				
				int len = sent.size();
				for(int i=0; i<len; i++) { //문장 수 만큼 행 생성
			%>
		<tbody>
			<td> <%= speaker.get(i) %> </td> 
			<td>
				<!-- voice option 붙이기-->
				<select id='voice' name='voice<%=i%>'>
					<%voiceRS.first(); //레코드 맨 앞으로 이동 => 다시 처음부터 while 돌면서 출력%> 
					<option value=<%= voiceRS.getString("voice_name") %>><%= voiceRS.getString("voice_kr_name") %></option>
					<% while(voiceRS.next()) { %>
					<option value= <%=voiceRS.getString("voice_name")%>> <%=voiceRS.getString("voice_kr_name")%> </option>
					<% } %>
				</select>
			</td>
			<td>
				<!-- emotion option 붙이기-->
				<select id='emotion' name='emotion<%=i%>'>
					<%emotionRS.first(); //레코드 맨 앞으로 이동 => 다시 처음부터 while 돌면서 출력%> 
					<option value=<%= emotionRS.getString("emotion_name") %>><%= emotionRS.getString("emotion_kr_name") %></option>
					<% while(emotionRS.next()){ %>
					<option value=<%= emotionRS.getString("emotion_name") %>><%= emotionRS.getString("emotion_kr_name") %></option>
					<% } %>
				</select>
			</td>
			<td>
				<!-- emotion intensity 붙이기-->
				<input type="range" name="range<%=i%>" min="0" max ="1" step="0.1" value="0.5">
			</td>
			<!-- sentence 붙이기-->
			<td><%= sent.get(i) %></td>
		
		</tbody>
		<%
			} //for문 
		}catch(SQLException e) {
		e.printStackTrace();
		}
		%>

	</table>
	<input type="SUBMIT">
	

</form>
</body>
</html>
