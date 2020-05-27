$(document).ready(function () {
    postList = $("#content").find("> #post-wrapper");
	for(let i = 0; i < $(postList).length; i++){
		//$(postList).eq(i).find("#addComment").hide();
		commentList = $(postList).eq(i).find("#post-comments").find("> #comment");
		if($(commentList).length <= 2){
			$(postList).eq(i).find("#read-full-wrapper").hide();
		}else{
			for(let j = 2; j < $(commentList).length; j++){
				$(commentList).eq(j).hide();
			}
		}
	}
	$("#read-full-wrapper").click(function(){
		commentList = $(this).parent().find("#post-comments").find("> #comment");
		console.log(commentList);
		for(let i = 2; i < $(commentList).length; i++){
			$(commentList).eq(i).show();
		}
		$(this).hide();
	});
	var circleList;
	$("#content").find("> #post-wrapper").find("#post-rating").mouseenter(function(){
		circleList = $(this).find("> #rating-circle");
	});
	$("#content").find("> #post-wrapper").find("#post-rating").find("> #rating-circle").mouseenter(function(){
		let index = $(this).index();
		//circleList = $(this).parent().find("> #rating-circle");
		for(let i = 0; i < $(circleList).length; i++){
			if($(circleList).eq(i).index() <= index)
				$(circleList).eq(i).css("background", "#274b69");
			else
				$(circleList).eq(i).css("background", "#838383");
		}		
	});
	/*$("#content").find("> #post-wrapper").find("#post-rating").find("> #rating-circle").mouseleave(function(){
		console.log("leave");
		circleList = $(this).parent().find("> #rating-circle");
		for(let i = 0; i < $(circleList).length; i++){
			
		}		
	});*/
	$("#content").find("> #post-wrapper").find("#post-rating").find("> #rating-circle").click(function(){
		let index = $(this).index();
		//circleList = $(this).parent().find("> #rating-circle");
		for(let i = 0; i < $(circleList).length; i++){
			if($(circleList).eq(i).index() <= index)
				$(circleList).eq(i).css("backgroundColor", "#274b69");
			else
				$(circleList).eq(i).css("backgroundColor", "#838383");
		}		
	});
});