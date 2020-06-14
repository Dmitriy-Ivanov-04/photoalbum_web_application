function openImage (src, description, date, id, accesLevel) {
	document.getElementById("photo-id").innerHTML = id;
	let currentImage = $("#lightbox").find(".lightboxImage");
	$(currentImage).attr("src", src);
	document.getElementById("descriptionP").innerHTML = description;
	document.getElementById("postDate").innerHTML = date;
	getTagsByPhoto(id);
	getCommentsByPhoto(id);
	getMarksByPhoto(id);
	if(accesLevel != 0)
		$("#copyButton").hide();
	$("#lightbox").fadeIn(100);
}
    
$(document).ready(function () {
	$("#lightbox").hide();
	//$("#copyButton").hide();
    $("#overlay").click(function () {
        $("#lightbox").fadeOut(100);
    });
    /*$("image-window").mouseenter(function(){
    	$("#copyButton").fadeIn(100);
    });
    $("image-window").mouseleave(function(){
    	$("#copyButton").fadeOut(100);
    });*/
});