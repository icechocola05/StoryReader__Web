<%@page import="java.io.*"%>
<%@page import="java.util.*" %>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="dao.*"%>
<%@ page import="dto.*"%>
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
		<div>
			<img class="prog" src="./Img/3.png" alt="image">
		</div>
	</div>
	
	<% 
		//저장한 이야기, 문장, 화자 정보 받아오기
		Story currStory = (Story) request.getAttribute("currStory");
		//String book_title = (String) request.getAttribute("story_name");
		ArrayList<String> sent = (ArrayList<String>)request.getAttribute("sent");
		ArrayList<String> speaker = (ArrayList<String>)request.getAttribute("speaker");
		request.setAttribute("speaker", speaker);
		int len = sent.size();
		
		//DB의 Emotion, Voice 가져오기
		ServletContext sc = getServletContext();
		Connection con = (Connection)sc.getAttribute("DBconnection");
		List<Voice> voiceSet = SettingDao.getVoice(con);
		List<Emotion> emotionSet = SettingDao.getEmotion(con);
	
	%>
	
	<form method="Post" action="setVoiceEmotion" >
	<div class="set">
		<div class="speakers" >
			<div class="row">
				<% 
					//언급되는 화자만 추리기
					ArrayList<String> speaker_t = new ArrayList<String>();//중복을 제외한 화자 리스트
					int flag=0;//0-같은 speaker 없음.1-있음
					int j_loc=0;
					for(int i=0; i<speaker.size(); i++) { //문장 수 만큼 행 생성
						System.out.println("==================i = "+i+"===================");
						flag=0;
						for(int j=0; j<speaker_t.size(); j++){
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
						j_loc = speaker_t.size()-1;
				%>
				<div class="col-sm-3">
					<span id='speaker_t<%=j_loc%>' class="fs-1"> <%= speaker_t.get(j_loc) %> </span>
				</div>
				<div class="col-sm-7">
					<!-- Voice Setting -->
					<select id='voice<%=j_loc%>' class='form-select fs-2' name='voice<%=j_loc%>' >
						<%for (int ls=0; ls<voiceSet.size(); ls++)  { %> 
							<option value=<%= voiceSet.get(ls).getVoiceName() %>><%= voiceSet.get(ls).getVoiceKrName() %></option>
						<% } %>
					</select> <br>
				</div>
				<% }
				request.setAttribute("voiceSet", voiceSet);
				request.setAttribute("emotionSet", emotionSet);
				request.setAttribute("speaker_t", speaker_t);
				//request.setAttribute("story_name", book_title);
				request.setAttribute("sent", sent);
				//request.setAttribute("speaker", speaker);
				
				%>
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
			               <% for (int ls=0; ls<emotionSet.size(); ls++)  { %> 
							<option value=<%= emotionSet.get(ls).getEmotionName() %>><%= emotionSet.get(ls).getEmotionKrName() %></option>
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
		
			<% 	} //for문
				//pageContext.forward("setting.jsp"); jsp->servelt 부분에서 request를 쓰면 값이 전달이 안되더라구..
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
