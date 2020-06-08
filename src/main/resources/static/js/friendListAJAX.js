$(document).ready(function () {
	var nick = document.getElementById("nickView").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	//let groups = $("#list-wrapper").find("> div");
	groupAJAX("friends");
	groupAJAX("followers");
	groupAJAX("subscriptions");

	
	function groupAJAX(divId){
		var xhr = new XMLHttpRequest();
		xhr.open('POST', '/ajax/friend-list/' + divId, true);
	    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	    xhr.setRequestHeader(header, token);
	    
	    xhr.onreadystatechange = function() {
	    	if (xhr.readyState != 4) 
	    		return;
	    	if (xhr.status != 200) {
	    		alert(xhr.status + ': ' + xhr.statusText);
	    	} else {
		    	if(xhr.responseText != 0){
		    		console.log("response " + divId);
		    		console.log(JSON.parse(xhr.responseText));
		    		fillList(divId, JSON.parse(xhr.responseText));
		    	} else
		    		$("#" + divId).hide();
	    	}
	    }
	    xhr.send("n=" + nick);
	}
    
    function fillList(divId, jsonArr){
    	let group = document.getElementById(divId);
    	for(let i = 0; i < jsonArr.length; i++){
    		group.innerHTML += '<div id="profile-block">'
	        + '<div id="f-avatar">'
	        	+ '<img src="/images/avatar.png" alt="" width="100px" height="100px">' //th:src="@{/images/avatar.png}"  //добавить ссылку на аву, когда сделаем 
	        + '</div>'
	        + '<div id="nick-name">'
		        + '<a href="#" class="nick-post"><p>' + jsonArr[i].nickname + '</p></a>'
		        + '<a href="#" class="name-people">' + jsonArr[i].fullName + '</a>'
	        + '</div>'
	        + '<div id="actions">'
		        + '<img src="/images/cross.png" alt="">'//th:src="@{/images/cross.png}"
	        + '</div>'
        + '</div>'
        + '<hr>';
    	}
    }
});