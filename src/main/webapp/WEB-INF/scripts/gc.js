
var myTimer;
var sectionObject = [];

window.onload = function(){		
	document.getElementsByClassName("tablink")[0].click();
	document.getElementsByClassName("autoTimeStamp")[0].click();	
	loadSections();
}


function openTab(evt, activeTab) {
    var i, tab, tablink;    
    tab = document.getElementsByClassName("tabcontent");    
    for (i = tab.length-1; i >=0; i--) {
        tab[i].style.display = "none";
    }
   
    document.getElementById(activeTab).style.display = "block";
    tablink = document.getElementsByClassName("tablink");
    for (i = tablink.length - 1; i >= 0; i--) {
        tablink[i].className = tablink[i].className.replace(" active", "");        
    }
    evt.currentTarget.className += " active";  
}		

function refreshTimeStamp(current) {	
	var objTime = document.getElementsByClassName("formtime");
	var objDate = document.getElementsByClassName("formdate");		
	var objCheckbox = document.getElementsByClassName("autoTimeStamp");	
	if(current.checked==true){
		myTimer = setInterval(processDateTime, 1000);
		processDateTime();
		for (var i=0; i < objCheckbox.length; i++){
			objCheckbox[i].checked = true;
			objTime[i].className += " auto";
			objDate[i].className += " auto";			
		}
	} else {
		clearInterval(myTimer);	
		for (var i=0; i < objCheckbox.length; i++){
			objCheckbox[i].checked = false;
			objTime[i].className = objTime[i].className.replace(" auto", " ");
			objDate[i].className = objDate[i].className.replace(" auto", " ");			
		}			
	}
}

function processDateTime() {
	var d= new Date();
	var dateString, timeString;	
	var objTime = document.getElementsByClassName("formtime");
	var objDate = document.getElementsByClassName("formdate");		
	dateString = "";	
	dateString = dateString.concat(padNumber(d.getFullYear(),4));
	dateString = dateString + "-";	
	dateString = dateString.concat(padNumber((d.getMonth() + 1),2));
	dateString = dateString + "-";
	dateString = dateString.concat(padNumber(d.getDate(),2));		
	timeString = "";
	timeString = timeString.concat(padNumber(d.getHours(),2));
	timeString = timeString + ":";
	timeString = timeString.concat(padNumber(d.getMinutes(),2));
	timeString = timeString + ":";
	timeString = timeString.concat(padNumber(d.getSeconds(),2));	
	for (var i=0; i < objTime.length; i++){
		objDate[i].value = dateString;
		objTime[i].value = timeString;
	}
}


function padNumber(num, digits){	
	var result="";
	var k;
	for (k=0;k<digits;k++){
		result=result+"0";		
	}		
	result=result.concat(num);
	result=result.slice(digits*(-1));	
	return result;
}
		
function submitQuery(requestString, callback, begintime){
	var xhttp;	
	if (window.XMLHttpRequest) {	   
	    xhttp = new XMLHttpRequest();
	 } else {	   
	    xhttp = new ActiveXObject("Microsoft.XMLHTTP");
	} 	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {			
				window[callback](this,begintime);
			}
	    };
	xhttp.open("POST", "/query", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(requestString);	
}

function submitIn() {
	var requestString;
	var begintime = new Date();
	var indicator = document.getElementById("statusDBin");
	requestString= "param[]=checkin";
	requestString+= "&param[]=" + document.getElementById("inDate").value;
	requestString+= "&param[]=" + document.getElementById("inTime").value;
	requestString+= "&param[]=" + document.getElementById("inSection").value;
	indicator.innerHTML = "Asteapta...";
	indicator.style.color = "rgb(0,0,0)";
	indicator.style.display="inline";
	submitQuery(requestString, "processIn", begintime);
}

function processIn(xhttp, begintime){
	var endtime = new Date();
	var result = xhttp.responseText;
	var indicator = document.getElementById("statusDBin");
	var requestString="";
	var section="";
	endtime = endtime-begintime;	
	if(parseInt(result)>0){
		indicator.innerHTML = "Tichet cu numarul \""+ result + "\" eliberat in " + endtime + "ms";
		indicator.style.color = "rgb(0,0,255)";
		section = xhttp.getResponseHeader("section");		
		requestString="param[]=countOccupied";
		requestString+="&param[]=" + section;		
		submitQuery(requestString, "updateSections", null);	
	}
	else{
		indicator.innerHTML = "EROARE!";
		indicator.style.color = "rgb(255,0,0)"
	}
	indicator.style.display="inline";
	setTimeout(function(){indicator.style.display="none";}, 3000);
}

