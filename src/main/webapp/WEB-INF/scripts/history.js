window.onload = function(){	
	updateHistory();		
}

function updateHistory(){
	var xhttp;
	var requestString;
	requestString="param[]=showhistorytable";
	if (window.XMLHttpRequest) {	   
	    xhttp = new XMLHttpRequest();
	 } else {	   
	    xhttp = new ActiveXObject("Microsoft.XMLHTTP");
	} 
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("table").innerHTML= this.responseText;
	    }
	};
	xhttp.open("POST", "/query", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");	
	xhttp.send(requestString);	
}