$.SaveForm = function(options) {
	var defaults = {
		url : "",
		param : {},
		type : "post",
		dataType : "json",
		contentType : 'application/json',
		success : null,
		close : true
	};
	var options = $.extend(defaults, options);
	dialogLoading(true);
	window.setTimeout(function() {
		$.ajax({
			url : options.url,
			data : JSON.stringify(options.param),
			type : options.type,
			dataType : options.dataType,
			contentType : options.contentType,
			success : function(data) {
				if (data.code == '500') {
					dialogAlert(data.msg, 'error');
				} else if (data.code == '0') {
					options.success(data);
					dialogMsg(data.msg, 'success');
					if (options.close == true) {
						dialogClose();
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				dialogLoading(false);
                if(textStatus=="parsererror"){
                    top.layer.open({
                        title: '系统提示',
                        area: '338px',
                        icon: 3,
                        move: false,
                        anim: -1,
                        isOutAnim: false,
                        content: '注：登录超时,请稍后重新登录.',
                        btn: ['立即退出'],
                        btnAlign: 'c',
                        yes: function(){
                            toUrl('sys/logout');
                        }
                    });
                    setTimeout(function(){
                        toUrl("sys/logout");
                    }, 2000);
                } else if(textStatus=="error"){
                    dialogMsg("请求超时，请稍候重试...", "error");
                } else {
                    dialogMsg(errorThrown, 'error');
				}
			},
			beforeSend : function() {
				dialogLoading(true);
			},
			complete : function() {
				dialogLoading(false);
			}
		});
	}, 500);
}

$.RemoveForm = function(options) {
	var defaults = {
		msg : "注：您确定要删除吗？该操作将无法恢复",
		url : "",
		param : [],
		type : "post",
		dataType : "json",
		contentType : 'application/json',
		success : null
	};
	var options = $.extend(defaults, options);
	dialogConfirm(options.msg, function() {
		dialogLoading(true);
		window.setTimeout(function() {
			var postdata = options.param;
			$.ajax({
				url : options.url,
				data : JSON.stringify(postdata),
				type : options.type,
				dataType : options.dataType,
				contentType : options.contentType,
				success : function(data) {
					if (data.code == '500') {
						dialogAlert(data.msg, 'error');
					} else if (data.code == '0') {
						dialogMsg(data.msg, 'success');
						options.success(data);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
                    dialogLoading(false);
                    if(textStatus=="parsererror"){
                        top.layer.open({
                            title: '系统提示',
                            area: '338px',
                            icon: 3,
                            move: false,
                            anim: -1,
                            isOutAnim: false,
                            content: '注：登录超时,请稍后重新登录.',
                            btn: ['立即退出'],
                            btnAlign: 'c',
                            yes: function(){
                                toUrl('sys/logout');
                            }
                        });
                        setTimeout(function(){
                            toUrl("sys/logout");
                        }, 2000);
                    } else if(textStatus=="error"){
                        dialogMsg("请求超时，请稍候重试...", "error");
                    } else {
                        dialogMsg(errorThrown, 'error');
                    }
                },
				beforeSend : function() {
					dialogLoading(true);
				},
				complete : function() {
					dialogLoading(false);
				}
			});
		}, 500);
	});
}

$.SetForm = function(options) {
	var defaults = {
		url : "",
		param : [],
		type : "post",
		dataType : "json",
		contentType : 'application/json',
		success : null
	};
	var options = $.extend(defaults, options);
	$.ajax({
		url : options.url,
		data : JSON.stringify(options.param),
		type : options.type,
		dataType : options.dataType,
		contentType : options.contentType,
		success : function(data) {
			if (data.code == '500') {
				dialogAlert(data.msg, 'error');
			} else if (data.code == '0') {
				options.success(data.rows);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
            dialogLoading(false);
            if(textStatus=="parsererror"){
                top.layer.open({
                    title: '系统提示',
                    area: '338px',
                    icon: 3,
                    move: false,
                    anim: -1,
                    isOutAnim: false,
                    content: '注：登录超时,请稍后重新登录.',
                    btn: ['立即退出'],
                    btnAlign: 'c',
                    yes: function(){
                        toUrl('sys/logout');
                    }
                });
                setTimeout(function(){
                    toUrl("sys/logout");
                }, 2000);
            } else if(textStatus=="error"){
                dialogMsg("请求超时，请稍候重试...", "error");
            } else {
                dialogMsg(errorThrown, 'error');
            }
		},
		beforeSend : function() {
			dialogLoading(true);
		},
		complete : function() {
			dialogLoading(false);
		}
	});
}

$.ConfirmForm = function(options) {
	var defaults = {
		msg : "您确定要保存当前数据项修改操作吗？",
		url : "",
		param : {},
		type : "post",
		dataType : "json",
		contentType : 'application/json',
		success : null,
		close : true
	};
	var options = $.extend(defaults, options);
	dialogConfirm(options.msg, function() {
		$.SaveForm(options);
	});
}

$.ConfirmAjax = function(options) {
	var defaults = {
		msg : "您确定要保存当前操作结果吗？",
		url : "",
		param : {},
		type : "post",
		dataType : "json",
		contentType : options.contentType,
		success : null,
		close : true
	};
	var options = $.extend(defaults, options);
	dialogConfirm(options.msg, function() {
		dialogLoading(true);
		window.setTimeout(function() {
			var postdata = options.param;
			$.ajax({
				url : options.url,
				data : JSON.stringify(postdata),
				type : options.type,
				dataType : options.dataType,
				contentType : options.contentType,
				success : function(data) {
					if (data.code == '500') {
						dialogAlert(data.msg, 'error');
					} else if (data.code == '0') {
						dialogMsg(data.msg, 'success');
						options.success(data);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
                    dialogLoading(false);
                    if(textStatus=="parsererror"){
                        top.layer.open({
                            title: '系统提示',
                            area: '338px',
                            icon: 3,
                            move: false,
                            anim: -1,
                            isOutAnim: false,
                            content: '注：登录超时,请稍后重新登录.',
                            btn: ['立即退出'],
                            btnAlign: 'c',
                            yes: function(){
                                toUrl('sys/logout');
                            }
                        });
                        setTimeout(function(){
                            toUrl("sys/logout");
                        }, 2000);
                    } else if(textStatus=="error"){
                        dialogMsg("请求超时，请稍候重试...", "error");
                    } else {
                        dialogMsg(errorThrown, 'error');
                    }
				},
				beforeSend : function() {
					dialogLoading(true);
				},
				complete : function() {
					dialogLoading(false);
				}
			});
		}, 500);
	});
}

/**
 * 上传文件
 */
$.UpLoadForm = function(options) {
		var defaults = {
			demoListView : '',
			elem : '',
			url : '',
			accept : 'file',
			exts : '',
			acceptMime : '',
			data : '',
			/*data:{
			  	fileLevel: function(){
					return $('#fileLevel').val();
				}
			},*/
			multiple : true,
			auto : false,
			bindAction : '',
			
		};
		var options = $.extend(defaults, options);
	dialogLoading(true);
	window.setTimeout(function() {
		var postdata = options.param;
		layui.use(['form','upload'], function(){
			var $ = layui.jquery,
			upload = layui.upload;
			//多文件列表示例
			var demoListView = $(options.demoListView),
			uploadListIns = upload.render({
			elem: options.elem,
			url: options.url,
			accept: options.accept,
			exts: options.exts, //表示允许上传的图片格式
			/* 规定打开文件选择框时，筛选出的文件类型，值为用逗号隔开的 MIME 类型列表。 */
			acceptMime: options.acceptMime,
			before : function(options) {
				// 返回的参数item，即为当前的input DOM对象
				data : options.data;
				console.log('文件上传中');
			},
			success : function(res) {
				layer.msg(res.msg);
			},
			/* 请求上传接口的额外参数。如：data: {id: 'xxx'} 从 layui 2.2.6 开始，支持动态值，如: */
			data : options.data,//可放扩展数据  key-value
			// 设置可多文件上传
			multiple : options.multiple,
			// 设置非自动上传
			auto: options.auto,
			// 设置上传按钮
			bindAction : options.bindAction,
			choose : function(obj) {
				var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
				//读取本地文件
				obj.preview(function(index, file, result) {
					var tr = $([ '<tr id="upload-' + index + '">',
						'<td>' + file.name + '</td>',
						'<td>' + (file.size / 1014).toFixed(1) + 'kb</td>',
						'<td>等待上传</td>',
						'<td>',
						'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>',
						'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>',
						'</td>',
						'</tr>' ].join(''));

					//单个重传
					tr.find('.demo-reload').on('click', function() {
						obj.upload(index, file);
					});

					//删除
					tr.find('.demo-delete').on('click', function() {
						delete files[index]; //删除对应的文件
						tr.remove();
						uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
					});

					demoListView.append(tr);
				});
			},

			// 将信息提交到后台接口，返回有信息时表示成功
			done : function(res, index, upload) {
				if (res.code == 0) //上传成功
					var tr = demoListView.find('tr#upload-' + index),
						tds = tr.children();
				tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
				tds.eq(3).html(''); //清空操作
				return delete this.files[index]; //删除文件队列已经上传成功的文件

			}, //code为后台传回来的数据，具体多少自己定，

			//后台只能传回json格式数据，不然会走error函数；

			error : function(index, upload) {}
		});
		});
	/*$.ajax({
		url : options.url,
		data : JSON.stringify(options.param),
		type : options.type,
		async:  options.async,
		cache:  options.cache,
		contentType:  options.contentType,
		processData:  options.processData,
		//dataType : options.dataType,
		success : function(data) {
			if (data.code == '500') {
				dialogAlert(data.msg, 'error');
			} else if (data.code == '0') {
				options.success(data);
				dialogMsg(data.msg, 'success');
				if (options.close == true) {
					dialogClose();
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			dialogLoading(false);
                if(textStatus=="parsererror"){
                    top.layer.open({
                        title: '系统提示',
                        area: '338px',
                        icon: 3,
                        move: false,
                        anim: -1,
                        isOutAnim: false,
                        content: '注：登录超时,请稍后重新登录.',
                        btn: ['立即退出'],
                        btnAlign: 'c',
                        yes: function(){
                            toUrl('sys/logout');
                        }
                    });
                    setTimeout(function(){
                        toUrl("sys/logout");
                    }, 2000);
                } else if(textStatus=="error"){
                    dialogMsg("请求超时，请稍候重试...", "error");
                } else {
                    dialogMsg(errorThrown, 'error');
			}
		},
		beforeSend : function() {
			dialogLoading(true);
		},
		complete : function() {
			dialogLoading(false);
		}
	});
	}, 500);*/
	});
}