function submitPay() {
	var requestString;
	var begintime = new Date();
	var indicator = document.getElementById("statusDBpay");
	requestString= "param[]=pay";
	requestString+= "&param[]=" + document.getElementById("payToken").value;
	requestString+= "&param[]=" + document.getElementById("payDate").value;
	requestString+= "&param[]=" + document.getElementById("payTime").value;
	requestString+= "&param[]=" + document.getElementById("paySum").value;
	indicator.innerHTML = "Asteapta...";
	indicator.style.color = "rgb(0,0,0)";
	indicator.style.display="inline";
	submitQuery(requestString, "processPay", begintime);
}

function processPay(xhttp, begintime){
	var endtime = new Date();
	var result = xhttp.responseText;
	var indicator = document.getElementById("statusDBpay");
	endtime = endtime-begintime;	
	if(result=="1"){
		indicator.innerHTML = "Succes, in " + endtime + "ms";
		indicator.style.color = "rgb(0,0,255)";
	}
	else if(result=="0"){
		indicator.innerHTML = "Tichet inexistent sau deja platit";
		indicator.style.color = "rgb(255,0,0)"
		}
	else{
		indicator.innerHTML = "EROARE!";
		indicator.style.color = "rgb(255,0,0)"
	}
	indicator.style.display="inline";
	setTimeout(function(){indicator.style.display="none";}, 3000);
}

function submitOut() {
	var requestString;
	var begintime = new Date();
	var indicator = document.getElementById("statusDBout");
	requestString= "param[]=checkout";
	requestString+= "&param[]=" + document.getElementById("outToken").value;
	requestString+= "&param[]=" + document.getElementById("outDate").value;
	requestString+= "&param[]=" + document.getElementById("outTime").value;	
	indicator.innerHTML = "Asteapta...";
	indicator.style.color = "rgb(0,0,0)";
	indicator.style.display="inline";
	submitQuery(requestString, "processOut", begintime);
}

function processOut(xhttp, begintime){
	var endtime = new Date();
	var result = xhttp.responseText;
	var indicator = document.getElementById("statusDBout");
	var section, requestString;
	endtime = endtime-begintime;	
	if(result=="1"){
		indicator.innerHTML = "Succes, in " + endtime + "ms";
		indicator.style.color = "rgb(0,0,255)";
		loadSections();
		//section = xhttp.getResponseHeader("section");		
		//requestString="param[]=countOccupied";
		//requestString+="&param[]=" + section;		
		//submitQuery(requestString, "updateSections", null);	
	}
	else if(result=="0"){
		indicator.innerHTML = "Tichet inexistent sau neplatit";
		indicator.style.color = "rgb(255,0,0)"
		}
	else{
		indicator.innerHTML = "EROARE!";
		indicator.style.color = "rgb(255,0,0)"
	}
	indicator.style.display="inline";
	setTimeout(function(){indicator.style.display="none";}, 3000);
}

function loadSections(){
	submitQuery("param[]=listSections", "processSections", null);
}

function processSections(xhttp,begintime){
	var name, size;
	var section;
	var i;	
	var sizetotal;
	var result = xhttp.responseText;
	var sectionsDropdown = document.getElementById("inSection");	
	var s;
	var requestString;
	for(i=0; i<=sectionsDropdown.options.length; i=i+1){
		sectionsDropdown.options.remove(i);			
	}	
		
	name = xhttp.getResponseHeader("name").split(", ");
	size = xhttp.getResponseHeader("size").split(", ");
	
	if(name.length>0){
		s="";
		sizetotal=0;
		for(i=0;i<name.length;i++){
			sectionObject.push({
				name: name[i],
				size: size[i],
				occupied: 0,
				free:0,
				}); 
			section = document.createElement("option");
			section.text= name[i];
			sizetotal+=parseInt(size[i]);
			sectionsDropdown.options.add(section,i);
			s+="<table><tr><th class=\"label\"></th><th><th></th></th></tr>"
			s+="<tr>" +	
				"<td id=\"label_"+ name[i] +"\" class=\"label\"></td>" + 
				"<td id=\"label_oc"+ name[i] +"\" class=\"occupied\"></td>" + 
				"<td id=\"label_fr"+ name[i]+ "\" class=\"free\"></td></tr>";
			s+="</table>";				
		}
		if(name.length>1){			
			s+="<hr/>"; 
			s+="<table><tr><th class=\"label\"></th><th></th><th></th></tr>"
			s+="<tr>" +	
			"<td id=\"label_TOTAL\" class=\"label\"></td>" + 
				"<td id=\"label_ocTOTAL\" class=\"occupied\"></td>" + 
				"<td id=\"label_frTOTAL\" class=\"free\"></td></tr>";
			s+="</table>";
		}			
		document.getElementById("situation").innerHTML=s;
		
		for(i=0;i<name.length;i++){
			requestString="param[]=countOccupied";
			requestString+="&param[]=" + name[i];
			submitQuery(requestString, "updateSections", null);			
		}		
	}		
}




