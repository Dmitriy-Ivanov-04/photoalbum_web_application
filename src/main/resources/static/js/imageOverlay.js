function openImage (src, description, date, id) {
	let currentImage = $("#lightbox").find(".lightboxImage");
	$(currentImage).attr("src", src);
	document.getElementById("descriptionP").innerHTML = description;
	document.getElementById("postDate").innerHTML = date;
	getTagsByPhoto(id);
	getCommentsByPhoto(id);
	getMarksByPhoto(id);
	$("#lightbox").fadeIn(100);
}
    
$(document).ready(function () {
	$("#lightbox").hide(); //перенести в стили или нет
    $("#overlay").click(function () {
        $("#lightbox").fadeOut(100);
    });
});