<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<link rel="stylesheet" href="CSS/textInput.css">
</head>
<body>

	<%
		//텍스트 전달받기
		String title = "";
		String mainTxt = "";
		if(request.getAttribute("title") != null) title = (String) request.getAttribute("title");
		if(request.getAttribute("mainTxt") != null) mainTxt = (String) request.getAttribute("mainTxt");
	 %>
	 
	<div class="head">
		<span>Story Reader</span>
	</div>
	
	<div class="main">
		<div>
			<img class="prog" src="./Img/2.png" alt="image">
		</div>
		
		<div class="input">
			<span id="header">텍스트를 확인해주세요!</span> <br>
			
			<div class="content">
				<form method="Post" action="ConfirmScript">	
					<div class="context-text">
						<label for='title'>제목</label>
						<input type="text" id="title" name="title" size=53 value="<%=title%>" placeholder="  제목을 입력해주세요.  ">
						<br>
						<label for='author'>작가</label>
						<input type="text" id="author" name="author" size=53 placeholder="  작가의 이름을 입력해주세요." />
						<br><br>
						<textarea rows="11" cols="63" id="mainTxt" name="mainTxt"><%=mainTxt%></textarea>
					</div>	
				<button type="submit" id="submit-btn">전송</button>
				</form>
				<br><br>
			</div>
		</div>
	</div>

</body>
</html>