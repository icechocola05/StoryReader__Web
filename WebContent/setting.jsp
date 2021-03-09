<%@page import="java.io.*"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
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
		ArrayList<TextInfo> t = new ArrayList<TextInfo>();
		t=(ArrayList<TextInfo>)session.getAttribute("textInfo"); //문장 받아오기
		String mainTxt=(String)session.getAttribute("mainTxt"); //full text 받아오기
		
		//DB와 연결
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");

		//Table table=new Table();
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
			try {
				Statement voiceSt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, Statement.RETURN_GENERATED_KEYS);
				Statement emotionSt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, Statement.RETURN_GENERATED_KEYS);
				
				voiceSt.execute("SELECT * FROM voice");
				emotionSt.execute("SELECT * FROM emotion");
				
				ResultSet voiceRS = voiceSt.getResultSet();
				ResultSet emotionRS = emotionSt.getResultSet();
			
				for(int i=0;i<t.size();i++){ //문장 수 만큼 행 생성
			%>
		<tbody>
			<td> <%=t.get(i).getSpeaker()%> </td> 
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
			<td><%=t.get(i).getText()%></td>
		
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
