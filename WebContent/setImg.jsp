<html>
<head>
<meta charset="UTF-8">
<title>Story Reader</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@500&display=swap" rel="stylesheet">
<link rel="stylesheet" href="CSS/index.css">
</head>
<body>
	<div class="head">
		Story Reader
	</div>
	<div class="main">
		<form method="Post" action="setImg.do" enctype="multipart/form-data">
			<div class="content">
				<input type="submit" name="move_btn" value="pre">
				<input type="text" id="<%=session.getAttribute("sent_id")%>"value="<%=session.getAttribute("sent")%>">
				<input type="submit" name="move_btn" value="next">
			</div>
		</form>
		<form method="Post" action="makeJsonServlet" enctype="multipart/form-data">
			<div class="content">
				<input type="submit" name="제출"></button>
			</div>
		</form>
	</div>
</body>
</html> 