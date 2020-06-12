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
    			fillMarks(JSON.parse(xhr.responseText), photoId);
    		else{
	    		let rating = document.getElementById("rating");
	    		rating.innerHTML = "";
	    		paintRatingCircles(0, photoId);
    		}
    		addClickEvent(photoId);
    	}
    }
    xhr.send("id=" + photoId);
}

function fillMarks(jsonArr, photoId){
	let rating = document.getElementById("rating");
	rating.innerHTML = "";
	let generalRating = 0;
   	for(let i = 0; i < jsonArr.length; i++){
   		generalRating += jsonArr[i].value;
   	}
   	let avgRating = (generalRating/jsonArr.length).toFixed(1);
   	let avgRatingText = document.createTextNode(avgRating);
   	rating.appendChild(avgRatingText);
   	paintRatingCircles(avgRating, photoId);
}

function paintRatingCircles(avgRating, photoId){
	circleList = $("#rating-post").find("> #rating-circle");
   	for(let i = 0; i < circleList.length; i++){
   		if(i+1 <= avgRating)
   			$(circleList[i]).css("background", "#274b69");
   		else
			$(circleList).eq(i).css("background", "#838383");
   	}
}

function addClickEvent(photoId){
	$(circleList).click(function(){
		let index = $(this).index();
		/*for(let i = 0; i < $(circleList).length; i++){
			if($(circleList).eq(i).index() <= index)
				$(circleList).eq(i).css("background", "#274b69");
			else
				$(circleList).eq(i).css("background", "#838383");
		}*/
		rate(index+1, photoId);
	});
}

function rate(mark, photoId){
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/marks/rate', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0)
    			fillMarks(JSON.parse(xhr.responseText), photoId);
    		else{
	    		let rating = document.getElementById("rating");
	    		rating.innerHTML = "";
	    		paintRatingCircles(0, photoId);
    		}
    	}
    }
    xhr.send("m=" + mark + "&id=" + photoId);
}
