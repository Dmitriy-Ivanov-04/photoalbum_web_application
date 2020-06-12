function copyPhoto(photoId) {
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/ajax/photos/copy', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);

    xhr.send("id=" + photoId);
}