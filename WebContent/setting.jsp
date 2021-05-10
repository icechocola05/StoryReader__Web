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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@500&display=swap" rel="stylesheet">

<link rel="stylesheet" href="CSS/index.css">
</head>
<body>
	<div class="head">
		Story Reader
	</div>
	<br>
	<% 		
		ArrayList<String> sent = (ArrayList<String>)session.getAttribute("sent");
		ArrayList<String> speaker = (ArrayList<String>)session.getAttribute("speaker");
		int len = sent.size();
		
		//DB와 연결
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");
		try {
			Statement voiceSt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, Statement.RETURN_GENERATED_KEYS);
			Statement emotionSt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, Statement.RETURN_GENERATED_KEYS);
			
			voiceSt.execute("SELECT * FROM voice");
			emotionSt.execute("SELECT * FROM emotion");
			
			ResultSet voiceRS = voiceSt.getResultSet();
			ResultSet emotionRS = emotionSt.getResultSet();
	%>
	
	
	<form method="Post" action="setVoiceEmotion">
	<table>
		<thead>
			<th>화자</th>
			<th>목소리</th>
		</thead>
		<% 
		
			ArrayList<String> speaker_t=new ArrayList<String>();//중복을 제외한 화자 리스트
			int flag=0;//0-같은 speaker 없음.1-있음
			int j_loc=0;
			for(int i=0; i<speaker.size(); i++) { //문장 수 만큼 행 생성
				System.out.println("==================i = "+i+"===================");
				flag=0;
				for(int j=0;j<speaker_t.size();j++){
					System.out.println("<<"+j+">>");
					System.out.println(speaker_t.get(j));
					System.out.println(speaker_t.get(j)+" vs "+speaker.get(i));
					if(speaker.get(i).equals(speaker_t.get(j))){
						flag=1;//이전 화자 목록에 현재 화자가 있는지
						System.out.println("중복 확인");
					}
				}
				if(flag==1) continue;
				speaker_t.add(speaker.get(i));
				j_loc=speaker_t.size()-1;
				
		%>
		<tbody>
			<td id='speaker_t<%=j_loc%>'> <%= speaker_t.get(j_loc) %> </td> 
			<td>
				<!-- voice option 붙이기-->
				<select id='voice<%=j_loc%>' name='voice<%=j_loc%>'>
					<%voiceRS.first(); //레코드 맨 앞으로 이동 => 다시 처음부터 while 돌면서 출력%> 
					<option value=<%= voiceRS.getString("voice_name") %>><%= voiceRS.getString("voice_kr_name") %></option>
					<% while(voiceRS.next()) { %>
					<option value= <%=voiceRS.getString("voice_name")%>> <%=voiceRS.getString("voice_kr_name")%> </option>
					<% } %>
				</select>
			</td>
		</tbody>
		<%} 
		session.setAttribute("speaker_t", speaker_t);%>
	</table>
	
	<br><br>
	<table>
		<thead>
			<th>화자</th>
			<th>감정 종류</th>
			<th>감정 세기</th>
			<th>문장</th>
		</thead>
		<%	
		for(int i=0; i<len; i++) { //문장 수 만큼 행 생성
		%>
		<tbody border=1>
			<td id='speaker<%=i%>'> <%= speaker.get(i) %> </td> 
			
			<td>
				<!-- emotion option 붙이기-->
				<select id='emotion<%=i%>' name='emotion<%=i%>'>
					<% emotionRS.first(); //레코드 맨 앞으로 이동 => 다시 처음부터 while 돌면서 출력%> 
					<option value=<%=emotionRS.getString("emotion_name") %>><%=emotionRS.getString("emotion_kr_name") %></option>
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
			<td><input type="text" id="sent<%=i%>" name="sent<%=i%>"value="<%= sent.get(i) %>" size="<%=sent.get(i).length()*1.7 %>" style = "text-align:center;"></td>
		
		</tbody>
		<%
			} //for문 
		}catch(SQLException e) {
		e.printStackTrace();
		}
		
		%>

	</table>
	<div style="text-align:center;">
	<input type="SUBMIT" class="center-block" id="submit-btn">
	</div>

</form>
</body>
</html>
