$(function (){
	$('.rigthWrap').css({"height": document.body.scrollHeight});

	$('.processWrap, .functionWrap').removeClass('hide');
	if($('.rigthWrap').hasClass('width-extend')){
		$('.processWrap, .functionWrap').addClass('hide');
		$('.fa-angle-double-left').show().siblings().removeClass('show');
		$('.rigthWrap').removeClass('width-extend').addClass('width-shrink');
	}else {
		$('.fa-angle-double-right').addClass('show').siblings().hide();
		$('.rigthWrap').removeClass('width-shrink').addClass('width-extend');
	}
	
	
	/**
	 * 滚动条层初始化
	 */
	$(".full-height-scroll").slimScroll({
		height : "100%"
	});
});