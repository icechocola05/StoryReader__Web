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
	<div class="head">
		Story Reader
	</div>
	<div>
		<form method="Post" action="ConfirmScript">
			<div class="content">
				
				<label for='bookname'>제목</label>
				<input type="text" id="bookname" name="bookname" value="<%=session.getAttribute("bookname")%>">
				<br><br>
				<label for='bookauthor'>작가</label>
				<input type="text" id="bookauthor" name="bookauthor" />
				<br><br>
				<label for='booktext'>내용</label>
				<textarea rows="10" cols="70" id="booktext" name="booktext"><%=session.getAttribute("mainTxt") %></textarea>
				<br><br>
				<button type="submit" id="submit-btn">전송</button>
				
			</div>
		</form>
	</div>
</body>
</html>