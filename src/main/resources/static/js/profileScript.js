$(document).ready(function () {
    /* images align*/
	//let imageList = $(".publications").find("> img");
	//imageAlign(imageList, $(".publications").width(), 2);
    /*plus drive*/
    $(".add-friend-text").hide();	
	$(".add-friend").mouseenter(function(){
		//$(".add-friend-text").queue('fx', []);
		$(".add-friend-text").delay(500).fadeIn(0);
	})
	$(".add-friend").mouseleave(function(){
		//$(".add-photo-text").queue('fx', []);
		$(".add-friend-text").fadeOut(0);
	})
    /*////////////////////*/
    /*photo drive*/
    $(".add-photo-text").hide();	
	$(".add-photo").mouseenter(function(){
		//$(".add-photo-text").queue('fx', []);
		$(".add-photo-text").delay(500).fadeIn(0);
	})
	$(".add-photo").mouseleave(function(){
		//$(".add-photo-text").queue('fx', []);
		$(".add-photo-text").fadeOut(0);
	})
});