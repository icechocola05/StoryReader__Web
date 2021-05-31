<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>Story Reader</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@500&display=swap" rel="stylesheet">
<link rel="stylesheet" href="CSS/result.css">

</head>
<body>
	<script>
	window.onload = function() {
		var sid = <%=(Integer)session.getAttribute("sentNum")%>;
		var endid = <%=(Integer)session.getAttribute("lastNum")%>;
		if(sid == 0) {
			document.getElementById("pre_btn").style.display="hidden";
		}
		else if(sid == endid) {
			document.getElementById("next_btn").style.display="hidden";
		}
	}
	var audio = document.getElementById("player");
	function next(nowNum, lastNum){
		if(nowNum == lastNum) {
			alert("마지막 문장입니다");
			audio.pause();
		}
		else {
			document.getElementById("next_btn").click();
		}
	}
	
</script>
	<div class="head">
		<span>Story Reader</span>
	</div>
	<div class="main">
		<form method="Post" action="setImg.do">
			<div class="title"><%=session.getAttribute("story_name") %></div>
			<div class="content-now">	
				<div class="speaker" id="<%=session.getAttribute("sent_id")%>"><%=session.getAttribute("speaker")%></div>
				<div class="sentence" id="<%=session.getAttribute("sent_id")%>"><%=session.getAttribute("sentence")%></div> 
			</div>
			<div class="audio">
  				<button type="submit" name="move_btn" value="pre" id="pre_btn">
					<img src="./Img/previous_w.png" alt="image">
				</button>
				<audio id='player' autoplay controls onended="next(<%=session.getAttribute("sentNum")%>, <%=session.getAttribute("lastNum")%>)">
			    <source src="output/<%=session.getAttribute("sentNum")%>.wav" type="audio/wav">
				</audio>
				<!--<input type="submit" id="next_btn" name="move_btn" value="next">-->
				<button type="submit" name="move_btn" value="next" id="next_btn">
					<img src="./Img/next_w.png" alt="image">
				</button>
				
			</div>
			
		</form>
	</div>
</body>
</html> 