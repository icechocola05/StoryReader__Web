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
	      String bookname = "";
	      String mainTxt = "";
	      if(session.getAttribute("bookname") != null) bookname = (String) session.getAttribute("bookname");
	      if(session.getAttribute("mainTxt") != null) mainTxt = (String) session.getAttribute("mainTxt");
	   %>


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
		
		<div class="input">
			<span id="title">텍스트를 확인해주세요!</span> <br>
			
			<div class="content">
				<form method="Post" action="ConfirmScript">	
					<div class="context-text">
						<label for='bookname'>제목</label>
						<input type="text" id="bookname" name="bookname" size=53 value="<%=bookname%>" placeholder="  제목을 입력해주세요.  ">
						<br>
						<label for='bookauthor'>작가</label>
						<input type="text" id="bookauthor" name="bookauthor" size=53 placeholder="  작가의 이름을 입력해주세요." />
						<br><br>
						<textarea rows="11" cols="63" id="booktext" name="booktext"><%=mainTxt%></textarea>
					</div>	
				<button type="submit" id="submit-btn">전송</button>
				</form>
				<br><br>
			</div>
			
		</div>
		
	</div>

</body>
</html>