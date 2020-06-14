$(document).ready(function () {
	let rating = 0;
	let ratingMenu = $("#rating-numbers").find("> a");
	$(ratingMenu).click(function(){
		let index = $(this).index();
		rating = index + 1;
		$("#ratingImage").attr("src", "/images/" + rating + ".png");
	});
	let searchInput = document.getElementById("search");
	searchInput.addEventListener('keypress', function (e) {
	    if (e.key === 'Enter'/* && $(searchInput).val() != ""*/) {
	      search($(searchInput).val(), rating, $("#dateSelector").val());
	      //console.log($("#dateSelector").val().split("/"));
	    }
	});
});

function search(query, rating, date){
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/search', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0){
    			console.log(JSON.parse(xhr.responseText));
    		}
    	}
    }
    xhr.send("q=" + query + "&r=" + rating + "&d=" + date);
}