function imageAlign(imageList, maxWidth, number){
	let divider = 1;
	let number1 = number;
	if(number > 3)
		divider = 2;
	for(let i = 0; i < imageList.length; i += number1){
		let generalWidth = 0;
		number1 = number;
		if(imageList.length - i < number1)
			number1 = imageList.length - i;
		if(number1 > 1){
			for(let j = 0; j < number1+1; j++)
				generalWidth += $(imageList).eq(i+j).width();
			if(generalWidth/divider <= maxWidth)
				number1++;
			//console.log(number1);
			//console.log(maxWidth);
			//console.log(generalWidth);
			let ratio = [];
			let generalRatio = 0;
			for(let j = 0; j < number1; j++){
				ratio[j] = ($(imageList).eq(i).height() * $(imageList).eq(i+j).width()) / ($(imageList).eq(i).width() * $(imageList).eq(i+j).height());
				generalRatio += ratio[j];
			}
			let margins = 1 - 0.023*(number1+1);
			let availableWidth = maxWidth*margins;
			for(let j = 0; j < number1; j++){
				$(imageList).eq(i+j).css("width", availableWidth/generalRatio*ratio[j]);
			}
		}
	}
}