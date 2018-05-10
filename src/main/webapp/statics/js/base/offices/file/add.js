/**
 * 新增-文件管理js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		levelList:{},
		file:{
			fileName: null,
			fileLevelId: '',
			fileLevelName: '',
			status: 1
			/*fileName,
			filePath,
			userNameCreate,
			userNameUpdate,
			fileLevel,
			status,
			gmtCreate,
			gmtModified
			*/
		},
		
		level:{
			dataId: 0,
			parentId: 0,
			parentName: null,
			name: null,
			status: 1,
			layer: 1
		}
		
		
		/*roleList:{},
		user:{
			orgId: 0,
			orgName: null,
			status: 1,
			roleIdList:[]
		}*/
	},
	methods : {
		/*getLevelList: function(){
			$.get('../../sys/offices/saveView?_' + $.now(), function(r){
				vm.levelList = r.rows;
            });
		},*/
		
		/**
		 * 获取文件级别信息
		 */
		/*initUpLoad: function(){
            $.UpLoadForm({
                url: '../../sys/offices/save?_' + $.now(),
		    	//多文件列表示例
				demoListView : '#demoList',
				elem: '#testList',
				accept: 'file',
				exts: 'jpg|png|jpeg|doc|zip|rar|7z', //表示允许上传的图片格式
				 规定打开文件选择框时，筛选出的文件类型，值为用逗号隔开的 MIME 类型列表。 
				//acceptMime: 'image/jpg, image/png',
				//data: vm.file,
				data:{
				  	fileLevel: function(){
						return vm.file.fileLevel;
					}
				},
				 请求上传接口的额外参数。如：data: {id: 'xxx'} 从 layui 2.2.6 开始，支持动态值，如: 
				data:{
				  	fileLevel: function(){
						return $('#fileLevel').val();
					}
				  },   //可放扩展数据  key-value
				// 设置可多文件上传
				multiple: true,
				// 设置非自动上传
				auto: false,
				// 设置上传按钮
				bindAction: '#testListAction',
            });
		},*/
		levelTree: function() {
			dialogOpen({
				id: 'layerLevelTree',
				title: '选择文件级别',
		        url: 'base/offices/tree.html?_' + $.now(),
		        scroll : true,
		        width: "300px",
		        height: "450px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    })
		},
		acceptClick: function() {
			 if (!$('#form').Validform()) {
	                return false;
	            }
			/*$.UpLoadForm({
                url: '../../sys/offices/save?_' + $.now(),
		    	//多文件列表示例
				demoListView : '#demoList',
				elem: '#testList',
				accept: 'file',
				exts: 'jpg|png|jpeg|doc|zip|rar|7z', //表示允许上传的图片格式
				 规定打开文件选择框时，筛选出的文件类型，值为用逗号隔开的 MIME 类型列表。 
				//acceptMime: 'image/jpg, image/png',
				data: fileLevel,
				data:{
				  	fileLevel: function(){
						return $('#fileLevel').val();
					}
				},
				 请求上传接口的额外参数。如：data: {id: 'xxx'} 从 layui 2.2.6 开始，支持动态值，如: 
				data:{
				  	fileLevel: function(){
						return $('#fileLevel').val();
					}
				  },   //可放扩展数据  key-value
				// 设置可多文件上传
				multiple: true,
				// 设置非自动上传
				auto: false,
				// 设置上传按钮
				bindAction: '#testListAction',
            });*/
            /*var roles = doublebox.getSelectedOptions();
            if(isNullOrEmpty(roles)) {
                dialogMsg('请先选择角色！');
                return false;
            }
            if (!$('#form').Validform()) {
                return false;
            }
            vm.user.roleIdList = [];
            $.each(roles.split(','), function(idx, item){
                vm.user.roleIdList.push(parseInt(item));
            });
            */
		    $.SaveForm({
		    	url: '../../sys/offices/save?_' + $.now(),
		    	param: vm.user,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
			
		}
	},
	created : function() {
		//this.getLevelList();
		/*$(document).ready(function(){ 
			$('#fileLevel').append("<option value=''></option>");
			$('#fileLevel').append("<option value='0'>绝密</option>"); 
			$('#fileLevel').append("<option value='1'>机密</option>"); 
			$('#fileLevel').append("<option value='2'>秘密</option>"); 
			$('#fileLevel').append("<option value='3'>敏感</option>"); 
			$('#fileLevel').append("<option value='4' selected=''>公开</option>");
		});*/
		//this.initUpLoad();
	}
})
