<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
				
				<label for='bookauthor'>작가</label>
				<input type="text" id="bookauthor" name="bookauthor" />
				
				<label for='booktext'>내용</label>
				<textarea rows="10" cols="20" id="booktext" name="booktext"><%=session.getAttribute("mainTxt") %></textarea>
				
				<button type="submit" id="submit-btn">전송</button>
				
			</div>
		</form>
	</div>
</body>
</html>