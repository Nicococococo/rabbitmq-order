$(".mountings-item").on('click',function(e){
	// 获取当前配件下的可选列表对象
	let node =$(this).children('div').eq(2);
	// console.log(node.css('display'))
	// 点重复点时，直接隐藏；
	if(node.css('display')!=='none'){
		node.hide();
		return;
	}
	// 隐藏所有
	let arrNode = $(".mountings-check-list");
	arrNode.each(function(){
		$(this).hide();
	})
	// 显示点击的目标
	node.css('display') === 'none'? node.get(0).style.display='flex': node.hide();
});