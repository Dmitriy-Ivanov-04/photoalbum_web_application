$(document).ready(function () {
	var nick = document.getElementById("nick").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/add-friend-button', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		//if(xhr.responseText.localeCompare("true") == 0)
    		if(xhr.responseText == "true")
    			$("#addFriend").show();	    			
    	}
    }
    xhr.send("n=" + nick);
	
	$("#addFriend").click(function() {
		var nick = document.getElementById("nick").innerHTML;
		var token = document.head.querySelector("meta[name='_csrf']").content;
		var header = document.head.querySelector("meta[name='_csrf_header']").content;
		var xhr = new XMLHttpRequest();
		xhr.open('POST', '/ajax/add-friend', true);
	    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	    xhr.setRequestHeader(header, token);
	    xhr.send("n=" + nick);
	});
});