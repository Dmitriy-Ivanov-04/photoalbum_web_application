$(document).ready(function () {	
	var nick = document.getElementById("nick").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/albums', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0){
    			fillAlbums(JSON.parse(xhr.responseText));
    		}
    		else{
    			let albums = document.getElementById("content-album");
    	    	albums.innerHTML = "";
    		}
    	}
    }
    xhr.send("n=" + nick);
    
    function fillAlbums(jsonArr){
    	let albums = document.getElementById("content-album");
    	albums.innerHTML = "";
    	let publications = document.getElementById("content-album");
    	let postLine;
    	for(let i = 0; i < jsonArr.length; i++){
    		if(i%3 == 0){
    	    	postLine = document.createElement("div");
    			publications.appendChild(postLine);
    	    	$(postLine).attr("id", "post-line");
    		}
	    	let albumWrapper = document.createElement("div");
	    	postLine.appendChild(albumWrapper);
	    	$(albumWrapper).attr("class", "album-wrapper");
	    	$(albumWrapper).attr("id", "album-block");
	    	if(i%3 == 1){
	    		$(albumWrapper).css("margin", "0 20px");
	    	}
	    		
	    	let albumName = document.createElement("span");
	    	$(albumName).attr("class", "album-name");
	    	let albumNameText = document.createTextNode(jsonArr[i].name)
	    	albumName.appendChild(albumNameText);
	    	albumWrapper.appendChild(albumName);
	    		
	    	$(albumWrapper).click(function() {
	    		let oneAlbumTab = document.getElementById("one-album-tab");
	    		oneAlbumTab.innerHTML = "";
	    		let albumNameText1 = document.createTextNode(jsonArr[i].name)
	    		oneAlbumTab.appendChild(albumNameText1);
	    		
	    		var nick = document.getElementById("nick").innerHTML;
	    		xhr = new XMLHttpRequest();
	    		xhr.open('POST', '/ajax/photos/' + jsonArr[i].id, true);
	    	    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	    	    xhr.setRequestHeader(header, token);
	    	    
	    	    xhr.onreadystatechange = function() {
	    	    	if (xhr.readyState != 4) 
	    	    		return;
	    	    	if (xhr.status != 200) {
	    	    		alert(xhr.status + ': ' + xhr.statusText);
	    	    	} else {
	    	    		if(xhr.responseText != 0){
	    	    			fillContentDiv(JSON.parse(xhr.responseText), "content-one-album");
	    	    		} else{
	    	    			let contentDiv = document.getElementById("content-one-album");
	    	    			contentDiv.innerHTML = "";
	    	    		}
	    	    	}
	    	    }
	    	    xhr.send();
	    		
	            $("#one-album-tab").show();
	    		$(".tab").removeClass("active");
	    		$("#one-album-tab").addClass("active");
	    		$(".publications").hide();
	    		$(".albums").hide();
	    		$(".one-album").show();
	    		let imageList = $(".one-album").find("> img");
	    		imageAlign(imageList, $(".one-album").width(), 2);
	        });
    	}
    }
});