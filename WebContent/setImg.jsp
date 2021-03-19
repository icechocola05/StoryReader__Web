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
	<div class="main">
		<form method="Post" action="uploadFile" enctype="multipart/form-data">
			<div class="content">
				<input type="button" id="" name="">
				<input type="text" id="bookname" name="bookname" value="<%=session.getAttribute("bookname")%>">
			</div>
		</form>
	</div>
</body>
</html> 