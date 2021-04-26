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
	<%
		String bookname = "";
		String mainTxt = "";
		if(session.getAttribute("bookname") != null) bookname = (String) session.getAttribute("bookname");
		if(session.getAttribute("mainTxt") != null) mainTxt = (String) session.getAttribute("mainTxt");
	%>
	<div>
		<form method="Post" action="ConfirmScript">
			<div class="content">
				
				<label for='bookname'>제목</label>
				<input type="text" id="bookname" name="bookname" size=30 placeholder="이야기의 제목을 입력하세요" value="<%=bookname%>">
				<br><br>
				<label for='bookauthor'>작가</label>
				<input type="text" id="bookauthor" name="bookauthor" size=30 placeholder="이야기의 작가를 입력하세요" />
				<br><br>
				<label for='booktext'>내용</label>
				<textarea rows="11" cols="78" id="booktext" name="booktext" placeholder="이야기 내용을 입력하세요  &#13;&#10;형식은 '화자 : 텍스트' 입니다. 화자를 설정하지 않으면 나레이션으로 지정됩니다.  &#13;&#10;예시) 여우 : 황새야 맛있게 먹어  "><%=mainTxt%></textarea>
				<br><br>
				<button type="submit" id="submit-btn">전송</button>
			</div>
		</form>
		<button onclick="location='index.jsp'"> 뒤로가기 </button>
	</div>
</body>
</html>