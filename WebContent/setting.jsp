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
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<script src="https://code.iconify.design/1/1.0.6/iconify.min.js"></script>

<link rel="stylesheet" href="CSS/setting.css">	
</head>

<body>
	<div class="head">
		<span>Story Reader</span>
	</div>
	
	<div class="main">
		<div class="progress">
			<span class="pro1">등록 방식 설정</span>
			<span class="pro2">텍스트 등록</span>
			<span class="pro3">화자 설정</span>
			<span class="pro4">완성</span>
			<hr />
			<span class="dot1"></span>
			<span class="dot2"></span>
			<span class="dot3"></span>
			<span class="dot4"></span>
		</div>
	</div>
	
	
	<% 		
		ArrayList<String> sent = (ArrayList<String>)session.getAttribute("sent");
		ArrayList<String> speaker = (ArrayList<String>)session.getAttribute("speaker");
		
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
	
	<div class="content">
		<div class="speakers">
			<%
				//화자 목소리 설정
				for(int i=0; i<speaker.size(); i++) { %>
				<span> <%=	speaker.get(i) %> </span> 
					<select id='voice<%=i%>' name='voice<%=i%>'onchange="changeSpeakerVoice(<%=i%>)">
						<%voiceRS.first(); //레코드 맨 앞으로 이동 => 다시 처음부터 while 돌면서 출력%> 
						<option value=<%= voiceRS.getString("voice_name") %>><%= voiceRS.getString("voice_kr_name") %></option>
						<% while(voiceRS.next()) { %>
						<option value= <%=voiceRS.getString("voice_name")%>> <%=voiceRS.getString("voice_kr_name")%> </option>
						<% } %>
					</select>
			<% } %>
		</div>
		
		<br>
	
		
			<%	//문장 수 만큼 div 생성
				int len = sent.size();
				for(int i=0; i<len; i++) { 
			%>
			
			<div class="sent-table">
				<!-- speaker 붙이기-->
				<span class="speak" id='speaker<%=i%>'> <%= speaker.get(i) %> </span>
			
				<!-- emotion option 붙이기-->
				<label id="emotionFace<%=i%>">
					<span class='iconify' data-inline='false' data-icon='noto:angry-face' ></span>
				</label>
				
				<select class='emoSelect' id='emotion<%=i%>' name='emotion<%=i%>' onchange="changeEmotion(<%=i%>)">
	               <%emotionRS.first(); //레코드 맨 앞으로 이동 => 다시 처음부터 while 돌면서 출력%> 
	               <option value=<%= emotionRS.getString("emotion_name") %>><%= emotionRS.getString("emotion_kr_name") %></option>
	               <% while(emotionRS.next()){ %>
	               <option value=<%= emotionRS.getString("emotion_name") %>><%= emotionRS.getString("emotion_kr_name") %></option>
	               <% } %>
	            </select>
				
				<!-- emotion intensity 붙이기-->
				<input type="range" class="emoRange" name="range<%=i%>" min="0" max ="1" step="0.1" value="0.5">
				
				<!-- sentence 붙이기-->
				<input type="text" id="sent<%=i%>" class="sentTxt" name="sent<%=i%>"value="<%= sent.get(i) %>" size="<%=sent.get(i).length()*1.2 %>">
			</div>
				<% 
					} //for문 
				}catch(SQLException e) {
				e.printStackTrace();
				}
				%>
				
			<div class="btn">
				<button type="SUBMIT" class="submit-btn"> 다음 단계로 >  </button>
			</div>
		</form>
		<br>
	</div>

<script>

		function changeEmotion(i) {
			var element = document.getElementById("emotionFace" + i);
			var target = document.getElementById("emotion" + i);
			var val = target.options[target.selectedIndex].text;
			
			while( element.hasChildNodes()) {
				element.removeChild(element.firstChild);
			}
			
			if(val == "화남") {
				var added = document.createElement('span');
				added.setAttribute('class', 'iconify');
				added.setAttribute('data-inline', 'false');
				added.setAttribute('data-icon', 'noto:angry-face');
				element.appendChild(added);
			}
			if(val == "슬픔") {
				var added = document.createElement('span');
				added.setAttribute('class', 'iconify');
				added.setAttribute('data-inline', 'false');
				added.setAttribute('data-icon', 'noto:crying-face');
				element.appendChild(added);
			}
			if(val == "중립") {
				var added = document.createElement('span');
				added.setAttribute('class', 'iconify');
				added.setAttribute('data-inline', 'false');
				added.setAttribute('data-icon', 'noto:neutral-face');
				element.appendChild(added);
			}
			if(val == "기쁨") {
				var added = document.createElement('span');
				added.setAttribute('class', 'iconify');
				added.setAttribute('data-inline', 'false');
				added.setAttribute('data-icon', 'noto:grinning-face-with-smiling-eyes');
				element.appendChild(added);
			}
			
		}
		
		function changeSpeakerVoice(i){	
			var vID="voice"+i;
    		var voiceSelect = document.getElementById(vID);
    		var length=<%=speaker.size()%>;
    		for(var j=0; j < length ; j++){
    			var jID="speaker"+j;
    			var iID="speaker"+i;
    			var jVID="voice"+j;
    			if(document.getElementById(jID).innerText == document.getElementById(iID).innerText){
    				//alert("if");
    				document.getElementById(jVID).options[voiceSelect.selectedIndex].selected=true;
    			}
    		}
		}
</script>
</body>

</html>
