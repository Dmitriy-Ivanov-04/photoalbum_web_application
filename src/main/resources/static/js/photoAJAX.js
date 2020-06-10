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
    			//let jsonArr = JSON.parse(xhr.responseText)
	    		//console.log(fillPublications(JSON.parse(xhr.responseText)));
	    		//imageAlign(fillPublications(JSON.parse(xhr.responseText)), $(".publications").width(), 2);
    			fillPublications(JSON.parse(xhr.responseText));
    		}
    	}
    }
    xhr.send("n=" + nick);
    
    //console.log($("#content").find("> img"));
    
    function fillPublications(jsonArr){
    	//let imgArr = [];
    	let publications = document.getElementById("content");
    	for(let i = 0; i < jsonArr.length; i++){
    		//let div = document.createElement("div");
    		//publications.appendChild(div);
    		//$(div).attr("class", "image");
    		let img = document.createElement("img");
    		publications.appendChild(img);
    	    $(img).attr("class", "content-img");
    	    let src = "/images/" + jsonArr[i].link + ".jpg";
    	    $(img).attr("src", src);
    	    
    	    //imgArr.push(img);
    		
    	    $(img).click(function (){
    	    	openImage(src);
    	    });
    	}
    	//return imgArr;
    }
});