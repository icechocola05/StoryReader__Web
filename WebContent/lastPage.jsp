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
<link rel="stylesheet" href="CSS/last.css">
</head>
<body>
	<div class="head">
		<span>Story Reader</span>
	</div>
	<div class="main">
	
	<div class="title"><%=session.getAttribute("story_name") %></div>
	
	<form method="post" action="setImg.do">
		<button id = "replay" name="move_btn" value="replay">다시 듣기</button>
	</form>
	<form method="post" action="chooseInput.jsp">
		<button id="another">다른 동화 읽기</button>
	</form>
	</div>
</body>
</html>