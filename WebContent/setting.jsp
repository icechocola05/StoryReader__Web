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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="CSS/setting.css">	
</head>

<body>
	<div class="head">
		<span>Story Reader</span>
	</div>
	
	<div class="main">
		<div class="prog">
			<hr />
			<span class="pro1">등록 방식 설정</span>
			<span class="pro2">텍스트 등록</span>
			<span class="pro3">화자 설정</span>
			<span class="pro4">완성</span>
			<span class="dot1"></span>
			<span class="dot2"></span>
			<span class="dot3"></span>
			<span class="dot4"></span>
		</div>
	</div>
	
	
	
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
	
	<form method="Post" action="setVoiceEmotion" >
	
	<div class="set">
		<div class="speakers" >
			<div class="row">
			<% 
				//특정 화자만 추리기
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
				<div class="col-sm-3">
				<span id='speaker_t<%=j_loc%>' class="fs-1"> <%= speaker_t.get(j_loc) %> </span>
					<!-- voice option 붙이기-->
				</div>
				
				<div class="col-sm-7">
				<select id='voice<%=j_loc%>' class='form-select fs-2' name='voice<%=j_loc%>' >
					<%voiceRS.first(); //레코드 맨 앞으로 이동 => 다시 처음부터 while 돌면서 출력%> 
					<option value=<%= voiceRS.getString("voice_name") %>><%= voiceRS.getString("voice_kr_name") %></option>
					<% while(voiceRS.next()) { %>
					<option value= <%=voiceRS.getString("voice_name")%>> <%=voiceRS.getString("voice_kr_name")%> </option>
					<% } %>
				</select> <br>
				</div>
				<%} 
				session.setAttribute("speaker_t", speaker_t);%>
			</div>
		</div>
		

		
		<br>
			<%	//문장 수 만큼 div 생성
				for(int i=0; i<len; i++) { 
			%>
			
			<div class="container-fluid align-items-center" style="border:2px solid #C4C4C4; border-radius:20px; margin-bottom: 2%; padding :1% 2% 1% 2%;">
				<div class="row justify-content-between align-items-center">
				
					<!-- speaker 붙이기-->
					<div class="col-1 text-center fs-1 fw-bold" style="margin: 1%; color: #3A91DA;">
					<span id='speaker<%=i%>'> <%= speaker.get(i) %> </span>
					</div>
					
					<!-- emotion option 붙이기-->
					<div class="col-md-auto text-center" style="margin: 1%;">
						<label id="emotionFace<%=i%>">
							<span class='iconify' data-inline='false' data-icon='noto:angry-face' ></span>
						</label>
						<select class='form-select fs-2' id='emotion<%=i%>' name='emotion<%=i%>' onchange="changeEmotion(<%=i%>)">
			               <%emotionRS.first(); //레코드 맨 앞으로 이동 => 다시 처음부터 while 돌면서 출력%> 
			               <option value=<%= emotionRS.getString("emotion_name") %>><%= emotionRS.getString("emotion_kr_name") %></option>
			               <% while(emotionRS.next()){ %>
			               <option value=<%= emotionRS.getString("emotion_name") %>><%= emotionRS.getString("emotion_kr_name") %></option>
			               <% } %>
			            </select>
		            </div>
					
					<!-- emotion intensity 붙이기-->
					<div class="col-2 text-center" style="margin: 1%;">
					<input type="range"name="range<%=i%>" min="0" max ="1" step="0.1" value="0.5">
					</div>
					
					<!-- sentence 붙이기-->
					<div class="col-6 text-center " style="margin: 1%;">
					<textarea id="sent<%=i%>" class="col-7 form-control fs-1" name="sent<%=i%>"><%= sent.get(i) %></textarea>
					</div>
				</div>
			</div>
		
				<% 
					} //for문 
				}catch(SQLException e) {
				e.printStackTrace();
				}
				%>
		</div>
			<div class="btn">
				<button type="SUBMIT" class="submit-btn"> 다음 단계로 >  </button>
			</div>
		</form>
		<br>
		


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
</script>
</body>

</html>
