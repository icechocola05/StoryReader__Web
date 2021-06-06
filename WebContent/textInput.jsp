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

	<div class="head">
		<span>Story Reader</span>
	</div>
	<div class="main">
		<div>
			<img class="prog" src="./Img/2.png" alt="image">
		</div>
		
		<div class="input">
			<span id="title">텍스트를 직접 입력해주세요!</span> <br>
			
			<div class="content">
				<form method="Post" action="ConfirmScript">	
					<div class="context-text">
						<label for='bookname'>제목</label>
						<input type="text" id="bookname" name="bookname" size=53 placeholder="  제목을 입력해주세요.  ">
						<br>
						<label for='bookauthor'>작가</label>
						<input type="text" id="bookauthor" name="bookauthor" size=53 placeholder="  작가의 이름을 입력해주세요." />
						<br><br>
						<textarea rows="11" cols="63" id="booktext" name="booktext" placeholder="   이야기 내용을 입력해주세요.  &#13;&#10;   형식은 '화자 : 텍스트' 입니다. &#13;&#10;   화자를 설정하지 않으면 나레이션으로 지정됩니다.  &#13;&#10;   예시) 여우 : 황새야 맛있게 먹어  "></textarea>
					</div>	
				<button type="submit" id="submit-btn">전송</button>
				</form>
				<br><br>
			</div>
			
		</div>
		
	</div>

</body>
</html>