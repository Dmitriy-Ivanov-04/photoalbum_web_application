$(document).ready(function () {
	let index = 0;

    $(".content-img").click(function() {
        index = $(this).index();
		//console.log(index);
		let currentImage = $("#overlay").find("#open-img");
        $(currentImage).attr("src", $(this).attr("src"));
		if($(currentImage).width() > 600)
			$("#image-post").css("width", $(currentImage).width());
		if($(currentImage).height() > 450)
			$("#image-post").css("height", $(currentImage).height());
        $("#overlay").fadeIn(100);
    });
    $("#overlay").click(function() {
        $("#overlay").fadeOut(100);
    });
});
/*	$(".next").click(function(){
        if(index + 1 < imageList.length){
            index++;
        }else{
            index = 0;
        }
        $(".lightbox").find("img").attr("src", $(imageList).eq(index).attr("src"));
    });

    $(".prev").click(function(){
        if(index > 0){
            index--;
        }else{
            index = imageList.length - 1;
        }
        $(".lightbox").find("img").attr("src", $(imageList).eq(index).attr("src"));
    });*/