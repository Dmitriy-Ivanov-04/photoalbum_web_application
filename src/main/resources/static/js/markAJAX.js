function getMarksByPhoto(photoId) {
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/marks', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0)
    			fillMarks(JSON.parse(xhr.responseText));
    		else{
	    		let rating = document.getElementById("rating");
	    		rating.innerHTML = "";
    		}
    	}
    }
    xhr.send("id=" + photoId);
}

function fillMarks(jsonArr){
	let rating = document.getElementById("rating");
	rating.innerHTML = "";
	let generalRating = 0;
   	for(let i = 0; i < jsonArr.length; i++){
   		generalRating += jsonArr[i].value;
   	}
   	let avgRating = document.createTextNode(generalRating/jsonArr.length);
   	rating.appendChild(avgRating);
}
