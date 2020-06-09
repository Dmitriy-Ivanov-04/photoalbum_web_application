$(document).ready(function () {
	var nick = document.getElementById("nickView").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	let groups = $("#list-wrapper").find("> div");

	for(let i = 0; i < groups.length; i++){
		groupAJAX($(groups[i]).attr("id"));
	}
	//groupAJAX("friends");
	//groupAJAX("followers");
	//groupAJAX("subscriptions");
	
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
    			$("#session").hide();	    			
    	}
    }
    xhr.send("n=" + nick);

	
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
		    		fillList(divId, JSON.parse(xhr.responseText));
		    	} else
		    		$("#" + divId).hide();
	    	}
	    }
	    xhr.send("n=" + nick);
	}
    /*
    function fillList1(divId, jsonArr){
    	let group = document.getElementById(divId);
    	for(let i = 0; i < jsonArr.length; i++){
    		$(group).find("#start").after('<div id="profile-block">'
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
        + '<hr>'
    	);
    	}
    }
    */
    function fillList(divId, jsonArr){
    	let group = document.getElementById(divId);
    	for(let i = 0; i < jsonArr.length; i++){
    		let profilePlock = document.createElement("div");
    	    group.appendChild(profilePlock);
    	    $(profilePlock).attr("id", "profile-block");
    	    
    	    let fAvatar = document.createElement("div");
    	    profilePlock.appendChild(fAvatar);
    	    $(fAvatar).attr("id", "f-avatar");
    	    let imgAvatar = document.createElement("img");
    	    fAvatar.appendChild(imgAvatar);
    	    $(imgAvatar).attr("src", "/images/avatar.png");
    	    $(imgAvatar).attr("width", "100px");
    	    $(imgAvatar).attr("height", "100px");
    	    //$(imgAvatar).attr("href", "/user/" + jsonArr[i].nickname);
    	    
    	    let nickname = document.createElement("div");
    	    profilePlock.appendChild(nickname);
    	    $(nickname).attr("id", "nick-name");
    	    let a = document.createElement("a");
    	    nickname.appendChild(a);
    	    $(a).attr("href", "/user/" + jsonArr[i].nickname);
    	    $(a).attr("class", "nick-post");
    	    let p = document.createElement("p");
    	    a.appendChild(p);
    	    let nicknameText = document.createTextNode(jsonArr[i].nickname);
    	    p.appendChild(nicknameText);
    	    let a1 = document.createElement("a");
    	    nickname.appendChild(a1);
    	    $(a1).attr("href", "/user/" + jsonArr[i].nickname);
    	    $(a1).attr("class", "name-people");
    	    let fullnameText = document.createTextNode(jsonArr[i].fullName);
    	    a1.appendChild(fullnameText);
    	    
    	    let actions = document.createElement("div");
    	    profilePlock.appendChild(actions);
    	    $(actions).attr("id", "actions");
    	    let imgActions = document.createElement("img");
    	    actions.appendChild(imgActions);
    	    $(imgActions).attr("src", "/images/cross.png");
    	    
    	    let hr = document.createElement("hr");
    	    group.appendChild(hr);
    	    
    	    $(actions).click(function (){
    	    	changeRelationship(nicknameText);
    	    	$(this).hide()
    	    });
    	}
    }
    
    function changeRelationship(nick) {
		var xhr = new XMLHttpRequest();
		xhr.open('POST', '/ajax/add-friend', true);
	    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	    xhr.setRequestHeader(header, token);
	    xhr.send("n=" + nick.wholeText);
    }
});