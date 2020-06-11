function getTagsByPhoto(photoId) {
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/tags', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0){
    			fillTags(JSON.parse(xhr.responseText));
    		}
    	}
    }
    xhr.send("id=" + photoId);
}

function fillTags(jsonArr){
	let tagP = document.getElementById("hashtagsP");
	tagP.innerHTML = "";
   	for(let i = 0; i < jsonArr.length; i++){
   		tagP.innerHTML += "#" + jsonArr[i].tag + " ";
   	}
}
