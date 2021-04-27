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
<link rel="stylesheet" href="CSS/index.css">
</head>
<body>
	<% session.invalidate(); %>
	<div class="head">
		Story Reader
	</div>
	<div class="main">
	<div class="content">
		<form method="Post" action="uploadFile" enctype="multipart/form-data" onsubmit="return check(this.submitted);">
			텍스트 파일을 등록하고 음성 설정 하면 완성! <br> <br>
			<input class="form-control form-control-lg center-block" name="file" id="input-file" type="file"> <br> <br>
			<input type="SUBMIT" class="center-block" id="submit-btn" value="제출하기" onclick="this.form.submitted=this.value" >
		</form>
		
		<form method="Post" action="confirm.jsp">
			<input type="SUBMIT" class="center-block" id="submit-btn" value="직접 입력하기">
		</form>
		</div>
	
	<script>
		function check(num){
			var fileCheck = document.getElementById("input-file").value;
			if(!fileCheck && num == "제출하기") {
				alert("텍스트 파일을 첨부해주세요");
				return false;
			}
		};
		
	</script>
</body>
</html> 