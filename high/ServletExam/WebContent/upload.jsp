<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드 예제</title>
</head>
<body>
<h3>아파치 자카르타 프로젝트의 fileupload모듈을 이용한 파일 업로드</h3>
<form action="upload.do" method="post" enctype="multipart/form-data">	<!--  클라이언트 반드시 해줘야할 것! 메소드: post, 인코딩타입: 멀티파트폼데이터 형식★ -->
	파일 선택: <input type="file" name="uploadFile">
	전송자: <input type="text" name="sender">
	<button type="submit">파일업로드</button>
</form>
</body>
</html>