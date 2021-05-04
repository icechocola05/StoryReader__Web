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
<link rel="stylesheet" href="CSS/index.css">

</head>
<body>
	<script>
	window.onload = function() {
		var sid = <%=(Integer)session.getAttribute("sentNum")%>;
		var endid = <%=(Integer)session.getAttribute("lastNum")%>;
		if(sid == 0) {
			document.getElementById("pre_btn").style.display="none";
		}
		else if(sid == endid) {
			document.getElementById("next_btn").style.display="none";
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
		Story Reader
	</div>
	<div class="main">
		
		<form method="Post" action="setImg.do" enctype="multipart/form-data">
			<div class="content">
				<input type="text" id="<%=session.getAttribute("sent_id")%>"value="<%=session.getAttribute("speaker")%>" style = "text-align:center;">
				<br>
				<input type="submit" name="move_btn" value="pre" id="pre_btn">
				<%String s=(String)session.getAttribute("sentence"); int len=s.length(); %>
				 <input type="text" id="<%=session.getAttribute("sent_id")%>"value="<%=session.getAttribute("sentence")%>" size="<%=len*1.7%>" style = "text-align:center;">
				<input type="submit" id="next_btn" name="move_btn" value="next">
				<br><br>
				<audio id='player' autoplay controls onended="next(<%=session.getAttribute("sentNum")%>, <%=session.getAttribute("lastNum")%>)">
			    <source src="output/<%=session.getAttribute("sentNum")%>.wav" type="audio/wav">
				</audio>
			</div>
		</form>
	</div>
</body>
</html> 