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
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@500&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
<link rel="stylesheet" href="CSS/chooseInput.css">
</head>
<body>
	<% session.invalidate(); %>
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
		
		<div class="select">
			<span>텍스트 등록 방식을 정해주세요!</span> <br>
			
			<button class="input_btn1" onclick="location='fileInput.jsp'">텍스트 파일로 등록하기</button> <br>
			<button class="input_btn2" onclick="location='textInput.jsp'">직접 입력하기</button>
		</div>
		
	</div>
		
</body>
</html> 