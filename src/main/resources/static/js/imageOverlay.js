//$(document).ready(function () {
	//let index = 0;

    function openImage (src) {
        //index = $(this).index();
		//console.log(index);
		let currentImage = $("#lightbox").find(".lightboxImage");
        $(currentImage).attr("src", src);
        $("#lightbox").fadeIn(100);
    }
    
$(document).ready(function () {
	$("#lightbox").hide(); //перенести в стили или нет
    $("#overlay").click(function () {
    	console.log(666);
        $("#lightbox").fadeOut(100);
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