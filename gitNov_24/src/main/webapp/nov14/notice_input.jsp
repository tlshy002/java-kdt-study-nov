<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>공지사항</title>
</head>
<body>
<div align="center">
<h3 align="center">게시글 쓰기</h3>
<form action="writeNotice.do" method="post">
<table border="1">
	<tr><th>공지사항 제목</th><td><input type="text" name="TITLE" size="30"/></td></tr>
	<tr><th>공지사항 내용</th><td><textarea rows="4" cols="50" name="CONTENT"></textarea></td></tr>
	<tr><td colspan="2" align="center"><input type="submit" value="글 올리기"/>
						<input type="reset" value="취 소"/></td></tr>
</table>
</form>
</div>
</body>
</html>










