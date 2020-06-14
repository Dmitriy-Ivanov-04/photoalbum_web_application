function profileCheck() {//не используется
	var nick = document.getElementById("nick").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/my-profile', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText == "true")
    			$("#copyButton").hide();
    	}
    }
    xhr.send("n=" + nick);
    
    if(xhr.responseText == "true"){
		return false;
	} else 
		return true;
}
