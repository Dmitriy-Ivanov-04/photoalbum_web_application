$(document).ready(function () {
	$("#copyButton").click(function(){
        copyPhoto(Number.parseInt(document.getElementById("photo-id").innerHTML));
        $(this).fadeOut(100);
    });
	
	$("#deleteButton").click(function(){
        deletePhoto(Number.parseInt(document.getElementById("photo-id").innerHTML));
        $(this).fadeOut(100);
    });
});

function copyPhoto(photoId) {
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/ajax/photos/copy', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    xhr.send("id=" + photoId);
}

function deletePhoto(photoId) {
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/ajax/photos/delete', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    xhr.send("id=" + photoId);
}