<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gate control</title>
<link rel="stylesheet" type="text/css" href="/css/gc.css">
<script type="text/javascript" src="/scripts/gc.js?version=5.0"></script>

</head>
<body>


<div class="tab">
	<button class="tablink" onclick="openTab(event, 'checkin')">Intrare</button>
  	<button class="tablink" onclick="openTab(event, 'payment')">Plata</button>
  	<button class="tablink" onclick="openTab(event, 'checkout')">Iesire</button>
</div>

<div id="checkin" class="tabcontent">
  <form class="form" accept-charset="UTF-8">
  	<fieldset>
  		<legend>Data si Ora</legend>
  		<input type="checkbox" class="autoTimeStamp" onchange="refreshTimeStamp(this)" /> Auto <br /> 
  		<input id="inTime" class= "formtime" type="time" step="1" placeholder="13:00:00"/>
  		<input id="inDate" class= "formdate" type="date" placeholder="2018-01-31" />  		   		
  	</fieldset>
  	<br />
  	<fieldset>
  		<legend>Sectiune</legend>
  		<select id="inSection"> 						
		</select> 		  		
  	</fieldset>	
  	<br />  	
  	<button type="button" onclick="submitIn()" style="height:32px;">Introduce</button> 
  	<span id="statusDBin" class="statusDB" >Status</span>  
  </form> 
</div>

<div id="payment" class="tabcontent">
	<form class="form" accept-charset="UTF-8">
  		<fieldset>
  			<legend>Data si Ora</legend>
  			<input type="checkbox" class="autoTimeStamp" onchange="refreshTimeStamp(this)" /> Auto <br /> 
  			<input id="payTime" class= "formtime" type="time" step="1" placeholder="13:00:00"/>
  			<input id="payDate" class= "formdate" type="date" placeholder="2018-01-31" />   		
  		</fieldset>  
  		<br />
  		<fieldset>
  			<legend>Alege tichet</legend>
  			<div class="clientDetails">
  				<span>Numar tichet:&nbsp;</span>
  				<input id="payToken" oninput="forcePayUpdateToken()" size="10" type="text" />
  				<button id="payUpdateToken" class="mustupdate" type="button" onclick="updatePayToken()" style="height:32px;">Actualizeaza</button>
  			</div> 
  			<table class="clientDetails">
  				<tr>
  					<th>Sectiune</th>
  					<th>Intrat La</th>
  					<th class="payhide">Platit La</th>
  				</tr>
  				<tr>
  					<td id="paySection" class="mustupdate"></td>
  					<td id="payInDateTime" class="mustupdate"></td>
  					<td id="payPayDateTime" class="payhide mustupdate"></td>
  				</tr>
  			</table>
  			
  			
  		</fieldset>
  	<br /> 
  	<span>Suma:&nbsp;</span>  		 
  	<input id="paySum" class="mustupdate" type="text" /> 	
  	
  	<button type="button" onclick="submitPay()" style="height:32px;">Plateste</button> 
  	 <span id="statusDBpay" class="statusDB">Status</span>
  </form> 
</div>

<div id="checkout" class="tabcontent">
  <form class="form" accept-charset="UTF-8">
  	<fieldset>
  		<legend>Data si Ora</legend>
  		<input type="checkbox" class="autoTimeStamp" onchange="refreshTimeStamp(this)" /> Auto <br /> 
  		<input id="outTime" class= "formtime" type="time" step="1" placeholder="13:00:00"/>
  		<input id="outDate" class= "formdate" type="date" placeholder="2018-01-31" />   		
  	</fieldset> 
  	<br /> 
  	<fieldset>
  		<legend></legend>
  		<span>Numar tichet:&nbsp;</span>
  		<input id="outToken" type="text" />
  		<br />  		 
  	</fieldset>	 	
  	<br />  	
  	<button type="button" onclick="submitOut()" style="height:32px;">Iesire</button> 
  	<span id="statusDBout" class="statusDB">Status</span> 
  </form> 
</div>
<br />
<div id="situation" style="width:100%;">
	
</div>







</body>
</html>