$(document).ready(function () {
    $(".albums").hide();
	$(".one-album").hide();
	$("#one-album-tab").hide();
	$(".tab").on("click", function(){
		$(".tab").removeClass("active");
		$(this).addClass("active");
		console.log($(this).index());
		if($(this).index() == 0){
			$(".albums").hide();
			$(".one-album").hide();
			$(".publications").show();
		}
		if($(this).index() == 1){
			$(".publications").hide();
			$(".one-album").hide();
			$(".albums").show();
		}
		if($(this).index() == 2){
			$(".publications").hide();
			$(".albums").hide();
			$(".one-album").show();
		}
	});
	$(".album-img").click(function() {
        $("#one-album-tab").show();
		$(".tab").removeClass("active");
		$("#one-album-tab").addClass("active");
		$(".publications").hide();
		$(".albums").hide();
		$(".one-album").show();
		let imageList = $(".one-album").find("> img");
		imageAlign(imageList, $(".one-album").width(), 2);
    });
});