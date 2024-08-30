<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>파일 업로드 폼처리 실습</title>
</head>
<body>

	<form action="uploadFormAction" method="post" enctype="multipart/form-data">
		<input type='file' name='uploadFile' multiple>
		<button>Submit</button>
	</form>

</body>
</html>