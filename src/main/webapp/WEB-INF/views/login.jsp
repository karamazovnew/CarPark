<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<title>VladSoft Web Factory</title>

</head>
<body style="width:100%; height:500px;" onload="if (window != window.top) { top.location.href='\login' }">
	<div style="width:100%; height:500px;">
	<p><font color="${color}">${message}</font></p>

	<form action="/login" method="post" target="_top">
		<div>
			Username: 
			<input name="username" type="text">
		</div>
		<br>
		<div>
			Password:
			<input name="password" type="password">
		</div>
		
		<input type="submit" >

	</form>
	</div>







</body>
</html>