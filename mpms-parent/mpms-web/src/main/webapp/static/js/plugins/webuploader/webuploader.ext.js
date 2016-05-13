/**
 * @author Sunset
 * @date 2016/02/24
 * 
 */
var webuploaderService = (function(){
	var webuploaderService = {},
		loadingIndex;
	
	/**
	 * @param _ {}
	 *            not null
	 * @param _.fileNumLimit
	 *            文件限制数量 default : 1
	 * @param _.fileSizeLimit
	 *            文件大小 default : 200MB
	 * @param _.method
	 *            请求方式 default : POST
	 * @param _.server
	 *            服务器地址 not null
	 * @param _.pick
	 *            选取文件的元素 not null
	 * @param _.resize
	 *            压缩 default : false
	 * @param _.accept
	 *            accept extensions default : jpg,bmp,png,pdf,zip
	 * @param _.formData
	 *            form data default : null
	 * @param _.success
	 *            upload success callback function not null
	 * 
	 */
	webuploaderService.init = function(_) {
		var uploader = WebUploader.create({
	    	// 自动上传
	        auto: false,
	        // swf文件路径
	        swf: 'Uploader.swf',
	        
	        // 文件总数
	        fileNumLimit: _[ "fileNumLimit" ] || 1,
	        
	        // 200 M上传文件总大小
	        fileSizeLimit: _[ "fileSizeLimit" ] || 200 * 1024 * 1024,    
	        
	        // 文件上传方式
	        method: _[ "method" ] || "POST",

	        // 文件接收服务端。
	        server: _[ "server" ],

	        // 选择文件的按钮。可选。
	        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	        pick: _[ "pick" ],

	        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	        resize: _[ "resize" ] || false,
	        
	        // 上传文件类型
	        accept: _[ "accept" ] || {
	            title: 'Applications',
	            extensions: 'jpg,bmp,png,pdf,zip,xlsx,xls,doc,docx,pptx,ppt',
				mimeTypes: 'image/jpg,image/bmp,image/png,application/pdf,application/zip,application/doc,application/docx,application/xls,application/xlsx,application/pptx,application/ppt'
	        }
	    });
		
		/**
		 * 选择文件
		 */
		uploader.on( 'fileQueued', function( file ) {
			$('#thelist, .uploader-list').show().children('div').remove();
			
			$('#thelist, .uploader-list').append( '<div id="' + file.id + '" class="item">' +
	                '<h4 class="info"> 已选择文件 《' + file.name + '》</h4>' +
	            '</div>' );
			// 设置参数
	    	uploader.options.formData = _[ "formData" ];
	    	// 上传
	    	uploader.upload();
	    });
		
		
		// 文件上传过程中创建进度条实时显示。
	   uploader.on('uploadProgress', function(file, percentage) {
		   var $li = $('.uploader-list div'), //$('#' + file.id), 
			$percent = $li.find('.progress .progress-bar');

	        // 避免重复创建
	        if ( !$percent.length ) {
	            $percent = $('<div class="progress progress-striped active">' +
	              '<div class="progress-bar" role="progressbar" style="width: 0%">' +
	              '</div>' +
	            '</div>').appendTo( $li ).find('.progress-bar');
	        }

	        $li.find('p.state').text('上传中');

	        $percent.css( 'width', percentage * 100 + '%' );
	        
	        // 打开加载层
	        loadingIndex = layer.load(2, {
			    shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
	    });

	    uploader.on( 'uploadSuccess', function(file, response) {
	    	if ( response.code == 0 ){
	    		layer.msg('上传成功！', {icon: 6, time: 1000}, function (){
	    			// 成功的回调函数
	    			_[ "success" ]();
	    		});
	    	} else {
	    		layer.msg(response.msg, {icon: 5, time: 1000}, function (){
	    			// 成功的回调函数
	    			_[ "success" ]();
	    		});
	    	}
	        uploader.reset();
	    });

	    uploader.on( 'uploadError', function(file, reason) {
	    	layer.msg('上传出错，请重新上传', {icon: 5, time: 1000});
	        uploader.reset();
	    });

	    uploader.on( 'uploadComplete', function(file) {
	    	// 关闭加载层
			layer.close(loadingIndex);
	    	
	        uploader.reset();
	        $('#thelist, .uploader-list').hide();
	        $('.uploader-list div').find('.progress').fadeOut();
	    });
	};
	return webuploaderService;
}());

	    	
