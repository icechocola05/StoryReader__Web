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
<link rel="stylesheet" href="CSS/fileInput.css">
</head>
<body>
<% session.invalidate(); %>
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
		
		<div class="input">
			<span id="title">텍스트를 등록해주세요!</span> <br>
			
			<div class="content">
				<form method="Post" action="uploadFile" enctype="multipart/form-data" onsubmit="return check(this.submitted);">
				<div class="content-file">
					<input class="form-control form-control-lg center-block" name="file" id="input-file" type="file"> <br> <br>
					<span id="sub"> txt 확장자만 가능합니다. </span> <br>
				</div>
					<input type="SUBMIT" class="center-block" id="submit-btn" value="다음 단계로  >" onclick="this.form.submitted=this.value" >
				</form>
			</div>
		</div>
		
	</div>

</body>
</html>