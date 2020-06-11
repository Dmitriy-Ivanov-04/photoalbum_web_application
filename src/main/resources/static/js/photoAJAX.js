$(document).ready(function () {	
	var nick = document.getElementById("nick").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/photos', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0){
    			fillPublications(JSON.parse(xhr.responseText));
    		}
    	}
    }
    xhr.send("n=" + nick);
    
    function fillPublications(jsonArr){
    	let publications = document.getElementById("content");
    	
    	let postLine = document.createElement("div");
		publications.appendChild(postLine);
		$(postLine).attr("id", "post-line");
    	for(let i = 0; i < jsonArr.length; i++){
    		let imageWrapper = document.createElement("div");
    		postLine.appendChild(imageWrapper);
    		$(imageWrapper).attr("id", "image-wrapper");
    		
    		let img = document.createElement("img");
    		imageWrapper.appendChild(img);
    	    $(img).attr("class", "content-img");
    	    let src = "/images/" + jsonArr[i].link + ".jpg";
    	    $(img).attr("src", src);
    		
    	    $(img).click(function (){
    	    	openImage(src, jsonArr[i].description, jsonArr[i].date, jsonArr[i].id);
    	    });
    	}
    }
});