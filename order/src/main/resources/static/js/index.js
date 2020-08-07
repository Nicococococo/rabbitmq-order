$("#goTop").on('click', function() {
	let myDocument = document.documentElement || document.body;
	let h = myDocument.scrollTop;
	if(h <=100) return;
	h = h - 10;
	myDocument.scrollTop = h + 'px';

})
