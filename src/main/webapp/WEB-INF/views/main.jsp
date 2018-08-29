<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>


<title>Car Park Main Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="/css/gc.css">
<link rel="stylesheet" type="text/css" href="/css/style.css">

<script src="/scripts/main.js?version=4.0"></script>

</head>

<body>
<input type="hidden" id="availablemenus" value="${availablemenus}" />

<header class="nonselectable">	
	<ul> 	
		<li class="login" > 
			<a class="menulink" href="/login" title="Click pentru revenire in pagina de inregistrare." target="_top">
			Logout&nbsp; <span style="font-weight:bold;">${username}</span>
			</a>
		</li>
		<li class="separator"> | </li>		
		<li class="art"></li>
		<li class="logo">P</li>
		<li class="appname">arcare</li>
		<li class="appnamefull">&nbsp;Cuplata</li>			
	</ul>	
</header>
	
<nav id="sidemenu" class="nonselectable">
	<ul>
		<li><a id="sidemenu_item_1">Operatiuni Clienti </a></li>
		<li><a id="sidemenu_item_2">Tichete Active</a></li>
		<li><a id="sidemenu_item_3">Statistici Ocupare</a></li>	
		<li><a id="sidemenu_item_4">Istoric Operatiuni</a></li>
		<li><a id="sidemenu_item_5">Administratie</a></li>	
	</ul>
</nav>

<iframe id="pagecontent" frameborder= "0" scrolling= "yes">
</iframe>



<footer class="nonselectable">
	<a class="menulink" onclick="ScrollTop()" >Sari la inceput</a>
	<div class="copyright">VladSoft Productions &copy;2018 </div>
</footer>

</body>
</html>