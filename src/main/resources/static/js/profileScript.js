$(document).ready(function () {
    /* images align*/
	let imageList = $(".publications").find("> img");
	imageAlign(imageList, $(".publications").width(), 2);
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
	let flag = 0;
	$(".add-friend").click(function() {
		$(".add-friend-plus").queue('fx', []);
		if(flag == 0){
			$(".add-friend-plus").animate(
				{rotate: 45},{
					duration: 300,
					step: function(now) {
						$(this).css({ transform: "rotate(" + now + "deg)" });
					}
				}
			);
			$(".add-friend-text").text("Удалить из друзей");
			flag = 1;
		}
		else{
			$(".add-friend-plus").animate(
				{rotate: 0},{
					duration: 300,
					step: function(now) {
						$(this).css({ transform: "rotate(" + now + "deg)" });
					}
				}
			);
			$(".add-friend-text").text("Добавить в друзья");
			flag = 0;
		}
	});
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