function updateSections(xhttp,begintime){
	var s;
	var sum_oc=0;
	var section = xhttp.getResponseHeader("section");
	var bar;
	for(var k=0; k<sectionObject.length; k++){
		if(sectionObject[k].name==section){
			sectionObject[k].occupied = xhttp.responseText;			
			sectionObject[k].free = sectionObject[k].size - sectionObject[k].occupied;
			s = "<b>"+sectionObject[k].name.toUpperCase()+"</b> (" + xhttp.responseText + " din " + sectionObject[k].size + ")\n" +
			"<i>Disponibile:&nbsp;" + sectionObject[k].free+"</i>";
			bar=document.getElementById("label_"+section);
			bar.innerHTML=s;
			bar=document.getElementById("label_oc"+section);
			bar.style.width = (sectionObject[k].occupied/sectionObject[k].size)*50 + "%";			
			bar=document.getElementById("label_fr"+section);
			bar.style.width = (1- sectionObject[k].occupied/sectionObject[k].size)*50 + "%";
			
			break;
		}
	}
	updateSectionTOTAL();
}
function updateSectionTOTAL(){
	var sum_oc=0;
	var sum_size=0;
	var sum_free;
	var bar;
	if(sectionObject.length > 1){
		for(var i = 0; i < sectionObject.length; i++){
			sum_size += parseInt(sectionObject[i].size);
			sum_oc += parseInt(sectionObject[i].occupied);			
		}
		sum_free=sum_size-sum_oc;	
		s = "<b>TOTAL</b> (" + sum_oc + " din " + sum_size + ")\n" +
		"<i>Disponibile:&nbsp;" + sum_free+"</i>";
		bar=document.getElementById("label_TOTAL");
		bar.innerHTML=s;
		bar=document.getElementById("label_ocTOTAL");
		bar.style.width = (sum_oc/sum_size)*50 + "%";
		bar=document.getElementById("label_frTOTAL");
		bar.style.width = (1- sum_oc/sum_size)*50 + "%";
	}	 	
}

//require player to press the payUpdateToken button to resume operations
function forcePayUpdateToken(){     
	//add CSS class to all items that must be locked
	var myElement = document.getElementsByClassName("mustupdate");
	for(var i = myElement.length -1; i >=0; i--) {
        if(!(myElement[i].classList.contains("notupdated"))) myElement[i].className += " notupdated";
    }	
}

/* Update information from current pay token number */
function updatePayToken(){
	//unlock all interface items	
    var myElement = document.getElementsByClassName("notupdated");   
    for(var i = myElement.length -1; i >=0; i--){		
		if(myElement[i].classList.contains("notupdated")){
			myElement[i].className = myElement[i].className.replace(" notupdated","");			
		}		 
	}
    //query server    
    var requestString;	
	requestString= "param[]=getTokenDetails";
	requestString+= "&param[]=" + document.getElementById("payToken").value;	
	submitQuery(requestString, "processPayTokenDetails", null);	
}

function processPayTokenDetails(xhttp,begintime){
	var result = xhttp.responseText;	
	if(result=="1"){
		//if user hasn't changed the id in the meantime, update the screen
		if(document.getElementById("payToken").value==xhttp.getResponseHeader("tokenId")){
			document.getElementById("payInDateTime").innerHTML = xhttp.getResponseHeader("inDate")
													+ " / " +  xhttp.getResponseHeader("inTime");
			document.getElementById("payPayDateTime").innerHTML = xhttp.getResponseHeader("payDate")
													+ " / " +  xhttp.getResponseHeader("payTime");
			document.getElementById("paySection").innerHTML = xhttp.getResponseHeader("section"); 
			document.getElementById("amountToPay").value = xhttp.getResponseHeader("payed"); 													
		}
				
	}
	
}

	
	
	
