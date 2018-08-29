window.onload = function(){	
	cssActivateSideMenu();		
        var x;
        console.log(x);
        x=null;
        console.log(x);
        x=x-2;
        console.log(x);
}

		

function cssActivateSideMenu(){
	var container=document.getElementById("sidemenu");
	var item = container.getElementsByTagName("a");
	var initial_page=-1;
	
	var availablemenus=document.getElementById("availablemenus").value;
	
	for(var i=item.length-1; i>=0; i--){
		if (availablemenus.charAt(i)==0){
			item[i].className += " disabled";
		}
		else{			
			item[i].addEventListener("click", function() {
				var current = document.getElementsByClassName(" active");				
				for (var i=0; i < current.length; i++){
					if (current[0] != undefined) {
						current[0].className = current[0].className.replace(" active", "");
					}
				}
				this.className += " active";
				document.getElementById("pagecontent").src= "/pagecontent?page=" + this.id ;
			});
			
			initial_page = i;			
		}		
	}
	
	initial_page!=-1 ? item[initial_page].click() : null;
}


function ScrollTop(){
	window.scrollTo(0, 0);
} 
		





	
	
	
