function ajaxAddFriend() {
	var nick = document.getElementById("nick").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
    xhr.open('POST', '/ajax/add-friend', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    xhr.send("n=" + nick);
    //xhr.send();
}